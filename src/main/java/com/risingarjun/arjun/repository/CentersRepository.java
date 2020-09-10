package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Centers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Centers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentersRepository extends JpaRepository<Centers, Long> {

}
