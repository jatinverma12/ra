package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.TeachersShareService;
import com.risingarjun.arjun.domain.TeachersShare;
import com.risingarjun.arjun.repository.TeachersShareRepository;
import com.risingarjun.arjun.service.dto.TeachersShareDTO;
import com.risingarjun.arjun.service.mapper.TeachersShareMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TeachersShare}.
 */
@Service
@Transactional
public class TeachersShareServiceImpl implements TeachersShareService {

    private final Logger log = LoggerFactory.getLogger(TeachersShareServiceImpl.class);

    private final TeachersShareRepository teachersShareRepository;

    private final TeachersShareMapper teachersShareMapper;

    public TeachersShareServiceImpl(TeachersShareRepository teachersShareRepository, TeachersShareMapper teachersShareMapper) {
        this.teachersShareRepository = teachersShareRepository;
        this.teachersShareMapper = teachersShareMapper;
    }

    /**
     * Save a teachersShare.
     *
     * @param teachersShareDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TeachersShareDTO save(TeachersShareDTO teachersShareDTO) {
        log.debug("Request to save TeachersShare : {}", teachersShareDTO);
        TeachersShare teachersShare = teachersShareMapper.toEntity(teachersShareDTO);
        teachersShare = teachersShareRepository.save(teachersShare);
        return teachersShareMapper.toDto(teachersShare);
    }

    /**
     * Get all the teachersShares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeachersShareDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeachersShares");
        return teachersShareRepository.findAll(pageable)
            .map(teachersShareMapper::toDto);
    }


    /**
     * Get one teachersShare by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeachersShareDTO> findOne(Long id) {
        log.debug("Request to get TeachersShare : {}", id);
        return teachersShareRepository.findById(id)
            .map(teachersShareMapper::toDto);
    }

    /**
     * Delete the teachersShare by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeachersShare : {}", id);
        teachersShareRepository.deleteById(id);
    }
}
