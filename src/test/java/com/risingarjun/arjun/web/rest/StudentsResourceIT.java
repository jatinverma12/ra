package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Students;
import com.risingarjun.arjun.repository.StudentsRepository;
import com.risingarjun.arjun.service.StudentsService;
import com.risingarjun.arjun.service.dto.StudentsDTO;
import com.risingarjun.arjun.service.mapper.StudentsMapper;
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
import org.springframework.util.Base64Utils;
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

import com.risingarjun.arjun.domain.enumeration.StudentStatus;
import com.risingarjun.arjun.domain.enumeration.LeavingReasons;
import com.risingarjun.arjun.domain.enumeration.InfoSources;
/**
 * Integration tests for the {@Link StudentsResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class StudentsResourceIT {

    private static final String DEFAULT_STUDENT_REG_ID = "AAAAAAAAAA";
    private static final String UPDATED_STUDENT_REG_ID = "BBBBBBBBBB";

    private static final byte[] DEFAULT_REGISTRATION_FORM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REGISTRATION_FORM = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REGISTRATION_FORM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REGISTRATION_FORM_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_PARENT_MOB_NO_1 = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_MOB_NO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_MOB_NO_2 = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_MOB_NO_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_EMAIL_ID = "Z\"@SS.*-";
    private static final String UPDATED_PARENT_EMAIL_ID = "\"v@P.Ld";

    private static final StudentStatus DEFAULT_STUDENT_STATUS = StudentStatus.GRADUATED;
    private static final StudentStatus UPDATED_STUDENT_STATUS = StudentStatus.JOINED;

    private static final LeavingReasons DEFAULT_LEAVING_REASON = LeavingReasons.DISTANCEFACTOR;
    private static final LeavingReasons UPDATED_LEAVING_REASON = LeavingReasons.UNHAPPYPHYSICS;

    private static final InfoSources DEFAULT_INFO_SOURCE = InfoSources.LOCATIONDIRECTLY;
    private static final InfoSources UPDATED_INFO_SOURCE = InfoSources.FRIENDS;

    @Autowired
    private StudentsRepository studentsRepository;

    @Mock
    private StudentsRepository studentsRepositoryMock;

    @Autowired
    private StudentsMapper studentsMapper;

    @Mock
    private StudentsService studentsServiceMock;

    @Autowired
    private StudentsService studentsService;

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

    private MockMvc restStudentsMockMvc;

    private Students students;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentsResource studentsResource = new StudentsResource(studentsService);
        this.restStudentsMockMvc = MockMvcBuilders.standaloneSetup(studentsResource)
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
    public static Students createEntity(EntityManager em) {
        Students students = new Students()
            .studentRegId(DEFAULT_STUDENT_REG_ID)
            .registrationForm(DEFAULT_REGISTRATION_FORM)
            .registrationFormContentType(DEFAULT_REGISTRATION_FORM_CONTENT_TYPE)
            .parentMobNo1(DEFAULT_PARENT_MOB_NO_1)
            .parentMobNo2(DEFAULT_PARENT_MOB_NO_2)
            .parentEmailId(DEFAULT_PARENT_EMAIL_ID)
            .studentStatus(DEFAULT_STUDENT_STATUS)
            .leavingReason(DEFAULT_LEAVING_REASON)
            .infoSource(DEFAULT_INFO_SOURCE);
        return students;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Students createUpdatedEntity(EntityManager em) {
        Students students = new Students()
            .studentRegId(UPDATED_STUDENT_REG_ID)
            .registrationForm(UPDATED_REGISTRATION_FORM)
            .registrationFormContentType(UPDATED_REGISTRATION_FORM_CONTENT_TYPE)
            .parentMobNo1(UPDATED_PARENT_MOB_NO_1)
            .parentMobNo2(UPDATED_PARENT_MOB_NO_2)
            .parentEmailId(UPDATED_PARENT_EMAIL_ID)
            .studentStatus(UPDATED_STUDENT_STATUS)
            .leavingReason(UPDATED_LEAVING_REASON)
            .infoSource(UPDATED_INFO_SOURCE);
        return students;
    }

    @BeforeEach
    public void initTest() {
        students = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudents() throws Exception {
        int databaseSizeBeforeCreate = studentsRepository.findAll().size();

        // Create the Students
        StudentsDTO studentsDTO = studentsMapper.toDto(students);
        restStudentsMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Students in the database
        List<Students> studentsList = studentsRepository.findAll();
        assertThat(studentsList).hasSize(databaseSizeBeforeCreate + 1);
        Students testStudents = studentsList.get(studentsList.size() - 1);
        assertThat(testStudents.getStudentRegId()).isEqualTo(DEFAULT_STUDENT_REG_ID);
        assertThat(testStudents.getRegistrationForm()).isEqualTo(DEFAULT_REGISTRATION_FORM);
        assertThat(testStudents.getRegistrationFormContentType()).isEqualTo(DEFAULT_REGISTRATION_FORM_CONTENT_TYPE);
        assertThat(testStudents.getParentMobNo1()).isEqualTo(DEFAULT_PARENT_MOB_NO_1);
        assertThat(testStudents.getParentMobNo2()).isEqualTo(DEFAULT_PARENT_MOB_NO_2);
        assertThat(testStudents.getParentEmailId()).isEqualTo(DEFAULT_PARENT_EMAIL_ID);
        assertThat(testStudents.getStudentStatus()).isEqualTo(DEFAULT_STUDENT_STATUS);
        assertThat(testStudents.getLeavingReason()).isEqualTo(DEFAULT_LEAVING_REASON);
        assertThat(testStudents.getInfoSource()).isEqualTo(DEFAULT_INFO_SOURCE);
    }

    @Test
    @Transactional
    public void createStudentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentsRepository.findAll().size();

        // Create the Students with an existing ID
        students.setId(1L);
        StudentsDTO studentsDTO = studentsMapper.toDto(students);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentsMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Students in the database
        List<Students> studentsList = studentsRepository.findAll();
        assertThat(studentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStudentRegIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentsRepository.findAll().size();
        // set the field null
        students.setStudentRegId(null);

        // Create the Students, which fails.
        StudentsDTO studentsDTO = studentsMapper.toDto(students);

        restStudentsMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsDTO)))
            .andExpect(status().isBadRequest());

        List<Students> studentsList = studentsRepository.findAll();
        assertThat(studentsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentsRepository.saveAndFlush(students);

        // Get all the studentsList
        restStudentsMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(students.getId().intValue())))
            .andExpect(jsonPath("$.[*].studentRegId").value(hasItem(DEFAULT_STUDENT_REG_ID.toString())))
            .andExpect(jsonPath("$.[*].registrationFormContentType").value(hasItem(DEFAULT_REGISTRATION_FORM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].registrationForm").value(hasItem(Base64Utils.encodeToString(DEFAULT_REGISTRATION_FORM))))
            .andExpect(jsonPath("$.[*].parentMobNo1").value(hasItem(DEFAULT_PARENT_MOB_NO_1.toString())))
            .andExpect(jsonPath("$.[*].parentMobNo2").value(hasItem(DEFAULT_PARENT_MOB_NO_2.toString())))
            .andExpect(jsonPath("$.[*].parentEmailId").value(hasItem(DEFAULT_PARENT_EMAIL_ID.toString())))
            .andExpect(jsonPath("$.[*].studentStatus").value(hasItem(DEFAULT_STUDENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].leavingReason").value(hasItem(DEFAULT_LEAVING_REASON.toString())))
            .andExpect(jsonPath("$.[*].infoSource").value(hasItem(DEFAULT_INFO_SOURCE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllStudentsWithEagerRelationshipsIsEnabled() throws Exception {
        StudentsResource studentsResource = new StudentsResource(studentsServiceMock);
        when(studentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restStudentsMockMvc = MockMvcBuilders.standaloneSetup(studentsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restStudentsMockMvc.perform(get("/api/students?eagerload=true"))
        .andExpect(status().isOk());

        verify(studentsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllStudentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        StudentsResource studentsResource = new StudentsResource(studentsServiceMock);
            when(studentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restStudentsMockMvc = MockMvcBuilders.standaloneSetup(studentsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restStudentsMockMvc.perform(get("/api/students?eagerload=true"))
        .andExpect(status().isOk());

            verify(studentsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getStudents() throws Exception {
        // Initialize the database
        studentsRepository.saveAndFlush(students);

        // Get the students
        restStudentsMockMvc.perform(get("/api/students/{id}", students.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(students.getId().intValue()))
            .andExpect(jsonPath("$.studentRegId").value(DEFAULT_STUDENT_REG_ID.toString()))
            .andExpect(jsonPath("$.registrationFormContentType").value(DEFAULT_REGISTRATION_FORM_CONTENT_TYPE))
            .andExpect(jsonPath("$.registrationForm").value(Base64Utils.encodeToString(DEFAULT_REGISTRATION_FORM)))
            .andExpect(jsonPath("$.parentMobNo1").value(DEFAULT_PARENT_MOB_NO_1.toString()))
            .andExpect(jsonPath("$.parentMobNo2").value(DEFAULT_PARENT_MOB_NO_2.toString()))
            .andExpect(jsonPath("$.parentEmailId").value(DEFAULT_PARENT_EMAIL_ID.toString()))
            .andExpect(jsonPath("$.studentStatus").value(DEFAULT_STUDENT_STATUS.toString()))
            .andExpect(jsonPath("$.leavingReason").value(DEFAULT_LEAVING_REASON.toString()))
            .andExpect(jsonPath("$.infoSource").value(DEFAULT_INFO_SOURCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudents() throws Exception {
        // Get the students
        restStudentsMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudents() throws Exception {
        // Initialize the database
        studentsRepository.saveAndFlush(students);

        int databaseSizeBeforeUpdate = studentsRepository.findAll().size();

        // Update the students
        Students updatedStudents = studentsRepository.findById(students.getId()).get();
        // Disconnect from session so that the updates on updatedStudents are not directly saved in db
        em.detach(updatedStudents);
        updatedStudents
            .studentRegId(UPDATED_STUDENT_REG_ID)
            .registrationForm(UPDATED_REGISTRATION_FORM)
            .registrationFormContentType(UPDATED_REGISTRATION_FORM_CONTENT_TYPE)
            .parentMobNo1(UPDATED_PARENT_MOB_NO_1)
            .parentMobNo2(UPDATED_PARENT_MOB_NO_2)
            .parentEmailId(UPDATED_PARENT_EMAIL_ID)
            .studentStatus(UPDATED_STUDENT_STATUS)
            .leavingReason(UPDATED_LEAVING_REASON)
            .infoSource(UPDATED_INFO_SOURCE);
        StudentsDTO studentsDTO = studentsMapper.toDto(updatedStudents);

        restStudentsMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsDTO)))
            .andExpect(status().isOk());

        // Validate the Students in the database
        List<Students> studentsList = studentsRepository.findAll();
        assertThat(studentsList).hasSize(databaseSizeBeforeUpdate);
        Students testStudents = studentsList.get(studentsList.size() - 1);
        assertThat(testStudents.getStudentRegId()).isEqualTo(UPDATED_STUDENT_REG_ID);
        assertThat(testStudents.getRegistrationForm()).isEqualTo(UPDATED_REGISTRATION_FORM);
        assertThat(testStudents.getRegistrationFormContentType()).isEqualTo(UPDATED_REGISTRATION_FORM_CONTENT_TYPE);
        assertThat(testStudents.getParentMobNo1()).isEqualTo(UPDATED_PARENT_MOB_NO_1);
        assertThat(testStudents.getParentMobNo2()).isEqualTo(UPDATED_PARENT_MOB_NO_2);
        assertThat(testStudents.getParentEmailId()).isEqualTo(UPDATED_PARENT_EMAIL_ID);
        assertThat(testStudents.getStudentStatus()).isEqualTo(UPDATED_STUDENT_STATUS);
        assertThat(testStudents.getLeavingReason()).isEqualTo(UPDATED_LEAVING_REASON);
        assertThat(testStudents.getInfoSource()).isEqualTo(UPDATED_INFO_SOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudents() throws Exception {
        int databaseSizeBeforeUpdate = studentsRepository.findAll().size();

        // Create the Students
        StudentsDTO studentsDTO = studentsMapper.toDto(students);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentsMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Students in the database
        List<Students> studentsList = studentsRepository.findAll();
        assertThat(studentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudents() throws Exception {
        // Initialize the database
        studentsRepository.saveAndFlush(students);

        int databaseSizeBeforeDelete = studentsRepository.findAll().size();

        // Delete the students
        restStudentsMockMvc.perform(delete("/api/students/{id}", students.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Students> studentsList = studentsRepository.findAll();
        assertThat(studentsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Students.class);
        Students students1 = new Students();
        students1.setId(1L);
        Students students2 = new Students();
        students2.setId(students1.getId());
        assertThat(students1).isEqualTo(students2);
        students2.setId(2L);
        assertThat(students1).isNotEqualTo(students2);
        students1.setId(null);
        assertThat(students1).isNotEqualTo(students2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentsDTO.class);
        StudentsDTO studentsDTO1 = new StudentsDTO();
        studentsDTO1.setId(1L);
        StudentsDTO studentsDTO2 = new StudentsDTO();
        assertThat(studentsDTO1).isNotEqualTo(studentsDTO2);
        studentsDTO2.setId(studentsDTO1.getId());
        assertThat(studentsDTO1).isEqualTo(studentsDTO2);
        studentsDTO2.setId(2L);
        assertThat(studentsDTO1).isNotEqualTo(studentsDTO2);
        studentsDTO1.setId(null);
        assertThat(studentsDTO1).isNotEqualTo(studentsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(studentsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(studentsMapper.fromId(null)).isNull();
    }
}
