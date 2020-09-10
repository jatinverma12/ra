package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.DiscountsService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.DiscountsDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.Discounts}.
 */
@RestController
@RequestMapping("/api")
public class DiscountsResource {

    private final Logger log = LoggerFactory.getLogger(DiscountsResource.class);

    private static final String ENTITY_NAME = "discounts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountsService discountsService;

    public DiscountsResource(DiscountsService discountsService) {
        this.discountsService = discountsService;
    }

    /**
     * {@code POST  /discounts} : Create a new discounts.
     *
     * @param discountsDTO the discountsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountsDTO, or with status {@code 400 (Bad Request)} if the discounts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discounts")
    public ResponseEntity<DiscountsDTO> createDiscounts(@RequestBody DiscountsDTO discountsDTO) throws URISyntaxException {
        log.debug("REST request to save Discounts : {}", discountsDTO);
        if (discountsDTO.getId() != null) {
            throw new BadRequestAlertException("A new discounts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountsDTO result = discountsService.save(discountsDTO);
        return ResponseEntity.created(new URI("/api/discounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discounts} : Updates an existing discounts.
     *
     * @param discountsDTO the discountsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountsDTO,
     * or with status {@code 400 (Bad Request)} if the discountsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discounts")
    public ResponseEntity<DiscountsDTO> updateDiscounts(@RequestBody DiscountsDTO discountsDTO) throws URISyntaxException {
        log.debug("REST request to update Discounts : {}", discountsDTO);
        if (discountsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountsDTO result = discountsService.save(discountsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, discountsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /discounts} : get all the discounts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discounts in body.
     */
    @GetMapping("/discounts")
    public List<DiscountsDTO> getAllDiscounts() {
        log.debug("REST request to get all Discounts");
        return discountsService.findAll();
    }

    /**
     * {@code GET  /discounts/:id} : get the "id" discounts.
     *
     * @param id the id of the discountsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discounts/{id}")
    public ResponseEntity<DiscountsDTO> getDiscounts(@PathVariable Long id) {
        log.debug("REST request to get Discounts : {}", id);
        Optional<DiscountsDTO> discountsDTO = discountsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountsDTO);
    }

    /**
     * {@code DELETE  /discounts/:id} : delete the "id" discounts.
     *
     * @param id the id of the discountsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<Void> deleteDiscounts(@PathVariable Long id) {
        log.debug("REST request to delete Discounts : {}", id);
        discountsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
