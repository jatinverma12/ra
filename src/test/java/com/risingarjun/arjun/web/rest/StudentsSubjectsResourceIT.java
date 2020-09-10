package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.StudentsSubjects;
import com.risingarjun.arjun.repository.StudentsSubjectsRepository;
import com.risingarjun.arjun.service.StudentsSubjectsService;
import com.risingarjun.arjun.service.dto.StudentsSubjectsDTO;
import com.risingarjun.arjun.service.mapper.StudentsSubjectsMapper;
import com.risingarjun.arjun.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.risingarjun.arjun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.risingarjun.arjun.domain.enumeration.Month;
/**
 * Integration tests for the {@Link StudentsSubjectsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class StudentsSubjectsResourceIT {

    private static final Month DEFAULT_MONTH = Month.JAN;
    private static final Month UPDATED_MONTH = Month.FEB;

    @Autowired
    private StudentsSubjectsRepository studentsSubjectsRepository;

    @Mock
    private StudentsSubjectsRepository studentsSubjectsRepositoryMock;

    @Autowired
    private StudentsSubjectsMapper studentsSubjectsMapper;

    @Mock
    private StudentsSubjectsService studentsSubjectsServiceMock;

    @Autowired
    private StudentsSubjectsService studentsSubjectsService;

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

    private MockMvc restStudentsSubjectsMockMvc;

    private StudentsSubjects studentsSubjects;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentsSubjectsResource studentsSubjectsResource = new StudentsSubjectsResource(studentsSubjectsService);
        this.restStudentsSubjectsMockMvc = MockMvcBuilders.standaloneSetup(studentsSubjectsResource)
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
    public static StudentsSubjects createEntity(EntityManager em) {
        StudentsSubjects studentsSubjects = new StudentsSubjects()
            .month(DEFAULT_MONTH);
        return studentsSubjects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentsSubjects createUpdatedEntity(EntityManager em) {
        StudentsSubjects studentsSubjects = new StudentsSubjects()
            .month(UPDATED_MONTH);
        return studentsSubjects;
    }

    @BeforeEach
    public void initTest() {
        studentsSubjects = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentsSubjects() throws Exception {
        int databaseSizeBeforeCreate = studentsSubjectsRepository.findAll().size();

        // Create the StudentsSubjects
        StudentsSubjectsDTO studentsSubjectsDTO = studentsSubjectsMapper.toDto(studentsSubjects);
        restStudentsSubjectsMockMvc.perform(post("/api/students-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsSubjectsDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentsSubjects in the database
        List<StudentsSubjects> studentsSubjectsList = studentsSubjectsRepository.findAll();
        assertThat(studentsSubjectsList).hasSize(databaseSizeBeforeCreate + 1);
        StudentsSubjects testStudentsSubjects = studentsSubjectsList.get(studentsSubjectsList.size() - 1);
        assertThat(testStudentsSubjects.getMonth()).isEqualTo(DEFAULT_MONTH);
    }

    @Test
    @Transactional
    public void createStudentsSubjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentsSubjectsRepository.findAll().size();

        // Create the StudentsSubjects with an existing ID
        studentsSubjects.setId(1L);
        StudentsSubjectsDTO studentsSubjectsDTO = studentsSubjectsMapper.toDto(studentsSubjects);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentsSubjectsMockMvc.perform(post("/api/students-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsSubjectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentsSubjects in the database
        List<StudentsSubjects> studentsSubjectsList = studentsSubjectsRepository.findAll();
        assertThat(studentsSubjectsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentsSubjectsRepository.findAll().size();
        // set the field null
        studentsSubjects.setMonth(null);

        // Create the StudentsSubjects, which fails.
        StudentsSubjectsDTO studentsSubjectsDTO = studentsSubjectsMapper.toDto(studentsSubjects);

        restStudentsSubjectsMockMvc.perform(post("/api/students-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsSubjectsDTO)))
            .andExpect(status().isBadRequest());

        List<StudentsSubjects> studentsSubjectsList = studentsSubjectsRepository.findAll();
        assertThat(studentsSubjectsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentsSubjects() throws Exception {
        // Initialize the database
        studentsSubjectsRepository.saveAndFlush(studentsSubjects);

        // Get all the studentsSubjectsList
        restStudentsSubjectsMockMvc.perform(get("/api/students-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentsSubjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllStudentsSubjectsWithEagerRelationshipsIsEnabled() throws Exception {
        StudentsSubjectsResource studentsSubjectsResource = new StudentsSubjectsResource(studentsSubjectsServiceMock);
        when(studentsSubjectsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restStudentsSubjectsMockMvc = MockMvcBuilders.standaloneSetup(studentsSubjectsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restStudentsSubjectsMockMvc.perform(get("/api/students-subjects?eagerload=true"))
        .andExpect(status().isOk());

        verify(studentsSubjectsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllStudentsSubjectsWithEagerRelationshipsIsNotEnabled() throws Exception {
        StudentsSubjectsResource studentsSubjectsResource = new StudentsSubjectsResource(studentsSubjectsServiceMock);
            when(studentsSubjectsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restStudentsSubjectsMockMvc = MockMvcBuilders.standaloneSetup(studentsSubjectsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restStudentsSubjectsMockMvc.perform(get("/api/students-subjects?eagerload=true"))
        .andExpect(status().isOk());

            verify(studentsSubjectsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getStudentsSubjects() throws Exception {
        // Initialize the database
        studentsSubjectsRepository.saveAndFlush(studentsSubjects);

        // Get the studentsSubjects
        restStudentsSubjectsMockMvc.perform(get("/api/students-subjects/{id}", studentsSubjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentsSubjects.getId().intValue()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentsSubjects() throws Exception {
        // Get the studentsSubjects
        restStudentsSubjectsMockMvc.perform(get("/api/students-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentsSubjects() throws Exception {
        // Initialize the database
        studentsSubjectsRepository.saveAndFlush(studentsSubjects);

        int databaseSizeBeforeUpdate = studentsSubjectsRepository.findAll().size();

        // Update the studentsSubjects
        StudentsSubjects updatedStudentsSubjects = studentsSubjectsRepository.findById(studentsSubjects.getId()).get();
        // Disconnect from session so that the updates on updatedStudentsSubjects are not directly saved in db
        em.detach(updatedStudentsSubjects);
        updatedStudentsSubjects
            .month(UPDATED_MONTH);
        StudentsSubjectsDTO studentsSubjectsDTO = studentsSubjectsMapper.toDto(updatedStudentsSubjects);

        restStudentsSubjectsMockMvc.perform(put("/api/students-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsSubjectsDTO)))
            .andExpect(status().isOk());

        // Validate the StudentsSubjects in the database
        List<StudentsSubjects> studentsSubjectsList = studentsSubjectsRepository.findAll();
        assertThat(studentsSubjectsList).hasSize(databaseSizeBeforeUpdate);
        StudentsSubjects testStudentsSubjects = studentsSubjectsList.get(studentsSubjectsList.size() - 1);
        assertThat(testStudentsSubjects.getMonth()).isEqualTo(UPDATED_MONTH);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentsSubjects() throws Exception {
        int databaseSizeBeforeUpdate = studentsSubjectsRepository.findAll().size();

        // Create the StudentsSubjects
        StudentsSubjectsDTO studentsSubjectsDTO = studentsSubjectsMapper.toDto(studentsSubjects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentsSubjectsMockMvc.perform(put("/api/students-subjects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsSubjectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentsSubjects in the database
        List<StudentsSubjects> studentsSubjectsList = studentsSubjectsRepository.findAll();
        assertThat(studentsSubjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentsSubjects() throws Exception {
        // Initialize the database
        studentsSubjectsRepository.saveAndFlush(studentsSubjects);

        int databaseSizeBeforeDelete = studentsSubjectsRepository.findAll().size();

        // Delete the studentsSubjects
        restStudentsSubjectsMockMvc.perform(delete("/api/students-subjects/{id}", studentsSubjects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentsSubjects> studentsSubjectsList = studentsSubjectsRepository.findAll();
        assertThat(studentsSubjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentsSubjects.class);
        StudentsSubjects studentsSubjects1 = new StudentsSubjects();
        studentsSubjects1.setId(1L);
        StudentsSubjects studentsSubjects2 = new StudentsSubjects();
        studentsSubjects2.setId(studentsSubjects1.getId());
        assertThat(studentsSubjects1).isEqualTo(studentsSubjects2);
        studentsSubjects2.setId(2L);
        assertThat(studentsSubjects1).isNotEqualTo(studentsSubjects2);
        studentsSubjects1.setId(null);
        assertThat(studentsSubjects1).isNotEqualTo(studentsSubjects2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentsSubjectsDTO.class);
        StudentsSubjectsDTO studentsSubjectsDTO1 = new StudentsSubjectsDTO();
        studentsSubjectsDTO1.setId(1L);
        StudentsSubjectsDTO studentsSubjectsDTO2 = new StudentsSubjectsDTO();
        assertThat(studentsSubjectsDTO1).isNotEqualTo(studentsSubjectsDTO2);
        studentsSubjectsDTO2.setId(studentsSubjectsDTO1.getId());
        assertThat(studentsSubjectsDTO1).isEqualTo(studentsSubjectsDTO2);
        studentsSubjectsDTO2.setId(2L);
        assertThat(studentsSubjectsDTO1).isNotEqualTo(studentsSubjectsDTO2);
        studentsSubjectsDTO1.setId(null);
        assertThat(studentsSubjectsDTO1).isNotEqualTo(studentsSubjectsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentsSubjectsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentsSubjectsMapper.fromId(null)).isNull();
    }
}
