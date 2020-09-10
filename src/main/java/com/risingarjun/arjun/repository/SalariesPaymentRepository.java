package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.SalariesPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalariesPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalariesPaymentRepository extends JpaRepository<SalariesPayment, Long> {

}
