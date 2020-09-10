package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.TeachersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Teachers}.
 */
public interface TeachersService {

    /**
     * Save a teachers.
     *
     * @param teachersDTO the entity to save.
     * @return the persisted entity.
     */
    TeachersDTO save(TeachersDTO teachersDTO);

    /**
     * Get all the teachers.
     *
     * @return the list of entities.
     */
    List<TeachersDTO> findAll();

    /**
     * Get all the teachers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<TeachersDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" teachers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeachersDTO> findOne(Long id);

    /**
     * Delete the "id" teachers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
