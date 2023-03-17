package com.sb.web.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sb.web.entities.Reward;

@NoRepositoryBean
public interface RewardBaseRepository <T extends Reward> 
extends CrudRepository<T, Long> {

	public T findByIdReward(Integer idReward);
}
