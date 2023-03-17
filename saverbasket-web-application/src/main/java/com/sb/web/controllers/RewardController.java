package com.sb.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sb.web.entities.LotteryRaffle;
import com.sb.web.request.dto.CreateRewardRequestDTO;
import com.sb.web.response.dto.CreatedRewardResponseDTO;
import com.sb.web.service.LotteryRaffleService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/rewards")
@Api(tags = "rewards")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Transactional
public class RewardController extends AbstractController {	
	
	 @Autowired
	 private LotteryRaffleService lotteryRaffleService;
	
	  @Transactional
	  @PostMapping(value = "/createGiftReward", produces = { MediaType.APPLICATION_JSON_VALUE })	
	  @ResponseBody
	  public CreatedRewardResponseDTO createGiftReward(@ModelAttribute @Valid CreateRewardRequestDTO createRewardRequestDTO,
	         BindingResult result) {	
		  CreatedRewardResponseDTO createdRewardResponseDTO = new CreatedRewardResponseDTO();
		  
		  String email =  getPrincipal();
		  if (email != null && email.equals("anonymousUser")) {
			  createdRewardResponseDTO.setUserAuthenticated(Boolean.FALSE);
		  }
		  else 
		  {
			  createdRewardResponseDTO.setUserAuthenticated(Boolean.TRUE);
			  LotteryRaffle createLottery = lotteryRaffleService.allocateSlotForReward(createRewardRequestDTO);	
			  if (createLottery != null) {				  	
				  //allocate reward to lottery raffle
				  LotteryRaffle lotteryRaffle = lotteryRaffleService.allocateSlotForReward(createRewardRequestDTO);
				  if (lotteryRaffle != null) {
					  createdRewardResponseDTO.setValidated(Boolean.TRUE);	
				  }
			  } 
		  }		  
		  		  
		  return createdRewardResponseDTO;		  
	  }

}
