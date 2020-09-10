package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.ExpensesService;
import com.risingarjun.arjun.domain.Expenses;
import com.risingarjun.arjun.repository.ExpensesRepository;
import com.risingarjun.arjun.service.dto.ExpensesDTO;
import com.risingarjun.arjun.service.mapper.ExpensesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Expenses}.
 */
@Service
@Transactional
public class ExpensesServiceImpl implements ExpensesService {

    private final Logger log = LoggerFactory.getLogger(ExpensesServiceImpl.class);

    private final ExpensesRepository expensesRepository;

    private final ExpensesMapper expensesMapper;

    public ExpensesServiceImpl(ExpensesRepository expensesRepository, ExpensesMapper expensesMapper) {
        this.expensesRepository = expensesRepository;
        this.expensesMapper = expensesMapper;
    }

    /**
     * Save a expenses.
     *
     * @param expensesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ExpensesDTO save(ExpensesDTO expensesDTO) {
        log.debug("Request to save Expenses : {}", expensesDTO);
        Expenses expenses = expensesMapper.toEntity(expensesDTO);
        expenses = expensesRepository.save(expenses);
        return expensesMapper.toDto(expenses);
    }

    /**
     * Get all the expenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExpensesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Expenses");
        return expensesRepository.findAll(pageable)
            .map(expensesMapper::toDto);
    }


    /**
     * Get one expenses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExpensesDTO> findOne(Long id) {
        log.debug("Request to get Expenses : {}", id);
        return expensesRepository.findById(id)
            .map(expensesMapper::toDto);
    }

    /**
     * Delete the expenses by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Expenses : {}", id);
        expensesRepository.deleteById(id);
    }
}
