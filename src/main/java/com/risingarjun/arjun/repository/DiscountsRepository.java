package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Discounts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Discounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountsRepository extends JpaRepository<Discounts, Long> {

}
