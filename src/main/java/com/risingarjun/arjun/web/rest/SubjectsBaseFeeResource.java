package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.SubjectsBaseFeeService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.SubjectsBaseFeeDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.SubjectsBaseFee}.
 */
@RestController
@RequestMapping("/api")
public class SubjectsBaseFeeResource {

    private final Logger log = LoggerFactory.getLogger(SubjectsBaseFeeResource.class);

    private static final String ENTITY_NAME = "subjectsBaseFee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubjectsBaseFeeService subjectsBaseFeeService;

    public SubjectsBaseFeeResource(SubjectsBaseFeeService subjectsBaseFeeService) {
        this.subjectsBaseFeeService = subjectsBaseFeeService;
    }

    /**
     * {@code POST  /subjects-base-fees} : Create a new subjectsBaseFee.
     *
     * @param subjectsBaseFeeDTO the subjectsBaseFeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subjectsBaseFeeDTO, or with status {@code 400 (Bad Request)} if the subjectsBaseFee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subjects-base-fees")
    public ResponseEntity<SubjectsBaseFeeDTO> createSubjectsBaseFee(@RequestBody SubjectsBaseFeeDTO subjectsBaseFeeDTO) throws URISyntaxException {
        log.debug("REST request to save SubjectsBaseFee : {}", subjectsBaseFeeDTO);
        if (subjectsBaseFeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new subjectsBaseFee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubjectsBaseFeeDTO result = subjectsBaseFeeService.save(subjectsBaseFeeDTO);
        return ResponseEntity.created(new URI("/api/subjects-base-fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subjects-base-fees} : Updates an existing subjectsBaseFee.
     *
     * @param subjectsBaseFeeDTO the subjectsBaseFeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subjectsBaseFeeDTO,
     * or with status {@code 400 (Bad Request)} if the subjectsBaseFeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subjectsBaseFeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subjects-base-fees")
    public ResponseEntity<SubjectsBaseFeeDTO> updateSubjectsBaseFee(@RequestBody SubjectsBaseFeeDTO subjectsBaseFeeDTO) throws URISyntaxException {
        log.debug("REST request to update SubjectsBaseFee : {}", subjectsBaseFeeDTO);
        if (subjectsBaseFeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubjectsBaseFeeDTO result = subjectsBaseFeeService.save(subjectsBaseFeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subjectsBaseFeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subjects-base-fees} : get all the subjectsBaseFees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subjectsBaseFees in body.
     */
    @GetMapping("/subjects-base-fees")
    public List<SubjectsBaseFeeDTO> getAllSubjectsBaseFees() {
        log.debug("REST request to get all SubjectsBaseFees");
        return subjectsBaseFeeService.findAll();
    }

    /**
     * {@code GET  /subjects-base-fees/:id} : get the "id" subjectsBaseFee.
     *
     * @param id the id of the subjectsBaseFeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subjectsBaseFeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subjects-base-fees/{id}")
    public ResponseEntity<SubjectsBaseFeeDTO> getSubjectsBaseFee(@PathVariable Long id) {
        log.debug("REST request to get SubjectsBaseFee : {}", id);
        Optional<SubjectsBaseFeeDTO> subjectsBaseFeeDTO = subjectsBaseFeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subjectsBaseFeeDTO);
    }

    /**
     * {@code DELETE  /subjects-base-fees/:id} : delete the "id" subjectsBaseFee.
     *
     * @param id the id of the subjectsBaseFeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subjects-base-fees/{id}")
    public ResponseEntity<Void> deleteSubjectsBaseFee(@PathVariable Long id) {
        log.debug("REST request to delete SubjectsBaseFee : {}", id);
        subjectsBaseFeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
