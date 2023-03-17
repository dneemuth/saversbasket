package com.sb.web.aspects;



import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sb.web.annotations.Auditable;
import com.sb.web.annotations.AuditingTargetField;
import com.sb.web.entities.Audit;
import com.sb.web.repositories.AuditRepository;
import com.sb.web.service.UserService;



@Aspect
@Component
public class AuditingAspect {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditingAspect.class);
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuditRepository auditRepository;
    
    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal(){
        String userName = null;
        
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
        	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
             
             if (principal != null) {
             	if (principal instanceof UserDetails) {
                     userName = ((UserDetails)principal).getUsername();
                 } else {
                     userName = principal.toString();
                 }
             } 
        }
        return userName;
    }
	
	@After("@annotation(auditable)")
	@Transactional
	public void logAuditActivity(JoinPoint jp, Auditable auditable) {
	
	String actionType = auditable.actionType().toString();
	
	String auditingUsername = getPrincipal();
	
	if (auditingUsername != null) {
		String targetAuditingUser = extractTargetAuditingUser(jp.getArgs());
		if (targetAuditingUser == null) {
			targetAuditingUser = auditingUsername;
		}
						
		String role = "ROLE_FREE_USER_PLAN";
		if (userService.findByUsername(auditingUsername) != null) {
			role = userService.findByUsername(auditingUsername).getRoles().toString();
		}
			
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String auditingUsernameIp = "";
		if (StringUtils.isNotEmpty(request.getRemoteAddr())) {
			 auditingUsernameIp =  request.getRemoteAddr();
		}		
		
		LOGGER.debug(
		"Auditing information. auditingUsername=" + auditingUsername + ", actionType=" + actionType + ", role=" + role + ", targetAuditingUser="
		+ targetAuditingUser + " auditingUsernameIp=" + auditingUsernameIp
		);
		
		auditRepository.save(new Audit(auditingUsername, role, actionType, targetAuditingUser,
				new java.util.Date(),auditingUsernameIp));		
	}
	
	}
	
	public String extractTargetAuditingUser(Object obj) {
		
		return getTargetAuditingUserViaAnnotation(obj);
	}
	
	private String getTargetAuditingUserViaAnnotation(Object obj) {
		Class cl=obj.getClass();
		String result = null;
		try {
		for (Field f : cl.getDeclaredFields())
			for (Annotation a : f.getAnnotations()) {
				if (a.annotationType() == AuditingTargetField.class) {
				f.setAccessible(true);
				Field annotatedFieldName = cl.getDeclaredField(f.getName());
				annotatedFieldName.setAccessible(true);
				String annotatedFieldVal = (String) annotatedFieldName.get(obj);
				
				LOGGER.debug("Found auditing annotation. type=" + a.annotationType() + " value=" + annotatedFieldVal.toString());
				result = annotatedFieldVal;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error extracting auditing annotations from obj" + obj.getClass());
		}
		return result;
	}
	
}
