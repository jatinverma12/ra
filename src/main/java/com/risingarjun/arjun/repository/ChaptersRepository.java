package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Chapters;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Chapters entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChaptersRepository extends JpaRepository<Chapters, Long> {

}
