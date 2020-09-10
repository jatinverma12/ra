package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Teachers;
import com.risingarjun.arjun.repository.TeachersRepository;
import com.risingarjun.arjun.service.TeachersService;
import com.risingarjun.arjun.service.dto.TeachersDTO;
import com.risingarjun.arjun.service.mapper.TeachersMapper;
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
 * Integration tests for the {@Link TeachersResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class TeachersResourceIT {

    @Autowired
    private TeachersRepository teachersRepository;

    @Mock
    private TeachersRepository teachersRepositoryMock;

    @Autowired
    private TeachersMapper teachersMapper;

    @Mock
    private TeachersService teachersServiceMock;

    @Autowired
    private TeachersService teachersService;

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

    private MockMvc restTeachersMockMvc;

    private Teachers teachers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeachersResource teachersResource = new TeachersResource(teachersService);
        this.restTeachersMockMvc = MockMvcBuilders.standaloneSetup(teachersResource)
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
    public static Teachers createEntity(EntityManager em) {
        Teachers teachers = new Teachers();
        return teachers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teachers createUpdatedEntity(EntityManager em) {
        Teachers teachers = new Teachers();
        return teachers;
    }

    @BeforeEach
    public void initTest() {
        teachers = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeachers() throws Exception {
        int databaseSizeBeforeCreate = teachersRepository.findAll().size();

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);
        restTeachersMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersDTO)))
            .andExpect(status().isCreated());

        // Validate the Teachers in the database
        List<Teachers> teachersList = teachersRepository.findAll();
        assertThat(teachersList).hasSize(databaseSizeBeforeCreate + 1);
        Teachers testTeachers = teachersList.get(teachersList.size() - 1);
    }

    @Test
    @Transactional
    public void createTeachersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teachersRepository.findAll().size();

        // Create the Teachers with an existing ID
        teachers.setId(1L);
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachersMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Teachers in the database
        List<Teachers> teachersList = teachersRepository.findAll();
        assertThat(teachersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTeachers() throws Exception {
        // Initialize the database
        teachersRepository.saveAndFlush(teachers);

        // Get all the teachersList
        restTeachersMockMvc.perform(get("/api/teachers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachers.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTeachersWithEagerRelationshipsIsEnabled() throws Exception {
        TeachersResource teachersResource = new TeachersResource(teachersServiceMock);
        when(teachersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTeachersMockMvc = MockMvcBuilders.standaloneSetup(teachersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTeachersMockMvc.perform(get("/api/teachers?eagerload=true"))
        .andExpect(status().isOk());

        verify(teachersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTeachersWithEagerRelationshipsIsNotEnabled() throws Exception {
        TeachersResource teachersResource = new TeachersResource(teachersServiceMock);
            when(teachersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTeachersMockMvc = MockMvcBuilders.standaloneSetup(teachersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTeachersMockMvc.perform(get("/api/teachers?eagerload=true"))
        .andExpect(status().isOk());

            verify(teachersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTeachers() throws Exception {
        // Initialize the database
        teachersRepository.saveAndFlush(teachers);

        // Get the teachers
        restTeachersMockMvc.perform(get("/api/teachers/{id}", teachers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teachers.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTeachers() throws Exception {
        // Get the teachers
        restTeachersMockMvc.perform(get("/api/teachers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeachers() throws Exception {
        // Initialize the database
        teachersRepository.saveAndFlush(teachers);

        int databaseSizeBeforeUpdate = teachersRepository.findAll().size();

        // Update the teachers
        Teachers updatedTeachers = teachersRepository.findById(teachers.getId()).get();
        // Disconnect from session so that the updates on updatedTeachers are not directly saved in db
        em.detach(updatedTeachers);
        TeachersDTO teachersDTO = teachersMapper.toDto(updatedTeachers);

        restTeachersMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersDTO)))
            .andExpect(status().isOk());

        // Validate the Teachers in the database
        List<Teachers> teachersList = teachersRepository.findAll();
        assertThat(teachersList).hasSize(databaseSizeBeforeUpdate);
        Teachers testTeachers = teachersList.get(teachersList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTeachers() throws Exception {
        int databaseSizeBeforeUpdate = teachersRepository.findAll().size();

        // Create the Teachers
        TeachersDTO teachersDTO = teachersMapper.toDto(teachers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeachersMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Teachers in the database
        List<Teachers> teachersList = teachersRepository.findAll();
        assertThat(teachersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeachers() throws Exception {
        // Initialize the database
        teachersRepository.saveAndFlush(teachers);

        int databaseSizeBeforeDelete = teachersRepository.findAll().size();

        // Delete the teachers
        restTeachersMockMvc.perform(delete("/api/teachers/{id}", teachers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Teachers> teachersList = teachersRepository.findAll();
        assertThat(teachersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teachers.class);
        Teachers teachers1 = new Teachers();
        teachers1.setId(1L);
        Teachers teachers2 = new Teachers();
        teachers2.setId(teachers1.getId());
        assertThat(teachers1).isEqualTo(teachers2);
        teachers2.setId(2L);
        assertThat(teachers1).isNotEqualTo(teachers2);
        teachers1.setId(null);
        assertThat(teachers1).isNotEqualTo(teachers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachersDTO.class);
        TeachersDTO teachersDTO1 = new TeachersDTO();
        teachersDTO1.setId(1L);
        TeachersDTO teachersDTO2 = new TeachersDTO();
        assertThat(teachersDTO1).isNotEqualTo(teachersDTO2);
        teachersDTO2.setId(teachersDTO1.getId());
        assertThat(teachersDTO1).isEqualTo(teachersDTO2);
        teachersDTO2.setId(2L);
        assertThat(teachersDTO1).isNotEqualTo(teachersDTO2);
        teachersDTO1.setId(null);
        assertThat(teachersDTO1).isNotEqualTo(teachersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(teachersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(teachersMapper.fromId(null)).isNull();
    }
}
