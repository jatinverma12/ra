package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.StudentScoreService;
import com.risingarjun.arjun.domain.StudentScore;
import com.risingarjun.arjun.repository.StudentScoreRepository;
import com.risingarjun.arjun.service.dto.StudentScoreDTO;
import com.risingarjun.arjun.service.mapper.StudentScoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StudentScore}.
 */
@Service
@Transactional
public class StudentScoreServiceImpl implements StudentScoreService {

    private final Logger log = LoggerFactory.getLogger(StudentScoreServiceImpl.class);

    private final StudentScoreRepository studentScoreRepository;

    private final StudentScoreMapper studentScoreMapper;

    public StudentScoreServiceImpl(StudentScoreRepository studentScoreRepository, StudentScoreMapper studentScoreMapper) {
        this.studentScoreRepository = studentScoreRepository;
        this.studentScoreMapper = studentScoreMapper;
    }

    /**
     * Save a studentScore.
     *
     * @param studentScoreDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StudentScoreDTO save(StudentScoreDTO studentScoreDTO) {
        log.debug("Request to save StudentScore : {}", studentScoreDTO);
        StudentScore studentScore = studentScoreMapper.toEntity(studentScoreDTO);
        studentScore = studentScoreRepository.save(studentScore);
        return studentScoreMapper.toDto(studentScore);
    }

    /**
     * Get all the studentScores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentScoreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentScores");
        return studentScoreRepository.findAll(pageable)
            .map(studentScoreMapper::toDto);
    }


    /**
     * Get one studentScore by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentScoreDTO> findOne(Long id) {
        log.debug("Request to get StudentScore : {}", id);
        return studentScoreRepository.findById(id)
            .map(studentScoreMapper::toDto);
    }

    /**
     * Delete the studentScore by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentScore : {}", id);
        studentScoreRepository.deleteById(id);
    }
}
