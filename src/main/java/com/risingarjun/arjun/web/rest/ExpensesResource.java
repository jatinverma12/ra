package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.ExpensesService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.ExpensesDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.Expenses}.
 */
@RestController
@RequestMapping("/api")
public class ExpensesResource {

    private final Logger log = LoggerFactory.getLogger(ExpensesResource.class);

    private static final String ENTITY_NAME = "expenses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpensesService expensesService;

    public ExpensesResource(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    /**
     * {@code POST  /expenses} : Create a new expenses.
     *
     * @param expensesDTO the expensesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expensesDTO, or with status {@code 400 (Bad Request)} if the expenses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expenses")
    public ResponseEntity<ExpensesDTO> createExpenses(@Valid @RequestBody ExpensesDTO expensesDTO) throws URISyntaxException {
        log.debug("REST request to save Expenses : {}", expensesDTO);
        if (expensesDTO.getId() != null) {
            throw new BadRequestAlertException("A new expenses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpensesDTO result = expensesService.save(expensesDTO);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expenses} : Updates an existing expenses.
     *
     * @param expensesDTO the expensesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expensesDTO,
     * or with status {@code 400 (Bad Request)} if the expensesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expensesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expenses")
    public ResponseEntity<ExpensesDTO> updateExpenses(@Valid @RequestBody ExpensesDTO expensesDTO) throws URISyntaxException {
        log.debug("REST request to update Expenses : {}", expensesDTO);
        if (expensesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExpensesDTO result = expensesService.save(expensesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expensesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /expenses} : get all the expenses.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenses in body.
     */
    @GetMapping("/expenses")
    public ResponseEntity<List<ExpensesDTO>> getAllExpenses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Expenses");
        Page<ExpensesDTO> page = expensesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expenses/:id} : get the "id" expenses.
     *
     * @param id the id of the expensesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expensesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expenses/{id}")
    public ResponseEntity<ExpensesDTO> getExpenses(@PathVariable Long id) {
        log.debug("REST request to get Expenses : {}", id);
        Optional<ExpensesDTO> expensesDTO = expensesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expensesDTO);
    }

    /**
     * {@code DELETE  /expenses/:id} : delete the "id" expenses.
     *
     * @param id the id of the expensesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpenses(@PathVariable Long id) {
        log.debug("REST request to delete Expenses : {}", id);
        expensesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
