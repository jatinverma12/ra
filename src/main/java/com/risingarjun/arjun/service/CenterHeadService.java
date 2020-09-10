package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.CenterHeadDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.CenterHead}.
 */
public interface CenterHeadService {

    /**
     * Save a centerHead.
     *
     * @param centerHeadDTO the entity to save.
     * @return the persisted entity.
     */
    CenterHeadDTO save(CenterHeadDTO centerHeadDTO);

    /**
     * Get all the centerHeads.
     *
     * @return the list of entities.
     */
    List<CenterHeadDTO> findAll();

    /**
     * Get all the centerHeads with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CenterHeadDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" centerHead.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CenterHeadDTO> findOne(Long id);

    /**
     * Delete the "id" centerHead.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
