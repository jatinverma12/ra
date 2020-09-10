package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.ExpensesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.Expenses}.
 */
public interface ExpensesService {

    /**
     * Save a expenses.
     *
     * @param expensesDTO the entity to save.
     * @return the persisted entity.
     */
    ExpensesDTO save(ExpensesDTO expensesDTO);

    /**
     * Get all the expenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExpensesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" expenses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExpensesDTO> findOne(Long id);

    /**
     * Delete the "id" expenses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
