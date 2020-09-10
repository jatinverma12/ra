package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.StudentsSubjectsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.StudentsSubjects}.
 */
public interface StudentsSubjectsService {

    /**
     * Save a studentsSubjects.
     *
     * @param studentsSubjectsDTO the entity to save.
     * @return the persisted entity.
     */
    StudentsSubjectsDTO save(StudentsSubjectsDTO studentsSubjectsDTO);

    /**
     * Get all the studentsSubjects.
     *
     * @return the list of entities.
     */
    List<StudentsSubjectsDTO> findAll();

    /**
     * Get all the studentsSubjects with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<StudentsSubjectsDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" studentsSubjects.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StudentsSubjectsDTO> findOne(Long id);

    /**
     * Delete the "id" studentsSubjects.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
