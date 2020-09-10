package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.RoleAccess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RoleAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoleAccessRepository extends JpaRepository<RoleAccess, Long> {

}
