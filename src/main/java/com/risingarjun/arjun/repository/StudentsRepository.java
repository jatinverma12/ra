package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Students entity.
 */
@Repository
public interface StudentsRepository extends JpaRepository<Students, Long> {

    @Query(value = "select distinct students from Students students left join fetch students.courses",
        countQuery = "select count(distinct students) from Students students")
    Page<Students> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct students from Students students left join fetch students.courses")
    List<Students> findAllWithEagerRelationships();

    @Query("select students from Students students left join fetch students.courses where students.id =:id")
    Optional<Students> findOneWithEagerRelationships(@Param("id") Long id);

}
