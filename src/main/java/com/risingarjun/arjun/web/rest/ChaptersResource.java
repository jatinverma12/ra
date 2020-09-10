package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.ChaptersService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.ChaptersDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.Chapters}.
 */
@RestController
@RequestMapping("/api")
public class ChaptersResource {

    private final Logger log = LoggerFactory.getLogger(ChaptersResource.class);

    private static final String ENTITY_NAME = "chapters";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChaptersService chaptersService;

    public ChaptersResource(ChaptersService chaptersService) {
        this.chaptersService = chaptersService;
    }

    /**
     * {@code POST  /chapters} : Create a new chapters.
     *
     * @param chaptersDTO the chaptersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chaptersDTO, or with status {@code 400 (Bad Request)} if the chapters has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chapters")
    public ResponseEntity<ChaptersDTO> createChapters(@Valid @RequestBody ChaptersDTO chaptersDTO) throws URISyntaxException {
        log.debug("REST request to save Chapters : {}", chaptersDTO);
        if (chaptersDTO.getId() != null) {
            throw new BadRequestAlertException("A new chapters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChaptersDTO result = chaptersService.save(chaptersDTO);
        return ResponseEntity.created(new URI("/api/chapters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chapters} : Updates an existing chapters.
     *
     * @param chaptersDTO the chaptersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chaptersDTO,
     * or with status {@code 400 (Bad Request)} if the chaptersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chaptersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chapters")
    public ResponseEntity<ChaptersDTO> updateChapters(@Valid @RequestBody ChaptersDTO chaptersDTO) throws URISyntaxException {
        log.debug("REST request to update Chapters : {}", chaptersDTO);
        if (chaptersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChaptersDTO result = chaptersService.save(chaptersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chaptersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /chapters} : get all the chapters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chapters in body.
     */
    @GetMapping("/chapters")
    public List<ChaptersDTO> getAllChapters() {
        log.debug("REST request to get all Chapters");
        return chaptersService.findAll();
    }

    /**
     * {@code GET  /chapters/:id} : get the "id" chapters.
     *
     * @param id the id of the chaptersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chaptersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chapters/{id}")
    public ResponseEntity<ChaptersDTO> getChapters(@PathVariable Long id) {
        log.debug("REST request to get Chapters : {}", id);
        Optional<ChaptersDTO> chaptersDTO = chaptersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chaptersDTO);
    }

    /**
     * {@code DELETE  /chapters/:id} : delete the "id" chapters.
     *
     * @param id the id of the chaptersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chapters/{id}")
    public ResponseEntity<Void> deleteChapters(@PathVariable Long id) {
        log.debug("REST request to delete Chapters : {}", id);
        chaptersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
