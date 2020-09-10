package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.RoleAccessService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.RoleAccessDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.RoleAccess}.
 */
@RestController
@RequestMapping("/api")
public class RoleAccessResource {

    private final Logger log = LoggerFactory.getLogger(RoleAccessResource.class);

    private static final String ENTITY_NAME = "roleAccess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleAccessService roleAccessService;

    public RoleAccessResource(RoleAccessService roleAccessService) {
        this.roleAccessService = roleAccessService;
    }

    /**
     * {@code POST  /role-accesses} : Create a new roleAccess.
     *
     * @param roleAccessDTO the roleAccessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleAccessDTO, or with status {@code 400 (Bad Request)} if the roleAccess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-accesses")
    public ResponseEntity<RoleAccessDTO> createRoleAccess(@RequestBody RoleAccessDTO roleAccessDTO) throws URISyntaxException {
        log.debug("REST request to save RoleAccess : {}", roleAccessDTO);
        if (roleAccessDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleAccess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleAccessDTO result = roleAccessService.save(roleAccessDTO);
        return ResponseEntity.created(new URI("/api/role-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-accesses} : Updates an existing roleAccess.
     *
     * @param roleAccessDTO the roleAccessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleAccessDTO,
     * or with status {@code 400 (Bad Request)} if the roleAccessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleAccessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-accesses")
    public ResponseEntity<RoleAccessDTO> updateRoleAccess(@RequestBody RoleAccessDTO roleAccessDTO) throws URISyntaxException {
        log.debug("REST request to update RoleAccess : {}", roleAccessDTO);
        if (roleAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleAccessDTO result = roleAccessService.save(roleAccessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roleAccessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /role-accesses} : get all the roleAccesses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleAccesses in body.
     */
    @GetMapping("/role-accesses")
    public List<RoleAccessDTO> getAllRoleAccesses() {
        log.debug("REST request to get all RoleAccesses");
        return roleAccessService.findAll();
    }

    /**
     * {@code GET  /role-accesses/:id} : get the "id" roleAccess.
     *
     * @param id the id of the roleAccessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleAccessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-accesses/{id}")
    public ResponseEntity<RoleAccessDTO> getRoleAccess(@PathVariable Long id) {
        log.debug("REST request to get RoleAccess : {}", id);
        Optional<RoleAccessDTO> roleAccessDTO = roleAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleAccessDTO);
    }

    /**
     * {@code DELETE  /role-accesses/:id} : delete the "id" roleAccess.
     *
     * @param id the id of the roleAccessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-accesses/{id}")
    public ResponseEntity<Void> deleteRoleAccess(@PathVariable Long id) {
        log.debug("REST request to delete RoleAccess : {}", id);
        roleAccessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
