package com.sb.web.service.test;

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

import com.sb.web.configuration.WebConfig;
import com.sb.web.response.dto.CompareBasketResponseDTO;
import com.sb.web.service.BasketService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.sb.web")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
public class BasketServiceTest {
	
	
	@Autowired
    private BasketService basketService;
	
	
	 @Test
	 public void testCalculatePricesFromBasketForSpecificRetailer() throws InterruptedException, ExecutionException {
		 
		 CompareBasketResponseDTO compareBasketResponseDTO = basketService.calculatePricesFromBasketForSpecificRetailer(7);
		 Assert.assertNotNull(compareBasketResponseDTO);		
			 
	 }

}
