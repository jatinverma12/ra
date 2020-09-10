package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.SubjectsBaseFee;
import com.risingarjun.arjun.repository.SubjectsBaseFeeRepository;
import com.risingarjun.arjun.service.SubjectsBaseFeeService;
import com.risingarjun.arjun.service.dto.SubjectsBaseFeeDTO;
import com.risingarjun.arjun.service.mapper.SubjectsBaseFeeMapper;
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
 * Integration tests for the {@Link SubjectsBaseFeeResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class SubjectsBaseFeeResourceIT {

    private static final Integer DEFAULT_BASE_FEE = 1;
    private static final Integer UPDATED_BASE_FEE = 2;

    @Autowired
    private SubjectsBaseFeeRepository subjectsBaseFeeRepository;

    @Autowired
    private SubjectsBaseFeeMapper subjectsBaseFeeMapper;

    @Autowired
    private SubjectsBaseFeeService subjectsBaseFeeService;

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

    private MockMvc restSubjectsBaseFeeMockMvc;

    private SubjectsBaseFee subjectsBaseFee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubjectsBaseFeeResource subjectsBaseFeeResource = new SubjectsBaseFeeResource(subjectsBaseFeeService);
        this.restSubjectsBaseFeeMockMvc = MockMvcBuilders.standaloneSetup(subjectsBaseFeeResource)
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
    public static SubjectsBaseFee createEntity(EntityManager em) {
        SubjectsBaseFee subjectsBaseFee = new SubjectsBaseFee()
            .baseFee(DEFAULT_BASE_FEE);
        return subjectsBaseFee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubjectsBaseFee createUpdatedEntity(EntityManager em) {
        SubjectsBaseFee subjectsBaseFee = new SubjectsBaseFee()
            .baseFee(UPDATED_BASE_FEE);
        return subjectsBaseFee;
    }

    @BeforeEach
    public void initTest() {
        subjectsBaseFee = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubjectsBaseFee() throws Exception {
        int databaseSizeBeforeCreate = subjectsBaseFeeRepository.findAll().size();

        // Create the SubjectsBaseFee
        SubjectsBaseFeeDTO subjectsBaseFeeDTO = subjectsBaseFeeMapper.toDto(subjectsBaseFee);
        restSubjectsBaseFeeMockMvc.perform(post("/api/subjects-base-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsBaseFeeDTO)))
            .andExpect(status().isCreated());

        // Validate the SubjectsBaseFee in the database
        List<SubjectsBaseFee> subjectsBaseFeeList = subjectsBaseFeeRepository.findAll();
        assertThat(subjectsBaseFeeList).hasSize(databaseSizeBeforeCreate + 1);
        SubjectsBaseFee testSubjectsBaseFee = subjectsBaseFeeList.get(subjectsBaseFeeList.size() - 1);
        assertThat(testSubjectsBaseFee.getBaseFee()).isEqualTo(DEFAULT_BASE_FEE);
    }

    @Test
    @Transactional
    public void createSubjectsBaseFeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subjectsBaseFeeRepository.findAll().size();

        // Create the SubjectsBaseFee with an existing ID
        subjectsBaseFee.setId(1L);
        SubjectsBaseFeeDTO subjectsBaseFeeDTO = subjectsBaseFeeMapper.toDto(subjectsBaseFee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectsBaseFeeMockMvc.perform(post("/api/subjects-base-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsBaseFeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubjectsBaseFee in the database
        List<SubjectsBaseFee> subjectsBaseFeeList = subjectsBaseFeeRepository.findAll();
        assertThat(subjectsBaseFeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubjectsBaseFees() throws Exception {
        // Initialize the database
        subjectsBaseFeeRepository.saveAndFlush(subjectsBaseFee);

        // Get all the subjectsBaseFeeList
        restSubjectsBaseFeeMockMvc.perform(get("/api/subjects-base-fees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjectsBaseFee.getId().intValue())))
            .andExpect(jsonPath("$.[*].baseFee").value(hasItem(DEFAULT_BASE_FEE)));
    }
    
    @Test
    @Transactional
    public void getSubjectsBaseFee() throws Exception {
        // Initialize the database
        subjectsBaseFeeRepository.saveAndFlush(subjectsBaseFee);

        // Get the subjectsBaseFee
        restSubjectsBaseFeeMockMvc.perform(get("/api/subjects-base-fees/{id}", subjectsBaseFee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subjectsBaseFee.getId().intValue()))
            .andExpect(jsonPath("$.baseFee").value(DEFAULT_BASE_FEE));
    }

    @Test
    @Transactional
    public void getNonExistingSubjectsBaseFee() throws Exception {
        // Get the subjectsBaseFee
        restSubjectsBaseFeeMockMvc.perform(get("/api/subjects-base-fees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubjectsBaseFee() throws Exception {
        // Initialize the database
        subjectsBaseFeeRepository.saveAndFlush(subjectsBaseFee);

        int databaseSizeBeforeUpdate = subjectsBaseFeeRepository.findAll().size();

        // Update the subjectsBaseFee
        SubjectsBaseFee updatedSubjectsBaseFee = subjectsBaseFeeRepository.findById(subjectsBaseFee.getId()).get();
        // Disconnect from session so that the updates on updatedSubjectsBaseFee are not directly saved in db
        em.detach(updatedSubjectsBaseFee);
        updatedSubjectsBaseFee
            .baseFee(UPDATED_BASE_FEE);
        SubjectsBaseFeeDTO subjectsBaseFeeDTO = subjectsBaseFeeMapper.toDto(updatedSubjectsBaseFee);

        restSubjectsBaseFeeMockMvc.perform(put("/api/subjects-base-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsBaseFeeDTO)))
            .andExpect(status().isOk());

        // Validate the SubjectsBaseFee in the database
        List<SubjectsBaseFee> subjectsBaseFeeList = subjectsBaseFeeRepository.findAll();
        assertThat(subjectsBaseFeeList).hasSize(databaseSizeBeforeUpdate);
        SubjectsBaseFee testSubjectsBaseFee = subjectsBaseFeeList.get(subjectsBaseFeeList.size() - 1);
        assertThat(testSubjectsBaseFee.getBaseFee()).isEqualTo(UPDATED_BASE_FEE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubjectsBaseFee() throws Exception {
        int databaseSizeBeforeUpdate = subjectsBaseFeeRepository.findAll().size();

        // Create the SubjectsBaseFee
        SubjectsBaseFeeDTO subjectsBaseFeeDTO = subjectsBaseFeeMapper.toDto(subjectsBaseFee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectsBaseFeeMockMvc.perform(put("/api/subjects-base-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subjectsBaseFeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubjectsBaseFee in the database
        List<SubjectsBaseFee> subjectsBaseFeeList = subjectsBaseFeeRepository.findAll();
        assertThat(subjectsBaseFeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubjectsBaseFee() throws Exception {
        // Initialize the database
        subjectsBaseFeeRepository.saveAndFlush(subjectsBaseFee);

        int databaseSizeBeforeDelete = subjectsBaseFeeRepository.findAll().size();

        // Delete the subjectsBaseFee
        restSubjectsBaseFeeMockMvc.perform(delete("/api/subjects-base-fees/{id}", subjectsBaseFee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubjectsBaseFee> subjectsBaseFeeList = subjectsBaseFeeRepository.findAll();
        assertThat(subjectsBaseFeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectsBaseFee.class);
        SubjectsBaseFee subjectsBaseFee1 = new SubjectsBaseFee();
        subjectsBaseFee1.setId(1L);
        SubjectsBaseFee subjectsBaseFee2 = new SubjectsBaseFee();
        subjectsBaseFee2.setId(subjectsBaseFee1.getId());
        assertThat(subjectsBaseFee1).isEqualTo(subjectsBaseFee2);
        subjectsBaseFee2.setId(2L);
        assertThat(subjectsBaseFee1).isNotEqualTo(subjectsBaseFee2);
        subjectsBaseFee1.setId(null);
        assertThat(subjectsBaseFee1).isNotEqualTo(subjectsBaseFee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectsBaseFeeDTO.class);
        SubjectsBaseFeeDTO subjectsBaseFeeDTO1 = new SubjectsBaseFeeDTO();
        subjectsBaseFeeDTO1.setId(1L);
        SubjectsBaseFeeDTO subjectsBaseFeeDTO2 = new SubjectsBaseFeeDTO();
        assertThat(subjectsBaseFeeDTO1).isNotEqualTo(subjectsBaseFeeDTO2);
        subjectsBaseFeeDTO2.setId(subjectsBaseFeeDTO1.getId());
        assertThat(subjectsBaseFeeDTO1).isEqualTo(subjectsBaseFeeDTO2);
        subjectsBaseFeeDTO2.setId(2L);
        assertThat(subjectsBaseFeeDTO1).isNotEqualTo(subjectsBaseFeeDTO2);
        subjectsBaseFeeDTO1.setId(null);
        assertThat(subjectsBaseFeeDTO1).isNotEqualTo(subjectsBaseFeeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subjectsBaseFeeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subjectsBaseFeeMapper.fromId(null)).isNull();
    }
}
