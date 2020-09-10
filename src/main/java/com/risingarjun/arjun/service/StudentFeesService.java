package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.StudentFeesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.StudentFees}.
 */
public interface StudentFeesService {

    /**
     * Save a studentFees.
     *
     * @param studentFeesDTO the entity to save.
     * @return the persisted entity.
     */
    StudentFeesDTO save(StudentFeesDTO studentFeesDTO);

    /**
     * Get all the studentFees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentFeesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentFees.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentFeesDTO> findOne(Long id);

    /**
     * Delete the "id" studentFees.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
