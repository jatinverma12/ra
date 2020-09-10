package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.CenterHeadService;
import com.risingarjun.arjun.domain.CenterHead;
import com.risingarjun.arjun.repository.CenterHeadRepository;
import com.risingarjun.arjun.service.dto.CenterHeadDTO;
import com.risingarjun.arjun.service.mapper.CenterHeadMapper;
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
 * Service Implementation for managing {@link CenterHead}.
 */
@Service
@Transactional
public class CenterHeadServiceImpl implements CenterHeadService {

    private final Logger log = LoggerFactory.getLogger(CenterHeadServiceImpl.class);

    private final CenterHeadRepository centerHeadRepository;

    private final CenterHeadMapper centerHeadMapper;

    public CenterHeadServiceImpl(CenterHeadRepository centerHeadRepository, CenterHeadMapper centerHeadMapper) {
        this.centerHeadRepository = centerHeadRepository;
        this.centerHeadMapper = centerHeadMapper;
    }

    /**
     * Save a centerHead.
     *
     * @param centerHeadDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CenterHeadDTO save(CenterHeadDTO centerHeadDTO) {
        log.debug("Request to save CenterHead : {}", centerHeadDTO);
        CenterHead centerHead = centerHeadMapper.toEntity(centerHeadDTO);
        centerHead = centerHeadRepository.save(centerHead);
        return centerHeadMapper.toDto(centerHead);
    }

    /**
     * Get all the centerHeads.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CenterHeadDTO> findAll() {
        log.debug("Request to get all CenterHeads");
        return centerHeadRepository.findAllWithEagerRelationships().stream()
            .map(centerHeadMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the centerHeads with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CenterHeadDTO> findAllWithEagerRelationships(Pageable pageable) {
        return centerHeadRepository.findAllWithEagerRelationships(pageable).map(centerHeadMapper::toDto);
    }
    

    /**
     * Get one centerHead by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CenterHeadDTO> findOne(Long id) {
        log.debug("Request to get CenterHead : {}", id);
        return centerHeadRepository.findOneWithEagerRelationships(id)
            .map(centerHeadMapper::toDto);
    }

    /**
     * Delete the centerHead by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CenterHead : {}", id);
        centerHeadRepository.deleteById(id);
    }
}
