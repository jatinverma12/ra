package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Questions;
import com.risingarjun.arjun.repository.QuestionsRepository;
import com.risingarjun.arjun.service.QuestionsService;
import com.risingarjun.arjun.service.dto.QuestionsDTO;
import com.risingarjun.arjun.service.mapper.QuestionsMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.risingarjun.arjun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.risingarjun.arjun.domain.enumeration.QuestionLevel;
/**
 * Integration tests for the {@Link QuestionsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class QuestionsResourceIT {

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DIAGRAM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DIAGRAM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DIAGRAM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DIAGRAM_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_OPTION_1 = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_1 = "BBBBBBBBBB";

    private static final String DEFAULT_OPTION_2 = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_OPTION_3 = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_3 = "BBBBBBBBBB";

    private static final String DEFAULT_OPTION_4 = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_MARKS = 1;
    private static final Integer UPDATED_MAX_MARKS = 2;

    private static final Integer DEFAULT_NEGATIVE_MARKS = 1;
    private static final Integer UPDATED_NEGATIVE_MARKS = 2;

    private static final QuestionLevel DEFAULT_LEVEL = QuestionLevel.BEGINNERS;
    private static final QuestionLevel UPDATED_LEVEL = QuestionLevel.MODERATE;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private QuestionsMapper questionsMapper;

    @Autowired
    private QuestionsService questionsService;

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

    private MockMvc restQuestionsMockMvc;

    private Questions questions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionsResource questionsResource = new QuestionsResource(questionsService);
        this.restQuestionsMockMvc = MockMvcBuilders.standaloneSetup(questionsResource)
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
    public static Questions createEntity(EntityManager em) {
        Questions questions = new Questions()
            .question(DEFAULT_QUESTION)
            .diagram(DEFAULT_DIAGRAM)
            .diagramContentType(DEFAULT_DIAGRAM_CONTENT_TYPE)
            .option1(DEFAULT_OPTION_1)
            .option2(DEFAULT_OPTION_2)
            .option3(DEFAULT_OPTION_3)
            .option4(DEFAULT_OPTION_4)
            .answer(DEFAULT_ANSWER)
            .maxMarks(DEFAULT_MAX_MARKS)
            .negativeMarks(DEFAULT_NEGATIVE_MARKS)
            .level(DEFAULT_LEVEL);
        return questions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createUpdatedEntity(EntityManager em) {
        Questions questions = new Questions()
            .question(UPDATED_QUESTION)
            .diagram(UPDATED_DIAGRAM)
            .diagramContentType(UPDATED_DIAGRAM_CONTENT_TYPE)
            .option1(UPDATED_OPTION_1)
            .option2(UPDATED_OPTION_2)
            .option3(UPDATED_OPTION_3)
            .option4(UPDATED_OPTION_4)
            .answer(UPDATED_ANSWER)
            .maxMarks(UPDATED_MAX_MARKS)
            .negativeMarks(UPDATED_NEGATIVE_MARKS)
            .level(UPDATED_LEVEL);
        return questions;
    }

    @BeforeEach
    public void initTest() {
        questions = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestions() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);
        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestions.getDiagram()).isEqualTo(DEFAULT_DIAGRAM);
        assertThat(testQuestions.getDiagramContentType()).isEqualTo(DEFAULT_DIAGRAM_CONTENT_TYPE);
        assertThat(testQuestions.getOption1()).isEqualTo(DEFAULT_OPTION_1);
        assertThat(testQuestions.getOption2()).isEqualTo(DEFAULT_OPTION_2);
        assertThat(testQuestions.getOption3()).isEqualTo(DEFAULT_OPTION_3);
        assertThat(testQuestions.getOption4()).isEqualTo(DEFAULT_OPTION_4);
        assertThat(testQuestions.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testQuestions.getMaxMarks()).isEqualTo(DEFAULT_MAX_MARKS);
        assertThat(testQuestions.getNegativeMarks()).isEqualTo(DEFAULT_NEGATIVE_MARKS);
        assertThat(testQuestions.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createQuestionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // Create the Questions with an existing ID
        questions.setId(1L);
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setAnswer(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaxMarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setMaxMarks(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionsRepository.findAll().size();
        // set the field null
        questions.setLevel(null);

        // Create the Questions, which fails.
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        restQuestionsMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get all the questionsList
        restQuestionsMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId().intValue())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].diagramContentType").value(hasItem(DEFAULT_DIAGRAM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].diagram").value(hasItem(Base64Utils.encodeToString(DEFAULT_DIAGRAM))))
            .andExpect(jsonPath("$.[*].option1").value(hasItem(DEFAULT_OPTION_1.toString())))
            .andExpect(jsonPath("$.[*].option2").value(hasItem(DEFAULT_OPTION_2.toString())))
            .andExpect(jsonPath("$.[*].option3").value(hasItem(DEFAULT_OPTION_3.toString())))
            .andExpect(jsonPath("$.[*].option4").value(hasItem(DEFAULT_OPTION_4.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].maxMarks").value(hasItem(DEFAULT_MAX_MARKS)))
            .andExpect(jsonPath("$.[*].negativeMarks").value(hasItem(DEFAULT_NEGATIVE_MARKS)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questions.getId().intValue()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.diagramContentType").value(DEFAULT_DIAGRAM_CONTENT_TYPE))
            .andExpect(jsonPath("$.diagram").value(Base64Utils.encodeToString(DEFAULT_DIAGRAM)))
            .andExpect(jsonPath("$.option1").value(DEFAULT_OPTION_1.toString()))
            .andExpect(jsonPath("$.option2").value(DEFAULT_OPTION_2.toString()))
            .andExpect(jsonPath("$.option3").value(DEFAULT_OPTION_3.toString()))
            .andExpect(jsonPath("$.option4").value(DEFAULT_OPTION_4.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()))
            .andExpect(jsonPath("$.maxMarks").value(DEFAULT_MAX_MARKS))
            .andExpect(jsonPath("$.negativeMarks").value(DEFAULT_NEGATIVE_MARKS))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestions() throws Exception {
        // Get the questions
        restQuestionsMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions
        Questions updatedQuestions = questionsRepository.findById(questions.getId()).get();
        // Disconnect from session so that the updates on updatedQuestions are not directly saved in db
        em.detach(updatedQuestions);
        updatedQuestions
            .question(UPDATED_QUESTION)
            .diagram(UPDATED_DIAGRAM)
            .diagramContentType(UPDATED_DIAGRAM_CONTENT_TYPE)
            .option1(UPDATED_OPTION_1)
            .option2(UPDATED_OPTION_2)
            .option3(UPDATED_OPTION_3)
            .option4(UPDATED_OPTION_4)
            .answer(UPDATED_ANSWER)
            .maxMarks(UPDATED_MAX_MARKS)
            .negativeMarks(UPDATED_NEGATIVE_MARKS)
            .level(UPDATED_LEVEL);
        QuestionsDTO questionsDTO = questionsMapper.toDto(updatedQuestions);

        restQuestionsMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestions.getDiagram()).isEqualTo(UPDATED_DIAGRAM);
        assertThat(testQuestions.getDiagramContentType()).isEqualTo(UPDATED_DIAGRAM_CONTENT_TYPE);
        assertThat(testQuestions.getOption1()).isEqualTo(UPDATED_OPTION_1);
        assertThat(testQuestions.getOption2()).isEqualTo(UPDATED_OPTION_2);
        assertThat(testQuestions.getOption3()).isEqualTo(UPDATED_OPTION_3);
        assertThat(testQuestions.getOption4()).isEqualTo(UPDATED_OPTION_4);
        assertThat(testQuestions.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testQuestions.getMaxMarks()).isEqualTo(UPDATED_MAX_MARKS);
        assertThat(testQuestions.getNegativeMarks()).isEqualTo(UPDATED_NEGATIVE_MARKS);
        assertThat(testQuestions.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Create the Questions
        QuestionsDTO questionsDTO = questionsMapper.toDto(questions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestions() throws Exception {
        // Initialize the database
        questionsRepository.saveAndFlush(questions);

        int databaseSizeBeforeDelete = questionsRepository.findAll().size();

        // Delete the questions
        restQuestionsMockMvc.perform(delete("/api/questions/{id}", questions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questions.class);
        Questions questions1 = new Questions();
        questions1.setId(1L);
        Questions questions2 = new Questions();
        questions2.setId(questions1.getId());
        assertThat(questions1).isEqualTo(questions2);
        questions2.setId(2L);
        assertThat(questions1).isNotEqualTo(questions2);
        questions1.setId(null);
        assertThat(questions1).isNotEqualTo(questions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionsDTO.class);
        QuestionsDTO questionsDTO1 = new QuestionsDTO();
        questionsDTO1.setId(1L);
        QuestionsDTO questionsDTO2 = new QuestionsDTO();
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO2.setId(questionsDTO1.getId());
        assertThat(questionsDTO1).isEqualTo(questionsDTO2);
        questionsDTO2.setId(2L);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
        questionsDTO1.setId(null);
        assertThat(questionsDTO1).isNotEqualTo(questionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionsMapper.fromId(null)).isNull();
    }
}
