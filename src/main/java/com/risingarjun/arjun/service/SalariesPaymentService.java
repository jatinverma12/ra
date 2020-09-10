package com.risingarjun.arjun.service;

import com.risingarjun.arjun.service.dto.SalariesPaymentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.risingarjun.arjun.domain.SalariesPayment}.
 */
public interface SalariesPaymentService {

    /**
     * Save a salariesPayment.
     *
     * @param salariesPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    SalariesPaymentDTO save(SalariesPaymentDTO salariesPaymentDTO);

    /**
     * Get all the salariesPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SalariesPaymentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" salariesPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SalariesPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" salariesPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
