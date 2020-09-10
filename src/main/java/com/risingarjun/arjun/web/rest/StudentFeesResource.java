package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.StudentFeesService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.StudentFeesDTO;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.risingarjun.arjun.domain.StudentFees}.
 */
@RestController
@RequestMapping("/api")
public class StudentFeesResource {

    private final Logger log = LoggerFactory.getLogger(StudentFeesResource.class);

    private static final String ENTITY_NAME = "studentFees";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentFeesService studentFeesService;

    public StudentFeesResource(StudentFeesService studentFeesService) {
        this.studentFeesService = studentFeesService;
    }

    /**
     * {@code POST  /student-fees} : Create a new studentFees.
     *
     * @param studentFeesDTO the studentFeesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentFeesDTO, or with status {@code 400 (Bad Request)} if the studentFees has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-fees")
    public ResponseEntity<StudentFeesDTO> createStudentFees(@RequestBody StudentFeesDTO studentFeesDTO) throws URISyntaxException {
        log.debug("REST request to save StudentFees : {}", studentFeesDTO);
        if (studentFeesDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentFees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentFeesDTO result = studentFeesService.save(studentFeesDTO);
        return ResponseEntity.created(new URI("/api/student-fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-fees} : Updates an existing studentFees.
     *
     * @param studentFeesDTO the studentFeesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentFeesDTO,
     * or with status {@code 400 (Bad Request)} if the studentFeesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentFeesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-fees")
    public ResponseEntity<StudentFeesDTO> updateStudentFees(@RequestBody StudentFeesDTO studentFeesDTO) throws URISyntaxException {
        log.debug("REST request to update StudentFees : {}", studentFeesDTO);
        if (studentFeesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentFeesDTO result = studentFeesService.save(studentFeesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentFeesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /student-fees} : get all the studentFees.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentFees in body.
     */
    @GetMapping("/student-fees")
    public ResponseEntity<List<StudentFeesDTO>> getAllStudentFees(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of StudentFees");
        Page<StudentFeesDTO> page = studentFeesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /student-fees/:id} : get the "id" studentFees.
     *
     * @param id the id of the studentFeesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentFeesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-fees/{id}")
    public ResponseEntity<StudentFeesDTO> getStudentFees(@PathVariable Long id) {
        log.debug("REST request to get StudentFees : {}", id);
        Optional<StudentFeesDTO> studentFeesDTO = studentFeesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentFeesDTO);
    }

    /**
     * {@code DELETE  /student-fees/:id} : delete the "id" studentFees.
     *
     * @param id the id of the studentFeesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-fees/{id}")
    public ResponseEntity<Void> deleteStudentFees(@PathVariable Long id) {
        log.debug("REST request to delete StudentFees : {}", id);
        studentFeesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
