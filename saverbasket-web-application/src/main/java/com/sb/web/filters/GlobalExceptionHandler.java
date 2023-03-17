package com.sb.web.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sb.web.email.EmailService;
import com.sb.web.exception.CustomException;
import com.sb.web.utils.SaversBasketConstants;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static final String ERROR_EMAIL_ADDRESS = SaversBasketConstants.SBASKET_SUPPORT_EMAIL;
	private static final String APPLICATION_ERROR_SUBJECT = "SVB Error Occurred";
	private static final String USER_AGENT = "user-agent";

	@Autowired
	private EmailService emailService;

	/**
	 * Handles multiple exceptions and mail stack trace to admins for enquiry.
	 * 
	 * @param request
	 * @param response
	 * @param principal
	 * @param e
	 * @return
	 */
	@ExceptionHandler({ NullPointerException.class, CustomException.class, Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView defaultErrorHandler(final HttpServletRequest request, final HttpServletResponse response,
			final Principal principal, final Exception e) {
		ModelAndView modelAndView = new ModelAndView("error");

		String userTime = "";
		if (principal != null && !StringUtils.isEmpty(principal.getName())) {
			userTime = principal.getName() + " triggered an error at " + new Date();
		}
		final String userAgent = "User-Agent: " + StringUtils.trimToEmpty(request.getHeader(USER_AGENT));
		final String url = "URL: " + StringUtils.trimToEmpty(request.getRequestURL().toString());
		final String httpMethod = "HTTP method: " + request.getMethod();

		final StringBuilder emailSb = new StringBuilder();
		emailSb.append(userTime).append("\n");
		emailSb.append(userAgent).append("\n");
		emailSb.append(url).append("\n");
		emailSb.append(httpMethod).append("\n");

		if (e instanceof ClientAbortException) {
			logger.debug("Not sending email for socketExceptions");
		} else {
			// CookieUtil.eraseCookie(request, response,
			// SaversBasketConstants.DOMAIN_COOKIE);
			emailSb.append(ExceptionUtils.getStackTrace(e));
			try {
				emailService.sendSimpleMessage(ERROR_EMAIL_ADDRESS, APPLICATION_ERROR_SUBJECT, emailSb.toString());
			} catch (IOException e1) {
				LOG.warn("Error occured while emailing exception.", e1);
			} catch (MessagingException e1) {
				LOG.warn("Error occured while emailing exception.", e1);
			}
		}

		return modelAndView;
	}

}
