package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.TeachersShare;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TeachersShare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachersShareRepository extends JpaRepository<TeachersShare, Long> {

}
