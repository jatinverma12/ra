package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.AcademicSessions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AcademicSessions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicSessionsRepository extends JpaRepository<AcademicSessions, Long> {

}
