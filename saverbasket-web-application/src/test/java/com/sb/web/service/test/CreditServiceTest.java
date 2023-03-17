package com.sb.web.service.test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.configuration.WebConfig;
import com.sb.web.service.CreditService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.sb.web")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
public class CreditServiceTest {
	
	@Autowired
    private CreditService creditService;
	
	 @Test
	 public void testRetrieveRewardsForContributionUser() throws InterruptedException, ExecutionException {		 
		 BigDecimal rewardsCollected = creditService.retrieveCreditsForContributionUser(2);
		 Assert.assertNotNull(rewardsCollected);				 
	 }

}
