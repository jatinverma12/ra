package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Subjects;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Subjects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectsRepository extends JpaRepository<Subjects, Long> {

}
