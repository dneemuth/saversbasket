package com.sb.web.security.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import com.sb.web.security.MyUserDetails;
import com.sb.web.service.UserService;

@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired 
	UserService userService;
		
	@Autowired 
	MyUserDetails myUserDetails;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
		String oauth2ClientName = oauth2User.getOauth2ClientName();	
		String username = oauth2User.<String>getAttribute("name");
		String email = oauth2User.getEmail();
				
		userService.processOAuthPostLogin(email , oauth2ClientName, username);
		userService.updateAuthenticationType(email, oauth2ClientName);
		
		/**
		 * Authentication service
		 */
		SecurityContextHolder.clearContext();
		
		Authentication updatedAuthentication = getAuthentication(email);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(updatedAuthentication);		
		
		 // Create a new session and add the security context.
	    HttpSession session = request.getSession(true);
	    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
		
		//super.onAuthenticationSuccess(request, response, authentication);		
		response.sendRedirect("/index");
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public Authentication getAuthentication(String email) {
		UserDetails userDetails = myUserDetails.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
}
