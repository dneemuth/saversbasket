package com.sb.web.entities.audit;

import java.util.Optional;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.entities.User;
import com.sb.web.service.UserService;

@Transactional(propagation= Propagation.REQUIRES_NEW)
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<Integer> {
	
	@Autowired
	private UserService userService;
	
	 /**
     * This method returns the principal[user-name] of logged-in user.
     */
	@Transactional(propagation= Propagation.REQUIRES_NEW)
    private Integer getPrincipal(){
        String email = null;
        Integer idUserLogged = new Integer(NumberUtils.INTEGER_ZERO);
        
        if (SecurityContextHolder.getContext() != null 
        		&& SecurityContextHolder.getContext().getAuthentication() != null 
        		&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
        	
        	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                email = ((UserDetails)principal).getUsername();
            } else {
                email = principal.toString();
            }
            
            if (email != null && !email.isEmpty()) {
            	User user = userService.findByUserEmail(email); 
                if (user != null) {
                	idUserLogged = user.getIdUser();
                } else
                {
                	//set auditor to admin user id by default.
                	user = userService.findByUserEmail("info.saversbasket@gmail.com"); 
                	if (user != null) {
                		idUserLogged = user.getIdUser();
                	} 
                }  
            }	
        	
        } 
        
        
        return idUserLogged;
    }

	@Override
	public Optional<Integer> getCurrentAuditor() {
		Optional<Integer> optionalIdUser = Optional.of(getPrincipal());
		return optionalIdUser;
	}

  

}
