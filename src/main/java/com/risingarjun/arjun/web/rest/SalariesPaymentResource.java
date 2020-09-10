package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.SalariesPaymentService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.SalariesPaymentDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.SalariesPayment}.
 */
@RestController
@RequestMapping("/api")
public class SalariesPaymentResource {

    private final Logger log = LoggerFactory.getLogger(SalariesPaymentResource.class);

    private static final String ENTITY_NAME = "salariesPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalariesPaymentService salariesPaymentService;

    public SalariesPaymentResource(SalariesPaymentService salariesPaymentService) {
        this.salariesPaymentService = salariesPaymentService;
    }

    /**
     * {@code POST  /salaries-payments} : Create a new salariesPayment.
     *
     * @param salariesPaymentDTO the salariesPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salariesPaymentDTO, or with status {@code 400 (Bad Request)} if the salariesPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/salaries-payments")
    public ResponseEntity<SalariesPaymentDTO> createSalariesPayment(@Valid @RequestBody SalariesPaymentDTO salariesPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save SalariesPayment : {}", salariesPaymentDTO);
        if (salariesPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new salariesPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalariesPaymentDTO result = salariesPaymentService.save(salariesPaymentDTO);
        return ResponseEntity.created(new URI("/api/salaries-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /salaries-payments} : Updates an existing salariesPayment.
     *
     * @param salariesPaymentDTO the salariesPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salariesPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the salariesPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salariesPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/salaries-payments")
    public ResponseEntity<SalariesPaymentDTO> updateSalariesPayment(@Valid @RequestBody SalariesPaymentDTO salariesPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update SalariesPayment : {}", salariesPaymentDTO);
        if (salariesPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalariesPaymentDTO result = salariesPaymentService.save(salariesPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salariesPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /salaries-payments} : get all the salariesPayments.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salariesPayments in body.
     */
    @GetMapping("/salaries-payments")
    public ResponseEntity<List<SalariesPaymentDTO>> getAllSalariesPayments(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of SalariesPayments");
        Page<SalariesPaymentDTO> page = salariesPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /salaries-payments/:id} : get the "id" salariesPayment.
     *
     * @param id the id of the salariesPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salariesPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/salaries-payments/{id}")
    public ResponseEntity<SalariesPaymentDTO> getSalariesPayment(@PathVariable Long id) {
        log.debug("REST request to get SalariesPayment : {}", id);
        Optional<SalariesPaymentDTO> salariesPaymentDTO = salariesPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salariesPaymentDTO);
    }

    /**
     * {@code DELETE  /salaries-payments/:id} : delete the "id" salariesPayment.
     *
     * @param id the id of the salariesPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/salaries-payments/{id}")
    public ResponseEntity<Void> deleteSalariesPayment(@PathVariable Long id) {
        log.debug("REST request to delete SalariesPayment : {}", id);
        salariesPaymentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
