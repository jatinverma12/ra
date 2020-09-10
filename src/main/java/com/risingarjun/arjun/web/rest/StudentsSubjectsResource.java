package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.StudentsSubjectsService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.StudentsSubjectsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.risingarjun.arjun.domain.StudentsSubjects}.
 */
@RestController
@RequestMapping("/api")
public class StudentsSubjectsResource {

    private final Logger log = LoggerFactory.getLogger(StudentsSubjectsResource.class);

    private static final String ENTITY_NAME = "studentsSubjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentsSubjectsService studentsSubjectsService;

    public StudentsSubjectsResource(StudentsSubjectsService studentsSubjectsService) {
        this.studentsSubjectsService = studentsSubjectsService;
    }

    /**
     * {@code POST  /students-subjects} : Create a new studentsSubjects.
     *
     * @param studentsSubjectsDTO the studentsSubjectsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentsSubjectsDTO, or with status {@code 400 (Bad Request)} if the studentsSubjects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/students-subjects")
    public ResponseEntity<StudentsSubjectsDTO> createStudentsSubjects(@Valid @RequestBody StudentsSubjectsDTO studentsSubjectsDTO) throws URISyntaxException {
        log.debug("REST request to save StudentsSubjects : {}", studentsSubjectsDTO);
        if (studentsSubjectsDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentsSubjects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentsSubjectsDTO result = studentsSubjectsService.save(studentsSubjectsDTO);
        return ResponseEntity.created(new URI("/api/students-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /students-subjects} : Updates an existing studentsSubjects.
     *
     * @param studentsSubjectsDTO the studentsSubjectsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentsSubjectsDTO,
     * or with status {@code 400 (Bad Request)} if the studentsSubjectsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentsSubjectsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/students-subjects")
    public ResponseEntity<StudentsSubjectsDTO> updateStudentsSubjects(@Valid @RequestBody StudentsSubjectsDTO studentsSubjectsDTO) throws URISyntaxException {
        log.debug("REST request to update StudentsSubjects : {}", studentsSubjectsDTO);
        if (studentsSubjectsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentsSubjectsDTO result = studentsSubjectsService.save(studentsSubjectsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studentsSubjectsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /students-subjects} : get all the studentsSubjects.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studentsSubjects in body.
     */
    @GetMapping("/students-subjects")
    public List<StudentsSubjectsDTO> getAllStudentsSubjects(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all StudentsSubjects");
        return studentsSubjectsService.findAll();
    }

    /**
     * {@code GET  /students-subjects/:id} : get the "id" studentsSubjects.
     *
     * @param id the id of the studentsSubjectsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studentsSubjectsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/students-subjects/{id}")
    public ResponseEntity<StudentsSubjectsDTO> getStudentsSubjects(@PathVariable Long id) {
        log.debug("REST request to get StudentsSubjects : {}", id);
        Optional<StudentsSubjectsDTO> studentsSubjectsDTO = studentsSubjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentsSubjectsDTO);
    }

    /**
     * {@code DELETE  /students-subjects/:id} : delete the "id" studentsSubjects.
     *
     * @param id the id of the studentsSubjectsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/students-subjects/{id}")
    public ResponseEntity<Void> deleteStudentsSubjects(@PathVariable Long id) {
        log.debug("REST request to delete StudentsSubjects : {}", id);
        studentsSubjectsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
