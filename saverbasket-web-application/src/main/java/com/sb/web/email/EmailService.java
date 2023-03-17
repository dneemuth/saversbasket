package com.sb.web.email;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
	
	void sendSimpleMessage(String to,
            String subject,
            String text)throws IOException, javax.mail.MessagingException;
	
	void sendSimpleMessageUsingTemplate(String to,
	                         String subject,
	                         String ...templateModel);
	
	void sendMessageWithAttachment(String to,
	                    String subject,
	                    String text,
	                    String pathToAttachment);
	
	void sendMessageUsingThymeleafTemplate(String to,
	                            String subject,String templateFile,
	                            Map<String, Object> templateModel) 
	throws IOException, javax.mail.MessagingException;
	
	

}
