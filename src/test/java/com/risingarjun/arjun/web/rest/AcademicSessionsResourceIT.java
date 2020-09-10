package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.AcademicSessions;
import com.risingarjun.arjun.repository.AcademicSessionsRepository;
import com.risingarjun.arjun.service.AcademicSessionsService;
import com.risingarjun.arjun.service.dto.AcademicSessionsDTO;
import com.risingarjun.arjun.service.mapper.AcademicSessionsMapper;
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
 * Integration tests for the {@Link AcademicSessionsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class AcademicSessionsResourceIT {

    private static final String DEFAULT_ACAD_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACAD_SESSION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACAD_SESSION = "AAAAAAAAAA";
    private static final String UPDATED_ACAD_SESSION = "BBBBBBBBBB";

    @Autowired
    private AcademicSessionsRepository academicSessionsRepository;

    @Autowired
    private AcademicSessionsMapper academicSessionsMapper;

    @Autowired
    private AcademicSessionsService academicSessionsService;

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

    private MockMvc restAcademicSessionsMockMvc;

    private AcademicSessions academicSessions;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcademicSessionsResource academicSessionsResource = new AcademicSessionsResource(academicSessionsService);
        this.restAcademicSessionsMockMvc = MockMvcBuilders.standaloneSetup(academicSessionsResource)
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
    public static AcademicSessions createEntity(EntityManager em) {
        AcademicSessions academicSessions = new AcademicSessions()
            .acadSessionId(DEFAULT_ACAD_SESSION_ID)
            .acadSession(DEFAULT_ACAD_SESSION);
        return academicSessions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicSessions createUpdatedEntity(EntityManager em) {
        AcademicSessions academicSessions = new AcademicSessions()
            .acadSessionId(UPDATED_ACAD_SESSION_ID)
            .acadSession(UPDATED_ACAD_SESSION);
        return academicSessions;
    }

    @BeforeEach
    public void initTest() {
        academicSessions = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcademicSessions() throws Exception {
        int databaseSizeBeforeCreate = academicSessionsRepository.findAll().size();

        // Create the AcademicSessions
        AcademicSessionsDTO academicSessionsDTO = academicSessionsMapper.toDto(academicSessions);
        restAcademicSessionsMockMvc.perform(post("/api/academic-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicSessionsDTO)))
            .andExpect(status().isCreated());

        // Validate the AcademicSessions in the database
        List<AcademicSessions> academicSessionsList = academicSessionsRepository.findAll();
        assertThat(academicSessionsList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicSessions testAcademicSessions = academicSessionsList.get(academicSessionsList.size() - 1);
        assertThat(testAcademicSessions.getAcadSessionId()).isEqualTo(DEFAULT_ACAD_SESSION_ID);
        assertThat(testAcademicSessions.getAcadSession()).isEqualTo(DEFAULT_ACAD_SESSION);
    }

    @Test
    @Transactional
    public void createAcademicSessionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = academicSessionsRepository.findAll().size();

        // Create the AcademicSessions with an existing ID
        academicSessions.setId(1L);
        AcademicSessionsDTO academicSessionsDTO = academicSessionsMapper.toDto(academicSessions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicSessionsMockMvc.perform(post("/api/academic-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicSessionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicSessions in the database
        List<AcademicSessions> academicSessionsList = academicSessionsRepository.findAll();
        assertThat(academicSessionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAcadSessionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicSessionsRepository.findAll().size();
        // set the field null
        academicSessions.setAcadSessionId(null);

        // Create the AcademicSessions, which fails.
        AcademicSessionsDTO academicSessionsDTO = academicSessionsMapper.toDto(academicSessions);

        restAcademicSessionsMockMvc.perform(post("/api/academic-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicSessionsDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicSessions> academicSessionsList = academicSessionsRepository.findAll();
        assertThat(academicSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAcadSessionIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicSessionsRepository.findAll().size();
        // set the field null
        academicSessions.setAcadSession(null);

        // Create the AcademicSessions, which fails.
        AcademicSessionsDTO academicSessionsDTO = academicSessionsMapper.toDto(academicSessions);

        restAcademicSessionsMockMvc.perform(post("/api/academic-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicSessionsDTO)))
            .andExpect(status().isBadRequest());

        List<AcademicSessions> academicSessionsList = academicSessionsRepository.findAll();
        assertThat(academicSessionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcademicSessions() throws Exception {
        // Initialize the database
        academicSessionsRepository.saveAndFlush(academicSessions);

        // Get all the academicSessionsList
        restAcademicSessionsMockMvc.perform(get("/api/academic-sessions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicSessions.getId().intValue())))
            .andExpect(jsonPath("$.[*].acadSessionId").value(hasItem(DEFAULT_ACAD_SESSION_ID.toString())))
            .andExpect(jsonPath("$.[*].acadSession").value(hasItem(DEFAULT_ACAD_SESSION.toString())));
    }
    
    @Test
    @Transactional
    public void getAcademicSessions() throws Exception {
        // Initialize the database
        academicSessionsRepository.saveAndFlush(academicSessions);

        // Get the academicSessions
        restAcademicSessionsMockMvc.perform(get("/api/academic-sessions/{id}", academicSessions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(academicSessions.getId().intValue()))
            .andExpect(jsonPath("$.acadSessionId").value(DEFAULT_ACAD_SESSION_ID.toString()))
            .andExpect(jsonPath("$.acadSession").value(DEFAULT_ACAD_SESSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAcademicSessions() throws Exception {
        // Get the academicSessions
        restAcademicSessionsMockMvc.perform(get("/api/academic-sessions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcademicSessions() throws Exception {
        // Initialize the database
        academicSessionsRepository.saveAndFlush(academicSessions);

        int databaseSizeBeforeUpdate = academicSessionsRepository.findAll().size();

        // Update the academicSessions
        AcademicSessions updatedAcademicSessions = academicSessionsRepository.findById(academicSessions.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicSessions are not directly saved in db
        em.detach(updatedAcademicSessions);
        updatedAcademicSessions
            .acadSessionId(UPDATED_ACAD_SESSION_ID)
            .acadSession(UPDATED_ACAD_SESSION);
        AcademicSessionsDTO academicSessionsDTO = academicSessionsMapper.toDto(updatedAcademicSessions);

        restAcademicSessionsMockMvc.perform(put("/api/academic-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicSessionsDTO)))
            .andExpect(status().isOk());

        // Validate the AcademicSessions in the database
        List<AcademicSessions> academicSessionsList = academicSessionsRepository.findAll();
        assertThat(academicSessionsList).hasSize(databaseSizeBeforeUpdate);
        AcademicSessions testAcademicSessions = academicSessionsList.get(academicSessionsList.size() - 1);
        assertThat(testAcademicSessions.getAcadSessionId()).isEqualTo(UPDATED_ACAD_SESSION_ID);
        assertThat(testAcademicSessions.getAcadSession()).isEqualTo(UPDATED_ACAD_SESSION);
    }

    @Test
    @Transactional
    public void updateNonExistingAcademicSessions() throws Exception {
        int databaseSizeBeforeUpdate = academicSessionsRepository.findAll().size();

        // Create the AcademicSessions
        AcademicSessionsDTO academicSessionsDTO = academicSessionsMapper.toDto(academicSessions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicSessionsMockMvc.perform(put("/api/academic-sessions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(academicSessionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AcademicSessions in the database
        List<AcademicSessions> academicSessionsList = academicSessionsRepository.findAll();
        assertThat(academicSessionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcademicSessions() throws Exception {
        // Initialize the database
        academicSessionsRepository.saveAndFlush(academicSessions);

        int databaseSizeBeforeDelete = academicSessionsRepository.findAll().size();

        // Delete the academicSessions
        restAcademicSessionsMockMvc.perform(delete("/api/academic-sessions/{id}", academicSessions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicSessions> academicSessionsList = academicSessionsRepository.findAll();
        assertThat(academicSessionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicSessions.class);
        AcademicSessions academicSessions1 = new AcademicSessions();
        academicSessions1.setId(1L);
        AcademicSessions academicSessions2 = new AcademicSessions();
        academicSessions2.setId(academicSessions1.getId());
        assertThat(academicSessions1).isEqualTo(academicSessions2);
        academicSessions2.setId(2L);
        assertThat(academicSessions1).isNotEqualTo(academicSessions2);
        academicSessions1.setId(null);
        assertThat(academicSessions1).isNotEqualTo(academicSessions2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicSessionsDTO.class);
        AcademicSessionsDTO academicSessionsDTO1 = new AcademicSessionsDTO();
        academicSessionsDTO1.setId(1L);
        AcademicSessionsDTO academicSessionsDTO2 = new AcademicSessionsDTO();
        assertThat(academicSessionsDTO1).isNotEqualTo(academicSessionsDTO2);
        academicSessionsDTO2.setId(academicSessionsDTO1.getId());
        assertThat(academicSessionsDTO1).isEqualTo(academicSessionsDTO2);
        academicSessionsDTO2.setId(2L);
        assertThat(academicSessionsDTO1).isNotEqualTo(academicSessionsDTO2);
        academicSessionsDTO1.setId(null);
        assertThat(academicSessionsDTO1).isNotEqualTo(academicSessionsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(academicSessionsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(academicSessionsMapper.fromId(null)).isNull();
    }
}
