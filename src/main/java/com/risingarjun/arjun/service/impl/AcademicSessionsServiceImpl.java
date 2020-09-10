package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.AcademicSessionsService;
import com.risingarjun.arjun.domain.AcademicSessions;
import com.risingarjun.arjun.repository.AcademicSessionsRepository;
import com.risingarjun.arjun.service.dto.AcademicSessionsDTO;
import com.risingarjun.arjun.service.mapper.AcademicSessionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AcademicSessions}.
 */
@Service
@Transactional
public class AcademicSessionsServiceImpl implements AcademicSessionsService {

    private final Logger log = LoggerFactory.getLogger(AcademicSessionsServiceImpl.class);

    private final AcademicSessionsRepository academicSessionsRepository;

    private final AcademicSessionsMapper academicSessionsMapper;

    public AcademicSessionsServiceImpl(AcademicSessionsRepository academicSessionsRepository, AcademicSessionsMapper academicSessionsMapper) {
        this.academicSessionsRepository = academicSessionsRepository;
        this.academicSessionsMapper = academicSessionsMapper;
    }

    /**
     * Save a academicSessions.
     *
     * @param academicSessionsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AcademicSessionsDTO save(AcademicSessionsDTO academicSessionsDTO) {
        log.debug("Request to save AcademicSessions : {}", academicSessionsDTO);
        AcademicSessions academicSessions = academicSessionsMapper.toEntity(academicSessionsDTO);
        academicSessions = academicSessionsRepository.save(academicSessions);
        return academicSessionsMapper.toDto(academicSessions);
    }

    /**
     * Get all the academicSessions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AcademicSessionsDTO> findAll() {
        log.debug("Request to get all AcademicSessions");
        return academicSessionsRepository.findAll().stream()
            .map(academicSessionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one academicSessions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicSessionsDTO> findOne(Long id) {
        log.debug("Request to get AcademicSessions : {}", id);
        return academicSessionsRepository.findById(id)
            .map(academicSessionsMapper::toDto);
    }

    /**
     * Delete the academicSessions by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AcademicSessions : {}", id);
        academicSessionsRepository.deleteById(id);
    }
}
