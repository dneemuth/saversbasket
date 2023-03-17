package com.sb.web.repositories;

import javax.transaction.Transactional;

import com.sb.web.entities.Reward;

@Transactional
public interface RewardRepository  extends RewardBaseRepository<Reward>
{

}
