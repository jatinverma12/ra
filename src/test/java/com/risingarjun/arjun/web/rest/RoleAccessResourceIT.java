package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.RisingArjunApp;
import com.risingarjun.arjun.domain.RoleAccess;
import com.risingarjun.arjun.repository.RoleAccessRepository;
import com.risingarjun.arjun.service.RoleAccessService;
import com.risingarjun.arjun.service.dto.RoleAccessDTO;
import com.risingarjun.arjun.service.mapper.RoleAccessMapper;
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
 * Integration tests for the {@Link RoleAccessResource} REST controller.
 */
@SpringBootTest(classes = RisingArjunApp.class)
public class RoleAccessResourceIT {

    private static final Boolean DEFAULT_CREATE = false;
    private static final Boolean UPDATED_CREATE = true;

    private static final Boolean DEFAULT_READ = false;
    private static final Boolean UPDATED_READ = true;

    private static final Boolean DEFAULT_UPDATE = false;
    private static final Boolean UPDATED_UPDATE = true;

    private static final Boolean DEFAULT_DEL = false;
    private static final Boolean UPDATED_DEL = true;

    @Autowired
    private RoleAccessRepository roleAccessRepository;

    @Autowired
    private RoleAccessMapper roleAccessMapper;

    @Autowired
    private RoleAccessService roleAccessService;

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

    private MockMvc restRoleAccessMockMvc;

