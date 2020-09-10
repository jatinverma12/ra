package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.StudentScore;
import com.risingarjun.arjun.repository.StudentScoreRepository;
import com.risingarjun.arjun.service.StudentScoreService;
import com.risingarjun.arjun.service.dto.StudentScoreDTO;
import com.risingarjun.arjun.service.mapper.StudentScoreMapper;
import com.risingarjun.arjun.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.risingarjun.arjun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link StudentScoreResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class StudentScoreResourceIT {

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StudentScoreRepository studentScoreRepository;

    @Autowired
    private StudentScoreMapper studentScoreMapper;

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStudentScoreMockMvc;

    private StudentScore studentScore;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentScoreResource studentScoreResource = new StudentScoreResource(studentScoreService);
        this.restStudentScoreMockMvc = MockMvcBuilders.standaloneSetup(studentScoreResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentScore createEntity(EntityManager em) {
        StudentScore studentScore = new StudentScore()
            .answer(DEFAULT_ANSWER)
            .score(DEFAULT_SCORE)
            .date(DEFAULT_DATE);
        return studentScore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentScore createUpdatedEntity(EntityManager em) {
        StudentScore studentScore = new StudentScore()
            .answer(UPDATED_ANSWER)
            .score(UPDATED_SCORE)
            .date(UPDATED_DATE);
        return studentScore;
    }

    @BeforeEach
    public void initTest() {
        studentScore = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentScore() throws Exception {
        int databaseSizeBeforeCreate = studentScoreRepository.findAll().size();

        // Create the StudentScore
        StudentScoreDTO studentScoreDTO = studentScoreMapper.toDto(studentScore);
        restStudentScoreMockMvc.perform(post("/api/student-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentScoreDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentScore in the database
        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeCreate + 1);
        StudentScore testStudentScore = studentScoreList.get(studentScoreList.size() - 1);
        assertThat(testStudentScore.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testStudentScore.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testStudentScore.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createStudentScoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentScoreRepository.findAll().size();

        // Create the StudentScore with an existing ID
        studentScore.setId(1L);
        StudentScoreDTO studentScoreDTO = studentScoreMapper.toDto(studentScore);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentScoreMockMvc.perform(post("/api/student-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentScore in the database
        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentScoreRepository.findAll().size();
        // set the field null
        studentScore.setAnswer(null);

        // Create the StudentScore, which fails.
        StudentScoreDTO studentScoreDTO = studentScoreMapper.toDto(studentScore);

        restStudentScoreMockMvc.perform(post("/api/student-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentScoreDTO)))
            .andExpect(status().isBadRequest());

        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentScoreRepository.findAll().size();
        // set the field null
        studentScore.setScore(null);

        // Create the StudentScore, which fails.
        StudentScoreDTO studentScoreDTO = studentScoreMapper.toDto(studentScore);

        restStudentScoreMockMvc.perform(post("/api/student-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentScoreDTO)))
            .andExpect(status().isBadRequest());

        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentScoreRepository.findAll().size();
        // set the field null
        studentScore.setDate(null);

        // Create the StudentScore, which fails.
        StudentScoreDTO studentScoreDTO = studentScoreMapper.toDto(studentScore);

        restStudentScoreMockMvc.perform(post("/api/student-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentScoreDTO)))
            .andExpect(status().isBadRequest());

        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentScores() throws Exception {
        // Initialize the database
        studentScoreRepository.saveAndFlush(studentScore);

        // Get all the studentScoreList
        restStudentScoreMockMvc.perform(get("/api/student-scores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentScore.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentScore() throws Exception {
        // Initialize the database
        studentScoreRepository.saveAndFlush(studentScore);

        // Get the studentScore
        restStudentScoreMockMvc.perform(get("/api/student-scores/{id}", studentScore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentScore.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentScore() throws Exception {
        // Get the studentScore
        restStudentScoreMockMvc.perform(get("/api/student-scores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentScore() throws Exception {
        // Initialize the database
        studentScoreRepository.saveAndFlush(studentScore);

        int databaseSizeBeforeUpdate = studentScoreRepository.findAll().size();

        // Update the studentScore
        StudentScore updatedStudentScore = studentScoreRepository.findById(studentScore.getId()).get();
        // Disconnect from session so that the updates on updatedStudentScore are not directly saved in db
        em.detach(updatedStudentScore);
        updatedStudentScore
            .answer(UPDATED_ANSWER)
            .score(UPDATED_SCORE)
            .date(UPDATED_DATE);
        StudentScoreDTO studentScoreDTO = studentScoreMapper.toDto(updatedStudentScore);

        restStudentScoreMockMvc.perform(put("/api/student-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentScoreDTO)))
            .andExpect(status().isOk());

        // Validate the StudentScore in the database
        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeUpdate);
        StudentScore testStudentScore = studentScoreList.get(studentScoreList.size() - 1);
        assertThat(testStudentScore.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testStudentScore.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testStudentScore.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentScore() throws Exception {
        int databaseSizeBeforeUpdate = studentScoreRepository.findAll().size();

        // Create the StudentScore
        StudentScoreDTO studentScoreDTO = studentScoreMapper.toDto(studentScore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentScoreMockMvc.perform(put("/api/student-scores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentScoreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentScore in the database
        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentScore() throws Exception {
        // Initialize the database
        studentScoreRepository.saveAndFlush(studentScore);

        int databaseSizeBeforeDelete = studentScoreRepository.findAll().size();

        // Delete the studentScore
        restStudentScoreMockMvc.perform(delete("/api/student-scores/{id}", studentScore.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentScore> studentScoreList = studentScoreRepository.findAll();
        assertThat(studentScoreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentScore.class);
        StudentScore studentScore1 = new StudentScore();
        studentScore1.setId(1L);
        StudentScore studentScore2 = new StudentScore();
        studentScore2.setId(studentScore1.getId());
        assertThat(studentScore1).isEqualTo(studentScore2);
        studentScore2.setId(2L);
        assertThat(studentScore1).isNotEqualTo(studentScore2);
        studentScore1.setId(null);
        assertThat(studentScore1).isNotEqualTo(studentScore2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentScoreDTO.class);
        StudentScoreDTO studentScoreDTO1 = new StudentScoreDTO();
        studentScoreDTO1.setId(1L);
        StudentScoreDTO studentScoreDTO2 = new StudentScoreDTO();
        assertThat(studentScoreDTO1).isNotEqualTo(studentScoreDTO2);
        studentScoreDTO2.setId(studentScoreDTO1.getId());
        assertThat(studentScoreDTO1).isEqualTo(studentScoreDTO2);
        studentScoreDTO2.setId(2L);
        assertThat(studentScoreDTO1).isNotEqualTo(studentScoreDTO2);
        studentScoreDTO1.setId(null);
        assertThat(studentScoreDTO1).isNotEqualTo(studentScoreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentScoreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentScoreMapper.fromId(null)).isNull();
    }
}
