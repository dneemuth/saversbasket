package com.sb.web.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.entities.UserContribution;
import com.sb.web.enumerations.ContributionStatus;
import com.sb.web.repositories.UserContributionRepository;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.request.dto.CreditRequestDTO;
import com.sb.web.response.dto.ContributionResponseDTO;
import com.sb.web.response.dto.CreditResponseDTO;

@Service
public class ContributionService {
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private UserContributionRepository userContributionRepository;
	
	
	
	@Transactional
	public BigDecimal retrieveNumberOfContributionForUser(Integer loggedUser) {	
		BigDecimal numContributions = new BigDecimal(0);
		List<UserContribution> userContributuions = userContributionRepository.getAllContributionsByUser(loggedUser);
		if (userContributuions != null && userContributuions.size() > 0) {
			numContributions = new BigDecimal(userContributuions.size());
		}
		return numContributions;
	}
	
	
	@Transactional
	public ContributionResponseDTO addContributionForUser(ContributionRequestDTO contributionRequestDTO) {	
		ContributionResponseDTO contributionResponseDTO = null;
		
		UserContribution userContribution = new UserContribution();
		userContribution.setContributionType(contributionRequestDTO.getContributionType());
		userContribution.setContributedByUserId(contributionRequestDTO.getUserIdToBeCredited());	
		userContribution.setContributionStatus(ContributionStatus.SUCCESS);			
		userContribution.setDateContributionAdded(new Date());
		if (contributionRequestDTO.getScannedBarCode() != null) {
			userContribution.setScannedBarCode(contributionRequestDTO.getScannedBarCode());
		}		
				
		UserContribution savedUserContribution = userContributionRepository.save(userContribution);	
		
		if (savedUserContribution != null) {
			contributionResponseDTO = new ContributionResponseDTO();
			
			CreditRequestDTO creditRequestDTO = new CreditRequestDTO();
			creditRequestDTO.setContributionType(contributionRequestDTO.getContributionType());
			creditRequestDTO.setCreditedUserId(contributionRequestDTO.getUserIdToBeCredited());
			creditRequestDTO.setNumberOfContributions(contributionRequestDTO.getNumberOfContributions());
			
			CreditResponseDTO creditResponseDTO = creditService.creditUserForAction(creditRequestDTO);
			
			if (creditResponseDTO != null) {
				
				if (contributionRequestDTO.getNumberOfContributions() != null) {
					contributionResponseDTO.setMonetaryReward(creditResponseDTO.getCreditsAwarded().multiply(new BigDecimal(contributionRequestDTO.getNumberOfContributions())));
				}
				else
				{
					contributionResponseDTO.setMonetaryReward(creditResponseDTO.getCreditsAwarded());
				}				
			}			
		}		
		
		return contributionResponseDTO;
	}

}
