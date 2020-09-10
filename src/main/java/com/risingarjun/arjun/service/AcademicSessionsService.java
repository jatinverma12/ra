package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.AcademicSessionsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.AcademicSessions}.
 */
public interface AcademicSessionsService {

    /**
     * Save a academicSessions.
     *
     * @param academicSessionsDTO the entity to save.
     * @return the persisted entity.
     */
    AcademicSessionsDTO save(AcademicSessionsDTO academicSessionsDTO);

    /**
     * Get all the academicSessions.
     *
     * @return the list of entities.
     */
    List<AcademicSessionsDTO> findAll();


    /**
     * Get the "id" academicSessions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicSessionsDTO> findOne(Long id);

    /**
     * Delete the "id" academicSessions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
