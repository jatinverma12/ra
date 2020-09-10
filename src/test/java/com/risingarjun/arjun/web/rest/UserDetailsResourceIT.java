package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.UserDetails;
import com.risingarjun.arjun.repository.UserDetailsRepository;
import com.risingarjun.arjun.service.UserDetailsService;
import com.risingarjun.arjun.service.dto.UserDetailsDTO;
import com.risingarjun.arjun.service.mapper.UserDetailsMapper;
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

import com.risingarjun.arjun.domain.enumeration.City;
import com.risingarjun.arjun.domain.enumeration.State;
/**
 * Integration tests for the {@Link UserDetailsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class UserDetailsResourceIT {

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HOUSE_NO = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_STREET_NO = 1;
    private static final Integer UPDATED_STREET_NO = 2;

    private static final City DEFAULT_CITY = City.GURGAON;
    private static final City UPDATED_CITY = City.DELHI;

    private static final State DEFAULT_STATE = State.DELHI;
    private static final State UPDATED_STATE = State.HARYANA;

    private static final Integer DEFAULT_PINCODE = 1;
    private static final Integer UPDATED_PINCODE = 2;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserDetailsMapper userDetailsMapper;

    @Autowired
    private UserDetailsService userDetailsService;

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

    private MockMvc restUserDetailsMockMvc;

    private UserDetails userDetails;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserDetailsResource userDetailsResource = new UserDetailsResource(userDetailsService);
        this.restUserDetailsMockMvc = MockMvcBuilders.standaloneSetup(userDetailsResource)
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
    public static UserDetails createEntity(EntityManager em) {
        UserDetails userDetails = new UserDetails()
            .mobileNo(DEFAULT_MOBILE_NO)
            .dob(DEFAULT_DOB)
            .houseNo(DEFAULT_HOUSE_NO)
            .streetNo(DEFAULT_STREET_NO)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .pincode(DEFAULT_PINCODE);
        return userDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserDetails createUpdatedEntity(EntityManager em) {
        UserDetails userDetails = new UserDetails()
            .mobileNo(UPDATED_MOBILE_NO)
            .dob(UPDATED_DOB)
            .houseNo(UPDATED_HOUSE_NO)
            .streetNo(UPDATED_STREET_NO)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE);
        return userDetails;
    }

    @BeforeEach
    public void initTest() {
        userDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserDetails() throws Exception {
        int databaseSizeBeforeCreate = userDetailsRepository.findAll().size();

        // Create the UserDetails
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);
        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        UserDetails testUserDetails = userDetailsList.get(userDetailsList.size() - 1);
        assertThat(testUserDetails.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testUserDetails.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testUserDetails.getHouseNo()).isEqualTo(DEFAULT_HOUSE_NO);
        assertThat(testUserDetails.getStreetNo()).isEqualTo(DEFAULT_STREET_NO);
        assertThat(testUserDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testUserDetails.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testUserDetails.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    public void createUserDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userDetailsRepository.findAll().size();

        // Create the UserDetails with an existing ID
        userDetails.setId(1L);
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMobileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDetailsRepository.findAll().size();
        // set the field null
        userDetails.setMobileNo(null);

        // Create the UserDetails, which fails.
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);

        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDobIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDetailsRepository.findAll().size();
        // set the field null
        userDetails.setDob(null);

        // Create the UserDetails, which fails.
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);

        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHouseNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDetailsRepository.findAll().size();
        // set the field null
        userDetails.setHouseNo(null);

        // Create the UserDetails, which fails.
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);

        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDetailsRepository.findAll().size();
        // set the field null
        userDetails.setCity(null);

        // Create the UserDetails, which fails.
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);

        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userDetailsRepository.findAll().size();
        // set the field null
        userDetails.setState(null);

        // Create the UserDetails, which fails.
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);

        restUserDetailsMockMvc.perform(post("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        // Get all the userDetailsList
        restUserDetailsMockMvc.perform(get("/api/user-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].houseNo").value(hasItem(DEFAULT_HOUSE_NO.toString())))
            .andExpect(jsonPath("$.[*].streetNo").value(hasItem(DEFAULT_STREET_NO)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)));
    }
    
    @Test
    @Transactional
    public void getUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        // Get the userDetails
        restUserDetailsMockMvc.perform(get("/api/user-details/{id}", userDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userDetails.getId().intValue()))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.houseNo").value(DEFAULT_HOUSE_NO.toString()))
            .andExpect(jsonPath("$.streetNo").value(DEFAULT_STREET_NO))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE));
    }

    @Test
    @Transactional
    public void getNonExistingUserDetails() throws Exception {
        // Get the userDetails
        restUserDetailsMockMvc.perform(get("/api/user-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        int databaseSizeBeforeUpdate = userDetailsRepository.findAll().size();

        // Update the userDetails
        UserDetails updatedUserDetails = userDetailsRepository.findById(userDetails.getId()).get();
        // Disconnect from session so that the updates on updatedUserDetails are not directly saved in db
        em.detach(updatedUserDetails);
        updatedUserDetails
            .mobileNo(UPDATED_MOBILE_NO)
            .dob(UPDATED_DOB)
            .houseNo(UPDATED_HOUSE_NO)
            .streetNo(UPDATED_STREET_NO)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE);
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(updatedUserDetails);

        restUserDetailsMockMvc.perform(put("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeUpdate);
        UserDetails testUserDetails = userDetailsList.get(userDetailsList.size() - 1);
        assertThat(testUserDetails.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testUserDetails.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testUserDetails.getHouseNo()).isEqualTo(UPDATED_HOUSE_NO);
        assertThat(testUserDetails.getStreetNo()).isEqualTo(UPDATED_STREET_NO);
        assertThat(testUserDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUserDetails.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testUserDetails.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserDetails() throws Exception {
        int databaseSizeBeforeUpdate = userDetailsRepository.findAll().size();

        // Create the UserDetails
        UserDetailsDTO userDetailsDTO = userDetailsMapper.toDto(userDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserDetailsMockMvc.perform(put("/api/user-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserDetails in the database
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserDetails() throws Exception {
        // Initialize the database
        userDetailsRepository.saveAndFlush(userDetails);

        int databaseSizeBeforeDelete = userDetailsRepository.findAll().size();

        // Delete the userDetails
        restUserDetailsMockMvc.perform(delete("/api/user-details/{id}", userDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        assertThat(userDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetails.class);
        UserDetails userDetails1 = new UserDetails();
        userDetails1.setId(1L);
        UserDetails userDetails2 = new UserDetails();
        userDetails2.setId(userDetails1.getId());
        assertThat(userDetails1).isEqualTo(userDetails2);
        userDetails2.setId(2L);
        assertThat(userDetails1).isNotEqualTo(userDetails2);
        userDetails1.setId(null);
        assertThat(userDetails1).isNotEqualTo(userDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDetailsDTO.class);
        UserDetailsDTO userDetailsDTO1 = new UserDetailsDTO();
        userDetailsDTO1.setId(1L);
        UserDetailsDTO userDetailsDTO2 = new UserDetailsDTO();
        assertThat(userDetailsDTO1).isNotEqualTo(userDetailsDTO2);
        userDetailsDTO2.setId(userDetailsDTO1.getId());
        assertThat(userDetailsDTO1).isEqualTo(userDetailsDTO2);
        userDetailsDTO2.setId(2L);
        assertThat(userDetailsDTO1).isNotEqualTo(userDetailsDTO2);
        userDetailsDTO1.setId(null);
        assertThat(userDetailsDTO1).isNotEqualTo(userDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userDetailsMapper.fromId(null)).isNull();
    }
}
