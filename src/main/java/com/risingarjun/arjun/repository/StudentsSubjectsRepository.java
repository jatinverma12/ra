package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.StudentsSubjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the StudentsSubjects entity.
 */
@Repository
public interface StudentsSubjectsRepository extends JpaRepository<StudentsSubjects, Long> {

    @Query(value = "select distinct studentsSubjects from StudentsSubjects studentsSubjects left join fetch studentsSubjects.subjects left join fetch studentsSubjects.courses",
        countQuery = "select count(distinct studentsSubjects) from StudentsSubjects studentsSubjects")
    Page<StudentsSubjects> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct studentsSubjects from StudentsSubjects studentsSubjects left join fetch studentsSubjects.subjects left join fetch studentsSubjects.courses")
    List<StudentsSubjects> findAllWithEagerRelationships();

    @Query("select studentsSubjects from StudentsSubjects studentsSubjects left join fetch studentsSubjects.subjects left join fetch studentsSubjects.courses where studentsSubjects.id =:id")
    Optional<StudentsSubjects> findOneWithEagerRelationships(@Param("id") Long id);

}
