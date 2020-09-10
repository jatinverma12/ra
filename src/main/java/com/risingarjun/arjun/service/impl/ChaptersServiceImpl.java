package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.ChaptersService;
import com.risingarjun.arjun.domain.Chapters;
import com.risingarjun.arjun.repository.ChaptersRepository;
import com.risingarjun.arjun.service.dto.ChaptersDTO;
import com.risingarjun.arjun.service.mapper.ChaptersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Chapters}.
 */
@Service
@Transactional
public class ChaptersServiceImpl implements ChaptersService {

    private final Logger log = LoggerFactory.getLogger(ChaptersServiceImpl.class);

    private final ChaptersRepository chaptersRepository;

    private final ChaptersMapper chaptersMapper;

    public ChaptersServiceImpl(ChaptersRepository chaptersRepository, ChaptersMapper chaptersMapper) {
        this.chaptersRepository = chaptersRepository;
        this.chaptersMapper = chaptersMapper;
    }

    /**
     * Save a chapters.
     *
     * @param chaptersDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ChaptersDTO save(ChaptersDTO chaptersDTO) {
        log.debug("Request to save Chapters : {}", chaptersDTO);
        Chapters chapters = chaptersMapper.toEntity(chaptersDTO);
        chapters = chaptersRepository.save(chapters);
        return chaptersMapper.toDto(chapters);
    }

    /**
     * Get all the chapters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ChaptersDTO> findAll() {
        log.debug("Request to get all Chapters");
        return chaptersRepository.findAll().stream()
            .map(chaptersMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one chapters by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ChaptersDTO> findOne(Long id) {
        log.debug("Request to get Chapters : {}", id);
        return chaptersRepository.findById(id)
            .map(chaptersMapper::toDto);
    }

    /**
     * Delete the chapters by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chapters : {}", id);
        chaptersRepository.deleteById(id);
    }
}
