package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.Credit;
import com.sb.web.enumerations.ContributionType;

public interface CreditRepository extends JpaRepository<Credit, Integer> {

	 
	 @Query("SELECT r FROM credit r WHERE r.contributionType=:contributionType")
	 public Credit getCreditByContributionType(@Param("contributionType") ContributionType contributionType);
	
}
