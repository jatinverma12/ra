package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.UserPreferences;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserPreferences entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {

}
