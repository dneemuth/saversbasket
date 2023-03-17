package com.sb.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.email.EmailService;
import com.sb.web.entities.User;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.response.dto.ContributionResponseDTO;
import com.sb.web.response.dto.SendMailInviteResponse;

@Service
public class InviteService {
		
	@Autowired
    private EmailService emailService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private UserActionRewarderService userActionRewarderService;
		
	@Transactional
	public SendMailInviteResponse sendEmailInvites(String ownerEmailSender, String inviteesEmail){

		SendMailInviteResponse sendMailInviteResponse = new SendMailInviteResponse();
		try {	
				
			List<String> emailAddresses = decodeEmailAddresses(inviteesEmail,",");
			
			if (emailAddresses != null && emailAddresses.size() > 0) {
				
				//calculate number of email invites
				Integer numberOfEmailInvites = emailAddresses.size();
				
				for (String email: emailAddresses) {	
					 emailService.sendMessageUsingThymeleafTemplate(email, "SaversBasket - Email Invites", "invitation-template.html", null);
				}
				
				//alert administrator
				emailService.sendSimpleMessage("dil.neemuth@gmail.com", "Invite feature used", "Invite feature used");
				
				//count emails sent
				sendMailInviteResponse.setNumberOfInviteSent(numberOfEmailInvites);
				sendMailInviteResponse.setInviteSent(Boolean.TRUE);
				
				/**
				 * Reward user if signed up.
				 */
				if (ownerEmailSender != null) {
					
					 User user = userService.findByUserEmail(ownerEmailSender); 
					
					/**
					   * Add reward
					   */			  
					  ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
					  contributionRequestDTO.setContributionType(ContributionType.SEND_INVITES_BENEFICIARY_CREDITS);
					  contributionRequestDTO.setNumberOfContributions(numberOfEmailInvites);
					  contributionRequestDTO.setUserIdToBeCredited(user.getIdUser());
					  
					  ContributionResponseDTO contributionResponseDTO = userActionRewarderService.grantRewardForUserAction(contributionRequestDTO);
					  if (contributionResponseDTO != null && contributionResponseDTO.getMonetaryReward() != null) {
						  sendMailInviteResponse.setCreditedReward(contributionResponseDTO.getMonetaryReward());
					  }	
					
				}
				 	
			}
			
						
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return sendMailInviteResponse;
		
	}
	
	
	/**
	 * Utility to retrieve list of emails.
	 * 
	 * @param str
	 * @return
	 */
	private List<String> decodeEmailAddresses(String str , String delimiter) {
	    List<String> tokens = new ArrayList<>();
	    StringTokenizer tokenizer = new StringTokenizer(str, delimiter);
	    while (tokenizer.hasMoreElements()) {
	        tokens.add(tokenizer.nextToken());
	    }
	    return tokens;
	}

}
