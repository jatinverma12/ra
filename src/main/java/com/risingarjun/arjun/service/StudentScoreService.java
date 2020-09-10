package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.StudentScoreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.StudentScore}.
 */
public interface StudentScoreService {

    /**
     * Save a studentScore.
     *
     * @param studentScoreDTO the entity to save.
     * @return the persisted entity.
     */
    StudentScoreDTO save(StudentScoreDTO studentScoreDTO);

    /**
     * Get all the studentScores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StudentScoreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentScore.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentScoreDTO> findOne(Long id);

    /**
     * Delete the "id" studentScore.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
