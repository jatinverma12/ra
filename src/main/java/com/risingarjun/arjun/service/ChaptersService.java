package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.ChaptersDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Chapters}.
 */
public interface ChaptersService {

    /**
     * Save a chapters.
     *
     * @param chaptersDTO the entity to save.
     * @return the persisted entity.
     */
    ChaptersDTO save(ChaptersDTO chaptersDTO);

    /**
     * Get all the chapters.
     *
     * @return the list of entities.
     */
    List<ChaptersDTO> findAll();


    /**
     * Get the "id" chapters.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ChaptersDTO> findOne(Long id);

    /**
     * Delete the "id" chapters.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
