package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Scholarships;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Scholarships entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScholarshipsRepository extends JpaRepository<Scholarships, Long> {

}
