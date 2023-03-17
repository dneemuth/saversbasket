package com.sb.web.service.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

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
import com.sb.web.email.EmailService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.sb.web")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
@Transactional
public class DefaultEmailServiceTest {
	
	@Autowired
    private EmailService emailService;
	
	@Test
	 public void testSendSimpleMessage() throws InterruptedException, ExecutionException, IOException, MessagingException {		 
		 emailService.sendSimpleMessage("dil.neemuth@gmail.com", "test", "text description...");
		// Assert.assertNotNull(compareBasketResponseDTO);					 
	 }
	
	
	@Test
	 public void testSendMessageUsingThymeleafTemplate() throws InterruptedException, ExecutionException, IOException, MessagingException {		 
		 emailService.sendMessageUsingThymeleafTemplate("dil.neemuth@gmail.com", "test", "invitation-template.html", null);
		// Assert.assertNotNull(compareBasketResponseDTO);	
			 
	 }
	
	
	@Test
	 public void testSendMessageUsingThymeleafTemplate_SingUp() throws InterruptedException, ExecutionException, IOException, MessagingException {
		
		 String confirmationUrl = "http://localhost:8080/registrationConfirm.html?token=123";
		 String subject = "SaversBasket - Complete Sign Up";
		
		 Map<String, Object> signUpModel = new HashMap<String, Object>();    	  
	     //signUpModel.put("confirmRegistrationLink", confirmationUrl);
		 signUpModel.put("token", "token123");		
		
		 emailService.sendMessageUsingThymeleafTemplate("dil.neemuth@gmail.com", subject, "sign-up-template.html", signUpModel);
		// Assert.assertNotNull(compareBasketResponseDTO);		
			 
	 }	

}
