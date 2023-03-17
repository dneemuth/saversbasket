package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer>{
	
	@Query("SELECT p FROM plan p WHERE  p.planName = :planName")
    public Plan retrieveAccountForUser(@Param("planName") String planName);

}
