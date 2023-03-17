package com.sb.web.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.response.dto.ContributionResponseDTO;

@Service
public class UserActionRewarderService {
	
	@Autowired
	private ContributionService contributionService;
	
	
	@Transactional
	public ContributionResponseDTO grantRewardForUserAction(ContributionRequestDTO contributionRequestDTO) {	
		
		if (contributionRequestDTO != null && contributionRequestDTO.getUserIdToBeCredited() == null) {
			return null;
		}
		return contributionService.addContributionForUser(contributionRequestDTO);
	}

}
