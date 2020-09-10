package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.CenterHead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CenterHead entity.
 */
@Repository
public interface CenterHeadRepository extends JpaRepository<CenterHead, Long> {

    @Query(value = "select distinct centerHead from CenterHead centerHead left join fetch centerHead.centers",
        countQuery = "select count(distinct centerHead) from CenterHead centerHead")
    Page<CenterHead> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct centerHead from CenterHead centerHead left join fetch centerHead.centers")
    List<CenterHead> findAllWithEagerRelationships();

    @Query("select centerHead from CenterHead centerHead left join fetch centerHead.centers where centerHead.id =:id")
    Optional<CenterHead> findOneWithEagerRelationships(@Param("id") Long id);

}
