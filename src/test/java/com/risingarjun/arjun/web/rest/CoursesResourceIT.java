package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Courses;
import com.risingarjun.arjun.repository.CoursesRepository;
import com.risingarjun.arjun.service.CoursesService;
import com.risingarjun.arjun.service.dto.CoursesDTO;
import com.risingarjun.arjun.service.mapper.CoursesMapper;
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
 * Integration tests for the {@Link CoursesResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class CoursesResourceIT {

    private static final String DEFAULT_COURSE_ID = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE = "BBBBBBBBBB";

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private CoursesMapper coursesMapper;

    @Autowired
    private CoursesService coursesService;

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

    private MockMvc restCoursesMockMvc;

    private Courses courses;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoursesResource coursesResource = new CoursesResource(coursesService);
        this.restCoursesMockMvc = MockMvcBuilders.standaloneSetup(coursesResource)
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
    public static Courses createEntity(EntityManager em) {
        Courses courses = new Courses()
            .courseId(DEFAULT_COURSE_ID)
            .course(DEFAULT_COURSE);
        return courses;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Courses createUpdatedEntity(EntityManager em) {
        Courses courses = new Courses()
            .courseId(UPDATED_COURSE_ID)
            .course(UPDATED_COURSE);
        return courses;
    }

    @BeforeEach
    public void initTest() {
        courses = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourses() throws Exception {
        int databaseSizeBeforeCreate = coursesRepository.findAll().size();

        // Create the Courses
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);
        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isCreated());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeCreate + 1);
        Courses testCourses = coursesList.get(coursesList.size() - 1);
        assertThat(testCourses.getCourseId()).isEqualTo(DEFAULT_COURSE_ID);
        assertThat(testCourses.getCourse()).isEqualTo(DEFAULT_COURSE);
    }

    @Test
    @Transactional
    public void createCoursesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coursesRepository.findAll().size();

        // Create the Courses with an existing ID
        courses.setId(1L);
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCourseIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursesRepository.findAll().size();
        // set the field null
        courses.setCourseId(null);

        // Create the Courses, which fails.
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);

        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isBadRequest());

        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCourseIsRequired() throws Exception {
        int databaseSizeBeforeTest = coursesRepository.findAll().size();
        // set the field null
        courses.setCourse(null);

        // Create the Courses, which fails.
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);

        restCoursesMockMvc.perform(post("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isBadRequest());

        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get all the coursesList
        restCoursesMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courses.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseId").value(hasItem(DEFAULT_COURSE_ID.toString())))
            .andExpect(jsonPath("$.[*].course").value(hasItem(DEFAULT_COURSE.toString())));
    }
    
    @Test
    @Transactional
    public void getCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        // Get the courses
        restCoursesMockMvc.perform(get("/api/courses/{id}", courses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(courses.getId().intValue()))
            .andExpect(jsonPath("$.courseId").value(DEFAULT_COURSE_ID.toString()))
            .andExpect(jsonPath("$.course").value(DEFAULT_COURSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourses() throws Exception {
        // Get the courses
        restCoursesMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        int databaseSizeBeforeUpdate = coursesRepository.findAll().size();

        // Update the courses
        Courses updatedCourses = coursesRepository.findById(courses.getId()).get();
        // Disconnect from session so that the updates on updatedCourses are not directly saved in db
        em.detach(updatedCourses);
        updatedCourses
            .courseId(UPDATED_COURSE_ID)
            .course(UPDATED_COURSE);
        CoursesDTO coursesDTO = coursesMapper.toDto(updatedCourses);

        restCoursesMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isOk());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeUpdate);
        Courses testCourses = coursesList.get(coursesList.size() - 1);
        assertThat(testCourses.getCourseId()).isEqualTo(UPDATED_COURSE_ID);
        assertThat(testCourses.getCourse()).isEqualTo(UPDATED_COURSE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourses() throws Exception {
        int databaseSizeBeforeUpdate = coursesRepository.findAll().size();

        // Create the Courses
        CoursesDTO coursesDTO = coursesMapper.toDto(courses);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursesMockMvc.perform(put("/api/courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coursesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Courses in the database
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourses() throws Exception {
        // Initialize the database
        coursesRepository.saveAndFlush(courses);

        int databaseSizeBeforeDelete = coursesRepository.findAll().size();

        // Delete the courses
        restCoursesMockMvc.perform(delete("/api/courses/{id}", courses.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Courses> coursesList = coursesRepository.findAll();
        assertThat(coursesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Courses.class);
        Courses courses1 = new Courses();
        courses1.setId(1L);
        Courses courses2 = new Courses();
        courses2.setId(courses1.getId());
        assertThat(courses1).isEqualTo(courses2);
        courses2.setId(2L);
        assertThat(courses1).isNotEqualTo(courses2);
        courses1.setId(null);
        assertThat(courses1).isNotEqualTo(courses2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursesDTO.class);
        CoursesDTO coursesDTO1 = new CoursesDTO();
        coursesDTO1.setId(1L);
        CoursesDTO coursesDTO2 = new CoursesDTO();
        assertThat(coursesDTO1).isNotEqualTo(coursesDTO2);
        coursesDTO2.setId(coursesDTO1.getId());
        assertThat(coursesDTO1).isEqualTo(coursesDTO2);
        coursesDTO2.setId(2L);
        assertThat(coursesDTO1).isNotEqualTo(coursesDTO2);
        coursesDTO1.setId(null);
        assertThat(coursesDTO1).isNotEqualTo(coursesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coursesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coursesMapper.fromId(null)).isNull();
    }
}
