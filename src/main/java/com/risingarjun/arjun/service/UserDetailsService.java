package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.UserDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.UserDetails}.
 */
public interface UserDetailsService {

    /**
     * Save a userDetails.
     *
     * @param userDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    UserDetailsDTO save(UserDetailsDTO userDetailsDTO);

    /**
     * Get all the userDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" userDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
