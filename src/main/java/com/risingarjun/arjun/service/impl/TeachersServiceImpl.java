package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.TeachersService;
import com.risingarjun.arjun.domain.Teachers;
import com.risingarjun.arjun.repository.TeachersRepository;
import com.risingarjun.arjun.service.dto.TeachersDTO;
import com.risingarjun.arjun.service.mapper.TeachersMapper;
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
 * Service Implementation for managing {@link Teachers}.
 */
@Service
@Transactional
public class TeachersServiceImpl implements TeachersService {

    private final Logger log = LoggerFactory.getLogger(TeachersServiceImpl.class);

    private final TeachersRepository teachersRepository;

    private final TeachersMapper teachersMapper;

    public TeachersServiceImpl(TeachersRepository teachersRepository, TeachersMapper teachersMapper) {
        this.teachersRepository = teachersRepository;
        this.teachersMapper = teachersMapper;
    }

    /**
     * Save a teachers.
     *
     * @param teachersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TeachersDTO save(TeachersDTO teachersDTO) {
        log.debug("Request to save Teachers : {}", teachersDTO);
        Teachers teachers = teachersMapper.toEntity(teachersDTO);
        teachers = teachersRepository.save(teachers);
        return teachersMapper.toDto(teachers);
    }

    /**
     * Get all the teachers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TeachersDTO> findAll() {
        log.debug("Request to get all Teachers");
        return teachersRepository.findAllWithEagerRelationships().stream()
            .map(teachersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the teachers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TeachersDTO> findAllWithEagerRelationships(Pageable pageable) {
        return teachersRepository.findAllWithEagerRelationships(pageable).map(teachersMapper::toDto);
    }
    

    /**
     * Get one teachers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeachersDTO> findOne(Long id) {
        log.debug("Request to get Teachers : {}", id);
        return teachersRepository.findOneWithEagerRelationships(id)
            .map(teachersMapper::toDto);
    }

    /**
     * Delete the teachers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Teachers : {}", id);
        teachersRepository.deleteById(id);
    }
}
