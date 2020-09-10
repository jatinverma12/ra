package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.StudentsSubjectsService;
import com.risingarjun.arjun.domain.StudentsSubjects;
import com.risingarjun.arjun.repository.StudentsSubjectsRepository;
import com.risingarjun.arjun.service.dto.StudentsSubjectsDTO;
import com.risingarjun.arjun.service.mapper.StudentsSubjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StudentsSubjects}.
 */
@Service
@Transactional
public class StudentsSubjectsServiceImpl implements StudentsSubjectsService {

    private final Logger log = LoggerFactory.getLogger(StudentsSubjectsServiceImpl.class);

    private final StudentsSubjectsRepository studentsSubjectsRepository;

    private final StudentsSubjectsMapper studentsSubjectsMapper;

    public StudentsSubjectsServiceImpl(StudentsSubjectsRepository studentsSubjectsRepository, StudentsSubjectsMapper studentsSubjectsMapper) {
        this.studentsSubjectsRepository = studentsSubjectsRepository;
        this.studentsSubjectsMapper = studentsSubjectsMapper;
    }

    /**
     * Save a studentsSubjects.
     *
     * @param studentsSubjectsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StudentsSubjectsDTO save(StudentsSubjectsDTO studentsSubjectsDTO) {
        log.debug("Request to save StudentsSubjects : {}", studentsSubjectsDTO);
        StudentsSubjects studentsSubjects = studentsSubjectsMapper.toEntity(studentsSubjectsDTO);
        studentsSubjects = studentsSubjectsRepository.save(studentsSubjects);
        return studentsSubjectsMapper.toDto(studentsSubjects);
    }

    /**
     * Get all the studentsSubjects.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentsSubjectsDTO> findAll() {
        log.debug("Request to get all StudentsSubjects");
        return studentsSubjectsRepository.findAllWithEagerRelationships().stream()
            .map(studentsSubjectsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the studentsSubjects with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StudentsSubjectsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return studentsSubjectsRepository.findAllWithEagerRelationships(pageable).map(studentsSubjectsMapper::toDto);
    }
    

    /**
     * Get one studentsSubjects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentsSubjectsDTO> findOne(Long id) {
        log.debug("Request to get StudentsSubjects : {}", id);
        return studentsSubjectsRepository.findOneWithEagerRelationships(id)
            .map(studentsSubjectsMapper::toDto);
    }

    /**
     * Delete the studentsSubjects by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentsSubjects : {}", id);
        studentsSubjectsRepository.deleteById(id);
    }
}
