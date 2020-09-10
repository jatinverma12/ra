package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.CenterHeadService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.CenterHeadDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.CenterHead}.
 */
@RestController
@RequestMapping("/api")
public class CenterHeadResource {

    private final Logger log = LoggerFactory.getLogger(CenterHeadResource.class);

    private static final String ENTITY_NAME = "centerHead";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CenterHeadService centerHeadService;

    public CenterHeadResource(CenterHeadService centerHeadService) {
        this.centerHeadService = centerHeadService;
    }

    /**
     * {@code POST  /center-heads} : Create a new centerHead.
     *
     * @param centerHeadDTO the centerHeadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new centerHeadDTO, or with status {@code 400 (Bad Request)} if the centerHead has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/center-heads")
    public ResponseEntity<CenterHeadDTO> createCenterHead(@RequestBody CenterHeadDTO centerHeadDTO) throws URISyntaxException {
        log.debug("REST request to save CenterHead : {}", centerHeadDTO);
        if (centerHeadDTO.getId() != null) {
            throw new BadRequestAlertException("A new centerHead cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CenterHeadDTO result = centerHeadService.save(centerHeadDTO);
        return ResponseEntity.created(new URI("/api/center-heads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /center-heads} : Updates an existing centerHead.
     *
     * @param centerHeadDTO the centerHeadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated centerHeadDTO,
     * or with status {@code 400 (Bad Request)} if the centerHeadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the centerHeadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/center-heads")
    public ResponseEntity<CenterHeadDTO> updateCenterHead(@RequestBody CenterHeadDTO centerHeadDTO) throws URISyntaxException {
        log.debug("REST request to update CenterHead : {}", centerHeadDTO);
        if (centerHeadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CenterHeadDTO result = centerHeadService.save(centerHeadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, centerHeadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /center-heads} : get all the centerHeads.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of centerHeads in body.
     */
    @GetMapping("/center-heads")
    public List<CenterHeadDTO> getAllCenterHeads(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all CenterHeads");
        return centerHeadService.findAll();
    }

    /**
     * {@code GET  /center-heads/:id} : get the "id" centerHead.
     *
     * @param id the id of the centerHeadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the centerHeadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/center-heads/{id}")
    public ResponseEntity<CenterHeadDTO> getCenterHead(@PathVariable Long id) {
        log.debug("REST request to get CenterHead : {}", id);
        Optional<CenterHeadDTO> centerHeadDTO = centerHeadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centerHeadDTO);
    }

    /**
     * {@code DELETE  /center-heads/:id} : delete the "id" centerHead.
     *
     * @param id the id of the centerHeadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/center-heads/{id}")
    public ResponseEntity<Void> deleteCenterHead(@PathVariable Long id) {
        log.debug("REST request to delete CenterHead : {}", id);
        centerHeadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