    private RoleAccess roleAccess;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleAccessResource roleAccessResource = new RoleAccessResource(roleAccessService);
        this.restRoleAccessMockMvc = MockMvcBuilders.standaloneSetup(roleAccessResource)
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
    public static RoleAccess createEntity(EntityManager em) {
        RoleAccess roleAccess = new RoleAccess()
            .create(DEFAULT_CREATE)
            .read(DEFAULT_READ)
            .update(DEFAULT_UPDATE)
            .del(DEFAULT_DEL);
        return roleAccess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleAccess createUpdatedEntity(EntityManager em) {
        RoleAccess roleAccess = new RoleAccess()
            .create(UPDATED_CREATE)
            .read(UPDATED_READ)
            .update(UPDATED_UPDATE)
            .del(UPDATED_DEL);
        return roleAccess;
    }

    @BeforeEach
    public void initTest() {
        roleAccess = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleAccess() throws Exception {
        int databaseSizeBeforeCreate = roleAccessRepository.findAll().size();

        // Create the RoleAccess
        RoleAccessDTO roleAccessDTO = roleAccessMapper.toDto(roleAccess);
        restRoleAccessMockMvc.perform(post("/api/role-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the RoleAccess in the database
        List<RoleAccess> roleAccessList = roleAccessRepository.findAll();
        assertThat(roleAccessList).hasSize(databaseSizeBeforeCreate + 1);
        RoleAccess testRoleAccess = roleAccessList.get(roleAccessList.size() - 1);
        assertThat(testRoleAccess.isCreate()).isEqualTo(DEFAULT_CREATE);
        assertThat(testRoleAccess.isRead()).isEqualTo(DEFAULT_READ);
        assertThat(testRoleAccess.isUpdate()).isEqualTo(DEFAULT_UPDATE);
        assertThat(testRoleAccess.isDel()).isEqualTo(DEFAULT_DEL);
    }

    @Test
    @Transactional
    public void createRoleAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleAccessRepository.findAll().size();

        // Create the RoleAccess with an existing ID
        roleAccess.setId(1L);
        RoleAccessDTO roleAccessDTO = roleAccessMapper.toDto(roleAccess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleAccessMockMvc.perform(post("/api/role-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoleAccess in the database
        List<RoleAccess> roleAccessList = roleAccessRepository.findAll();
        assertThat(roleAccessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRoleAccesses() throws Exception {
        // Initialize the database
        roleAccessRepository.saveAndFlush(roleAccess);

        // Get all the roleAccessList
        restRoleAccessMockMvc.perform(get("/api/role-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].create").value(hasItem(DEFAULT_CREATE.booleanValue())))
            .andExpect(jsonPath("$.[*].read").value(hasItem(DEFAULT_READ.booleanValue())))
            .andExpect(jsonPath("$.[*].update").value(hasItem(DEFAULT_UPDATE.booleanValue())))
            .andExpect(jsonPath("$.[*].del").value(hasItem(DEFAULT_DEL.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRoleAccess() throws Exception {
        // Initialize the database
        roleAccessRepository.saveAndFlush(roleAccess);

        // Get the roleAccess
        restRoleAccessMockMvc.perform(get("/api/role-accesses/{id}", roleAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roleAccess.getId().intValue()))
            .andExpect(jsonPath("$.create").value(DEFAULT_CREATE.booleanValue()))
            .andExpect(jsonPath("$.read").value(DEFAULT_READ.booleanValue()))
            .andExpect(jsonPath("$.update").value(DEFAULT_UPDATE.booleanValue()))
            .andExpect(jsonPath("$.del").value(DEFAULT_DEL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRoleAccess() throws Exception {
        // Get the roleAccess
        restRoleAccessMockMvc.perform(get("/api/role-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleAccess() throws Exception {
        // Initialize the database
        roleAccessRepository.saveAndFlush(roleAccess);

        int databaseSizeBeforeUpdate = roleAccessRepository.findAll().size();

        // Update the roleAccess
        RoleAccess updatedRoleAccess = roleAccessRepository.findById(roleAccess.getId()).get();
        // Disconnect from session so that the updates on updatedRoleAccess are not directly saved in db
        em.detach(updatedRoleAccess);
        updatedRoleAccess
            .create(UPDATED_CREATE)
            .read(UPDATED_READ)
            .update(UPDATED_UPDATE)
            .del(UPDATED_DEL);
        RoleAccessDTO roleAccessDTO = roleAccessMapper.toDto(updatedRoleAccess);

        restRoleAccessMockMvc.perform(put("/api/role-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleAccessDTO)))
            .andExpect(status().isOk());

        // Validate the RoleAccess in the database
        List<RoleAccess> roleAccessList = roleAccessRepository.findAll();
        assertThat(roleAccessList).hasSize(databaseSizeBeforeUpdate);
        RoleAccess testRoleAccess = roleAccessList.get(roleAccessList.size() - 1);
        assertThat(testRoleAccess.isCreate()).isEqualTo(UPDATED_CREATE);
        assertThat(testRoleAccess.isRead()).isEqualTo(UPDATED_READ);
        assertThat(testRoleAccess.isUpdate()).isEqualTo(UPDATED_UPDATE);
        assertThat(testRoleAccess.isDel()).isEqualTo(UPDATED_DEL);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleAccess() throws Exception {
        int databaseSizeBeforeUpdate = roleAccessRepository.findAll().size();

        // Create the RoleAccess
        RoleAccessDTO roleAccessDTO = roleAccessMapper.toDto(roleAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleAccessMockMvc.perform(put("/api/role-accesses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RoleAccess in the database
        List<RoleAccess> roleAccessList = roleAccessRepository.findAll();
        assertThat(roleAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoleAccess() throws Exception {
        // Initialize the database
        roleAccessRepository.saveAndFlush(roleAccess);

        int databaseSizeBeforeDelete = roleAccessRepository.findAll().size();

        // Delete the roleAccess
        restRoleAccessMockMvc.perform(delete("/api/role-accesses/{id}", roleAccess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleAccess> roleAccessList = roleAccessRepository.findAll();
        assertThat(roleAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleAccess.class);
        RoleAccess roleAccess1 = new RoleAccess();
        roleAccess1.setId(1L);
        RoleAccess roleAccess2 = new RoleAccess();
        roleAccess2.setId(roleAccess1.getId());
        assertThat(roleAccess1).isEqualTo(roleAccess2);
        roleAccess2.setId(2L);
        assertThat(roleAccess1).isNotEqualTo(roleAccess2);
        roleAccess1.setId(null);
        assertThat(roleAccess1).isNotEqualTo(roleAccess2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleAccessDTO.class);
        RoleAccessDTO roleAccessDTO1 = new RoleAccessDTO();
        roleAccessDTO1.setId(1L);
        RoleAccessDTO roleAccessDTO2 = new RoleAccessDTO();
        assertThat(roleAccessDTO1).isNotEqualTo(roleAccessDTO2);
        roleAccessDTO2.setId(roleAccessDTO1.getId());
        assertThat(roleAccessDTO1).isEqualTo(roleAccessDTO2);
        roleAccessDTO2.setId(2L);
        assertThat(roleAccessDTO1).isNotEqualTo(roleAccessDTO2);
        roleAccessDTO1.setId(null);
        assertThat(roleAccessDTO1).isNotEqualTo(roleAccessDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roleAccessMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roleAccessMapper.fromId(null)).isNull();
    }
}
