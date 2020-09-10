package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.StudentsService;
import com.risingarjun.arjun.domain.Students;
import com.risingarjun.arjun.repository.StudentsRepository;
import com.risingarjun.arjun.service.dto.StudentsDTO;
import com.risingarjun.arjun.service.mapper.StudentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Students}.
 */
@Service
@Transactional
public class StudentsServiceImpl implements StudentsService {

    private final Logger log = LoggerFactory.getLogger(StudentsServiceImpl.class);

    private final StudentsRepository studentsRepository;

    private final StudentsMapper studentsMapper;

    public StudentsServiceImpl(StudentsRepository studentsRepository, StudentsMapper studentsMapper) {
        this.studentsRepository = studentsRepository;
        this.studentsMapper = studentsMapper;
    }

    /**
     * Save a students.
     *
     * @param studentsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StudentsDTO save(StudentsDTO studentsDTO) {
        log.debug("Request to save Students : {}", studentsDTO);
        Students students = studentsMapper.toEntity(studentsDTO);
        students = studentsRepository.save(students);
        return studentsMapper.toDto(students);
    }

    /**
     * Get all the students.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentsRepository.findAll(pageable)
            .map(studentsMapper::toDto);
    }

    /**
     * Get all the students with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StudentsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return studentsRepository.findAllWithEagerRelationships(pageable).map(studentsMapper::toDto);
    }
    

    /**
     * Get one students by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentsDTO> findOne(Long id) {
        log.debug("Request to get Students : {}", id);
        return studentsRepository.findOneWithEagerRelationships(id)
            .map(studentsMapper::toDto);
    }

    /**
     * Delete the students by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Students : {}", id);
        studentsRepository.deleteById(id);
    }
}
