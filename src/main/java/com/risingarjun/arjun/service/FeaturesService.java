package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.FeaturesDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Features}.
 */
public interface FeaturesService {

    /**
     * Save a features.
     *
     * @param featuresDTO the entity to save.
     * @return the persisted entity.
     */
    FeaturesDTO save(FeaturesDTO featuresDTO);

    /**
     * Get all the features.
     *
     * @return the list of entities.
     */
    List<FeaturesDTO> findAll();


    /**
     * Get the "id" features.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeaturesDTO> findOne(Long id);

    /**
     * Delete the "id" features.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
