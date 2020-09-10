package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.StudentsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Students}.
 */
public interface StudentsService {

    /**
     * Save a students.
     *
     * @param studentsDTO the entity to save.
     * @return the persisted entity.
     */
    StudentsDTO save(StudentsDTO studentsDTO);

    /**
     * Get all the students.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentsDTO> findAll(Pageable pageable);

    /**
     * Get all the students with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<StudentsDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" students.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentsDTO> findOne(Long id);

    /**
     * Delete the "id" students.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
