package com.sb.web.security;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sb.web.exception.CustomException;
import com.sb.web.utils.SaversBasketConstants;

import io.jsonwebtoken.ExpiredJwtException;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Value("${security.jwt.token.secret-key}")
	private String secretKey = "secretKey";

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		String signingKey = Base64.getEncoder().encodeToString(secretKey.getBytes());

		try {

			String username = jwtTokenProvider.getSubject(httpServletRequest, SaversBasketConstants.JWT_TOKEN_COOKIE,
					signingKey);
			if (username != null) {

				UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) jwtTokenProvider
						.getAuthenticationByUsername(username);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				logger.info("authenticated user " + username + ", setting security context");

				SecurityContextHolder.getContext().setAuthentication(authentication);

			}
		} catch (ExpiredJwtException expiredJwtEx) {
			//logger.error("Unauthorized error: {}", expiredJwtEx.getMessage());
			//httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
			return;
		} catch (CustomException ex) {
			// this is very important, since it guarantees the user is not authenticated at
			// all
			SecurityContextHolder.clearContext();
			// httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
			return;
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
