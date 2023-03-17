package com.sb.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.web.entities.LotteryRaffle;

public interface LotteryRaffleRepository  extends JpaRepository<LotteryRaffle, Integer> {

}
