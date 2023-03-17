package com.sb.web.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.entities.GiftReward;
import com.sb.web.entities.LotteryRaffle;
import com.sb.web.entities.Reward;
import com.sb.web.entities.SystemSetting;
import com.sb.web.entities.User;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.repositories.LotteryRaffleRepository;
import com.sb.web.repositories.SystemSettingRepository;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.request.dto.CreateRewardRequestDTO;
import com.sb.web.utils.SaversBasketConstants;

@Transactional
@Service
public class LotteryRaffleService {	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private ContributionService contributionService;
	
	@Autowired
	private LotteryRaffleRepository lotteryRaffleRepository;
	
	@Autowired
	private SystemSettingRepository systemSettingRepository;
	
	@Autowired
	private RewardService rewardService;
	
	private List<Integer> lotteryPool = new ArrayList<Integer>();
	
	
	@Transactional
    public LotteryRaffle allocateSlotForReward(CreateRewardRequestDTO createRewardRequestDTO) {
		
		GiftReward giftreward = rewardService.createGiftReward(createRewardRequestDTO);
		
		/* Persist Raffle */
		LotteryRaffle lotteryRaffle = new LotteryRaffle();
		lotteryRaffle.setReward(giftreward);		
		return lotteryRaffleRepository.save(lotteryRaffle);		
	}
	
	
	
	@Transactional
    public void electUserForLotteryRaffle() {
		
		/* retrieve all valid users */
		List<User> lotteryCandidates = userService.getAllValidUsers();		
		for (User lotteryCandidate : lotteryCandidates) {
			BigDecimal userCredits = creditService.retrieveCreditsForContributionUser(lotteryCandidate.getIdUser());
			
			SystemSetting systemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.LOTTERY_DRAW_PARTICIPATION_LIMIT);
			if (userCredits.intValue() > Integer.valueOf(systemSetting.getSystemValue())) {
				lotteryPool.add(lotteryCandidate.getIdUser());
			}
		}	
		
		Integer electedCandidate = shuffleCollectionSimpleStragegy(lotteryPool);
		List<LotteryRaffle> lotteryRaffles = lotteryRaffleRepository.findAll();
		for (LotteryRaffle lotteryRaffle: lotteryRaffles) {
			if (lotteryRaffle.getReward() != null) {
				Reward reward  = lotteryRaffle.getReward();
				if (reward.getRewardRedeemedByUser() == null) {
					/* Link User to Lottery Raffle */
					lotteryRaffle.setUser(userService.getUserById(electedCandidate));					
					
					/* remove extra credits */		
					ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
					contributionRequestDTO.setContributionType(ContributionType.ELECTED_LOTTERY_RAFFLE);
					contributionRequestDTO.setUserIdToBeCredited(electedCandidate);
					contributionService.addContributionForUser(contributionRequestDTO);
					
					/* Send Email to Notify User */
					
					break;
				}
			}
		}
		
	}
	
	/**
	 * convenience method to shuffle collection and return lucky candidate
	 * 
	 * @param lotteryPool
	 * @return
	 */
	private Integer shuffleCollectionSimpleStragegy(List<Integer> lotteryPool) {
		//Random() to shuffle the given list.
		Collections.shuffle(lotteryPool, new Random());
		return lotteryPool.get(0);
	}

}
