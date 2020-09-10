package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.FeaturesService;
import com.risingarjun.arjun.domain.Features;
import com.risingarjun.arjun.repository.FeaturesRepository;
import com.risingarjun.arjun.service.dto.FeaturesDTO;
import com.risingarjun.arjun.service.mapper.FeaturesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Features}.
 */
@Service
@Transactional
public class FeaturesServiceImpl implements FeaturesService {

    private final Logger log = LoggerFactory.getLogger(FeaturesServiceImpl.class);

    private final FeaturesRepository featuresRepository;

    private final FeaturesMapper featuresMapper;

    public FeaturesServiceImpl(FeaturesRepository featuresRepository, FeaturesMapper featuresMapper) {
        this.featuresRepository = featuresRepository;
        this.featuresMapper = featuresMapper;
    }

    /**
     * Save a features.
     *
     * @param featuresDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FeaturesDTO save(FeaturesDTO featuresDTO) {
        log.debug("Request to save Features : {}", featuresDTO);
        Features features = featuresMapper.toEntity(featuresDTO);
        features = featuresRepository.save(features);
        return featuresMapper.toDto(features);
    }

    /**
     * Get all the features.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FeaturesDTO> findAll() {
        log.debug("Request to get all Features");
        return featuresRepository.findAll().stream()
            .map(featuresMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one features by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FeaturesDTO> findOne(Long id) {
        log.debug("Request to get Features : {}", id);
        return featuresRepository.findById(id)
            .map(featuresMapper::toDto);
    }

    /**
     * Delete the features by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Features : {}", id);
        featuresRepository.deleteById(id);
    }
}
