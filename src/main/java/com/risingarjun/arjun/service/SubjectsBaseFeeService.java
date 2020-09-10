package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.SubjectsBaseFeeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.SubjectsBaseFee}.
 */
public interface SubjectsBaseFeeService {

    /**
     * Save a subjectsBaseFee.
     *
     * @param subjectsBaseFeeDTO the entity to save.
     * @return the persisted entity.
     */
    SubjectsBaseFeeDTO save(SubjectsBaseFeeDTO subjectsBaseFeeDTO);

    /**
     * Get all the subjectsBaseFees.
     *
     * @return the list of entities.
     */
    List<SubjectsBaseFeeDTO> findAll();


    /**
     * Get the "id" subjectsBaseFee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectsBaseFeeDTO> findOne(Long id);

    /**
     * Delete the "id" subjectsBaseFee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
