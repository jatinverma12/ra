package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.StudentFeesService;
import com.risingarjun.arjun.domain.StudentFees;
import com.risingarjun.arjun.repository.StudentFeesRepository;
import com.risingarjun.arjun.service.dto.StudentFeesDTO;
import com.risingarjun.arjun.service.mapper.StudentFeesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StudentFees}.
 */
@Service
@Transactional
public class StudentFeesServiceImpl implements StudentFeesService {

    private final Logger log = LoggerFactory.getLogger(StudentFeesServiceImpl.class);

    private final StudentFeesRepository studentFeesRepository;

    private final StudentFeesMapper studentFeesMapper;

    public StudentFeesServiceImpl(StudentFeesRepository studentFeesRepository, StudentFeesMapper studentFeesMapper) {
        this.studentFeesRepository = studentFeesRepository;
        this.studentFeesMapper = studentFeesMapper;
    }

    /**
     * Save a studentFees.
     *
     * @param studentFeesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StudentFeesDTO save(StudentFeesDTO studentFeesDTO) {
        log.debug("Request to save StudentFees : {}", studentFeesDTO);
        StudentFees studentFees = studentFeesMapper.toEntity(studentFeesDTO);
        studentFees = studentFeesRepository.save(studentFees);
        return studentFeesMapper.toDto(studentFees);
    }

    /**
     * Get all the studentFees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentFeesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentFees");
        return studentFeesRepository.findAll(pageable)
            .map(studentFeesMapper::toDto);
    }


    /**
     * Get one studentFees by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentFeesDTO> findOne(Long id) {
        log.debug("Request to get StudentFees : {}", id);
        return studentFeesRepository.findById(id)
            .map(studentFeesMapper::toDto);
    }

    /**
     * Delete the studentFees by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentFees : {}", id);
        studentFeesRepository.deleteById(id);
    }
}
