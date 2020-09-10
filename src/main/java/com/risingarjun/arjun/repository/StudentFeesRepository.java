package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.StudentFees;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentFees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentFeesRepository extends JpaRepository<StudentFees, Long> {

}
