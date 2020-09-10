package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Features;
import com.risingarjun.arjun.repository.FeaturesRepository;
import com.risingarjun.arjun.service.FeaturesService;
import com.risingarjun.arjun.service.dto.FeaturesDTO;
import com.risingarjun.arjun.service.mapper.FeaturesMapper;
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
 * Integration tests for the {@Link FeaturesResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class FeaturesResourceIT {

    private static final String DEFAULT_FEATURE_ID = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURE_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_DETAIL = "BBBBBBBBBB";

    @Autowired
    private FeaturesRepository featuresRepository;

    @Autowired
    private FeaturesMapper featuresMapper;

    @Autowired
    private FeaturesService featuresService;

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

    private MockMvc restFeaturesMockMvc;

    private Features features;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FeaturesResource featuresResource = new FeaturesResource(featuresService);
        this.restFeaturesMockMvc = MockMvcBuilders.standaloneSetup(featuresResource)
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
    public static Features createEntity(EntityManager em) {
        Features features = new Features()
            .featureId(DEFAULT_FEATURE_ID)
            .featureDetail(DEFAULT_FEATURE_DETAIL);
        return features;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Features createUpdatedEntity(EntityManager em) {
        Features features = new Features()
            .featureId(UPDATED_FEATURE_ID)
            .featureDetail(UPDATED_FEATURE_DETAIL);
        return features;
    }

    @BeforeEach
    public void initTest() {
        features = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeatures() throws Exception {
        int databaseSizeBeforeCreate = featuresRepository.findAll().size();

        // Create the Features
        FeaturesDTO featuresDTO = featuresMapper.toDto(features);
        restFeaturesMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featuresDTO)))
            .andExpect(status().isCreated());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeCreate + 1);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getFeatureId()).isEqualTo(DEFAULT_FEATURE_ID);
        assertThat(testFeatures.getFeatureDetail()).isEqualTo(DEFAULT_FEATURE_DETAIL);
    }

    @Test
    @Transactional
    public void createFeaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = featuresRepository.findAll().size();

        // Create the Features with an existing ID
        features.setId(1L);
        FeaturesDTO featuresDTO = featuresMapper.toDto(features);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeaturesMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featuresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFeatureIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = featuresRepository.findAll().size();
        // set the field null
        features.setFeatureId(null);

        // Create the Features, which fails.
        FeaturesDTO featuresDTO = featuresMapper.toDto(features);

        restFeaturesMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featuresDTO)))
            .andExpect(status().isBadRequest());

        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeatureDetailIsRequired() throws Exception {
        int databaseSizeBeforeTest = featuresRepository.findAll().size();
        // set the field null
        features.setFeatureDetail(null);

        // Create the Features, which fails.
        FeaturesDTO featuresDTO = featuresMapper.toDto(features);

        restFeaturesMockMvc.perform(post("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featuresDTO)))
            .andExpect(status().isBadRequest());

        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get all the featuresList
        restFeaturesMockMvc.perform(get("/api/features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(features.getId().intValue())))
            .andExpect(jsonPath("$.[*].featureId").value(hasItem(DEFAULT_FEATURE_ID.toString())))
            .andExpect(jsonPath("$.[*].featureDetail").value(hasItem(DEFAULT_FEATURE_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        // Get the features
        restFeaturesMockMvc.perform(get("/api/features/{id}", features.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(features.getId().intValue()))
            .andExpect(jsonPath("$.featureId").value(DEFAULT_FEATURE_ID.toString()))
            .andExpect(jsonPath("$.featureDetail").value(DEFAULT_FEATURE_DETAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeatures() throws Exception {
        // Get the features
        restFeaturesMockMvc.perform(get("/api/features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();

        // Update the features
        Features updatedFeatures = featuresRepository.findById(features.getId()).get();
        // Disconnect from session so that the updates on updatedFeatures are not directly saved in db
        em.detach(updatedFeatures);
        updatedFeatures
            .featureId(UPDATED_FEATURE_ID)
            .featureDetail(UPDATED_FEATURE_DETAIL);
        FeaturesDTO featuresDTO = featuresMapper.toDto(updatedFeatures);

        restFeaturesMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featuresDTO)))
            .andExpect(status().isOk());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
        Features testFeatures = featuresList.get(featuresList.size() - 1);
        assertThat(testFeatures.getFeatureId()).isEqualTo(UPDATED_FEATURE_ID);
        assertThat(testFeatures.getFeatureDetail()).isEqualTo(UPDATED_FEATURE_DETAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingFeatures() throws Exception {
        int databaseSizeBeforeUpdate = featuresRepository.findAll().size();

        // Create the Features
        FeaturesDTO featuresDTO = featuresMapper.toDto(features);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeaturesMockMvc.perform(put("/api/features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(featuresDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Features in the database
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFeatures() throws Exception {
        // Initialize the database
        featuresRepository.saveAndFlush(features);

        int databaseSizeBeforeDelete = featuresRepository.findAll().size();

        // Delete the features
        restFeaturesMockMvc.perform(delete("/api/features/{id}", features.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Features> featuresList = featuresRepository.findAll();
        assertThat(featuresList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Features.class);
        Features features1 = new Features();
        features1.setId(1L);
        Features features2 = new Features();
        features2.setId(features1.getId());
        assertThat(features1).isEqualTo(features2);
        features2.setId(2L);
        assertThat(features1).isNotEqualTo(features2);
        features1.setId(null);
        assertThat(features1).isNotEqualTo(features2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FeaturesDTO.class);
        FeaturesDTO featuresDTO1 = new FeaturesDTO();
        featuresDTO1.setId(1L);
        FeaturesDTO featuresDTO2 = new FeaturesDTO();
        assertThat(featuresDTO1).isNotEqualTo(featuresDTO2);
        featuresDTO2.setId(featuresDTO1.getId());
        assertThat(featuresDTO1).isEqualTo(featuresDTO2);
        featuresDTO2.setId(2L);
        assertThat(featuresDTO1).isNotEqualTo(featuresDTO2);
        featuresDTO1.setId(null);
        assertThat(featuresDTO1).isNotEqualTo(featuresDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(featuresMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(featuresMapper.fromId(null)).isNull();
    }
}
