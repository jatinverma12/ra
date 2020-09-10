package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Centers;
import com.risingarjun.arjun.repository.CentersRepository;
import com.risingarjun.arjun.service.CentersService;
import com.risingarjun.arjun.service.dto.CentersDTO;
import com.risingarjun.arjun.service.mapper.CentersMapper;
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

import com.risingarjun.arjun.domain.enumeration.City;
import com.risingarjun.arjun.domain.enumeration.State;
/**
 * Integration tests for the {@Link CentersResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class CentersResourceIT {

    private static final String DEFAULT_CENTER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CENTER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CENTER_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CENTER_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STREET_NO = 1;
    private static final Integer UPDATED_STREET_NO = 2;

    private static final City DEFAULT_CITY = City.GURGAON;
    private static final City UPDATED_CITY = City.DELHI;

    private static final State DEFAULT_STATE = State.DELHI;
    private static final State UPDATED_STATE = State.HARYANA;

    private static final Integer DEFAULT_PINCODE = 1;
    private static final Integer UPDATED_PINCODE = 2;

    @Autowired
    private CentersRepository centersRepository;

    @Autowired
    private CentersMapper centersMapper;

    @Autowired
    private CentersService centersService;

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

    private MockMvc restCentersMockMvc;

    private Centers centers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CentersResource centersResource = new CentersResource(centersService);
        this.restCentersMockMvc = MockMvcBuilders.standaloneSetup(centersResource)
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
    public static Centers createEntity(EntityManager em) {
        Centers centers = new Centers()
            .centerCode(DEFAULT_CENTER_CODE)
            .centerTitle(DEFAULT_CENTER_TITLE)
            .streetNo(DEFAULT_STREET_NO)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .pincode(DEFAULT_PINCODE);
        return centers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Centers createUpdatedEntity(EntityManager em) {
        Centers centers = new Centers()
            .centerCode(UPDATED_CENTER_CODE)
            .centerTitle(UPDATED_CENTER_TITLE)
            .streetNo(UPDATED_STREET_NO)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE);
        return centers;
    }

    @BeforeEach
    public void initTest() {
        centers = createEntity(em);
    }

    @Test
    @Transactional
    public void createCenters() throws Exception {
        int databaseSizeBeforeCreate = centersRepository.findAll().size();

        // Create the Centers
        CentersDTO centersDTO = centersMapper.toDto(centers);
        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isCreated());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeCreate + 1);
        Centers testCenters = centersList.get(centersList.size() - 1);
        assertThat(testCenters.getCenterCode()).isEqualTo(DEFAULT_CENTER_CODE);
        assertThat(testCenters.getCenterTitle()).isEqualTo(DEFAULT_CENTER_TITLE);
        assertThat(testCenters.getStreetNo()).isEqualTo(DEFAULT_STREET_NO);
        assertThat(testCenters.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCenters.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCenters.getPincode()).isEqualTo(DEFAULT_PINCODE);
    }

    @Test
    @Transactional
    public void createCentersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centersRepository.findAll().size();

        // Create the Centers with an existing ID
        centers.setId(1L);
        CentersDTO centersDTO = centersMapper.toDto(centers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCenterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = centersRepository.findAll().size();
        // set the field null
        centers.setCenterCode(null);

        // Create the Centers, which fails.
        CentersDTO centersDTO = centersMapper.toDto(centers);

        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCenterTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = centersRepository.findAll().size();
        // set the field null
        centers.setCenterTitle(null);

        // Create the Centers, which fails.
        CentersDTO centersDTO = centersMapper.toDto(centers);

        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = centersRepository.findAll().size();
        // set the field null
        centers.setCity(null);

        // Create the Centers, which fails.
        CentersDTO centersDTO = centersMapper.toDto(centers);

        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = centersRepository.findAll().size();
        // set the field null
        centers.setState(null);

        // Create the Centers, which fails.
        CentersDTO centersDTO = centersMapper.toDto(centers);

        restCentersMockMvc.perform(post("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        // Get all the centersList
        restCentersMockMvc.perform(get("/api/centers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centers.getId().intValue())))
            .andExpect(jsonPath("$.[*].centerCode").value(hasItem(DEFAULT_CENTER_CODE.toString())))
            .andExpect(jsonPath("$.[*].centerTitle").value(hasItem(DEFAULT_CENTER_TITLE.toString())))
            .andExpect(jsonPath("$.[*].streetNo").value(hasItem(DEFAULT_STREET_NO)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)));
    }
    
    @Test
    @Transactional
    public void getCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        // Get the centers
        restCentersMockMvc.perform(get("/api/centers/{id}", centers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centers.getId().intValue()))
            .andExpect(jsonPath("$.centerCode").value(DEFAULT_CENTER_CODE.toString()))
            .andExpect(jsonPath("$.centerTitle").value(DEFAULT_CENTER_TITLE.toString()))
            .andExpect(jsonPath("$.streetNo").value(DEFAULT_STREET_NO))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE));
    }

    @Test
    @Transactional
    public void getNonExistingCenters() throws Exception {
        // Get the centers
        restCentersMockMvc.perform(get("/api/centers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        int databaseSizeBeforeUpdate = centersRepository.findAll().size();

        // Update the centers
        Centers updatedCenters = centersRepository.findById(centers.getId()).get();
        // Disconnect from session so that the updates on updatedCenters are not directly saved in db
        em.detach(updatedCenters);
        updatedCenters
            .centerCode(UPDATED_CENTER_CODE)
            .centerTitle(UPDATED_CENTER_TITLE)
            .streetNo(UPDATED_STREET_NO)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE);
        CentersDTO centersDTO = centersMapper.toDto(updatedCenters);

        restCentersMockMvc.perform(put("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isOk());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeUpdate);
        Centers testCenters = centersList.get(centersList.size() - 1);
        assertThat(testCenters.getCenterCode()).isEqualTo(UPDATED_CENTER_CODE);
        assertThat(testCenters.getCenterTitle()).isEqualTo(UPDATED_CENTER_TITLE);
        assertThat(testCenters.getStreetNo()).isEqualTo(UPDATED_STREET_NO);
        assertThat(testCenters.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCenters.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCenters.getPincode()).isEqualTo(UPDATED_PINCODE);
    }

    @Test
    @Transactional
    public void updateNonExistingCenters() throws Exception {
        int databaseSizeBeforeUpdate = centersRepository.findAll().size();

        // Create the Centers
        CentersDTO centersDTO = centersMapper.toDto(centers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentersMockMvc.perform(put("/api/centers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Centers in the database
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCenters() throws Exception {
        // Initialize the database
        centersRepository.saveAndFlush(centers);

        int databaseSizeBeforeDelete = centersRepository.findAll().size();

        // Delete the centers
        restCentersMockMvc.perform(delete("/api/centers/{id}", centers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Centers> centersList = centersRepository.findAll();
        assertThat(centersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Centers.class);
        Centers centers1 = new Centers();
        centers1.setId(1L);
        Centers centers2 = new Centers();
        centers2.setId(centers1.getId());
        assertThat(centers1).isEqualTo(centers2);
        centers2.setId(2L);
        assertThat(centers1).isNotEqualTo(centers2);
        centers1.setId(null);
        assertThat(centers1).isNotEqualTo(centers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentersDTO.class);
        CentersDTO centersDTO1 = new CentersDTO();
        centersDTO1.setId(1L);
        CentersDTO centersDTO2 = new CentersDTO();
        assertThat(centersDTO1).isNotEqualTo(centersDTO2);
        centersDTO2.setId(centersDTO1.getId());
        assertThat(centersDTO1).isEqualTo(centersDTO2);
        centersDTO2.setId(2L);
        assertThat(centersDTO1).isNotEqualTo(centersDTO2);
        centersDTO1.setId(null);
        assertThat(centersDTO1).isNotEqualTo(centersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(centersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(centersMapper.fromId(null)).isNull();
    }
}
