package com.risingarjun.arjun.repository;

import com.risingarjun.arjun.domain.Expenses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Expenses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

}
