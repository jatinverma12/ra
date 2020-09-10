package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.SubjectsService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.SubjectsDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.Subjects}.
 */
@RestController
@RequestMapping("/api")
public class SubjectsResource {

    private final Logger log = LoggerFactory.getLogger(SubjectsResource.class);

    private static final String ENTITY_NAME = "subjects";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubjectsService subjectsService;

    public SubjectsResource(SubjectsService subjectsService) {
        this.subjectsService = subjectsService;
    }

    /**
     * {@code POST  /subjects} : Create a new subjects.
     *
     * @param subjectsDTO the subjectsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subjectsDTO, or with status {@code 400 (Bad Request)} if the subjects has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subjects")
    public ResponseEntity<SubjectsDTO> createSubjects(@Valid @RequestBody SubjectsDTO subjectsDTO) throws URISyntaxException {
        log.debug("REST request to save Subjects : {}", subjectsDTO);
        if (subjectsDTO.getId() != null) {
            throw new BadRequestAlertException("A new subjects cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubjectsDTO result = subjectsService.save(subjectsDTO);
        return ResponseEntity.created(new URI("/api/subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subjects} : Updates an existing subjects.
     *
     * @param subjectsDTO the subjectsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subjectsDTO,
     * or with status {@code 400 (Bad Request)} if the subjectsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subjectsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subjects")
    public ResponseEntity<SubjectsDTO> updateSubjects(@Valid @RequestBody SubjectsDTO subjectsDTO) throws URISyntaxException {
        log.debug("REST request to update Subjects : {}", subjectsDTO);
        if (subjectsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubjectsDTO result = subjectsService.save(subjectsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subjectsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subjects} : get all the subjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subjects in body.
     */
    @GetMapping("/subjects")
    public List<SubjectsDTO> getAllSubjects() {
        log.debug("REST request to get all Subjects");
        return subjectsService.findAll();
    }

    /**
     * {@code GET  /subjects/:id} : get the "id" subjects.
     *
     * @param id the id of the subjectsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subjectsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectsDTO> getSubjects(@PathVariable Long id) {
        log.debug("REST request to get Subjects : {}", id);
        Optional<SubjectsDTO> subjectsDTO = subjectsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subjectsDTO);
    }

    /**
     * {@code DELETE  /subjects/:id} : delete the "id" subjects.
     *
     * @param id the id of the subjectsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubjects(@PathVariable Long id) {
        log.debug("REST request to delete Subjects : {}", id);
        subjectsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
