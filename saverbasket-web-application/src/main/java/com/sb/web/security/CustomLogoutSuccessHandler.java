package com.sb.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import com.sb.web.entities.User;
import com.sb.web.exception.CustomException;
import com.sb.web.repositories.UserRepository;
import com.sb.web.utils.SaversBasketConstants;


public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private UserRepository userRepository;
	
	 /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal(){
        String email = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
        	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	 
            if (principal instanceof UserDetails) {
                email = ((UserDetails)principal).getUsername();
            } else {
                email = principal.toString();
            }
        }
        
        return email;
    }
	
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
       
            try {
            	
            	/**
        		 * Retrieve logged user
        		 */
        		String email = getPrincipal();
        		if (email != null && !StringUtils.isEmpty(email)) {
        			User user = userRepository.getUserAndRoleByEmail(email);
            		
            		if (user != null) {		
            			//clear cookies
            			handleLogOutResponse(httpServletRequest, httpServletResponse);
            			
            			//update user last time logged
            			userRepository.updateLastTimeLoggedForUser(user.getIdUser());				
            		}
        			
        		}
        		
                httpServletRequest.getSession().invalidate();
          
            } catch (Exception e) {
            	
            	  throw new CustomException(
            	          "Log out failed.",
            	          "Error encountered while trying to log out.",
            	          "",
            	          "clear the browser data history to clear any remaining cookies. ",
            	          "Reach out to http://saversbasket.com/contact for more help");            	
               
            }
       
 
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        //redirect to login
        httpServletResponse.sendRedirect("/login");
    }
    
    
	/**
	 * This method would edit the cookie information and make JSESSIONID empty
	 * while responding to logout. This would further help in order to. This would help
	 * to avoid same cookie ID each time a person logs in
	 * @param response
	 */

	private void handleLogOutResponse(HttpServletRequest request, HttpServletResponse response) {
	
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {	
				if (cookie != null && cookie.getName().equals(SaversBasketConstants.JWT_TOKEN_COOKIE)) {
					cookie.setMaxAge(0);	
					String cookiePath = request.getContextPath() + "/";
		            if (!StringUtils.hasLength(cookiePath)) {
		                cookiePath = "/";
		            }
		            cookie.setPath(cookiePath);
					cookie.setValue(null);				
					response.addCookie(cookie);	
				}
			
			}
		}	
	
	}

}
