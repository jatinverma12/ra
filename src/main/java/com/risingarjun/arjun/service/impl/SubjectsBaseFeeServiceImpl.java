package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.SubjectsBaseFeeService;
import com.risingarjun.arjun.domain.SubjectsBaseFee;
import com.risingarjun.arjun.repository.SubjectsBaseFeeRepository;
import com.risingarjun.arjun.service.dto.SubjectsBaseFeeDTO;
import com.risingarjun.arjun.service.mapper.SubjectsBaseFeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SubjectsBaseFee}.
 */
@Service
@Transactional
public class SubjectsBaseFeeServiceImpl implements SubjectsBaseFeeService {

    private final Logger log = LoggerFactory.getLogger(SubjectsBaseFeeServiceImpl.class);

    private final SubjectsBaseFeeRepository subjectsBaseFeeRepository;

    private final SubjectsBaseFeeMapper subjectsBaseFeeMapper;

    public SubjectsBaseFeeServiceImpl(SubjectsBaseFeeRepository subjectsBaseFeeRepository, SubjectsBaseFeeMapper subjectsBaseFeeMapper) {
        this.subjectsBaseFeeRepository = subjectsBaseFeeRepository;
        this.subjectsBaseFeeMapper = subjectsBaseFeeMapper;
    }

    /**
     * Save a subjectsBaseFee.
     *
     * @param subjectsBaseFeeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubjectsBaseFeeDTO save(SubjectsBaseFeeDTO subjectsBaseFeeDTO) {
        log.debug("Request to save SubjectsBaseFee : {}", subjectsBaseFeeDTO);
        SubjectsBaseFee subjectsBaseFee = subjectsBaseFeeMapper.toEntity(subjectsBaseFeeDTO);
        subjectsBaseFee = subjectsBaseFeeRepository.save(subjectsBaseFee);
        return subjectsBaseFeeMapper.toDto(subjectsBaseFee);
    }

    /**
     * Get all the subjectsBaseFees.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectsBaseFeeDTO> findAll() {
        log.debug("Request to get all SubjectsBaseFees");
        return subjectsBaseFeeRepository.findAll().stream()
            .map(subjectsBaseFeeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one subjectsBaseFee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectsBaseFeeDTO> findOne(Long id) {
        log.debug("Request to get SubjectsBaseFee : {}", id);
        return subjectsBaseFeeRepository.findById(id)
            .map(subjectsBaseFeeMapper::toDto);
    }

    /**
     * Delete the subjectsBaseFee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SubjectsBaseFee : {}", id);
        subjectsBaseFeeRepository.deleteById(id);
    }
}
