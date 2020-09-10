package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.CoursesService;
import com.risingarjun.arjun.domain.Courses;
import com.risingarjun.arjun.repository.CoursesRepository;
import com.risingarjun.arjun.service.dto.CoursesDTO;
import com.risingarjun.arjun.service.mapper.CoursesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Courses}.
 */
@Service
@Transactional
public class CoursesServiceImpl implements CoursesService {

    private final Logger log = LoggerFactory.getLogger(CoursesServiceImpl.class);

    private final CoursesRepository coursesRepository;

    private final CoursesMapper coursesMapper;

    public CoursesServiceImpl(CoursesRepository coursesRepository, CoursesMapper coursesMapper) {
        this.coursesRepository = coursesRepository;
        this.coursesMapper = coursesMapper;
    }

    /**
     * Save a courses.
     *
     * @param coursesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CoursesDTO save(CoursesDTO coursesDTO) {
        log.debug("Request to save Courses : {}", coursesDTO);
        Courses courses = coursesMapper.toEntity(coursesDTO);
        courses = coursesRepository.save(courses);
        return coursesMapper.toDto(courses);
    }

    /**
     * Get all the courses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoursesDTO> findAll() {
        log.debug("Request to get all Courses");
        return coursesRepository.findAll().stream()
            .map(coursesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one courses by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CoursesDTO> findOne(Long id) {
        log.debug("Request to get Courses : {}", id);
        return coursesRepository.findById(id)
            .map(coursesMapper::toDto);
    }

    /**
     * Delete the courses by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Courses : {}", id);
        coursesRepository.deleteById(id);
    }
}
