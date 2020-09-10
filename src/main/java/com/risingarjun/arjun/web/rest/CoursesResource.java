package com.risingarjun.arjun.web.rest;

import com.risingarjun.arjun.service.CoursesService;
import com.risingarjun.arjun.web.rest.errors.BadRequestAlertException;
import com.risingarjun.arjun.service.dto.CoursesDTO;

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
 * REST controller for managing {@link com.risingarjun.arjun.domain.Courses}.
 */
@RestController
@RequestMapping("/api")
public class CoursesResource {

    private final Logger log = LoggerFactory.getLogger(CoursesResource.class);

    private static final String ENTITY_NAME = "courses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursesService coursesService;

    public CoursesResource(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    /**
     * {@code POST  /courses} : Create a new courses.
     *
     * @param coursesDTO the coursesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coursesDTO, or with status {@code 400 (Bad Request)} if the courses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/courses")
    public ResponseEntity<CoursesDTO> createCourses(@Valid @RequestBody CoursesDTO coursesDTO) throws URISyntaxException {
        log.debug("REST request to save Courses : {}", coursesDTO);
        if (coursesDTO.getId() != null) {
            throw new BadRequestAlertException("A new courses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoursesDTO result = coursesService.save(coursesDTO);
        return ResponseEntity.created(new URI("/api/courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /courses} : Updates an existing courses.
     *
     * @param coursesDTO the coursesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursesDTO,
     * or with status {@code 400 (Bad Request)} if the coursesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coursesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/courses")
    public ResponseEntity<CoursesDTO> updateCourses(@Valid @RequestBody CoursesDTO coursesDTO) throws URISyntaxException {
        log.debug("REST request to update Courses : {}", coursesDTO);
        if (coursesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CoursesDTO result = coursesService.save(coursesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /courses} : get all the courses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/courses")
    public List<CoursesDTO> getAllCourses() {
        log.debug("REST request to get all Courses");
        return coursesService.findAll();
    }

    /**
     * {@code GET  /courses/:id} : get the "id" courses.
     *
     * @param id the id of the coursesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coursesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<CoursesDTO> getCourses(@PathVariable Long id) {
        log.debug("REST request to get Courses : {}", id);
        Optional<CoursesDTO> coursesDTO = coursesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursesDTO);
    }

    /**
     * {@code DELETE  /courses/:id} : delete the "id" courses.
     *
     * @param id the id of the coursesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourses(@PathVariable Long id) {
        log.debug("REST request to delete Courses : {}", id);
        coursesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
