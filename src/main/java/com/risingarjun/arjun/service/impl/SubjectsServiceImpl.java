package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.SubjectsService;
import com.risingarjun.arjun.domain.Subjects;
import com.risingarjun.arjun.repository.SubjectsRepository;
import com.risingarjun.arjun.service.dto.SubjectsDTO;
import com.risingarjun.arjun.service.mapper.SubjectsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Subjects}.
 */
@Service
@Transactional
public class SubjectsServiceImpl implements SubjectsService {

    private final Logger log = LoggerFactory.getLogger(SubjectsServiceImpl.class);

    private final SubjectsRepository subjectsRepository;

    private final SubjectsMapper subjectsMapper;

    public SubjectsServiceImpl(SubjectsRepository subjectsRepository, SubjectsMapper subjectsMapper) {
        this.subjectsRepository = subjectsRepository;
        this.subjectsMapper = subjectsMapper;
    }

    /**
     * Save a subjects.
     *
     * @param subjectsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SubjectsDTO save(SubjectsDTO subjectsDTO) {
        log.debug("Request to save Subjects : {}", subjectsDTO);
        Subjects subjects = subjectsMapper.toEntity(subjectsDTO);
        subjects = subjectsRepository.save(subjects);
        return subjectsMapper.toDto(subjects);
    }

    /**
     * Get all the subjects.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectsDTO> findAll() {
        log.debug("Request to get all Subjects");
        return subjectsRepository.findAll().stream()
            .map(subjectsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one subjects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubjectsDTO> findOne(Long id) {
        log.debug("Request to get Subjects : {}", id);
        return subjectsRepository.findById(id)
            .map(subjectsMapper::toDto);
    }

    /**
     * Delete the subjects by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subjects : {}", id);
        subjectsRepository.deleteById(id);
    }
}
