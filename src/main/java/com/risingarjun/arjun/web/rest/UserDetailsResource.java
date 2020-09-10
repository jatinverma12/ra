package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.UserDetailsService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.UserDetailsDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.UserDetails}.
 */
@RestController
@RequestMapping("/api")
public class UserDetailsResource {

    private final Logger log = LoggerFactory.getLogger(UserDetailsResource.class);

    private static final String ENTITY_NAME = "userDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserDetailsService userDetailsService;

    public UserDetailsResource(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * {@code POST  /user-details} : Create a new userDetails.
     *
     * @param userDetailsDTO the userDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDetailsDTO, or with status {@code 400 (Bad Request)} if the userDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-details")
    public ResponseEntity<UserDetailsDTO> createUserDetails(@Valid @RequestBody UserDetailsDTO userDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save UserDetails : {}", userDetailsDTO);
        if (userDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserDetailsDTO result = userDetailsService.save(userDetailsDTO);
        return ResponseEntity.created(new URI("/api/user-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-details} : Updates an existing userDetails.
     *
     * @param userDetailsDTO the userDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the userDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-details")
    public ResponseEntity<UserDetailsDTO> updateUserDetails(@Valid @RequestBody UserDetailsDTO userDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update UserDetails : {}", userDetailsDTO);
        if (userDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserDetailsDTO result = userDetailsService.save(userDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-details} : get all the userDetails.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userDetails in body.
     */
    @GetMapping("/user-details")
    public ResponseEntity<List<UserDetailsDTO>> getAllUserDetails(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of UserDetails");
        Page<UserDetailsDTO> page = userDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-details/:id} : get the "id" userDetails.
     *
     * @param id the id of the userDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-details/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long id) {
        log.debug("REST request to get UserDetails : {}", id);
        Optional<UserDetailsDTO> userDetailsDTO = userDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userDetailsDTO);
    }

    /**
     * {@code DELETE  /user-details/:id} : delete the "id" userDetails.
     *
     * @param id the id of the userDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-details/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable Long id) {
        log.debug("REST request to delete UserDetails : {}", id);
        userDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
