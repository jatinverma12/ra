package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Features;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Features entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeaturesRepository extends JpaRepository<Features, Long> {

}
