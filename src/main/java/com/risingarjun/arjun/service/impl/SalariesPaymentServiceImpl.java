package com.risingarjun.arjun.service.impl;

import com.risingarjun.arjun.service.SalariesPaymentService;
import com.risingarjun.arjun.domain.SalariesPayment;
import com.risingarjun.arjun.repository.SalariesPaymentRepository;
import com.risingarjun.arjun.service.dto.SalariesPaymentDTO;
import com.risingarjun.arjun.service.mapper.SalariesPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SalariesPayment}.
 */
@Service
@Transactional
public class SalariesPaymentServiceImpl implements SalariesPaymentService {

    private final Logger log = LoggerFactory.getLogger(SalariesPaymentServiceImpl.class);

    private final SalariesPaymentRepository salariesPaymentRepository;

    private final SalariesPaymentMapper salariesPaymentMapper;

    public SalariesPaymentServiceImpl(SalariesPaymentRepository salariesPaymentRepository, SalariesPaymentMapper salariesPaymentMapper) {
        this.salariesPaymentRepository = salariesPaymentRepository;
        this.salariesPaymentMapper = salariesPaymentMapper;
    }

    /**
     * Save a salariesPayment.
     *
     * @param salariesPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SalariesPaymentDTO save(SalariesPaymentDTO salariesPaymentDTO) {
        log.debug("Request to save SalariesPayment : {}", salariesPaymentDTO);
        SalariesPayment salariesPayment = salariesPaymentMapper.toEntity(salariesPaymentDTO);
        salariesPayment = salariesPaymentRepository.save(salariesPayment);
        return salariesPaymentMapper.toDto(salariesPayment);
    }

    /**
     * Get all the salariesPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalariesPaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalariesPayments");
        return salariesPaymentRepository.findAll(pageable)
            .map(salariesPaymentMapper::toDto);
    }


    /**
     * Get one salariesPayment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SalariesPaymentDTO> findOne(Long id) {
        log.debug("Request to get SalariesPayment : {}", id);
        return salariesPaymentRepository.findById(id)
            .map(salariesPaymentMapper::toDto);
    }

    /**
     * Delete the salariesPayment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SalariesPayment : {}", id);
        salariesPaymentRepository.deleteById(id);
    }
}
