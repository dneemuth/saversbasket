package com.sb.web.controllers;


import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sb.web.entities.User;
import com.sb.web.response.dto.SearchBasketResponseDTO;
import com.sb.web.security.MyUserDetails;
import com.sb.web.service.ContributionService;
import com.sb.web.service.SearchBasketService;
import com.sb.web.utils.ApplicationUtils;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Transactional
public abstract class AbstractController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractController.class);	
	
	@Autowired
	private ContributionService contributionService;
	
	@Autowired
	private SearchBasketService searchBasketService;
	
	@Autowired
	private MyUserDetails myUserDetails;
	
	@Autowired
	BuildProperties buildProperties;
	
	@GetMapping(value = "/secure")
	public ResponseEntity secure() {
		LOG.info("Received request: /api/v1/secure");
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/nonsecure")
	public ResponseEntity nonsecure() {
		LOG.info("Received request: /api/v1/nonsecure");
		return ResponseEntity.ok().build();
	}
	
	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	protected String getPrincipal() {
		String email = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			email = ((UserDetails) principal).getUsername();
		} else {
			email = principal.toString();
		}
		return email;
	}
	
	// permitAll
	@RequestMapping(method = RequestMethod.GET, value = "/isAuthorized")
	public Boolean isAuthorized() {
		String email = getPrincipal();
		return email != null && email.equals("anonymousUser") ? false : true;
	}
	
	public Authentication getAuthentication(String email) {
		UserDetails userDetails = myUserDetails.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	/**
	 *  Adds additional information to ModelView object
	 * 
	 * @param modelAndView
	 * @param user
	 * @return
	 */
	protected ModelAndView addUserInformation(ModelAndView modelAndView, User user) {
		if (user != null) {
			SearchBasketResponseDTO searchBasketResponseDTO = searchBasketService.retrieveAllBasketItemsForUser(user.getIdUser());
			modelAndView.addObject("searchBasketResponseDTO", searchBasketResponseDTO);
			modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
			if (!StringUtils.isEmpty(user.getEmail())) {
				modelAndView.addObject("userLogged", user.getEmail());
			}			
			modelAndView.addObject("basketItems", searchBasketResponseDTO.getProducts().size());
			BigDecimal userContributions = contributionService.retrieveNumberOfContributionForUser(user.getIdUser());
			if (userContributions!= null) {
				modelAndView.addObject("userContributions", userContributions);
			}			
		}		
		return modelAndView;
	}
}
