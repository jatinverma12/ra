package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.CenterHead;
import com.risingarjun.arjun.repository.CenterHeadRepository;
import com.risingarjun.arjun.service.CenterHeadService;
import com.risingarjun.arjun.service.dto.CenterHeadDTO;
import com.risingarjun.arjun.service.mapper.CenterHeadMapper;
import com.risingarjun.arjun.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.risingarjun.arjun.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CenterHeadResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class CenterHeadResourceIT {

    @Autowired
    private CenterHeadRepository centerHeadRepository;

    @Mock
    private CenterHeadRepository centerHeadRepositoryMock;

    @Autowired
    private CenterHeadMapper centerHeadMapper;

    @Mock
    private CenterHeadService centerHeadServiceMock;

    @Autowired
    private CenterHeadService centerHeadService;

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

    private MockMvc restCenterHeadMockMvc;

    private CenterHead centerHead;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CenterHeadResource centerHeadResource = new CenterHeadResource(centerHeadService);
        this.restCenterHeadMockMvc = MockMvcBuilders.standaloneSetup(centerHeadResource)
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
    public static CenterHead createEntity(EntityManager em) {
        CenterHead centerHead = new CenterHead();
        return centerHead;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CenterHead createUpdatedEntity(EntityManager em) {
        CenterHead centerHead = new CenterHead();
        return centerHead;
    }

    @BeforeEach
    public void initTest() {
        centerHead = createEntity(em);
    }

    @Test
    @Transactional
    public void createCenterHead() throws Exception {
        int databaseSizeBeforeCreate = centerHeadRepository.findAll().size();

        // Create the CenterHead
        CenterHeadDTO centerHeadDTO = centerHeadMapper.toDto(centerHead);
        restCenterHeadMockMvc.perform(post("/api/center-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centerHeadDTO)))
            .andExpect(status().isCreated());

        // Validate the CenterHead in the database
        List<CenterHead> centerHeadList = centerHeadRepository.findAll();
        assertThat(centerHeadList).hasSize(databaseSizeBeforeCreate + 1);
        CenterHead testCenterHead = centerHeadList.get(centerHeadList.size() - 1);
    }

    @Test
    @Transactional
    public void createCenterHeadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = centerHeadRepository.findAll().size();

        // Create the CenterHead with an existing ID
        centerHead.setId(1L);
        CenterHeadDTO centerHeadDTO = centerHeadMapper.toDto(centerHead);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCenterHeadMockMvc.perform(post("/api/center-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centerHeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CenterHead in the database
        List<CenterHead> centerHeadList = centerHeadRepository.findAll();
        assertThat(centerHeadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCenterHeads() throws Exception {
        // Initialize the database
        centerHeadRepository.saveAndFlush(centerHead);

        // Get all the centerHeadList
        restCenterHeadMockMvc.perform(get("/api/center-heads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centerHead.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCenterHeadsWithEagerRelationshipsIsEnabled() throws Exception {
        CenterHeadResource centerHeadResource = new CenterHeadResource(centerHeadServiceMock);
        when(centerHeadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCenterHeadMockMvc = MockMvcBuilders.standaloneSetup(centerHeadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCenterHeadMockMvc.perform(get("/api/center-heads?eagerload=true"))
        .andExpect(status().isOk());

        verify(centerHeadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCenterHeadsWithEagerRelationshipsIsNotEnabled() throws Exception {
        CenterHeadResource centerHeadResource = new CenterHeadResource(centerHeadServiceMock);
            when(centerHeadServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCenterHeadMockMvc = MockMvcBuilders.standaloneSetup(centerHeadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCenterHeadMockMvc.perform(get("/api/center-heads?eagerload=true"))
        .andExpect(status().isOk());

            verify(centerHeadServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCenterHead() throws Exception {
        // Initialize the database
        centerHeadRepository.saveAndFlush(centerHead);

        // Get the centerHead
        restCenterHeadMockMvc.perform(get("/api/center-heads/{id}", centerHead.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(centerHead.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCenterHead() throws Exception {
        // Get the centerHead
        restCenterHeadMockMvc.perform(get("/api/center-heads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCenterHead() throws Exception {
        // Initialize the database
        centerHeadRepository.saveAndFlush(centerHead);

        int databaseSizeBeforeUpdate = centerHeadRepository.findAll().size();

        // Update the centerHead
        CenterHead updatedCenterHead = centerHeadRepository.findById(centerHead.getId()).get();
        // Disconnect from session so that the updates on updatedCenterHead are not directly saved in db
        em.detach(updatedCenterHead);
        CenterHeadDTO centerHeadDTO = centerHeadMapper.toDto(updatedCenterHead);

        restCenterHeadMockMvc.perform(put("/api/center-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centerHeadDTO)))
            .andExpect(status().isOk());

        // Validate the CenterHead in the database
        List<CenterHead> centerHeadList = centerHeadRepository.findAll();
        assertThat(centerHeadList).hasSize(databaseSizeBeforeUpdate);
        CenterHead testCenterHead = centerHeadList.get(centerHeadList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCenterHead() throws Exception {
        int databaseSizeBeforeUpdate = centerHeadRepository.findAll().size();

        // Create the CenterHead
        CenterHeadDTO centerHeadDTO = centerHeadMapper.toDto(centerHead);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCenterHeadMockMvc.perform(put("/api/center-heads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(centerHeadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CenterHead in the database
        List<CenterHead> centerHeadList = centerHeadRepository.findAll();
        assertThat(centerHeadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCenterHead() throws Exception {
        // Initialize the database
        centerHeadRepository.saveAndFlush(centerHead);

        int databaseSizeBeforeDelete = centerHeadRepository.findAll().size();

        // Delete the centerHead
        restCenterHeadMockMvc.perform(delete("/api/center-heads/{id}", centerHead.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CenterHead> centerHeadList = centerHeadRepository.findAll();
        assertThat(centerHeadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterHead.class);
        CenterHead centerHead1 = new CenterHead();
        centerHead1.setId(1L);
        CenterHead centerHead2 = new CenterHead();
        centerHead2.setId(centerHead1.getId());
        assertThat(centerHead1).isEqualTo(centerHead2);
        centerHead2.setId(2L);
        assertThat(centerHead1).isNotEqualTo(centerHead2);
        centerHead1.setId(null);
        assertThat(centerHead1).isNotEqualTo(centerHead2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CenterHeadDTO.class);
        CenterHeadDTO centerHeadDTO1 = new CenterHeadDTO();
        centerHeadDTO1.setId(1L);
        CenterHeadDTO centerHeadDTO2 = new CenterHeadDTO();
        assertThat(centerHeadDTO1).isNotEqualTo(centerHeadDTO2);
        centerHeadDTO2.setId(centerHeadDTO1.getId());
        assertThat(centerHeadDTO1).isEqualTo(centerHeadDTO2);
        centerHeadDTO2.setId(2L);
        assertThat(centerHeadDTO1).isNotEqualTo(centerHeadDTO2);
        centerHeadDTO1.setId(null);
        assertThat(centerHeadDTO1).isNotEqualTo(centerHeadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(centerHeadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(centerHeadMapper.fromId(null)).isNull();
    }
}
