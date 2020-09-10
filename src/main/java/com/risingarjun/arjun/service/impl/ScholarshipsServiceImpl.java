package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.ScholarshipsService;
import com.risingarjun.arjun.domain.Scholarships;
import com.risingarjun.arjun.repository.ScholarshipsRepository;
import com.risingarjun.arjun.service.dto.ScholarshipsDTO;
import com.risingarjun.arjun.service.mapper.ScholarshipsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Scholarships}.
 */
@Service
@Transactional
public class ScholarshipsServiceImpl implements ScholarshipsService {

    private final Logger log = LoggerFactory.getLogger(ScholarshipsServiceImpl.class);

    private final ScholarshipsRepository scholarshipsRepository;

    private final ScholarshipsMapper scholarshipsMapper;

    public ScholarshipsServiceImpl(ScholarshipsRepository scholarshipsRepository, ScholarshipsMapper scholarshipsMapper) {
        this.scholarshipsRepository = scholarshipsRepository;
        this.scholarshipsMapper = scholarshipsMapper;
    }

    /**
     * Save a scholarships.
     *
     * @param scholarshipsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ScholarshipsDTO save(ScholarshipsDTO scholarshipsDTO) {
        log.debug("Request to save Scholarships : {}", scholarshipsDTO);
        Scholarships scholarships = scholarshipsMapper.toEntity(scholarshipsDTO);
        scholarships = scholarshipsRepository.save(scholarships);
        return scholarshipsMapper.toDto(scholarships);
    }

    /**
     * Get all the scholarships.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScholarshipsDTO> findAll() {
        log.debug("Request to get all Scholarships");
        return scholarshipsRepository.findAll().stream()
            .map(scholarshipsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one scholarships by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScholarshipsDTO> findOne(Long id) {
        log.debug("Request to get Scholarships : {}", id);
        return scholarshipsRepository.findById(id)
            .map(scholarshipsMapper::toDto);
    }

    /**
     * Delete the scholarships by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scholarships : {}", id);
        scholarshipsRepository.deleteById(id);
    }
}
