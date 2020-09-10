package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.TeachersService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.TeachersDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.risingarjun.arjun.domain.Teachers}.
 */
@RestController
@RequestMapping("/api")
public class TeachersResource {

    private final Logger log = LoggerFactory.getLogger(TeachersResource.class);

    private static final String ENTITY_NAME = "teachers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeachersService teachersService;

    public TeachersResource(TeachersService teachersService) {
        this.teachersService = teachersService;
    }

    /**
     * {@code POST  /teachers} : Create a new teachers.
     *
     * @param teachersDTO the teachersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachersDTO, or with status {@code 400 (Bad Request)} if the teachers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachers")
    public ResponseEntity<TeachersDTO> createTeachers(@RequestBody TeachersDTO teachersDTO) throws URISyntaxException {
        log.debug("REST request to save Teachers : {}", teachersDTO);
        if (teachersDTO.getId() != null) {
            throw new BadRequestAlertException("A new teachers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeachersDTO result = teachersService.save(teachersDTO);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teachers} : Updates an existing teachers.
     *
     * @param teachersDTO the teachersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachersDTO,
     * or with status {@code 400 (Bad Request)} if the teachersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachers")
    public ResponseEntity<TeachersDTO> updateTeachers(@RequestBody TeachersDTO teachersDTO) throws URISyntaxException {
        log.debug("REST request to update Teachers : {}", teachersDTO);
        if (teachersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeachersDTO result = teachersService.save(teachersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teachersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /teachers} : get all the teachers.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("/teachers")
    public List<TeachersDTO> getAllTeachers(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Teachers");
        return teachersService.findAll();
    }

    /**
     * {@code GET  /teachers/:id} : get the "id" teachers.
     *
     * @param id the id of the teachersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teachersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeachersDTO> getTeachers(@PathVariable Long id) {
        log.debug("REST request to get Teachers : {}", id);
        Optional<TeachersDTO> teachersDTO = teachersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teachersDTO);
    }

    /**
     * {@code DELETE  /teachers/:id} : delete the "id" teachers.
     *
     * @param id the id of the teachersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeachers(@PathVariable Long id) {
        log.debug("REST request to delete Teachers : {}", id);
        teachersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
