package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Subjects;
import com.risingarjun.arjun.repository.SubjectsRepository;
import com.risingarjun.arjun.service.SubjectsService;
import com.risingarjun.arjun.service.dto.SubjectsDTO;
import com.risingarjun.arjun.service.mapper.SubjectsMapper;
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
import java.util.List;

import static com.risingarjun.arjun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SubjectsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class SubjectsResourceIT {

    private static final String DEFAULT_SUBJECT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_TITLE = "BBBBBBBBBB";

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Autowired
    private SubjectsMapper subjectsMapper;

    @Autowired
    private SubjectsService subjectsService;

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

    private MockMvc restSubjectsMockMvc;

    private Subjects subjects;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubjectsResource subjectsResource = new SubjectsResource(subjectsService);
        this.restSubjectsMockMvc = MockMvcBuilders.standaloneSetup(subjectsResource)
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
    public static Subjects createEntity(EntityManager em) {
        Subjects subjects = new Subjects()
            .subjectCode(DEFAULT_SUBJECT_CODE)
            .subjectTitle(DEFAULT_SUBJECT_TITLE);
        return subjects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subjects createUpdatedEntity(EntityManager em) {
        Subjects subjects = new Subjects()
            .subjectCode(UPDATED_SUBJECT_CODE)
            .subjectTitle(UPDATED_SUBJECT_TITLE);
        return subjects;
    }

    @BeforeEach
    public void initTest() {
        subjects = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubjects() throws Exception {
        int databaseSizeBeforeCreate = subjectsRepository.findAll().size();

        // Create the Subjects
        SubjectsDTO subjectsDTO = subjectsMapper.toDto(subjects);
        restSubjectsMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsDTO)))
            .andExpect(status().isCreated());

        // Validate the Subjects in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeCreate + 1);
        Subjects testSubjects = subjectsList.get(subjectsList.size() - 1);
        assertThat(testSubjects.getSubjectCode()).isEqualTo(DEFAULT_SUBJECT_CODE);
        assertThat(testSubjects.getSubjectTitle()).isEqualTo(DEFAULT_SUBJECT_TITLE);
    }

    @Test
    @Transactional
    public void createSubjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subjectsRepository.findAll().size();

        // Create the Subjects with an existing ID
        subjects.setId(1L);
        SubjectsDTO subjectsDTO = subjectsMapper.toDto(subjects);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectsMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subjects in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSubjectCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectsRepository.findAll().size();
        // set the field null
        subjects.setSubjectCode(null);

        // Create the Subjects, which fails.
        SubjectsDTO subjectsDTO = subjectsMapper.toDto(subjects);

        restSubjectsMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsDTO)))
            .andExpect(status().isBadRequest());

        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectsRepository.findAll().size();
        // set the field null
        subjects.setSubjectTitle(null);

        // Create the Subjects, which fails.
        SubjectsDTO subjectsDTO = subjectsMapper.toDto(subjects);

        restSubjectsMockMvc.perform(post("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsDTO)))
            .andExpect(status().isBadRequest());

        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubjects() throws Exception {
        // Initialize the database
        subjectsRepository.saveAndFlush(subjects);

        // Get all the subjectsList
        restSubjectsMockMvc.perform(get("/api/subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectCode").value(hasItem(DEFAULT_SUBJECT_CODE.toString())))
            .andExpect(jsonPath("$.[*].subjectTitle").value(hasItem(DEFAULT_SUBJECT_TITLE.toString())));
    }
    
    @Test
    @Transactional
    public void getSubjects() throws Exception {
        // Initialize the database
        subjectsRepository.saveAndFlush(subjects);

        // Get the subjects
        restSubjectsMockMvc.perform(get("/api/subjects/{id}", subjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subjects.getId().intValue()))
            .andExpect(jsonPath("$.subjectCode").value(DEFAULT_SUBJECT_CODE.toString()))
            .andExpect(jsonPath("$.subjectTitle").value(DEFAULT_SUBJECT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubjects() throws Exception {
        // Get the subjects
        restSubjectsMockMvc.perform(get("/api/subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubjects() throws Exception {
        // Initialize the database
        subjectsRepository.saveAndFlush(subjects);

        int databaseSizeBeforeUpdate = subjectsRepository.findAll().size();

        // Update the subjects
        Subjects updatedSubjects = subjectsRepository.findById(subjects.getId()).get();
        // Disconnect from session so that the updates on updatedSubjects are not directly saved in db
        em.detach(updatedSubjects);
        updatedSubjects
            .subjectCode(UPDATED_SUBJECT_CODE)
            .subjectTitle(UPDATED_SUBJECT_TITLE);
        SubjectsDTO subjectsDTO = subjectsMapper.toDto(updatedSubjects);

        restSubjectsMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsDTO)))
            .andExpect(status().isOk());

        // Validate the Subjects in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeUpdate);
        Subjects testSubjects = subjectsList.get(subjectsList.size() - 1);
        assertThat(testSubjects.getSubjectCode()).isEqualTo(UPDATED_SUBJECT_CODE);
        assertThat(testSubjects.getSubjectTitle()).isEqualTo(UPDATED_SUBJECT_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubjects() throws Exception {
        int databaseSizeBeforeUpdate = subjectsRepository.findAll().size();

        // Create the Subjects
        SubjectsDTO subjectsDTO = subjectsMapper.toDto(subjects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectsMockMvc.perform(put("/api/subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subjects in the database
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubjects() throws Exception {
        // Initialize the database
        subjectsRepository.saveAndFlush(subjects);

        int databaseSizeBeforeDelete = subjectsRepository.findAll().size();

        // Delete the subjects
        restSubjectsMockMvc.perform(delete("/api/subjects/{id}", subjects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subjects> subjectsList = subjectsRepository.findAll();
        assertThat(subjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subjects.class);
        Subjects subjects1 = new Subjects();
        subjects1.setId(1L);
        Subjects subjects2 = new Subjects();
        subjects2.setId(subjects1.getId());
        assertThat(subjects1).isEqualTo(subjects2);
        subjects2.setId(2L);
        assertThat(subjects1).isNotEqualTo(subjects2);
        subjects1.setId(null);
        assertThat(subjects1).isNotEqualTo(subjects2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectsDTO.class);
        SubjectsDTO subjectsDTO1 = new SubjectsDTO();
        subjectsDTO1.setId(1L);
        SubjectsDTO subjectsDTO2 = new SubjectsDTO();
        assertThat(subjectsDTO1).isNotEqualTo(subjectsDTO2);
        subjectsDTO2.setId(subjectsDTO1.getId());
        assertThat(subjectsDTO1).isEqualTo(subjectsDTO2);
        subjectsDTO2.setId(2L);
        assertThat(subjectsDTO1).isNotEqualTo(subjectsDTO2);
        subjectsDTO1.setId(null);
        assertThat(subjectsDTO1).isNotEqualTo(subjectsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subjectsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subjectsMapper.fromId(null)).isNull();
    }
}
