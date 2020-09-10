package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.ScholarshipsService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.ScholarshipsDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.Scholarships}.
 */
@RestController
@RequestMapping("/api")
public class ScholarshipsResource {

    private final Logger log = LoggerFactory.getLogger(ScholarshipsResource.class);

    private static final String ENTITY_NAME = "scholarships";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScholarshipsService scholarshipsService;

    public ScholarshipsResource(ScholarshipsService scholarshipsService) {
        this.scholarshipsService = scholarshipsService;
    }

    /**
     * {@code POST  /scholarships} : Create a new scholarships.
     *
     * @param scholarshipsDTO the scholarshipsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scholarshipsDTO, or with status {@code 400 (Bad Request)} if the scholarships has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scholarships")
    public ResponseEntity<ScholarshipsDTO> createScholarships(@RequestBody ScholarshipsDTO scholarshipsDTO) throws URISyntaxException {
        log.debug("REST request to save Scholarships : {}", scholarshipsDTO);
        if (scholarshipsDTO.getId() != null) {
            throw new BadRequestAlertException("A new scholarships cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScholarshipsDTO result = scholarshipsService.save(scholarshipsDTO);
        return ResponseEntity.created(new URI("/api/scholarships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scholarships} : Updates an existing scholarships.
     *
     * @param scholarshipsDTO the scholarshipsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scholarshipsDTO,
     * or with status {@code 400 (Bad Request)} if the scholarshipsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scholarshipsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scholarships")
    public ResponseEntity<ScholarshipsDTO> updateScholarships(@RequestBody ScholarshipsDTO scholarshipsDTO) throws URISyntaxException {
        log.debug("REST request to update Scholarships : {}", scholarshipsDTO);
        if (scholarshipsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScholarshipsDTO result = scholarshipsService.save(scholarshipsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scholarshipsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scholarships} : get all the scholarships.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scholarships in body.
     */
    @GetMapping("/scholarships")
    public List<ScholarshipsDTO> getAllScholarships() {
        log.debug("REST request to get all Scholarships");
        return scholarshipsService.findAll();
    }

    /**
     * {@code GET  /scholarships/:id} : get the "id" scholarships.
     *
     * @param id the id of the scholarshipsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scholarshipsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scholarships/{id}")
    public ResponseEntity<ScholarshipsDTO> getScholarships(@PathVariable Long id) {
        log.debug("REST request to get Scholarships : {}", id);
        Optional<ScholarshipsDTO> scholarshipsDTO = scholarshipsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scholarshipsDTO);
    }

    /**
     * {@code DELETE  /scholarships/:id} : delete the "id" scholarships.
     *
     * @param id the id of the scholarshipsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scholarships/{id}")
    public ResponseEntity<Void> deleteScholarships(@PathVariable Long id) {
        log.debug("REST request to delete Scholarships : {}", id);
        scholarshipsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
