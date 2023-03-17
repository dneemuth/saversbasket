package com.sb.web.email;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.sb.web.configuration.CustomPropertyConfig;


@Service
public class DefaultEmailService  implements EmailService  {
	
	@Autowired
    public JavaMailSender emailSender;
    
    @Autowired
    public SimpleMailMessage template;
    
    @Autowired
    public CustomPropertyConfig customPropertyConfig;
    
    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;
     
   // @Value("classpath:/img/logo/sblogo.png")
   // Resource resourceFile;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            
            //Important: Set from address for AWS SES
            message.setFrom(customPropertyConfig.mailFrom);
            
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to,
                                               String subject,
                                               String ...templateModel) {
        String text = String.format(template.getText(), templateModel);  
        sendSimpleMessage(to, subject, text);
    }

    @Override
    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text,
                                          String pathToAttachment) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

          //Important: Set from address for AWS SES
            helper.setFrom(customPropertyConfig.mailFrom);
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void sendMessageUsingThymeleafTemplate(
        String to, String subject,String templateFile, Map<String, Object> templateModel)
            throws javax.mail.MessagingException {
                
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        
        String htmlBody = thymeleafTemplateEngine.process(templateFile, thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    
    private void sendHtmlMessage(String to, String subject, String htmlBody) throws javax.mail.MessagingException {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
      //Important: Set from address for AWS SES
        helper.setFrom(customPropertyConfig.mailFrom);
        
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        // helper.addInline("attachment.png", resourceFile);
        
        emailSender.send(message);

    }

}
