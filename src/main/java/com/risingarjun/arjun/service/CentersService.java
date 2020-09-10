package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.CentersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Centers}.
 */
public interface CentersService {

    /**
     * Save a centers.
     *
     * @param centersDTO the entity to save.
     * @return the persisted entity.
     */
    CentersDTO save(CentersDTO centersDTO);

    /**
     * Get all the centers.
     *
     * @return the list of entities.
     */
    List<CentersDTO> findAll();


    /**
     * Get the "id" centers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CentersDTO> findOne(Long id);

    /**
     * Delete the "id" centers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
