package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.SubjectsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Subjects}.
 */
public interface SubjectsService {

    /**
     * Save a subjects.
     *
     * @param subjectsDTO the entity to save.
     * @return the persisted entity.
     */
    SubjectsDTO save(SubjectsDTO subjectsDTO);

    /**
     * Get all the subjects.
     *
     * @return the list of entities.
     */
    List<SubjectsDTO> findAll();


    /**
     * Get the "id" subjects.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectsDTO> findOne(Long id);

    /**
     * Delete the "id" subjects.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
