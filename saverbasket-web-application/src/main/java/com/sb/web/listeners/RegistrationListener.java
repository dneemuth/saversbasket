package com.sb.web.listeners;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.sb.web.email.EmailService;
import com.sb.web.entities.User;
import com.sb.web.enumerations.DeviceType;
import com.sb.web.events.OnRegistrationCompleteEvent;
import com.sb.web.service.BasketService;

@Component
public class RegistrationListener implements 
ApplicationListener<OnRegistrationCompleteEvent> {
	
	 private final Logger LOG = LoggerFactory.getLogger(RegistrationListener.class);
	 
    @Autowired
    private BasketService service;
   
    @Autowired
    private EmailService emailService;
	
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        try {
			this.confirmRegistration(event);
		} catch (IOException e) {
			
		} catch (MessagingException e) {
			LOG.warn("Error occured on user registration.", e);
		}
    }
 
    /**
     * 
     * @param event
     * @throws IOException
     * @throws MessagingException
     */
    private void confirmRegistration(OnRegistrationCompleteEvent event) throws IOException, MessagingException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);
        
        String recipientAddress = user.getEmail();
        String subject = "SaversBasket - Complete Sign Up Process";
        
        String confirmationUrl = "";
        if (event.getDeviceType() == DeviceType.DEVICE_TYPE_DESKTOP) {
        	confirmationUrl =event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        }       
              
        Map<String, Object> signUpModel = new HashMap<String, Object>();    	  
     	signUpModel.put("confirmRegistrationLink", confirmationUrl);
        
     	/**
     	 * Email service
     	 */
        emailService.sendMessageUsingThymeleafTemplate(recipientAddress, subject, "sign-up-template.html",signUpModel);
    }

}
