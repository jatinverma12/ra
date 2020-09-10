package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.SalariesPayment;
import com.risingarjun.arjun.repository.SalariesPaymentRepository;
import com.risingarjun.arjun.service.SalariesPaymentService;
import com.risingarjun.arjun.service.dto.SalariesPaymentDTO;
import com.risingarjun.arjun.service.mapper.SalariesPaymentMapper;
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

import com.risingarjun.arjun.domain.enumeration.Mode;
/**
 * Integration tests for the {@Link SalariesPaymentResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class SalariesPaymentResourceIT {

    private static final Integer DEFAULT_SALARY = 0;
    private static final Integer UPDATED_SALARY = 1;

    private static final Integer DEFAULT_PAID = 0;
    private static final Integer UPDATED_PAID = 1;

    private static final Integer DEFAULT_UNPAID = 0;
    private static final Integer UPDATED_UNPAID = 1;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final Mode DEFAULT_PAYMENT_MODE = Mode.NEFT;
    private static final Mode UPDATED_PAYMENT_MODE = Mode.UPI;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private SalariesPaymentRepository salariesPaymentRepository;

    @Autowired
    private SalariesPaymentMapper salariesPaymentMapper;

    @Autowired
    private SalariesPaymentService salariesPaymentService;

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

    private MockMvc restSalariesPaymentMockMvc;

    private SalariesPayment salariesPayment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SalariesPaymentResource salariesPaymentResource = new SalariesPaymentResource(salariesPaymentService);
        this.restSalariesPaymentMockMvc = MockMvcBuilders.standaloneSetup(salariesPaymentResource)
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
    public static SalariesPayment createEntity(EntityManager em) {
        SalariesPayment salariesPayment = new SalariesPayment()
            .salary(DEFAULT_SALARY)
            .paid(DEFAULT_PAID)
            .unpaid(DEFAULT_UNPAID)
            .date(DEFAULT_DATE)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .remarks(DEFAULT_REMARKS);
        return salariesPayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalariesPayment createUpdatedEntity(EntityManager em) {
        SalariesPayment salariesPayment = new SalariesPayment()
            .salary(UPDATED_SALARY)
            .paid(UPDATED_PAID)
            .unpaid(UPDATED_UNPAID)
            .date(UPDATED_DATE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .remarks(UPDATED_REMARKS);
        return salariesPayment;
    }

    @BeforeEach
    public void initTest() {
        salariesPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalariesPayment() throws Exception {
        int databaseSizeBeforeCreate = salariesPaymentRepository.findAll().size();

        // Create the SalariesPayment
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);
        restSalariesPaymentMockMvc.perform(post("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the SalariesPayment in the database
        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        SalariesPayment testSalariesPayment = salariesPaymentList.get(salariesPaymentList.size() - 1);
        assertThat(testSalariesPayment.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testSalariesPayment.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testSalariesPayment.getUnpaid()).isEqualTo(DEFAULT_UNPAID);
        assertThat(testSalariesPayment.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSalariesPayment.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testSalariesPayment.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testSalariesPayment.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createSalariesPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salariesPaymentRepository.findAll().size();

        // Create the SalariesPayment with an existing ID
        salariesPayment.setId(1L);
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalariesPaymentMockMvc.perform(post("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalariesPayment in the database
        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSalaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = salariesPaymentRepository.findAll().size();
        // set the field null
        salariesPayment.setSalary(null);

        // Create the SalariesPayment, which fails.
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);

        restSalariesPaymentMockMvc.perform(post("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaidIsRequired() throws Exception {
        int databaseSizeBeforeTest = salariesPaymentRepository.findAll().size();
        // set the field null
        salariesPayment.setPaid(null);

        // Create the SalariesPayment, which fails.
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);

        restSalariesPaymentMockMvc.perform(post("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnpaidIsRequired() throws Exception {
        int databaseSizeBeforeTest = salariesPaymentRepository.findAll().size();
        // set the field null
        salariesPayment.setUnpaid(null);

        // Create the SalariesPayment, which fails.
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);

        restSalariesPaymentMockMvc.perform(post("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = salariesPaymentRepository.findAll().size();
        // set the field null
        salariesPayment.setDate(null);

        // Create the SalariesPayment, which fails.
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);

        restSalariesPaymentMockMvc.perform(post("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = salariesPaymentRepository.findAll().size();
        // set the field null
        salariesPayment.setTransactionId(null);

        // Create the SalariesPayment, which fails.
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);

        restSalariesPaymentMockMvc.perform(post("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSalariesPayments() throws Exception {
        // Initialize the database
        salariesPaymentRepository.saveAndFlush(salariesPayment);

        // Get all the salariesPaymentList
        restSalariesPaymentMockMvc.perform(get("/api/salaries-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salariesPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY)))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID)))
            .andExpect(jsonPath("$.[*].unpaid").value(hasItem(DEFAULT_UNPAID)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getSalariesPayment() throws Exception {
        // Initialize the database
        salariesPaymentRepository.saveAndFlush(salariesPayment);

        // Get the salariesPayment
        restSalariesPaymentMockMvc.perform(get("/api/salaries-payments/{id}", salariesPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salariesPayment.getId().intValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID))
            .andExpect(jsonPath("$.unpaid").value(DEFAULT_UNPAID))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSalariesPayment() throws Exception {
        // Get the salariesPayment
        restSalariesPaymentMockMvc.perform(get("/api/salaries-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalariesPayment() throws Exception {
        // Initialize the database
        salariesPaymentRepository.saveAndFlush(salariesPayment);

        int databaseSizeBeforeUpdate = salariesPaymentRepository.findAll().size();

        // Update the salariesPayment
        SalariesPayment updatedSalariesPayment = salariesPaymentRepository.findById(salariesPayment.getId()).get();
        // Disconnect from session so that the updates on updatedSalariesPayment are not directly saved in db
        em.detach(updatedSalariesPayment);
        updatedSalariesPayment
            .salary(UPDATED_SALARY)
            .paid(UPDATED_PAID)
            .unpaid(UPDATED_UNPAID)
            .date(UPDATED_DATE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .remarks(UPDATED_REMARKS);
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(updatedSalariesPayment);

        restSalariesPaymentMockMvc.perform(put("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the SalariesPayment in the database
        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeUpdate);
        SalariesPayment testSalariesPayment = salariesPaymentList.get(salariesPaymentList.size() - 1);
        assertThat(testSalariesPayment.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testSalariesPayment.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testSalariesPayment.getUnpaid()).isEqualTo(UPDATED_UNPAID);
        assertThat(testSalariesPayment.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSalariesPayment.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testSalariesPayment.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testSalariesPayment.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingSalariesPayment() throws Exception {
        int databaseSizeBeforeUpdate = salariesPaymentRepository.findAll().size();

        // Create the SalariesPayment
        SalariesPaymentDTO salariesPaymentDTO = salariesPaymentMapper.toDto(salariesPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalariesPaymentMockMvc.perform(put("/api/salaries-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salariesPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalariesPayment in the database
        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalariesPayment() throws Exception {
        // Initialize the database
        salariesPaymentRepository.saveAndFlush(salariesPayment);

        int databaseSizeBeforeDelete = salariesPaymentRepository.findAll().size();

        // Delete the salariesPayment
        restSalariesPaymentMockMvc.perform(delete("/api/salaries-payments/{id}", salariesPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalariesPayment> salariesPaymentList = salariesPaymentRepository.findAll();
        assertThat(salariesPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalariesPayment.class);
        SalariesPayment salariesPayment1 = new SalariesPayment();
        salariesPayment1.setId(1L);
        SalariesPayment salariesPayment2 = new SalariesPayment();
        salariesPayment2.setId(salariesPayment1.getId());
        assertThat(salariesPayment1).isEqualTo(salariesPayment2);
        salariesPayment2.setId(2L);
        assertThat(salariesPayment1).isNotEqualTo(salariesPayment2);
        salariesPayment1.setId(null);
        assertThat(salariesPayment1).isNotEqualTo(salariesPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalariesPaymentDTO.class);
        SalariesPaymentDTO salariesPaymentDTO1 = new SalariesPaymentDTO();
        salariesPaymentDTO1.setId(1L);
        SalariesPaymentDTO salariesPaymentDTO2 = new SalariesPaymentDTO();
        assertThat(salariesPaymentDTO1).isNotEqualTo(salariesPaymentDTO2);
        salariesPaymentDTO2.setId(salariesPaymentDTO1.getId());
        assertThat(salariesPaymentDTO1).isEqualTo(salariesPaymentDTO2);
        salariesPaymentDTO2.setId(2L);
        assertThat(salariesPaymentDTO1).isNotEqualTo(salariesPaymentDTO2);
        salariesPaymentDTO1.setId(null);
        assertThat(salariesPaymentDTO1).isNotEqualTo(salariesPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(salariesPaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(salariesPaymentMapper.fromId(null)).isNull();
    }
}
