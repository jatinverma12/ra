package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.TeachersShareService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.TeachersShareDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.TeachersShare}.
 */
@RestController
@RequestMapping("/api")
public class TeachersShareResource {

    private final Logger log = LoggerFactory.getLogger(TeachersShareResource.class);

    private static final String ENTITY_NAME = "teachersShare";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeachersShareService teachersShareService;

    public TeachersShareResource(TeachersShareService teachersShareService) {
        this.teachersShareService = teachersShareService;
    }

    /**
     * {@code POST  /teachers-shares} : Create a new teachersShare.
     *
     * @param teachersShareDTO the teachersShareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teachersShareDTO, or with status {@code 400 (Bad Request)} if the teachersShare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachers-shares")
    public ResponseEntity<TeachersShareDTO> createTeachersShare(@Valid @RequestBody TeachersShareDTO teachersShareDTO) throws URISyntaxException {
        log.debug("REST request to save TeachersShare : {}", teachersShareDTO);
        if (teachersShareDTO.getId() != null) {
            throw new BadRequestAlertException("A new teachersShare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeachersShareDTO result = teachersShareService.save(teachersShareDTO);
        return ResponseEntity.created(new URI("/api/teachers-shares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teachers-shares} : Updates an existing teachersShare.
     *
     * @param teachersShareDTO the teachersShareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teachersShareDTO,
     * or with status {@code 400 (Bad Request)} if the teachersShareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teachersShareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachers-shares")
    public ResponseEntity<TeachersShareDTO> updateTeachersShare(@Valid @RequestBody TeachersShareDTO teachersShareDTO) throws URISyntaxException {
        log.debug("REST request to update TeachersShare : {}", teachersShareDTO);
        if (teachersShareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeachersShareDTO result = teachersShareService.save(teachersShareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teachersShareDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /teachers-shares} : get all the teachersShares.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachersShares in body.
     */
    @GetMapping("/teachers-shares")
    public ResponseEntity<List<TeachersShareDTO>> getAllTeachersShares(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TeachersShares");
        Page<TeachersShareDTO> page = teachersShareService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /teachers-shares/:id} : get the "id" teachersShare.
     *
     * @param id the id of the teachersShareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teachersShareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachers-shares/{id}")
    public ResponseEntity<TeachersShareDTO> getTeachersShare(@PathVariable Long id) {
        log.debug("REST request to get TeachersShare : {}", id);
        Optional<TeachersShareDTO> teachersShareDTO = teachersShareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teachersShareDTO);
    }

    /**
     * {@code DELETE  /teachers-shares/:id} : delete the "id" teachersShare.
     *
     * @param id the id of the teachersShareDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teachers-shares/{id}")
    public ResponseEntity<Void> deleteTeachersShare(@PathVariable Long id) {
        log.debug("REST request to delete TeachersShare : {}", id);
        teachersShareService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
