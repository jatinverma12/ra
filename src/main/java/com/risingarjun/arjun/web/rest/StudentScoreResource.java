package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.StudentScoreService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.StudentScoreDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.StudentScore}.
 */
@RestController
@RequestMapping("/api")
public class StudentScoreResource {

    private final Logger log = LoggerFactory.getLogger(StudentScoreResource.class);

    private static final String ENTITY_NAME = "studentScore";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentScoreService studentScoreService;

    public StudentScoreResource(StudentScoreService studentScoreService) {
        this.studentScoreService = studentScoreService;
    }

    /**
     * {@code POST  /student-scores} : Create a new studentScore.
     *
     * @param studentScoreDTO the studentScoreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentScoreDTO, or with status {@code 400 (Bad Request)} if the studentScore has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-scores")
    public ResponseEntity<StudentScoreDTO> createStudentScore(@Valid @RequestBody StudentScoreDTO studentScoreDTO) throws URISyntaxException {
        log.debug("REST request to save StudentScore : {}", studentScoreDTO);
        if (studentScoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentScore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentScoreDTO result = studentScoreService.save(studentScoreDTO);
        return ResponseEntity.created(new URI("/api/student-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /student-scores} : Updates an existing studentScore.
     *
     * @param studentScoreDTO the studentScoreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentScoreDTO,
     * or with status {@code 400 (Bad Request)} if the studentScoreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentScoreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-scores")
    public ResponseEntity<StudentScoreDTO> updateStudentScore(@Valid @RequestBody StudentScoreDTO studentScoreDTO) throws URISyntaxException {
        log.debug("REST request to update StudentScore : {}", studentScoreDTO);
        if (studentScoreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentScoreDTO result = studentScoreService.save(studentScoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentScoreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /student-scores} : get all the studentScores.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentScores in body.
     */
    @GetMapping("/student-scores")
    public ResponseEntity<List<StudentScoreDTO>> getAllStudentScores(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of StudentScores");
        Page<StudentScoreDTO> page = studentScoreService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /student-scores/:id} : get the "id" studentScore.
     *
     * @param id the id of the studentScoreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentScoreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-scores/{id}")
    public ResponseEntity<StudentScoreDTO> getStudentScore(@PathVariable Long id) {
        log.debug("REST request to get StudentScore : {}", id);
        Optional<StudentScoreDTO> studentScoreDTO = studentScoreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentScoreDTO);
    }

    /**
     * {@code DELETE  /student-scores/:id} : delete the "id" studentScore.
     *
     * @param id the id of the studentScoreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-scores/{id}")
    public ResponseEntity<Void> deleteStudentScore(@PathVariable Long id) {
        log.debug("REST request to delete StudentScore : {}", id);
        studentScoreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
