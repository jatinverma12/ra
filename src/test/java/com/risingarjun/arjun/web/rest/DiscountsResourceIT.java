package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Discounts;
import com.risingarjun.arjun.repository.DiscountsRepository;
import com.risingarjun.arjun.service.DiscountsService;
import com.risingarjun.arjun.service.dto.DiscountsDTO;
import com.risingarjun.arjun.service.mapper.DiscountsMapper;
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
 * Integration tests for the {@Link DiscountsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class DiscountsResourceIT {

    private static final Integer DEFAULT_SUBJECT_2 = 1;
    private static final Integer UPDATED_SUBJECT_2 = 2;

    private static final Integer DEFAULT_SUBJECT_3 = 1;
    private static final Integer UPDATED_SUBJECT_3 = 2;

    private static final Integer DEFAULT_SUBJECT_4 = 1;
    private static final Integer UPDATED_SUBJECT_4 = 2;

    private static final Integer DEFAULT_SUBJECT_5 = 1;
    private static final Integer UPDATED_SUBJECT_5 = 2;

    private static final Integer DEFAULT_SUBJECT_6 = 1;
    private static final Integer UPDATED_SUBJECT_6 = 2;

    private static final Integer DEFAULT_SUBJECT_7 = 1;
    private static final Integer UPDATED_SUBJECT_7 = 2;

    private static final Integer DEFAULT_SUBJECT_8 = 1;
    private static final Integer UPDATED_SUBJECT_8 = 2;

    private static final Integer DEFAULT_QUARTERLY = 1;
    private static final Integer UPDATED_QUARTERLY = 2;

    private static final Integer DEFAULT_HALF_YEARLY = 1;
    private static final Integer UPDATED_HALF_YEARLY = 2;

    private static final Integer DEFAULT_ANNUALLY = 1;
    private static final Integer UPDATED_ANNUALLY = 2;

    private static final Integer DEFAULT_SIBLING = 1;
    private static final Integer UPDATED_SIBLING = 2;

    private static final Integer DEFAULT_REFERRAL = 1;
    private static final Integer UPDATED_REFERRAL = 2;

    @Autowired
    private DiscountsRepository discountsRepository;

    @Autowired
    private DiscountsMapper discountsMapper;

    @Autowired
    private DiscountsService discountsService;

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

    private MockMvc restDiscountsMockMvc;

    private Discounts discounts;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscountsResource discountsResource = new DiscountsResource(discountsService);
        this.restDiscountsMockMvc = MockMvcBuilders.standaloneSetup(discountsResource)
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
    public static Discounts createEntity(EntityManager em) {
        Discounts discounts = new Discounts()
            .subject2(DEFAULT_SUBJECT_2)
            .subject3(DEFAULT_SUBJECT_3)
            .subject4(DEFAULT_SUBJECT_4)
            .subject5(DEFAULT_SUBJECT_5)
            .subject6(DEFAULT_SUBJECT_6)
            .subject7(DEFAULT_SUBJECT_7)
            .subject8(DEFAULT_SUBJECT_8)
            .quarterly(DEFAULT_QUARTERLY)
            .halfYearly(DEFAULT_HALF_YEARLY)
            .annually(DEFAULT_ANNUALLY)
            .sibling(DEFAULT_SIBLING)
            .referral(DEFAULT_REFERRAL);
        return discounts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discounts createUpdatedEntity(EntityManager em) {
        Discounts discounts = new Discounts()
            .subject2(UPDATED_SUBJECT_2)
            .subject3(UPDATED_SUBJECT_3)
            .subject4(UPDATED_SUBJECT_4)
            .subject5(UPDATED_SUBJECT_5)
            .subject6(UPDATED_SUBJECT_6)
            .subject7(UPDATED_SUBJECT_7)
            .subject8(UPDATED_SUBJECT_8)
            .quarterly(UPDATED_QUARTERLY)
            .halfYearly(UPDATED_HALF_YEARLY)
            .annually(UPDATED_ANNUALLY)
            .sibling(UPDATED_SIBLING)
            .referral(UPDATED_REFERRAL);
        return discounts;
    }

    @BeforeEach
    public void initTest() {
        discounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscounts() throws Exception {
        int databaseSizeBeforeCreate = discountsRepository.findAll().size();

        // Create the Discounts
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);
        restDiscountsMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isCreated());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeCreate + 1);
        Discounts testDiscounts = discountsList.get(discountsList.size() - 1);
        assertThat(testDiscounts.getSubject2()).isEqualTo(DEFAULT_SUBJECT_2);
        assertThat(testDiscounts.getSubject3()).isEqualTo(DEFAULT_SUBJECT_3);
        assertThat(testDiscounts.getSubject4()).isEqualTo(DEFAULT_SUBJECT_4);
        assertThat(testDiscounts.getSubject5()).isEqualTo(DEFAULT_SUBJECT_5);
        assertThat(testDiscounts.getSubject6()).isEqualTo(DEFAULT_SUBJECT_6);
        assertThat(testDiscounts.getSubject7()).isEqualTo(DEFAULT_SUBJECT_7);
        assertThat(testDiscounts.getSubject8()).isEqualTo(DEFAULT_SUBJECT_8);
        assertThat(testDiscounts.getQuarterly()).isEqualTo(DEFAULT_QUARTERLY);
        assertThat(testDiscounts.getHalfYearly()).isEqualTo(DEFAULT_HALF_YEARLY);
        assertThat(testDiscounts.getAnnually()).isEqualTo(DEFAULT_ANNUALLY);
        assertThat(testDiscounts.getSibling()).isEqualTo(DEFAULT_SIBLING);
        assertThat(testDiscounts.getReferral()).isEqualTo(DEFAULT_REFERRAL);
    }

    @Test
    @Transactional
    public void createDiscountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountsRepository.findAll().size();

        // Create the Discounts with an existing ID
        discounts.setId(1L);
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountsMockMvc.perform(post("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get all the discountsList
        restDiscountsMockMvc.perform(get("/api/discounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject2").value(hasItem(DEFAULT_SUBJECT_2)))
            .andExpect(jsonPath("$.[*].subject3").value(hasItem(DEFAULT_SUBJECT_3)))
            .andExpect(jsonPath("$.[*].subject4").value(hasItem(DEFAULT_SUBJECT_4)))
            .andExpect(jsonPath("$.[*].subject5").value(hasItem(DEFAULT_SUBJECT_5)))
            .andExpect(jsonPath("$.[*].subject6").value(hasItem(DEFAULT_SUBJECT_6)))
            .andExpect(jsonPath("$.[*].subject7").value(hasItem(DEFAULT_SUBJECT_7)))
            .andExpect(jsonPath("$.[*].subject8").value(hasItem(DEFAULT_SUBJECT_8)))
            .andExpect(jsonPath("$.[*].quarterly").value(hasItem(DEFAULT_QUARTERLY)))
            .andExpect(jsonPath("$.[*].halfYearly").value(hasItem(DEFAULT_HALF_YEARLY)))
            .andExpect(jsonPath("$.[*].annually").value(hasItem(DEFAULT_ANNUALLY)))
            .andExpect(jsonPath("$.[*].sibling").value(hasItem(DEFAULT_SIBLING)))
            .andExpect(jsonPath("$.[*].referral").value(hasItem(DEFAULT_REFERRAL)));
    }
    
    @Test
    @Transactional
    public void getDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        // Get the discounts
        restDiscountsMockMvc.perform(get("/api/discounts/{id}", discounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discounts.getId().intValue()))
            .andExpect(jsonPath("$.subject2").value(DEFAULT_SUBJECT_2))
            .andExpect(jsonPath("$.subject3").value(DEFAULT_SUBJECT_3))
            .andExpect(jsonPath("$.subject4").value(DEFAULT_SUBJECT_4))
            .andExpect(jsonPath("$.subject5").value(DEFAULT_SUBJECT_5))
            .andExpect(jsonPath("$.subject6").value(DEFAULT_SUBJECT_6))
            .andExpect(jsonPath("$.subject7").value(DEFAULT_SUBJECT_7))
            .andExpect(jsonPath("$.subject8").value(DEFAULT_SUBJECT_8))
            .andExpect(jsonPath("$.quarterly").value(DEFAULT_QUARTERLY))
            .andExpect(jsonPath("$.halfYearly").value(DEFAULT_HALF_YEARLY))
            .andExpect(jsonPath("$.annually").value(DEFAULT_ANNUALLY))
            .andExpect(jsonPath("$.sibling").value(DEFAULT_SIBLING))
            .andExpect(jsonPath("$.referral").value(DEFAULT_REFERRAL));
    }

    @Test
    @Transactional
    public void getNonExistingDiscounts() throws Exception {
        // Get the discounts
        restDiscountsMockMvc.perform(get("/api/discounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        int databaseSizeBeforeUpdate = discountsRepository.findAll().size();

        // Update the discounts
        Discounts updatedDiscounts = discountsRepository.findById(discounts.getId()).get();
        // Disconnect from session so that the updates on updatedDiscounts are not directly saved in db
        em.detach(updatedDiscounts);
        updatedDiscounts
            .subject2(UPDATED_SUBJECT_2)
            .subject3(UPDATED_SUBJECT_3)
            .subject4(UPDATED_SUBJECT_4)
            .subject5(UPDATED_SUBJECT_5)
            .subject6(UPDATED_SUBJECT_6)
            .subject7(UPDATED_SUBJECT_7)
            .subject8(UPDATED_SUBJECT_8)
            .quarterly(UPDATED_QUARTERLY)
            .halfYearly(UPDATED_HALF_YEARLY)
            .annually(UPDATED_ANNUALLY)
            .sibling(UPDATED_SIBLING)
            .referral(UPDATED_REFERRAL);
        DiscountsDTO discountsDTO = discountsMapper.toDto(updatedDiscounts);

        restDiscountsMockMvc.perform(put("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isOk());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeUpdate);
        Discounts testDiscounts = discountsList.get(discountsList.size() - 1);
        assertThat(testDiscounts.getSubject2()).isEqualTo(UPDATED_SUBJECT_2);
        assertThat(testDiscounts.getSubject3()).isEqualTo(UPDATED_SUBJECT_3);
        assertThat(testDiscounts.getSubject4()).isEqualTo(UPDATED_SUBJECT_4);
        assertThat(testDiscounts.getSubject5()).isEqualTo(UPDATED_SUBJECT_5);
        assertThat(testDiscounts.getSubject6()).isEqualTo(UPDATED_SUBJECT_6);
        assertThat(testDiscounts.getSubject7()).isEqualTo(UPDATED_SUBJECT_7);
        assertThat(testDiscounts.getSubject8()).isEqualTo(UPDATED_SUBJECT_8);
        assertThat(testDiscounts.getQuarterly()).isEqualTo(UPDATED_QUARTERLY);
        assertThat(testDiscounts.getHalfYearly()).isEqualTo(UPDATED_HALF_YEARLY);
        assertThat(testDiscounts.getAnnually()).isEqualTo(UPDATED_ANNUALLY);
        assertThat(testDiscounts.getSibling()).isEqualTo(UPDATED_SIBLING);
        assertThat(testDiscounts.getReferral()).isEqualTo(UPDATED_REFERRAL);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscounts() throws Exception {
        int databaseSizeBeforeUpdate = discountsRepository.findAll().size();

        // Create the Discounts
        DiscountsDTO discountsDTO = discountsMapper.toDto(discounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountsMockMvc.perform(put("/api/discounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discounts in the database
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscounts() throws Exception {
        // Initialize the database
        discountsRepository.saveAndFlush(discounts);

        int databaseSizeBeforeDelete = discountsRepository.findAll().size();

        // Delete the discounts
        restDiscountsMockMvc.perform(delete("/api/discounts/{id}", discounts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discounts> discountsList = discountsRepository.findAll();
        assertThat(discountsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Discounts.class);
        Discounts discounts1 = new Discounts();
        discounts1.setId(1L);
        Discounts discounts2 = new Discounts();
        discounts2.setId(discounts1.getId());
        assertThat(discounts1).isEqualTo(discounts2);
        discounts2.setId(2L);
        assertThat(discounts1).isNotEqualTo(discounts2);
        discounts1.setId(null);
        assertThat(discounts1).isNotEqualTo(discounts2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountsDTO.class);
        DiscountsDTO discountsDTO1 = new DiscountsDTO();
        discountsDTO1.setId(1L);
        DiscountsDTO discountsDTO2 = new DiscountsDTO();
        assertThat(discountsDTO1).isNotEqualTo(discountsDTO2);
        discountsDTO2.setId(discountsDTO1.getId());
        assertThat(discountsDTO1).isEqualTo(discountsDTO2);
        discountsDTO2.setId(2L);
        assertThat(discountsDTO1).isNotEqualTo(discountsDTO2);
        discountsDTO1.setId(null);
        assertThat(discountsDTO1).isNotEqualTo(discountsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(discountsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(discountsMapper.fromId(null)).isNull();
    }
}
