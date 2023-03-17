package com.sb.web.repositories;

import javax.transaction.Transactional;

import com.sb.web.entities.GiftReward;

@Transactional
public interface GiftRewardRepository  extends RewardBaseRepository<GiftReward> {

}
