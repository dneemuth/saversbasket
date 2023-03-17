package com.sb.web.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sb.web.utils.CookieUtil;
import com.sb.web.utils.SaversBasketConstants;

@Component
public class CustomLogoutHandler  extends SecurityContextLogoutHandler {		
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) {
		
		/* Getting session and then invalidating it */

		HttpSession session = request.getSession(false);
		if (request.isRequestedSessionIdValid() && session != null) {
			session.invalidate();
		}

		handleLogOutResponse(request, response);
		
		CookieUtil.eraseCookie(request, response, SaversBasketConstants.DOMAIN_COOKIE);
    	SecurityContextHolder.getContext().setAuthentication(null);
        new SecurityContextLogoutHandler().logout(request, response, auth);
     
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
				if (cookie != null && (cookie.getName().equals(SaversBasketConstants.JWT_TOKEN_COOKIE) || cookie.getName().equals("JSESSIONID"))) {
					cookie.setMaxAge(0);	
					String cookiePath = request.getContextPath() + "/";
		            if (!StringUtils.hasLength(cookiePath)) {
		                cookiePath = "/";
		            }
					cookie.setValue(null);				
					response.addCookie(cookie);	
				}
			
			}
		}
	
	}
	
	
}
