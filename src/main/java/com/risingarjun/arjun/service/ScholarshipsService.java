package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.ScholarshipsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Scholarships}.
 */
public interface ScholarshipsService {

    /**
     * Save a scholarships.
     *
     * @param scholarshipsDTO the entity to save.
     * @return the persisted entity.
     */
    ScholarshipsDTO save(ScholarshipsDTO scholarshipsDTO);

    /**
     * Get all the scholarships.
     *
     * @return the list of entities.
     */
    List<ScholarshipsDTO> findAll();


    /**
     * Get the "id" scholarships.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScholarshipsDTO> findOne(Long id);

    /**
     * Delete the "id" scholarships.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
