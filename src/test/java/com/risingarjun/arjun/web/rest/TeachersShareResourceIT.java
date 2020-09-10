package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.TeachersShare;
import com.risingarjun.arjun.repository.TeachersShareRepository;
import com.risingarjun.arjun.service.TeachersShareService;
import com.risingarjun.arjun.service.dto.TeachersShareDTO;
import com.risingarjun.arjun.service.mapper.TeachersShareMapper;
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
 * Integration tests for the {@Link TeachersShareResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class TeachersShareResourceIT {

    private static final Integer DEFAULT_SHARE = 100;
    private static final Integer UPDATED_SHARE = 99;

    private static final Integer DEFAULT_PLANNED_CLASSES = 1;
    private static final Integer UPDATED_PLANNED_CLASSES = 2;

    private static final Integer DEFAULT_ACTUAL_CLASSES = 1;
    private static final Integer UPDATED_ACTUAL_CLASSES = 2;

    private static final Integer DEFAULT_SHARE_CORRECTION = 1;
    private static final Integer UPDATED_SHARE_CORRECTION = 2;

    private static final Month DEFAULT_MONTH = Month.JAN;
    private static final Month UPDATED_MONTH = Month.FEB;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private TeachersShareRepository teachersShareRepository;

    @Autowired
    private TeachersShareMapper teachersShareMapper;

    @Autowired
    private TeachersShareService teachersShareService;

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

    private MockMvc restTeachersShareMockMvc;

    private TeachersShare teachersShare;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeachersShareResource teachersShareResource = new TeachersShareResource(teachersShareService);
        this.restTeachersShareMockMvc = MockMvcBuilders.standaloneSetup(teachersShareResource)
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
    public static TeachersShare createEntity(EntityManager em) {
        TeachersShare teachersShare = new TeachersShare()
            .share(DEFAULT_SHARE)
            .plannedClasses(DEFAULT_PLANNED_CLASSES)
            .actualClasses(DEFAULT_ACTUAL_CLASSES)
            .shareCorrection(DEFAULT_SHARE_CORRECTION)
            .month(DEFAULT_MONTH)
            .remarks(DEFAULT_REMARKS);
        return teachersShare;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeachersShare createUpdatedEntity(EntityManager em) {
        TeachersShare teachersShare = new TeachersShare()
            .share(UPDATED_SHARE)
            .plannedClasses(UPDATED_PLANNED_CLASSES)
            .actualClasses(UPDATED_ACTUAL_CLASSES)
            .shareCorrection(UPDATED_SHARE_CORRECTION)
            .month(UPDATED_MONTH)
            .remarks(UPDATED_REMARKS);
        return teachersShare;
    }

    @BeforeEach
    public void initTest() {
        teachersShare = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeachersShare() throws Exception {
        int databaseSizeBeforeCreate = teachersShareRepository.findAll().size();

        // Create the TeachersShare
        TeachersShareDTO teachersShareDTO = teachersShareMapper.toDto(teachersShare);
        restTeachersShareMockMvc.perform(post("/api/teachers-shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersShareDTO)))
            .andExpect(status().isCreated());

        // Validate the TeachersShare in the database
        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeCreate + 1);
        TeachersShare testTeachersShare = teachersShareList.get(teachersShareList.size() - 1);
        assertThat(testTeachersShare.getShare()).isEqualTo(DEFAULT_SHARE);
        assertThat(testTeachersShare.getPlannedClasses()).isEqualTo(DEFAULT_PLANNED_CLASSES);
        assertThat(testTeachersShare.getActualClasses()).isEqualTo(DEFAULT_ACTUAL_CLASSES);
        assertThat(testTeachersShare.getShareCorrection()).isEqualTo(DEFAULT_SHARE_CORRECTION);
        assertThat(testTeachersShare.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testTeachersShare.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createTeachersShareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teachersShareRepository.findAll().size();

        // Create the TeachersShare with an existing ID
        teachersShare.setId(1L);
        TeachersShareDTO teachersShareDTO = teachersShareMapper.toDto(teachersShare);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachersShareMockMvc.perform(post("/api/teachers-shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersShareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeachersShare in the database
        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkShareIsRequired() throws Exception {
        int databaseSizeBeforeTest = teachersShareRepository.findAll().size();
        // set the field null
        teachersShare.setShare(null);

        // Create the TeachersShare, which fails.
        TeachersShareDTO teachersShareDTO = teachersShareMapper.toDto(teachersShare);

        restTeachersShareMockMvc.perform(post("/api/teachers-shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersShareDTO)))
            .andExpect(status().isBadRequest());

        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlannedClassesIsRequired() throws Exception {
        int databaseSizeBeforeTest = teachersShareRepository.findAll().size();
        // set the field null
        teachersShare.setPlannedClasses(null);

        // Create the TeachersShare, which fails.
        TeachersShareDTO teachersShareDTO = teachersShareMapper.toDto(teachersShare);

        restTeachersShareMockMvc.perform(post("/api/teachers-shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersShareDTO)))
            .andExpect(status().isBadRequest());

        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActualClassesIsRequired() throws Exception {
        int databaseSizeBeforeTest = teachersShareRepository.findAll().size();
        // set the field null
        teachersShare.setActualClasses(null);

        // Create the TeachersShare, which fails.
        TeachersShareDTO teachersShareDTO = teachersShareMapper.toDto(teachersShare);

        restTeachersShareMockMvc.perform(post("/api/teachers-shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersShareDTO)))
            .andExpect(status().isBadRequest());

        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeachersShares() throws Exception {
        // Initialize the database
        teachersShareRepository.saveAndFlush(teachersShare);

        // Get all the teachersShareList
        restTeachersShareMockMvc.perform(get("/api/teachers-shares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachersShare.getId().intValue())))
            .andExpect(jsonPath("$.[*].share").value(hasItem(DEFAULT_SHARE)))
            .andExpect(jsonPath("$.[*].plannedClasses").value(hasItem(DEFAULT_PLANNED_CLASSES)))
            .andExpect(jsonPath("$.[*].actualClasses").value(hasItem(DEFAULT_ACTUAL_CLASSES)))
            .andExpect(jsonPath("$.[*].shareCorrection").value(hasItem(DEFAULT_SHARE_CORRECTION)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }
    
    @Test
    @Transactional
    public void getTeachersShare() throws Exception {
        // Initialize the database
        teachersShareRepository.saveAndFlush(teachersShare);

        // Get the teachersShare
        restTeachersShareMockMvc.perform(get("/api/teachers-shares/{id}", teachersShare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teachersShare.getId().intValue()))
            .andExpect(jsonPath("$.share").value(DEFAULT_SHARE))
            .andExpect(jsonPath("$.plannedClasses").value(DEFAULT_PLANNED_CLASSES))
            .andExpect(jsonPath("$.actualClasses").value(DEFAULT_ACTUAL_CLASSES))
            .andExpect(jsonPath("$.shareCorrection").value(DEFAULT_SHARE_CORRECTION))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTeachersShare() throws Exception {
        // Get the teachersShare
        restTeachersShareMockMvc.perform(get("/api/teachers-shares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeachersShare() throws Exception {
        // Initialize the database
        teachersShareRepository.saveAndFlush(teachersShare);

        int databaseSizeBeforeUpdate = teachersShareRepository.findAll().size();

        // Update the teachersShare
        TeachersShare updatedTeachersShare = teachersShareRepository.findById(teachersShare.getId()).get();
        // Disconnect from session so that the updates on updatedTeachersShare are not directly saved in db
        em.detach(updatedTeachersShare);
        updatedTeachersShare
            .share(UPDATED_SHARE)
            .plannedClasses(UPDATED_PLANNED_CLASSES)
            .actualClasses(UPDATED_ACTUAL_CLASSES)
            .shareCorrection(UPDATED_SHARE_CORRECTION)
            .month(UPDATED_MONTH)
            .remarks(UPDATED_REMARKS);
        TeachersShareDTO teachersShareDTO = teachersShareMapper.toDto(updatedTeachersShare);

        restTeachersShareMockMvc.perform(put("/api/teachers-shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersShareDTO)))
            .andExpect(status().isOk());

        // Validate the TeachersShare in the database
        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeUpdate);
        TeachersShare testTeachersShare = teachersShareList.get(teachersShareList.size() - 1);
        assertThat(testTeachersShare.getShare()).isEqualTo(UPDATED_SHARE);
        assertThat(testTeachersShare.getPlannedClasses()).isEqualTo(UPDATED_PLANNED_CLASSES);
        assertThat(testTeachersShare.getActualClasses()).isEqualTo(UPDATED_ACTUAL_CLASSES);
        assertThat(testTeachersShare.getShareCorrection()).isEqualTo(UPDATED_SHARE_CORRECTION);
        assertThat(testTeachersShare.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testTeachersShare.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingTeachersShare() throws Exception {
        int databaseSizeBeforeUpdate = teachersShareRepository.findAll().size();

        // Create the TeachersShare
        TeachersShareDTO teachersShareDTO = teachersShareMapper.toDto(teachersShare);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachersShareMockMvc.perform(put("/api/teachers-shares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersShareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TeachersShare in the database
        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeachersShare() throws Exception {
        // Initialize the database
        teachersShareRepository.saveAndFlush(teachersShare);

        int databaseSizeBeforeDelete = teachersShareRepository.findAll().size();

        // Delete the teachersShare
        restTeachersShareMockMvc.perform(delete("/api/teachers-shares/{id}", teachersShare.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeachersShare> teachersShareList = teachersShareRepository.findAll();
        assertThat(teachersShareList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachersShare.class);
        TeachersShare teachersShare1 = new TeachersShare();
        teachersShare1.setId(1L);
        TeachersShare teachersShare2 = new TeachersShare();
        teachersShare2.setId(teachersShare1.getId());
        assertThat(teachersShare1).isEqualTo(teachersShare2);
        teachersShare2.setId(2L);
        assertThat(teachersShare1).isNotEqualTo(teachersShare2);
        teachersShare1.setId(null);
        assertThat(teachersShare1).isNotEqualTo(teachersShare2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachersShareDTO.class);
        TeachersShareDTO teachersShareDTO1 = new TeachersShareDTO();
        teachersShareDTO1.setId(1L);
        TeachersShareDTO teachersShareDTO2 = new TeachersShareDTO();
        assertThat(teachersShareDTO1).isNotEqualTo(teachersShareDTO2);
        teachersShareDTO2.setId(teachersShareDTO1.getId());
        assertThat(teachersShareDTO1).isEqualTo(teachersShareDTO2);
        teachersShareDTO2.setId(2L);
        assertThat(teachersShareDTO1).isNotEqualTo(teachersShareDTO2);
        teachersShareDTO1.setId(null);
        assertThat(teachersShareDTO1).isNotEqualTo(teachersShareDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teachersShareMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teachersShareMapper.fromId(null)).isNull();
    }
}
