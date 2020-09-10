package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.CoursesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Courses}.
 */
public interface CoursesService {

    /**
     * Save a courses.
     *
     * @param coursesDTO the entity to save.
     * @return the persisted entity.
     */
    CoursesDTO save(CoursesDTO coursesDTO);

    /**
     * Get all the courses.
     *
     * @return the list of entities.
     */
    List<CoursesDTO> findAll();


    /**
     * Get the "id" courses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CoursesDTO> findOne(Long id);

    /**
     * Delete the "id" courses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
