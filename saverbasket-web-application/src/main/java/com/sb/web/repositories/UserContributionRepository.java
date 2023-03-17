package com.sb.web.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sb.web.entities.UserContribution;

public interface UserContributionRepository  extends JpaRepository<UserContribution, Integer>  {
	
	 @Query("SELECT uc FROM usercontribution uc where uc.contributedByUserId=:idUserContributor AND uc.contributionType in (0,1,2,3,12)")
	 List<UserContribution> getAllContributionsByUser(@Param("idUserContributor")  Integer idUserContributor);
	 
	 @Query("SELECT uc FROM usercontribution uc WHERE uc.dateContributionAdded >= :xDaysAgoDate AND uc.contributionType = 4")
	 List<UserContribution> findByDateGreaterThan(@Param("xDaysAgoDate") Date xDaysAgoDate);
		
	 @Query("SELECT uc FROM usercontribution uc WHERE uc.dateContributionAdded >= :xDaysAgoDate AND uc.contributionType = 12")
	 List<UserContribution> findBarcodeScansByDateGreaterThan(@Param("xDaysAgoDate") Date xDaysAgoDate);
}
