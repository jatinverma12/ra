package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.RoleAccessDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.RoleAccess}.
 */
public interface RoleAccessService {

    /**
     * Save a roleAccess.
     *
     * @param roleAccessDTO the entity to save.
     * @return the persisted entity.
     */
    RoleAccessDTO save(RoleAccessDTO roleAccessDTO);

    /**
     * Get all the roleAccesses.
     *
     * @return the list of entities.
     */
    List<RoleAccessDTO> findAll();


    /**
     * Get the "id" roleAccess.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleAccessDTO> findOne(Long id);

    /**
     * Delete the "id" roleAccess.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
