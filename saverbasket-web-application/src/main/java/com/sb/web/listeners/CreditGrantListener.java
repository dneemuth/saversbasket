package com.sb.web.listeners;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sb.web.entities.User;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.events.OnGrantCreditsEvent;
import com.sb.web.repositories.UserRepository;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.response.dto.ContributionResponseDTO;
import com.sb.web.service.UserActionRewarderService;

@Component
@Transactional
public class CreditGrantListener implements 
ApplicationListener<OnGrantCreditsEvent>  {	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserActionRewarderService userActionRewarderService;	

	@Override
	public void onApplicationEvent(OnGrantCreditsEvent event) {		
		User user = event.getUser();
		if (user != null) {
			grantBeneficiaryCreditsForAdmin(user.getEmail());
		}		
	}
	
	@Transactional
    private final ContributionResponseDTO grantBeneficiaryCreditsForAdmin(final String email) {    	
    	ContributionResponseDTO contributionResponseDTO = null;
        User user = userRepository.findByEmail(email);
        if (user != null) {        	
        	ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
		    contributionRequestDTO.setContributionType(ContributionType.GRANT_BENEFICIARY_CREDITS);
		    contributionRequestDTO.setUserIdToBeCredited(user.getIdUser());
		  
			contributionResponseDTO = userActionRewarderService.grantRewardForUserAction(contributionRequestDTO);
        }        
        return contributionResponseDTO;      
    }

}
