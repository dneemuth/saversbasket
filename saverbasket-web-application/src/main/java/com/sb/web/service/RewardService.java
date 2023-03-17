package com.sb.web.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.entities.GiftReward;
import com.sb.web.repositories.GiftRewardRepository;
import com.sb.web.request.dto.CreateRewardRequestDTO;
import com.sb.web.utils.ApplicationUtils;

@Transactional
@Service
public class RewardService {	
	
	private static int GIFT_DURATION_DAY = 5;

	@Autowired
	private GiftRewardRepository giftRewardRepository;
	
	@Transactional
    public GiftReward createGiftReward(CreateRewardRequestDTO createRewardRequestDTO) {
		
		GiftReward giftReward = new GiftReward();
		giftReward.setRewardName(createRewardRequestDTO.getRewardName());
		giftReward.setGiftDetails(createRewardRequestDTO.getGiftDetails());
		giftReward.setRewardRedeemed(Boolean.FALSE);
		giftReward.setStartRewardDate(new Date());
		
		LocalDate startRewardDate = LocalDate.now();
		startRewardDate.plusDays(GIFT_DURATION_DAY);		
		giftReward.setEndRewardDate(ApplicationUtils.convertToDateViaInstant(startRewardDate));
		
		return giftRewardRepository.save(giftReward);
	}
	
}
