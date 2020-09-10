package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Scholarships;
import com.risingarjun.arjun.repository.ScholarshipsRepository;
import com.risingarjun.arjun.service.ScholarshipsService;
import com.risingarjun.arjun.service.dto.ScholarshipsDTO;
import com.risingarjun.arjun.service.mapper.ScholarshipsMapper;
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
 * Integration tests for the {@Link ScholarshipsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class ScholarshipsResourceIT {

    private static final Integer DEFAULT_MIN_MARKS = 1;
    private static final Integer UPDATED_MIN_MARKS = 2;

    private static final Integer DEFAULT_PERCENT = 1;
    private static final Integer UPDATED_PERCENT = 2;

    @Autowired
    private ScholarshipsRepository scholarshipsRepository;

    @Autowired
    private ScholarshipsMapper scholarshipsMapper;

    @Autowired
    private ScholarshipsService scholarshipsService;

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

    private MockMvc restScholarshipsMockMvc;

    private Scholarships scholarships;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScholarshipsResource scholarshipsResource = new ScholarshipsResource(scholarshipsService);
        this.restScholarshipsMockMvc = MockMvcBuilders.standaloneSetup(scholarshipsResource)
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
    public static Scholarships createEntity(EntityManager em) {
        Scholarships scholarships = new Scholarships()
            .minMarks(DEFAULT_MIN_MARKS)
            .percent(DEFAULT_PERCENT);
        return scholarships;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Scholarships createUpdatedEntity(EntityManager em) {
        Scholarships scholarships = new Scholarships()
            .minMarks(UPDATED_MIN_MARKS)
            .percent(UPDATED_PERCENT);
        return scholarships;
    }

    @BeforeEach
    public void initTest() {
        scholarships = createEntity(em);
    }

    @Test
    @Transactional
    public void createScholarships() throws Exception {
        int databaseSizeBeforeCreate = scholarshipsRepository.findAll().size();

        // Create the Scholarships
        ScholarshipsDTO scholarshipsDTO = scholarshipsMapper.toDto(scholarships);
        restScholarshipsMockMvc.perform(post("/api/scholarships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scholarshipsDTO)))
            .andExpect(status().isCreated());

        // Validate the Scholarships in the database
        List<Scholarships> scholarshipsList = scholarshipsRepository.findAll();
        assertThat(scholarshipsList).hasSize(databaseSizeBeforeCreate + 1);
        Scholarships testScholarships = scholarshipsList.get(scholarshipsList.size() - 1);
        assertThat(testScholarships.getMinMarks()).isEqualTo(DEFAULT_MIN_MARKS);
        assertThat(testScholarships.getPercent()).isEqualTo(DEFAULT_PERCENT);
    }

    @Test
    @Transactional
    public void createScholarshipsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scholarshipsRepository.findAll().size();

        // Create the Scholarships with an existing ID
        scholarships.setId(1L);
        ScholarshipsDTO scholarshipsDTO = scholarshipsMapper.toDto(scholarships);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScholarshipsMockMvc.perform(post("/api/scholarships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scholarshipsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scholarships in the database
        List<Scholarships> scholarshipsList = scholarshipsRepository.findAll();
        assertThat(scholarshipsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScholarships() throws Exception {
        // Initialize the database
        scholarshipsRepository.saveAndFlush(scholarships);

        // Get all the scholarshipsList
        restScholarshipsMockMvc.perform(get("/api/scholarships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scholarships.getId().intValue())))
            .andExpect(jsonPath("$.[*].minMarks").value(hasItem(DEFAULT_MIN_MARKS)))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT)));
    }
    
    @Test
    @Transactional
    public void getScholarships() throws Exception {
        // Initialize the database
        scholarshipsRepository.saveAndFlush(scholarships);

        // Get the scholarships
        restScholarshipsMockMvc.perform(get("/api/scholarships/{id}", scholarships.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scholarships.getId().intValue()))
            .andExpect(jsonPath("$.minMarks").value(DEFAULT_MIN_MARKS))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT));
    }

    @Test
    @Transactional
    public void getNonExistingScholarships() throws Exception {
        // Get the scholarships
        restScholarshipsMockMvc.perform(get("/api/scholarships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScholarships() throws Exception {
        // Initialize the database
        scholarshipsRepository.saveAndFlush(scholarships);

        int databaseSizeBeforeUpdate = scholarshipsRepository.findAll().size();

        // Update the scholarships
        Scholarships updatedScholarships = scholarshipsRepository.findById(scholarships.getId()).get();
        // Disconnect from session so that the updates on updatedScholarships are not directly saved in db
        em.detach(updatedScholarships);
        updatedScholarships
            .minMarks(UPDATED_MIN_MARKS)
            .percent(UPDATED_PERCENT);
        ScholarshipsDTO scholarshipsDTO = scholarshipsMapper.toDto(updatedScholarships);

        restScholarshipsMockMvc.perform(put("/api/scholarships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scholarshipsDTO)))
            .andExpect(status().isOk());

        // Validate the Scholarships in the database
        List<Scholarships> scholarshipsList = scholarshipsRepository.findAll();
        assertThat(scholarshipsList).hasSize(databaseSizeBeforeUpdate);
        Scholarships testScholarships = scholarshipsList.get(scholarshipsList.size() - 1);
        assertThat(testScholarships.getMinMarks()).isEqualTo(UPDATED_MIN_MARKS);
        assertThat(testScholarships.getPercent()).isEqualTo(UPDATED_PERCENT);
    }

    @Test
    @Transactional
    public void updateNonExistingScholarships() throws Exception {
        int databaseSizeBeforeUpdate = scholarshipsRepository.findAll().size();

        // Create the Scholarships
        ScholarshipsDTO scholarshipsDTO = scholarshipsMapper.toDto(scholarships);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScholarshipsMockMvc.perform(put("/api/scholarships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scholarshipsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Scholarships in the database
        List<Scholarships> scholarshipsList = scholarshipsRepository.findAll();
        assertThat(scholarshipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScholarships() throws Exception {
        // Initialize the database
        scholarshipsRepository.saveAndFlush(scholarships);

        int databaseSizeBeforeDelete = scholarshipsRepository.findAll().size();

        // Delete the scholarships
        restScholarshipsMockMvc.perform(delete("/api/scholarships/{id}", scholarships.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Scholarships> scholarshipsList = scholarshipsRepository.findAll();
        assertThat(scholarshipsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scholarships.class);
        Scholarships scholarships1 = new Scholarships();
        scholarships1.setId(1L);
        Scholarships scholarships2 = new Scholarships();
        scholarships2.setId(scholarships1.getId());
        assertThat(scholarships1).isEqualTo(scholarships2);
        scholarships2.setId(2L);
        assertThat(scholarships1).isNotEqualTo(scholarships2);
        scholarships1.setId(null);
        assertThat(scholarships1).isNotEqualTo(scholarships2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScholarshipsDTO.class);
        ScholarshipsDTO scholarshipsDTO1 = new ScholarshipsDTO();
        scholarshipsDTO1.setId(1L);
        ScholarshipsDTO scholarshipsDTO2 = new ScholarshipsDTO();
        assertThat(scholarshipsDTO1).isNotEqualTo(scholarshipsDTO2);
        scholarshipsDTO2.setId(scholarshipsDTO1.getId());
        assertThat(scholarshipsDTO1).isEqualTo(scholarshipsDTO2);
        scholarshipsDTO2.setId(2L);
        assertThat(scholarshipsDTO1).isNotEqualTo(scholarshipsDTO2);
        scholarshipsDTO1.setId(null);
        assertThat(scholarshipsDTO1).isNotEqualTo(scholarshipsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scholarshipsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scholarshipsMapper.fromId(null)).isNull();
    }
}
