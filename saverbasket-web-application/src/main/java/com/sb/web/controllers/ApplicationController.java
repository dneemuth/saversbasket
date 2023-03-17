package com.sb.web.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Stopwatch;
import com.sb.web.configuration.CustomPropertyConfig;
import com.sb.web.entities.Account;
import com.sb.web.entities.Role;
import com.sb.web.entities.SystemSetting;
import com.sb.web.entities.User;
import com.sb.web.entities.VerificationToken;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.enumerations.DeviceType;
import com.sb.web.events.OnGrantCreditsEvent;
import com.sb.web.events.OnRegistrationCompleteEvent;
import com.sb.web.exception.CustomException;
import com.sb.web.exception.TokenRefreshException;
import com.sb.web.repositories.SystemSettingRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.request.dto.SignUpUserRequest;
import com.sb.web.response.dto.CompareBasketResponseDTO;
import com.sb.web.response.dto.ContributionResponseDTO;
import com.sb.web.response.dto.JwtAuthenticationResponse;
import com.sb.web.response.dto.SearchBasketResponseDTO;
import com.sb.web.security.JwtTokenProvider;
import com.sb.web.service.BasketService;
import com.sb.web.service.ContributionService;
import com.sb.web.service.PlanService;
import com.sb.web.service.SearchBasketService;
import com.sb.web.service.UserActionRewarderService;
import com.sb.web.service.UserService;
import com.sb.web.service.dto.ProductDTO;
import com.sb.web.service.dto.UserSignDTO;
import com.sb.web.utils.ApplicationUtils;
import com.sb.web.utils.CookieUtil;
import com.sb.web.utils.EncryptionUtil;
import com.sb.web.utils.SaversBasketConstants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class ApplicationController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	private static final String jwtTokenCookieName = "JWT-TOKEN";
	private static final String secretKey = "secretKey";

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;	

	@Autowired
	private BasketService basketService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private CustomPropertyConfig customPropertyConfig;

	@Autowired
	private SystemSettingRepository systemSettingRepository;
	
	@Autowired
	private ContributionService contributionService;
	
	@Autowired
	private SearchBasketService searchBasketService;
	
	@Autowired
	private UserActionRewarderService userActionRewarderService;
	
	@Autowired
	private PlanService planService;

	@Autowired
	BuildProperties buildProperties;	
	
	
	@GetMapping("/userSettings")
	public ModelAndView userSettings(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("userSettings");
		HttpSession session = request.getSession(true);
		SecurityContext securityContext = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		if (securityContext != null && securityContext.getAuthentication() != null) {
			String username =  ((UsernamePasswordAuthenticationToken) securityContext.getAuthentication()).getName();
			modelAndView.addObject("userLogged", username);
		}
		else
		{
			modelAndView.addObject("userLogged", getPrincipal());
		}
		
		/** user contributions */
		User user = userService.findByUserEmail(getPrincipal());
		if  (user != null) {
			Account account = user.getAccount();
			modelAndView.addObject("account", account);
		}		
		
		return addUserInformation(modelAndView, user);
	}
	

	@GetMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		
		HttpSession session = request.getSession(true);
		SecurityContext securityContext = (SecurityContext)session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		
		if (securityContext != null && securityContext.getAuthentication() != null) {
			String username =  ((UsernamePasswordAuthenticationToken) securityContext.getAuthentication()).getName();
			modelAndView.addObject("userLogged", username);
		}
		else
		{
			modelAndView.addObject("userLogged", getPrincipal());
		}
		
		/** user contributions */
		User user = userService.findByUserEmail(getPrincipal());
		if  (user != null) {
			BigDecimal userContributions = contributionService.retrieveNumberOfContributionForUser(user.getIdUser());
			if (userContributions!= null) {
				modelAndView.addObject("userContributions", userContributions);
			}
		}		
		
		return addUserInformation(modelAndView, user);
	}
	
	
	private CompareBasketResponseDTO performBasketComparisons() throws InterruptedException, ExecutionException {
		CompareBasketResponseDTO compareBasketResponseDTO = null;
		String email = getPrincipal();

		/** retrieve user info */
		User user = userService.findByUserEmail(email);

		if (user != null) {

			/** check if user can perform basket comparison */
			if (planService.canPerformCompareBasketDaily(user.getIdUser())) {

				// creates and starts a new stopwatch
				Stopwatch stopwatch = Stopwatch.createStarted();
			
				/** measured execution time of method */
				compareBasketResponseDTO = basketService.calculatePricesFromBarCodeForSpecificRetailer(user.getIdUser(),basketService.getAllBarcodesForSpecificUserBasket(user.getIdUser()));
				
				compareBasketResponseDTO.setExecuteComparison(Boolean.TRUE);
				compareBasketResponseDTO.setUserAuthenticated(Boolean.TRUE);

				stopwatch.stop();

				/** get elapsed time,expressed in milliseconds */
				long timeElapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
				compareBasketResponseDTO.setExecutionTimeElapsed(timeElapsed);

				SystemSetting systemSetting = systemSettingRepository
						.findBySystemKey(SaversBasketConstants.ENABLE_REWARD_FEATURE);
				if (systemSetting != null && systemSetting.getSystemValue().equals("1")) {
					/**
					 * Rewards
					 */
					if (compareBasketResponseDTO != null
							&& compareBasketResponseDTO.getRetailerBasketResponses() != null
							&& compareBasketResponseDTO.getRetailerBasketResponses().size() > 0) {

						ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
						contributionRequestDTO.setContributionType(ContributionType.PERFORM_PRODUCT_COMPARISON);
						contributionRequestDTO.setUserIdToBeCredited(user.getIdUser());

						ContributionResponseDTO contributionResponseDTO = userActionRewarderService
								.grantRewardForUserAction(contributionRequestDTO);
						if (contributionResponseDTO != null && contributionResponseDTO.getMonetaryReward() != null) {
							compareBasketResponseDTO.setMonetaryReward(contributionResponseDTO.getMonetaryReward());
						}
					}
				}
			} else {
				compareBasketResponseDTO = new CompareBasketResponseDTO();
				compareBasketResponseDTO.setExecuteComparison(Boolean.FALSE);
				compareBasketResponseDTO.setUserAuthenticated(Boolean.TRUE);
				compareBasketResponseDTO.setMaxBasketComparisonReached(Boolean.TRUE);
			}
		} else {
			compareBasketResponseDTO = new CompareBasketResponseDTO();
			compareBasketResponseDTO.setUserAuthenticated(Boolean.FALSE);
			compareBasketResponseDTO.setExecuteComparison(Boolean.FALSE);
		}

		return compareBasketResponseDTO;
	}
	
	@GetMapping("/listings")
	public ModelAndView listings() throws InterruptedException, ExecutionException {
		ModelAndView modelAndView = new ModelAndView("listings");		
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		modelAndView.addObject("basketComparisonResult", performBasketComparisons());
		return addUserInformation(modelAndView, user);
	}
	
	
	@GetMapping("/basket")
	public ModelAndView myBasket() {
		ModelAndView modelAndView = new ModelAndView("basket");		
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/sitemaintenance")
	public ModelAndView sitemaintenance() {
		ModelAndView modelAndView = new ModelAndView("site-maintenance");
		return modelAndView;
	}
	
	@GetMapping("/privacy")
	public ModelAndView privacypage() {
		ModelAndView modelAndView = new ModelAndView("privacy");
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/contactus")
	public ModelAndView contactus() {
		ModelAndView modelAndView = new ModelAndView("contactus");
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		modelAndView.addObject("userLogged", getPrincipal());
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/aboutus")
	public ModelAndView aboutus() {
		ModelAndView modelAndView = new ModelAndView("aboutus");
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		modelAndView.addObject("userLogged", getPrincipal());
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/newBusiness")
	public ModelAndView addNewBusiness() {
		ModelAndView modelAndView = new ModelAndView("newBusiness");
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		modelAndView.addObject("userLogged", getPrincipal());
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/newProduct")
	public ModelAndView newProduct(@RequestParam String barcode) {
		ModelAndView modelAndView = new ModelAndView("newProduct");
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		if (barcode != null && !StringUtils.isEmpty(barcode) && !barcode.equals("NA")) {
			modelAndView.addObject("barcode", barcode);
		}
		modelAndView.addObject("userLogged", getPrincipal());
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/scanProduct")
	public ModelAndView scanProduct() {
		ModelAndView modelAndView = new ModelAndView("scanProduct");
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		modelAndView.addObject("userLogged", getPrincipal());
		/** Pass Barcode IO API Key */
		SystemSetting systemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.BARCODE_API_KEY);
		if (systemSetting != null && !StringUtils.isEmpty(systemSetting.getSystemValue())) {
			modelAndView.addObject("barcodeApiKey", systemSetting.getSystemValue());
		}		
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/viewProduct")
	public ModelAndView viewProduct(@RequestParam String pid) {
		ModelAndView modelAndView = new ModelAndView("viewProduct");
		modelAndView.addObject("appVersionNumber", ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
		modelAndView.addObject("userLogged", getPrincipal());
		if (pid != null && StringUtils.isNotEmpty(pid)) {
			/** Decrypt Product key before processing */			
			if (pid!=null && !StringUtils.isEmpty(pid)) {				
				String decryptedProductKey = pid.replace("ENC", "+");
				ProductDTO productDisplay = basketService
						.retrieveProductToPopulateForm(Integer.parseInt(EncryptionUtil.decrypt(decryptedProductKey)));
				modelAndView.addObject("productDisplay", productDisplay);
			}	
		}
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@GetMapping("/error")
	public ModelAndView error(Model model) {
		ModelAndView modelAndView = new ModelAndView("error");
		return modelAndView;
	}

	@RequestMapping("/signuplink")
	public ModelAndView signuplink(Model model) {
		ModelAndView modelAndView = new ModelAndView("signup");
		return modelAndView;
	}

	@RequestMapping("/")
	public ModelAndView homepage(Model model, Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("login");
		SystemSetting maintenanceSystemSetting = systemSettingRepository
				.findBySystemKey(SaversBasketConstants.SITE_MAINTENANCE_FLAG_ON);
		if (maintenanceSystemSetting != null && !StringUtils.isEmpty(maintenanceSystemSetting.getSystemValue())
				&& maintenanceSystemSetting.getSystemValue().equals("Y")) {
			modelAndView = new ModelAndView("site-maintenance");
		} else {
			if (isAuthorized()) {
				// Add version information for display
				modelAndView.addObject("appVersionNumber",
						ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
				modelAndView.addObject("userLogged", getPrincipal());
				
				User user = userRepository.getUserAndRoleByEmail(getPrincipal());
				SearchBasketResponseDTO searchBasketResponseDTO = searchBasketService.retrieveAllBasketItemsForUser(user.getIdUser());
				modelAndView.addObject("basketItems", searchBasketResponseDTO.getProducts().size());
				
				
				modelAndView.setViewName("index");
			} else {
				modelAndView = new ModelAndView("login");
			}
		}
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	@RequestMapping("/login")
	public ModelAndView login(Model model) {
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		SystemSetting maintenanceSystemSetting = systemSettingRepository
				.findBySystemKey(SaversBasketConstants.SITE_MAINTENANCE_FLAG_ON);
		if (maintenanceSystemSetting != null && !StringUtils.isEmpty(maintenanceSystemSetting.getSystemValue())
				&& maintenanceSystemSetting.getSystemValue().equals("Y")) {
			modelAndView = new ModelAndView("site-maintenance");
		} else {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
	        	modelAndView.setViewName("login");
	        }		
		}
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return  addUserInformation(modelAndView, user);
	}

	/**
	 * Refresh the expired jwt token using a refresh token for the specific device
	 * and return a new token to the caller
	 * 
	 * @throws InvalidJwtException
	 */
	@GetMapping("/refresh")
	@ApiOperation(value = "Refresh the expired jwt authentication by issuing a token refresh request and returns the"
			+ "updated response tokens")
	public ResponseEntity<JwtAuthenticationResponse> refreshSecurityToken(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws InvalidJwtException {

		/**
		 * Decode refresh token from browser request.
		 */
		String signingKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		String jwtToken = jwtTokenProvider.getTokenFromBrowser(httpServletRequest, jwtTokenCookieName, signingKey);

		if (jwtToken.isEmpty()) {
			return null;
		}

		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setSkipSignatureVerification().build();

		JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtToken);
		String refreshToken = (String) jwtClaims.getClaimValue("refresh");

		/**
		 * Inject new jwt token in cookie session
		 */
		String email = jwtTokenProvider.getUsername(jwtToken);
		final User user = userRepository.getUserAndRoleByEmail(email);
		List<Role> roleList = userRepository.getUserAndRole(user.getIdUser()).getRoles();

		String newGeneratedToken = jwtTokenProvider.createToken(email, refreshToken, roleList);
		CookieUtil.create(httpServletResponse, jwtTokenCookieName, newGeneratedToken, false, -1,
				SaversBasketConstants.DOMAIN_COOKIE);

		return userService.refreshJwtToken(refreshToken).map(updatedToken -> {
			//String refreshToken = tokenRefreshRequest.getRefreshToken();
			return ResponseEntity.ok(
					new JwtAuthenticationResponse(updatedToken, refreshToken, jwtTokenProvider.getJwtExpirationInMs()));
		}).orElseThrow(() -> new TokenRefreshException(refreshToken,
				"Unexpected error during token refresh. Please logout and login again."));

	}

	@PostMapping("/signin")
	@ApiOperation(value = "Authenticates user and returns its JWT token")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public ModelAndView signin(HttpServletRequest request, HttpServletResponse httpServletResponse,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "referralCode", required = false) String referralCode, Model model) {
		ModelAndView modelAndView = new ModelAndView("index");
		String jwtToken = null;
		try {
			jwtToken = userService.signin(email, password);
			if (!StringUtils.isBlank(jwtToken)) {
				/**
				 * Authentification service
				 */
				Authentication authentication = getAuthentication(email);

				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(authentication);
				HttpSession session = request.getSession(true);
				session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

				CookieUtil.create(httpServletResponse, jwtTokenCookieName, jwtToken, false, -1,
						SaversBasketConstants.DOMAIN_COOKIE);
				// Add version information for display
				modelAndView.addObject("appVersionNumber",
						ApplicationUtils.generateAppVersion(buildProperties.getVersion()));
				modelAndView.addObject("userLogged", email);

			}
			else {
				
				modelAndView = new ModelAndView("login");
				modelAndView.addObject("message",
						"Please verify credentials and try again.");		
				modelAndView.addObject("isWarning", true);
				
			}
		} catch (CustomException ce) {
			modelAndView = new ModelAndView("login");
			modelAndView.addObject("message",
					"Login could not be completed. Please check your login information and try again.");		
			modelAndView.addObject("isWarning", true);
		}
		
		User user = userRepository.getUserAndRoleByEmail(getPrincipal());
		return addUserInformation(modelAndView, user);
	}

	@PostMapping("/signup")
	@ApiOperation(value = "Creates user and returns its JWT token")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public ModelAndView signup(HttpServletRequest request, HttpServletResponse httpServletResponse,
			@RequestParam(value = "username", required = true) @NotBlank String username,
			@RequestParam(value = "email", required = true) @NotBlank @Email String email,
			@RequestParam(value = "password", required = true) String password, Model model)
			throws IOException, MessagingException {
		ModelAndView modelAndView = new ModelAndView("login");
		SignUpUserRequest signUpUserRequest = new SignUpUserRequest();
		signUpUserRequest.setUsername(username);
		signUpUserRequest.setPassword(password);
		signUpUserRequest.setEmail(email);
		String tokenCreated = null;
		UserSignDTO userSignDTO = null;

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SignUpUserRequest>> violations = validator.validate(signUpUserRequest);
		if (violations.isEmpty()) {

			User userForSignUp = new User();
			userForSignUp.setUsername(username);
			userForSignUp.setPassword(password);
			userForSignUp.setEmail(email);

			User userRegistered = modelMapper.map(userForSignUp, User.class);
			userSignDTO = userService.signup(userRegistered);

			if (userSignDTO.getJwtToken() == null) {
				modelAndView.addObject("message",
						"Sign up process could not be completed. Please check your information and try again.");
				modelAndView.addObject("cssClass", "error-text");
			} else {
				/**
				 * Trigger on registration event.
				 */
				String requestPath = request.getRequestURL().toString();
				String appUrl = "";
				if (requestPath.contains("localhost")) {
					appUrl = "http://localhost:8080";
				} else {
					appUrl = customPropertyConfig.applicationUrl;
				}
				eventPublisher
						.publishEvent(new OnRegistrationCompleteEvent(userRegistered, request.getLocale(), appUrl, DeviceType.DEVICE_TYPE_DESKTOP));
			}
		}
		if (!StringUtils.isBlank(tokenCreated)) {
			modelAndView.addObject("message",
					"A confirmation email has been sent to your email address. Please confirm to complete sign-up.");
			modelAndView.addObject("cssClass", "signup-text");
		} else {

			if (!violations.isEmpty()) {
				StringBuilder builder = new StringBuilder();
				violations.forEach(violation -> builder.append(" " + violation.getMessage() + " | "));
				String errorMessage = builder.toString();

				modelAndView.addObject("message", errorMessage);
				modelAndView.addObject("cssClass", "error-text");
			} else {
				modelAndView.addObject("message", "A confirmation email has been sent to your email address. Please confirm to complete sign-up.");
				modelAndView.addObject("cssClass", "error-text");
			}
		}
		return modelAndView;
	}


	@GetMapping("/registrationConfirm")
	public ModelAndView confirmRegistration(@RequestParam String token) {

		ModelAndView modelAndView = new ModelAndView("login");

		VerificationToken verificationToken = userService.getVerificationToken(token);
		if (verificationToken == null) {
			String message = "Invalid token found !";
			modelAndView.addObject("isWarning", true);
			modelAndView.addObject("message", message);
			return modelAndView;
		}

		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			String messageValue = "Your registration token has expired. Please try again.";
			modelAndView.addObject("isWarning", true);
			modelAndView.addObject("message", messageValue);
			return modelAndView;
		}

		user.setEnabled(true);
		User updatedUser = userService.saveRegisteredUser(user);

		if (updatedUser != null) {

			/**
			 * Trigger grant credits
			 */
			eventPublisher.publishEvent(new OnGrantCreditsEvent(updatedUser, new BigDecimal(100)));
		}

		String messageValue = "Your registration token has been approved. You can now login.";
		modelAndView.addObject("isWarning", false);
		modelAndView.addObject("message", messageValue);

		return modelAndView;
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public ModelAndView logoutPage(Model model, Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("login");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			CookieUtil.clear(response, jwtTokenCookieName, SaversBasketConstants.DOMAIN_COOKIE);
			SecurityContextHolder.getContext().setAuthentication(null);
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return modelAndView;
	}

}
