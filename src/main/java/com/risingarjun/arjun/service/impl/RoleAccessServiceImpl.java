package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.RoleAccessService;
import com.risingarjun.arjun.domain.RoleAccess;
import com.risingarjun.arjun.repository.RoleAccessRepository;
import com.risingarjun.arjun.service.dto.RoleAccessDTO;
import com.risingarjun.arjun.service.mapper.RoleAccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link RoleAccess}.
 */
@Service
@Transactional
public class RoleAccessServiceImpl implements RoleAccessService {

    private final Logger log = LoggerFactory.getLogger(RoleAccessServiceImpl.class);

    private final RoleAccessRepository roleAccessRepository;

    private final RoleAccessMapper roleAccessMapper;

    public RoleAccessServiceImpl(RoleAccessRepository roleAccessRepository, RoleAccessMapper roleAccessMapper) {
        this.roleAccessRepository = roleAccessRepository;
        this.roleAccessMapper = roleAccessMapper;
    }

    /**
     * Save a roleAccess.
     *
     * @param roleAccessDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RoleAccessDTO save(RoleAccessDTO roleAccessDTO) {
        log.debug("Request to save RoleAccess : {}", roleAccessDTO);
        RoleAccess roleAccess = roleAccessMapper.toEntity(roleAccessDTO);
        roleAccess = roleAccessRepository.save(roleAccess);
        return roleAccessMapper.toDto(roleAccess);
    }

    /**
     * Get all the roleAccesses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoleAccessDTO> findAll() {
        log.debug("Request to get all RoleAccesses");
        return roleAccessRepository.findAll().stream()
            .map(roleAccessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one roleAccess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoleAccessDTO> findOne(Long id) {
        log.debug("Request to get RoleAccess : {}", id);
        return roleAccessRepository.findById(id)
            .map(roleAccessMapper::toDto);
    }

    /**
     * Delete the roleAccess by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleAccess : {}", id);
        roleAccessRepository.deleteById(id);
    }
}
