package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.CentersService;
import com.risingarjun.arjun.domain.Centers;
import com.risingarjun.arjun.repository.CentersRepository;
import com.risingarjun.arjun.service.dto.CentersDTO;
import com.risingarjun.arjun.service.mapper.CentersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Centers}.
 */
@Service
@Transactional
public class CentersServiceImpl implements CentersService {

    private final Logger log = LoggerFactory.getLogger(CentersServiceImpl.class);

    private final CentersRepository centersRepository;

    private final CentersMapper centersMapper;

    public CentersServiceImpl(CentersRepository centersRepository, CentersMapper centersMapper) {
        this.centersRepository = centersRepository;
        this.centersMapper = centersMapper;
    }

    /**
     * Save a centers.
     *
     * @param centersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CentersDTO save(CentersDTO centersDTO) {
        log.debug("Request to save Centers : {}", centersDTO);
        Centers centers = centersMapper.toEntity(centersDTO);
        centers = centersRepository.save(centers);
        return centersMapper.toDto(centers);
    }

    /**
     * Get all the centers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentersDTO> findAll() {
        log.debug("Request to get all Centers");
        return centersRepository.findAll().stream()
            .map(centersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one centers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CentersDTO> findOne(Long id) {
        log.debug("Request to get Centers : {}", id);
        return centersRepository.findById(id)
            .map(centersMapper::toDto);
    }

    /**
     * Delete the centers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Centers : {}", id);
        centersRepository.deleteById(id);
    }
}
