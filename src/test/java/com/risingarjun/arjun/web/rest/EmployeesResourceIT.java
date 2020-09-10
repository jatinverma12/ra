package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Employees;
import com.risingarjun.arjun.repository.EmployeesRepository;
import com.risingarjun.arjun.service.EmployeesService;
import com.risingarjun.arjun.service.dto.EmployeesDTO;
import com.risingarjun.arjun.service.mapper.EmployeesMapper;
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

import com.risingarjun.arjun.domain.enumeration.JobNature;
/**
 * Integration tests for the {@Link EmployeesResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class EmployeesResourceIT {

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final JobNature DEFAULT_JOB_NATURE = JobNature.PARTTIME;
    private static final JobNature UPDATED_JOB_NATURE = JobNature.FULLTIME;

    private static final Boolean DEFAULT_BGC = false;
    private static final Boolean UPDATED_BGC = true;

    private static final byte[] DEFAULT_RESUME = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_RESUME = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_RESUME_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_RESUME_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PAN = "AAAAAAAAAA";
    private static final String UPDATED_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC = "AAAAAAAAAA";
    private static final String UPDATED_IFSC = "BBBBBBBBBB";

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private EmployeesService employeesService;

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

    private MockMvc restEmployeesMockMvc;

    private Employees employees;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeesResource employeesResource = new EmployeesResource(employeesService);
        this.restEmployeesMockMvc = MockMvcBuilders.standaloneSetup(employeesResource)
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
    public static Employees createEntity(EntityManager em) {
        Employees employees = new Employees()
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .jobNature(DEFAULT_JOB_NATURE)
            .bgc(DEFAULT_BGC)
            .resume(DEFAULT_RESUME)
            .resumeContentType(DEFAULT_RESUME_CONTENT_TYPE)
            .pan(DEFAULT_PAN)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .bank(DEFAULT_BANK)
            .ifsc(DEFAULT_IFSC);
        return employees;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employees createUpdatedEntity(EntityManager em) {
        Employees employees = new Employees()
            .employeeId(UPDATED_EMPLOYEE_ID)
            .jobNature(UPDATED_JOB_NATURE)
            .bgc(UPDATED_BGC)
            .resume(UPDATED_RESUME)
            .resumeContentType(UPDATED_RESUME_CONTENT_TYPE)
            .pan(UPDATED_PAN)
            .accountNo(UPDATED_ACCOUNT_NO)
            .bank(UPDATED_BANK)
            .ifsc(UPDATED_IFSC);
        return employees;
    }

    @BeforeEach
    public void initTest() {
        employees = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployees() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);
        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isCreated());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate + 1);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployees.getJobNature()).isEqualTo(DEFAULT_JOB_NATURE);
        assertThat(testEmployees.isBgc()).isEqualTo(DEFAULT_BGC);
        assertThat(testEmployees.getResume()).isEqualTo(DEFAULT_RESUME);
        assertThat(testEmployees.getResumeContentType()).isEqualTo(DEFAULT_RESUME_CONTENT_TYPE);
        assertThat(testEmployees.getPan()).isEqualTo(DEFAULT_PAN);
        assertThat(testEmployees.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testEmployees.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testEmployees.getIfsc()).isEqualTo(DEFAULT_IFSC);
    }

    @Test
    @Transactional
    public void createEmployeesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeesRepository.findAll().size();

        // Create the Employees with an existing ID
        employees.setId(1L);
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmployeeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setEmployeeId(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPanIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setPan(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setAccountNo(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setBank(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIfscIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeesRepository.findAll().size();
        // set the field null
        employees.setIfsc(null);

        // Create the Employees, which fails.
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        restEmployeesMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get all the employeesList
        restEmployeesMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employees.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID.toString())))
            .andExpect(jsonPath("$.[*].jobNature").value(hasItem(DEFAULT_JOB_NATURE.toString())))
            .andExpect(jsonPath("$.[*].bgc").value(hasItem(DEFAULT_BGC.booleanValue())))
            .andExpect(jsonPath("$.[*].resumeContentType").value(hasItem(DEFAULT_RESUME_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].resume").value(hasItem(Base64Utils.encodeToString(DEFAULT_RESUME))))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.toString())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
            .andExpect(jsonPath("$.[*].ifsc").value(hasItem(DEFAULT_IFSC.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        // Get the employees
        restEmployeesMockMvc.perform(get("/api/employees/{id}", employees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employees.getId().intValue()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID.toString()))
            .andExpect(jsonPath("$.jobNature").value(DEFAULT_JOB_NATURE.toString()))
            .andExpect(jsonPath("$.bgc").value(DEFAULT_BGC.booleanValue()))
            .andExpect(jsonPath("$.resumeContentType").value(DEFAULT_RESUME_CONTENT_TYPE))
            .andExpect(jsonPath("$.resume").value(Base64Utils.encodeToString(DEFAULT_RESUME)))
            .andExpect(jsonPath("$.pan").value(DEFAULT_PAN.toString()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK.toString()))
            .andExpect(jsonPath("$.ifsc").value(DEFAULT_IFSC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployees() throws Exception {
        // Get the employees
        restEmployeesMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Update the employees
        Employees updatedEmployees = employeesRepository.findById(employees.getId()).get();
        // Disconnect from session so that the updates on updatedEmployees are not directly saved in db
        em.detach(updatedEmployees);
        updatedEmployees
            .employeeId(UPDATED_EMPLOYEE_ID)
            .jobNature(UPDATED_JOB_NATURE)
            .bgc(UPDATED_BGC)
            .resume(UPDATED_RESUME)
            .resumeContentType(UPDATED_RESUME_CONTENT_TYPE)
            .pan(UPDATED_PAN)
            .accountNo(UPDATED_ACCOUNT_NO)
            .bank(UPDATED_BANK)
            .ifsc(UPDATED_IFSC);
        EmployeesDTO employeesDTO = employeesMapper.toDto(updatedEmployees);

        restEmployeesMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isOk());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
        Employees testEmployees = employeesList.get(employeesList.size() - 1);
        assertThat(testEmployees.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployees.getJobNature()).isEqualTo(UPDATED_JOB_NATURE);
        assertThat(testEmployees.isBgc()).isEqualTo(UPDATED_BGC);
        assertThat(testEmployees.getResume()).isEqualTo(UPDATED_RESUME);
        assertThat(testEmployees.getResumeContentType()).isEqualTo(UPDATED_RESUME_CONTENT_TYPE);
        assertThat(testEmployees.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testEmployees.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testEmployees.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testEmployees.getIfsc()).isEqualTo(UPDATED_IFSC);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployees() throws Exception {
        int databaseSizeBeforeUpdate = employeesRepository.findAll().size();

        // Create the Employees
        EmployeesDTO employeesDTO = employeesMapper.toDto(employees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeesMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employees in the database
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployees() throws Exception {
        // Initialize the database
        employeesRepository.saveAndFlush(employees);

        int databaseSizeBeforeDelete = employeesRepository.findAll().size();

        // Delete the employees
        restEmployeesMockMvc.perform(delete("/api/employees/{id}", employees.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employees> employeesList = employeesRepository.findAll();
        assertThat(employeesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employees.class);
        Employees employees1 = new Employees();
        employees1.setId(1L);
        Employees employees2 = new Employees();
        employees2.setId(employees1.getId());
        assertThat(employees1).isEqualTo(employees2);
        employees2.setId(2L);
        assertThat(employees1).isNotEqualTo(employees2);
        employees1.setId(null);
        assertThat(employees1).isNotEqualTo(employees2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeesDTO.class);
        EmployeesDTO employeesDTO1 = new EmployeesDTO();
        employeesDTO1.setId(1L);
        EmployeesDTO employeesDTO2 = new EmployeesDTO();
        assertThat(employeesDTO1).isNotEqualTo(employeesDTO2);
        employeesDTO2.setId(employeesDTO1.getId());
        assertThat(employeesDTO1).isEqualTo(employeesDTO2);
        employeesDTO2.setId(2L);
        assertThat(employeesDTO1).isNotEqualTo(employeesDTO2);
        employeesDTO1.setId(null);
        assertThat(employeesDTO1).isNotEqualTo(employeesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeesMapper.fromId(null)).isNull();
    }
}
