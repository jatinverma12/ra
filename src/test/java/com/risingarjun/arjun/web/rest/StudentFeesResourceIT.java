package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.StudentFees;
import com.risingarjun.arjun.repository.StudentFeesRepository;
import com.risingarjun.arjun.service.StudentFeesService;
import com.risingarjun.arjun.service.dto.StudentFeesDTO;
import com.risingarjun.arjun.service.mapper.StudentFeesMapper;
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

import com.risingarjun.arjun.domain.enumeration.Month;
/**
 * Integration tests for the {@Link StudentFeesResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class StudentFeesResourceIT {

    private static final Integer DEFAULT_FEE = 1;
    private static final Integer UPDATED_FEE = 2;

    private static final Integer DEFAULT_FEE_CORRECTION = 1;
    private static final Integer UPDATED_FEE_CORRECTION = 2;

    private static final Month DEFAULT_MONTH = Month.JAN;
    private static final Month UPDATED_MONTH = Month.FEB;

    private static final Boolean DEFAULT_FEE_STATUS = false;
    private static final Boolean UPDATED_FEE_STATUS = true;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private StudentFeesRepository studentFeesRepository;

    @Autowired
    private StudentFeesMapper studentFeesMapper;

    @Autowired
    private StudentFeesService studentFeesService;

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

    private MockMvc restStudentFeesMockMvc;

    private StudentFees studentFees;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentFeesResource studentFeesResource = new StudentFeesResource(studentFeesService);
        this.restStudentFeesMockMvc = MockMvcBuilders.standaloneSetup(studentFeesResource)
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
    public static StudentFees createEntity(EntityManager em) {
        StudentFees studentFees = new StudentFees()
            .fee(DEFAULT_FEE)
            .feeCorrection(DEFAULT_FEE_CORRECTION)
            .month(DEFAULT_MONTH)
            .feeStatus(DEFAULT_FEE_STATUS)
            .remarks(DEFAULT_REMARKS);
        return studentFees;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentFees createUpdatedEntity(EntityManager em) {
        StudentFees studentFees = new StudentFees()
            .fee(UPDATED_FEE)
            .feeCorrection(UPDATED_FEE_CORRECTION)
            .month(UPDATED_MONTH)
            .feeStatus(UPDATED_FEE_STATUS)
            .remarks(UPDATED_REMARKS);
        return studentFees;
    }

    @BeforeEach
    public void initTest() {
        studentFees = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentFees() throws Exception {
        int databaseSizeBeforeCreate = studentFeesRepository.findAll().size();

        // Create the StudentFees
        StudentFeesDTO studentFeesDTO = studentFeesMapper.toDto(studentFees);
        restStudentFeesMockMvc.perform(post("/api/student-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentFeesDTO)))
            .andExpect(status().isCreated());

        // Validate the StudentFees in the database
        List<StudentFees> studentFeesList = studentFeesRepository.findAll();
        assertThat(studentFeesList).hasSize(databaseSizeBeforeCreate + 1);
        StudentFees testStudentFees = studentFeesList.get(studentFeesList.size() - 1);
        assertThat(testStudentFees.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testStudentFees.getFeeCorrection()).isEqualTo(DEFAULT_FEE_CORRECTION);
        assertThat(testStudentFees.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testStudentFees.isFeeStatus()).isEqualTo(DEFAULT_FEE_STATUS);
        assertThat(testStudentFees.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createStudentFeesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentFeesRepository.findAll().size();

        // Create the StudentFees with an existing ID
        studentFees.setId(1L);
        StudentFeesDTO studentFeesDTO = studentFeesMapper.toDto(studentFees);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentFeesMockMvc.perform(post("/api/student-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentFeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentFees in the database
        List<StudentFees> studentFeesList = studentFeesRepository.findAll();
        assertThat(studentFeesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudentFees() throws Exception {
        // Initialize the database
        studentFeesRepository.saveAndFlush(studentFees);

        // Get all the studentFeesList
        restStudentFeesMockMvc.perform(get("/api/student-fees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentFees.getId().intValue())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE)))
            .andExpect(jsonPath("$.[*].feeCorrection").value(hasItem(DEFAULT_FEE_CORRECTION)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].feeStatus").value(hasItem(DEFAULT_FEE_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getStudentFees() throws Exception {
        // Initialize the database
        studentFeesRepository.saveAndFlush(studentFees);

        // Get the studentFees
        restStudentFeesMockMvc.perform(get("/api/student-fees/{id}", studentFees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentFees.getId().intValue()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE))
            .andExpect(jsonPath("$.feeCorrection").value(DEFAULT_FEE_CORRECTION))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.feeStatus").value(DEFAULT_FEE_STATUS.booleanValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentFees() throws Exception {
        // Get the studentFees
        restStudentFeesMockMvc.perform(get("/api/student-fees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentFees() throws Exception {
        // Initialize the database
        studentFeesRepository.saveAndFlush(studentFees);

        int databaseSizeBeforeUpdate = studentFeesRepository.findAll().size();

        // Update the studentFees
        StudentFees updatedStudentFees = studentFeesRepository.findById(studentFees.getId()).get();
        // Disconnect from session so that the updates on updatedStudentFees are not directly saved in db
        em.detach(updatedStudentFees);
        updatedStudentFees
            .fee(UPDATED_FEE)
            .feeCorrection(UPDATED_FEE_CORRECTION)
            .month(UPDATED_MONTH)
            .feeStatus(UPDATED_FEE_STATUS)
            .remarks(UPDATED_REMARKS);
        StudentFeesDTO studentFeesDTO = studentFeesMapper.toDto(updatedStudentFees);

        restStudentFeesMockMvc.perform(put("/api/student-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentFeesDTO)))
            .andExpect(status().isOk());

        // Validate the StudentFees in the database
        List<StudentFees> studentFeesList = studentFeesRepository.findAll();
        assertThat(studentFeesList).hasSize(databaseSizeBeforeUpdate);
        StudentFees testStudentFees = studentFeesList.get(studentFeesList.size() - 1);
        assertThat(testStudentFees.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testStudentFees.getFeeCorrection()).isEqualTo(UPDATED_FEE_CORRECTION);
        assertThat(testStudentFees.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testStudentFees.isFeeStatus()).isEqualTo(UPDATED_FEE_STATUS);
        assertThat(testStudentFees.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentFees() throws Exception {
        int databaseSizeBeforeUpdate = studentFeesRepository.findAll().size();

        // Create the StudentFees
        StudentFeesDTO studentFeesDTO = studentFeesMapper.toDto(studentFees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentFeesMockMvc.perform(put("/api/student-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentFeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StudentFees in the database
        List<StudentFees> studentFeesList = studentFeesRepository.findAll();
        assertThat(studentFeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentFees() throws Exception {
        // Initialize the database
        studentFeesRepository.saveAndFlush(studentFees);

        int databaseSizeBeforeDelete = studentFeesRepository.findAll().size();

        // Delete the studentFees
        restStudentFeesMockMvc.perform(delete("/api/student-fees/{id}", studentFees.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentFees> studentFeesList = studentFeesRepository.findAll();
        assertThat(studentFeesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentFees.class);
        StudentFees studentFees1 = new StudentFees();
        studentFees1.setId(1L);
        StudentFees studentFees2 = new StudentFees();
        studentFees2.setId(studentFees1.getId());
        assertThat(studentFees1).isEqualTo(studentFees2);
        studentFees2.setId(2L);
        assertThat(studentFees1).isNotEqualTo(studentFees2);
        studentFees1.setId(null);
        assertThat(studentFees1).isNotEqualTo(studentFees2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentFeesDTO.class);
        StudentFeesDTO studentFeesDTO1 = new StudentFeesDTO();
        studentFeesDTO1.setId(1L);
        StudentFeesDTO studentFeesDTO2 = new StudentFeesDTO();
        assertThat(studentFeesDTO1).isNotEqualTo(studentFeesDTO2);
        studentFeesDTO2.setId(studentFeesDTO1.getId());
        assertThat(studentFeesDTO1).isEqualTo(studentFeesDTO2);
        studentFeesDTO2.setId(2L);
        assertThat(studentFeesDTO1).isNotEqualTo(studentFeesDTO2);
        studentFeesDTO1.setId(null);
        assertThat(studentFeesDTO1).isNotEqualTo(studentFeesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentFeesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentFeesMapper.fromId(null)).isNull();
    }
}
