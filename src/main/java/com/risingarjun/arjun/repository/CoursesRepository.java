package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Courses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Courses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {

}
