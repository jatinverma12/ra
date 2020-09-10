package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.TeachersShareDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.TeachersShare}.
 */
public interface TeachersShareService {

    /**
     * Save a teachersShare.
     *
     * @param teachersShareDTO the entity to save.
     * @return the persisted entity.
     */
    TeachersShareDTO save(TeachersShareDTO teachersShareDTO);

    /**
     * Get all the teachersShares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TeachersShareDTO> findAll(Pageable pageable);


    /**
     * Get the "id" teachersShare.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TeachersShareDTO> findOne(Long id);

    /**
     * Delete the "id" teachersShare.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
