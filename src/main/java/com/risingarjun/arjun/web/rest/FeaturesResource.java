package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.FeaturesService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.FeaturesDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.Features}.
 */
@RestController
@RequestMapping("/api")
public class FeaturesResource {

    private final Logger log = LoggerFactory.getLogger(FeaturesResource.class);

    private static final String ENTITY_NAME = "features";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeaturesService featuresService;

    public FeaturesResource(FeaturesService featuresService) {
        this.featuresService = featuresService;
    }

    /**
     * {@code POST  /features} : Create a new features.
     *
     * @param featuresDTO the featuresDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new featuresDTO, or with status {@code 400 (Bad Request)} if the features has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/features")
    public ResponseEntity<FeaturesDTO> createFeatures(@Valid @RequestBody FeaturesDTO featuresDTO) throws URISyntaxException {
        log.debug("REST request to save Features : {}", featuresDTO);
        if (featuresDTO.getId() != null) {
            throw new BadRequestAlertException("A new features cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeaturesDTO result = featuresService.save(featuresDTO);
        return ResponseEntity.created(new URI("/api/features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /features} : Updates an existing features.
     *
     * @param featuresDTO the featuresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated featuresDTO,
     * or with status {@code 400 (Bad Request)} if the featuresDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the featuresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/features")
    public ResponseEntity<FeaturesDTO> updateFeatures(@Valid @RequestBody FeaturesDTO featuresDTO) throws URISyntaxException {
        log.debug("REST request to update Features : {}", featuresDTO);
        if (featuresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FeaturesDTO result = featuresService.save(featuresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, featuresDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /features} : get all the features.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @GetMapping("/features")
    public List<FeaturesDTO> getAllFeatures() {
        log.debug("REST request to get all Features");
        return featuresService.findAll();
    }

    /**
     * {@code GET  /features/:id} : get the "id" features.
     *
     * @param id the id of the featuresDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the featuresDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/features/{id}")
    public ResponseEntity<FeaturesDTO> getFeatures(@PathVariable Long id) {
        log.debug("REST request to get Features : {}", id);
        Optional<FeaturesDTO> featuresDTO = featuresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(featuresDTO);
    }

    /**
     * {@code DELETE  /features/:id} : delete the "id" features.
     *
     * @param id the id of the featuresDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/features/{id}")
    public ResponseEntity<Void> deleteFeatures(@PathVariable Long id) {
        log.debug("REST request to delete Features : {}", id);
        featuresService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
