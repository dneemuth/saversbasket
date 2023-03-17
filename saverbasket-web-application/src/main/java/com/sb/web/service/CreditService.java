package com.sb.web.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.entities.Credit;
import com.sb.web.entities.CreditContribution;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.enumerations.CreditContributionStatus;
import com.sb.web.repositories.CreditContributionRepository;
import com.sb.web.repositories.CreditRepository;
import com.sb.web.request.dto.CreditRequestDTO;
import com.sb.web.response.dto.CreditResponseDTO;
import com.sb.web.utils.SaversBasketConstants;

@Service
public class CreditService {	
	
	@Autowired
	private CreditContributionRepository creditContributionRepository;	
	
	@Autowired
	private CreditRepository creditRepository;	
		
	@Transactional
	public BigDecimal retrieveCreditsForContributionUser(Integer rewardedUser) {		
		/**
		 * Retrieve rewards
		 */
		 List<CreditContribution> creditContributions =  creditContributionRepository.retrieveCreditsForContributionUser(rewardedUser);
		 
		 BigDecimal totalRewards = creditContributions.stream()
			        .filter(creditContribution -> creditContribution != null && creditContribution.getCreditGranted() != null)
			        .map(creditContribution -> creditContribution.getCreditGranted())
			        .reduce(BigDecimal.ZERO, BigDecimal::add)
			        .setScale(SaversBasketConstants.SCALE, RoundingMode.CEILING);
		 
		 return totalRewards;		 
	}
	
	
	@Transactional
	public BigDecimal calculateRewardForContribution(ContributionType contributionType) {		
		/**
		 * Retrieve rewards
		 */
		 List<CreditContribution> creditContributions =  creditContributionRepository.retrieveCreditsForContributionType(contributionType);
		
		 BigDecimal totalRewards = creditContributions.stream()
				  .filter(creditContribution -> creditContribution != null && creditContribution.getCreditGranted() != null)
			        .map(creditContribution -> creditContribution.getCreditGranted())
			        .reduce(BigDecimal.ZERO, BigDecimal::add)
			        .setScale(SaversBasketConstants.SCALE, RoundingMode.CEILING);	 
		 
		 return totalRewards;		 
	}
	
	
	/**
	 * Rewards user for action or contribution
	 * 
	 * @param contributionType
	 */
	@Transactional
	public CreditResponseDTO creditUserForAction(CreditRequestDTO rewardRequestDTO) {
		
		CreditResponseDTO creditResponseDTO = null;
		
		/**
		 * Get points related to action
		 */
		Credit credit = creditRepository.getCreditByContributionType(rewardRequestDTO.getContributionType());
		
		if (credit != null) {
			
			/**
			 * create RewardContribution
			 */
			CreditContribution rewardContribution = new CreditContribution();		
			
			rewardContribution.setCreditGranted(credit.getPoints());		
			rewardContribution.setContributionType(rewardRequestDTO.getContributionType());
			if (rewardRequestDTO.getNumberOfContributions() != null) {
				rewardContribution.setNumberOfContributions(rewardRequestDTO.getNumberOfContributions());
			}		
			rewardContribution.setCreditContributionStatus(CreditContributionStatus.GRANTED);			
			CreditContribution savedRewardContribution = creditContributionRepository.save(rewardContribution);
			
			if (savedRewardContribution != null) {
				creditResponseDTO = new CreditResponseDTO();			
				creditResponseDTO.setCreditsAwarded(credit.getPoints());			
			}			
		}
		
		return creditResponseDTO;		
	}

}
