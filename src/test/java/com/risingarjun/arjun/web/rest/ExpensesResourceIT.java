package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Expenses;
import com.risingarjun.arjun.repository.ExpensesRepository;
import com.risingarjun.arjun.service.ExpensesService;
import com.risingarjun.arjun.service.dto.ExpensesDTO;
import com.risingarjun.arjun.service.mapper.ExpensesMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.risingarjun.arjun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.risingarjun.arjun.domain.enumeration.Mode;
import com.risingarjun.arjun.domain.enumeration.ExpensesType;
/**
 * Integration tests for the {@Link ExpensesResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class ExpensesResourceIT {

    private static final String DEFAULT_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_ITEM = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final Integer DEFAULT_RATE = 0;
    private static final Integer UPDATED_RATE = 1;

    private static final Integer DEFAULT_LABOR_COST = 0;
    private static final Integer UPDATED_LABOR_COST = 1;

    private static final Integer DEFAULT_OTHER_EXPENSE = 1;
    private static final Integer UPDATED_OTHER_EXPENSE = 2;

    private static final Integer DEFAULT_TOTAL = 1;
    private static final Integer UPDATED_TOTAL = 2;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final Mode DEFAULT_EXPENSE_MODE = Mode.NEFT;
    private static final Mode UPDATED_EXPENSE_MODE = Mode.UPI;

    private static final ExpensesType DEFAULT_TYPE = ExpensesType.OPERATING;
    private static final ExpensesType UPDATED_TYPE = ExpensesType.MARKETING;

    private static final byte[] DEFAULT_BILL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BILL = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BILL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BILL_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private ExpensesRepository expensesRepository;

    @Autowired
    private ExpensesMapper expensesMapper;

    @Autowired
    private ExpensesService expensesService;

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

    private MockMvc restExpensesMockMvc;

    private Expenses expenses;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpensesResource expensesResource = new ExpensesResource(expensesService);
        this.restExpensesMockMvc = MockMvcBuilders.standaloneSetup(expensesResource)
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
    public static Expenses createEntity(EntityManager em) {
        Expenses expenses = new Expenses()
            .item(DEFAULT_ITEM)
            .quantity(DEFAULT_QUANTITY)
            .rate(DEFAULT_RATE)
            .laborCost(DEFAULT_LABOR_COST)
            .otherExpense(DEFAULT_OTHER_EXPENSE)
            .total(DEFAULT_TOTAL)
            .date(DEFAULT_DATE)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .expenseMode(DEFAULT_EXPENSE_MODE)
            .type(DEFAULT_TYPE)
            .bill(DEFAULT_BILL)
            .billContentType(DEFAULT_BILL_CONTENT_TYPE)
            .remarks(DEFAULT_REMARKS);
        return expenses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expenses createUpdatedEntity(EntityManager em) {
        Expenses expenses = new Expenses()
            .item(UPDATED_ITEM)
            .quantity(UPDATED_QUANTITY)
            .rate(UPDATED_RATE)
            .laborCost(UPDATED_LABOR_COST)
            .otherExpense(UPDATED_OTHER_EXPENSE)
            .total(UPDATED_TOTAL)
            .date(UPDATED_DATE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .expenseMode(UPDATED_EXPENSE_MODE)
            .type(UPDATED_TYPE)
            .bill(UPDATED_BILL)
            .billContentType(UPDATED_BILL_CONTENT_TYPE)
            .remarks(UPDATED_REMARKS);
        return expenses;
    }

    @BeforeEach
    public void initTest() {
        expenses = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpenses() throws Exception {
        int databaseSizeBeforeCreate = expensesRepository.findAll().size();

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);
        restExpensesMockMvc.perform(post("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isCreated());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeCreate + 1);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getItem()).isEqualTo(DEFAULT_ITEM);
        assertThat(testExpenses.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testExpenses.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testExpenses.getLaborCost()).isEqualTo(DEFAULT_LABOR_COST);
        assertThat(testExpenses.getOtherExpense()).isEqualTo(DEFAULT_OTHER_EXPENSE);
        assertThat(testExpenses.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testExpenses.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testExpenses.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testExpenses.getExpenseMode()).isEqualTo(DEFAULT_EXPENSE_MODE);
        assertThat(testExpenses.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testExpenses.getBill()).isEqualTo(DEFAULT_BILL);
        assertThat(testExpenses.getBillContentType()).isEqualTo(DEFAULT_BILL_CONTENT_TYPE);
        assertThat(testExpenses.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createExpensesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expensesRepository.findAll().size();

        // Create the Expenses with an existing ID
        expenses.setId(1L);
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpensesMockMvc.perform(post("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkItemIsRequired() throws Exception {
        int databaseSizeBeforeTest = expensesRepository.findAll().size();
        // set the field null
        expenses.setItem(null);

        // Create the Expenses, which fails.
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        restExpensesMockMvc.perform(post("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = expensesRepository.findAll().size();
        // set the field null
        expenses.setTotal(null);

        // Create the Expenses, which fails.
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        restExpensesMockMvc.perform(post("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = expensesRepository.findAll().size();
        // set the field null
        expenses.setTransactionId(null);

        // Create the Expenses, which fails.
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        restExpensesMockMvc.perform(post("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        // Get all the expensesList
        restExpensesMockMvc.perform(get("/api/expenses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenses.getId().intValue())))
            .andExpect(jsonPath("$.[*].item").value(hasItem(DEFAULT_ITEM.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE)))
            .andExpect(jsonPath("$.[*].laborCost").value(hasItem(DEFAULT_LABOR_COST)))
            .andExpect(jsonPath("$.[*].otherExpense").value(hasItem(DEFAULT_OTHER_EXPENSE)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].expenseMode").value(hasItem(DEFAULT_EXPENSE_MODE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].billContentType").value(hasItem(DEFAULT_BILL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bill").value(hasItem(Base64Utils.encodeToString(DEFAULT_BILL))))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        // Get the expenses
        restExpensesMockMvc.perform(get("/api/expenses/{id}", expenses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expenses.getId().intValue()))
            .andExpect(jsonPath("$.item").value(DEFAULT_ITEM.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE))
            .andExpect(jsonPath("$.laborCost").value(DEFAULT_LABOR_COST))
            .andExpect(jsonPath("$.otherExpense").value(DEFAULT_OTHER_EXPENSE))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.expenseMode").value(DEFAULT_EXPENSE_MODE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.billContentType").value(DEFAULT_BILL_CONTENT_TYPE))
            .andExpect(jsonPath("$.bill").value(Base64Utils.encodeToString(DEFAULT_BILL)))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExpenses() throws Exception {
        // Get the expenses
        restExpensesMockMvc.perform(get("/api/expenses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();

        // Update the expenses
        Expenses updatedExpenses = expensesRepository.findById(expenses.getId()).get();
        // Disconnect from session so that the updates on updatedExpenses are not directly saved in db
        em.detach(updatedExpenses);
        updatedExpenses
            .item(UPDATED_ITEM)
            .quantity(UPDATED_QUANTITY)
            .rate(UPDATED_RATE)
            .laborCost(UPDATED_LABOR_COST)
            .otherExpense(UPDATED_OTHER_EXPENSE)
            .total(UPDATED_TOTAL)
            .date(UPDATED_DATE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .expenseMode(UPDATED_EXPENSE_MODE)
            .type(UPDATED_TYPE)
            .bill(UPDATED_BILL)
            .billContentType(UPDATED_BILL_CONTENT_TYPE)
            .remarks(UPDATED_REMARKS);
        ExpensesDTO expensesDTO = expensesMapper.toDto(updatedExpenses);

        restExpensesMockMvc.perform(put("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isOk());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
        Expenses testExpenses = expensesList.get(expensesList.size() - 1);
        assertThat(testExpenses.getItem()).isEqualTo(UPDATED_ITEM);
        assertThat(testExpenses.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testExpenses.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testExpenses.getLaborCost()).isEqualTo(UPDATED_LABOR_COST);
        assertThat(testExpenses.getOtherExpense()).isEqualTo(UPDATED_OTHER_EXPENSE);
        assertThat(testExpenses.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testExpenses.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testExpenses.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testExpenses.getExpenseMode()).isEqualTo(UPDATED_EXPENSE_MODE);
        assertThat(testExpenses.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExpenses.getBill()).isEqualTo(UPDATED_BILL);
        assertThat(testExpenses.getBillContentType()).isEqualTo(UPDATED_BILL_CONTENT_TYPE);
        assertThat(testExpenses.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingExpenses() throws Exception {
        int databaseSizeBeforeUpdate = expensesRepository.findAll().size();

        // Create the Expenses
        ExpensesDTO expensesDTO = expensesMapper.toDto(expenses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpensesMockMvc.perform(put("/api/expenses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expensesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expenses in the database
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExpenses() throws Exception {
        // Initialize the database
        expensesRepository.saveAndFlush(expenses);

        int databaseSizeBeforeDelete = expensesRepository.findAll().size();

        // Delete the expenses
        restExpensesMockMvc.perform(delete("/api/expenses/{id}", expenses.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expenses> expensesList = expensesRepository.findAll();
        assertThat(expensesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expenses.class);
        Expenses expenses1 = new Expenses();
        expenses1.setId(1L);
        Expenses expenses2 = new Expenses();
        expenses2.setId(expenses1.getId());
        assertThat(expenses1).isEqualTo(expenses2);
        expenses2.setId(2L);
        assertThat(expenses1).isNotEqualTo(expenses2);
        expenses1.setId(null);
        assertThat(expenses1).isNotEqualTo(expenses2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpensesDTO.class);
        ExpensesDTO expensesDTO1 = new ExpensesDTO();
        expensesDTO1.setId(1L);
        ExpensesDTO expensesDTO2 = new ExpensesDTO();
        assertThat(expensesDTO1).isNotEqualTo(expensesDTO2);
        expensesDTO2.setId(expensesDTO1.getId());
        assertThat(expensesDTO1).isEqualTo(expensesDTO2);
        expensesDTO2.setId(2L);
        assertThat(expensesDTO1).isNotEqualTo(expensesDTO2);
        expensesDTO1.setId(null);
        assertThat(expensesDTO1).isNotEqualTo(expensesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(expensesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(expensesMapper.fromId(null)).isNull();
    }
}
