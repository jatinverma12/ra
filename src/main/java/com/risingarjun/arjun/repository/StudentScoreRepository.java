package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.StudentScore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentScore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {

}
