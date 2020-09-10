package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.StudentsService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.StudentsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.risingarjun.arjun.domain.Students}.
 */
@RestController
@RequestMapping("/api")
public class StudentsResource {

    private final Logger log = LoggerFactory.getLogger(StudentsResource.class);

    private static final String ENTITY_NAME = "students";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentsService studentsService;

    public StudentsResource(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    /**
     * {@code POST  /students} : Create a new students.
     *
     * @param studentsDTO the studentsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentsDTO, or with status {@code 400 (Bad Request)} if the students has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/students")
    public ResponseEntity<StudentsDTO> createStudents(@Valid @RequestBody StudentsDTO studentsDTO) throws URISyntaxException {
        log.debug("REST request to save Students : {}", studentsDTO);
        if (studentsDTO.getId() != null) {
            throw new BadRequestAlertException("A new students cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentsDTO result = studentsService.save(studentsDTO);
        return ResponseEntity.created(new URI("/api/students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /students} : Updates an existing students.
     *
     * @param studentsDTO the studentsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentsDTO,
     * or with status {@code 400 (Bad Request)} if the studentsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/students")
    public ResponseEntity<StudentsDTO> updateStudents(@Valid @RequestBody StudentsDTO studentsDTO) throws URISyntaxException {
        log.debug("REST request to update Students : {}", studentsDTO);
        if (studentsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentsDTO result = studentsService.save(studentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /students} : get all the students.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of students in body.
     */
    @GetMapping("/students")
    public ResponseEntity<List<StudentsDTO>> getAllStudents(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Students");
        Page<StudentsDTO> page;
        if (eagerload) {
            page = studentsService.findAllWithEagerRelationships(pageable);
        } else {
            page = studentsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /students/:id} : get the "id" students.
     *
     * @param id the id of the studentsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<StudentsDTO> getStudents(@PathVariable Long id) {
        log.debug("REST request to get Students : {}", id);
        Optional<StudentsDTO> studentsDTO = studentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentsDTO);
    }

    /**
     * {@code DELETE  /students/:id} : delete the "id" students.
     *
     * @param id the id of the studentsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudents(@PathVariable Long id) {
        log.debug("REST request to delete Students : {}", id);
        studentsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
