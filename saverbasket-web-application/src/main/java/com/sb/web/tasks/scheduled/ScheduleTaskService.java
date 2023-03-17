package com.sb.web.tasks.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sb.web.service.LotteryRaffleService;

@Service
@EnableAsync
public class ScheduleTaskService {
	
	@Autowired
	private LotteryRaffleService lotteryRaffleService;
	
	@Async
	@Scheduled(cron = "${cron.shuffle.run.event}")
	public void triggerLotteryRaffleEvent() throws InterruptedException {		
		//lotteryRaffleService.electUserForLotteryRaffle();
		 System.out.println(
		          "Fixed rate task async - " + System.currentTimeMillis() / 1000);
		        Thread.sleep(2000);
	}

}
