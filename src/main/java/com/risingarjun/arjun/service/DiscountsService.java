package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.DiscountsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Discounts}.
 */
public interface DiscountsService {

    /**
     * Save a discounts.
     *
     * @param discountsDTO the entity to save.
     * @return the persisted entity.
     */
    DiscountsDTO save(DiscountsDTO discountsDTO);

    /**
     * Get all the discounts.
     *
     * @return the list of entities.
     */
    List<DiscountsDTO> findAll();


    /**
     * Get the "id" discounts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscountsDTO> findOne(Long id);

    /**
     * Delete the "id" discounts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
