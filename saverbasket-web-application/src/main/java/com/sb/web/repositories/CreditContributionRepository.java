package com.sb.web.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.CreditContribution;
import com.sb.web.enumerations.ContributionType;

public interface CreditContributionRepository extends JpaRepository<CreditContribution, Integer>{
	
	 @Query("SELECT rc FROM creditcontribution rc WHERE rc.contributionType=:contributionType")
	 public List<CreditContribution> retrieveCreditsForContributionType(@Param("contributionType") ContributionType contributionType);
	 
	 @Query("SELECT rc FROM creditcontribution rc WHERE rc.createdBy=:rewardedUser")
	 public List<CreditContribution> retrieveCreditsForContributionUser(@Param("rewardedUser") Integer creditedUser);
}
