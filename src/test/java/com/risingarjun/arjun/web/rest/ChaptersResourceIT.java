package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.Chapters;
import com.risingarjun.arjun.repository.ChaptersRepository;
import com.risingarjun.arjun.service.ChaptersService;
import com.risingarjun.arjun.service.dto.ChaptersDTO;
import com.risingarjun.arjun.service.mapper.ChaptersMapper;
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
 * Integration tests for the {@Link ChaptersResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class ChaptersResourceIT {

    private static final String DEFAULT_CHAPTER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHAPTER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CHAPTER_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CHAPTER_TITLE = "BBBBBBBBBB";

    @Autowired
    private ChaptersRepository chaptersRepository;

    @Autowired
    private ChaptersMapper chaptersMapper;

    @Autowired
    private ChaptersService chaptersService;

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

    private MockMvc restChaptersMockMvc;

    private Chapters chapters;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChaptersResource chaptersResource = new ChaptersResource(chaptersService);
        this.restChaptersMockMvc = MockMvcBuilders.standaloneSetup(chaptersResource)
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
    public static Chapters createEntity(EntityManager em) {
        Chapters chapters = new Chapters()
            .chapterId(DEFAULT_CHAPTER_ID)
            .chapterTitle(DEFAULT_CHAPTER_TITLE);
        return chapters;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chapters createUpdatedEntity(EntityManager em) {
        Chapters chapters = new Chapters()
            .chapterId(UPDATED_CHAPTER_ID)
            .chapterTitle(UPDATED_CHAPTER_TITLE);
        return chapters;
    }

    @BeforeEach
    public void initTest() {
        chapters = createEntity(em);
    }

    @Test
    @Transactional
    public void createChapters() throws Exception {
        int databaseSizeBeforeCreate = chaptersRepository.findAll().size();

        // Create the Chapters
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);
        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isCreated());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeCreate + 1);
        Chapters testChapters = chaptersList.get(chaptersList.size() - 1);
        assertThat(testChapters.getChapterId()).isEqualTo(DEFAULT_CHAPTER_ID);
        assertThat(testChapters.getChapterTitle()).isEqualTo(DEFAULT_CHAPTER_TITLE);
    }

    @Test
    @Transactional
    public void createChaptersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chaptersRepository.findAll().size();

        // Create the Chapters with an existing ID
        chapters.setId(1L);
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkChapterIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chaptersRepository.findAll().size();
        // set the field null
        chapters.setChapterId(null);

        // Create the Chapters, which fails.
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        restChaptersMockMvc.perform(post("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        // Get all the chaptersList
        restChaptersMockMvc.perform(get("/api/chapters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chapters.getId().intValue())))
            .andExpect(jsonPath("$.[*].chapterId").value(hasItem(DEFAULT_CHAPTER_ID.toString())))
            .andExpect(jsonPath("$.[*].chapterTitle").value(hasItem(DEFAULT_CHAPTER_TITLE.toString())));
    }
    
    @Test
    @Transactional
    public void getChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        // Get the chapters
        restChaptersMockMvc.perform(get("/api/chapters/{id}", chapters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chapters.getId().intValue()))
            .andExpect(jsonPath("$.chapterId").value(DEFAULT_CHAPTER_ID.toString()))
            .andExpect(jsonPath("$.chapterTitle").value(DEFAULT_CHAPTER_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChapters() throws Exception {
        // Get the chapters
        restChaptersMockMvc.perform(get("/api/chapters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        int databaseSizeBeforeUpdate = chaptersRepository.findAll().size();

        // Update the chapters
        Chapters updatedChapters = chaptersRepository.findById(chapters.getId()).get();
        // Disconnect from session so that the updates on updatedChapters are not directly saved in db
        em.detach(updatedChapters);
        updatedChapters
            .chapterId(UPDATED_CHAPTER_ID)
            .chapterTitle(UPDATED_CHAPTER_TITLE);
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(updatedChapters);

        restChaptersMockMvc.perform(put("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isOk());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeUpdate);
        Chapters testChapters = chaptersList.get(chaptersList.size() - 1);
        assertThat(testChapters.getChapterId()).isEqualTo(UPDATED_CHAPTER_ID);
        assertThat(testChapters.getChapterTitle()).isEqualTo(UPDATED_CHAPTER_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingChapters() throws Exception {
        int databaseSizeBeforeUpdate = chaptersRepository.findAll().size();

        // Create the Chapters
        ChaptersDTO chaptersDTO = chaptersMapper.toDto(chapters);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChaptersMockMvc.perform(put("/api/chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaptersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chapters in the database
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChapters() throws Exception {
        // Initialize the database
        chaptersRepository.saveAndFlush(chapters);

        int databaseSizeBeforeDelete = chaptersRepository.findAll().size();

        // Delete the chapters
        restChaptersMockMvc.perform(delete("/api/chapters/{id}", chapters.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chapters> chaptersList = chaptersRepository.findAll();
        assertThat(chaptersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chapters.class);
        Chapters chapters1 = new Chapters();
        chapters1.setId(1L);
        Chapters chapters2 = new Chapters();
        chapters2.setId(chapters1.getId());
        assertThat(chapters1).isEqualTo(chapters2);
        chapters2.setId(2L);
        assertThat(chapters1).isNotEqualTo(chapters2);
        chapters1.setId(null);
        assertThat(chapters1).isNotEqualTo(chapters2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChaptersDTO.class);
        ChaptersDTO chaptersDTO1 = new ChaptersDTO();
        chaptersDTO1.setId(1L);
        ChaptersDTO chaptersDTO2 = new ChaptersDTO();
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
        chaptersDTO2.setId(chaptersDTO1.getId());
        assertThat(chaptersDTO1).isEqualTo(chaptersDTO2);
        chaptersDTO2.setId(2L);
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
        chaptersDTO1.setId(null);
        assertThat(chaptersDTO1).isNotEqualTo(chaptersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chaptersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chaptersMapper.fromId(null)).isNull();
    }
}
