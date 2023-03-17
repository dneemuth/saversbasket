package com.sb.web.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.annotations.Auditable;
import com.sb.web.annotations.Auditable.AuditingActionType;
import com.sb.web.controllers.AbstractController;
import com.sb.web.entities.Account;
import com.sb.web.entities.Basket;
import com.sb.web.entities.DeviceProfile;
import com.sb.web.entities.Plan;
import com.sb.web.entities.RefreshToken;
import com.sb.web.entities.Role;
import com.sb.web.entities.Subscription;
import com.sb.web.entities.User;
import com.sb.web.entities.VerificationToken;
import com.sb.web.enumerations.AccountType;
import com.sb.web.enumerations.AuthenticationType;
import com.sb.web.enumerations.DeviceType;
import com.sb.web.exception.CustomException;
import com.sb.web.exception.TokenRefreshException;
import com.sb.web.repositories.PlanRepository;
import com.sb.web.repositories.RoleRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.repositories.VerificationTokenRepository;
import com.sb.web.request.dto.LoginRequest;
import com.sb.web.request.dto.TokenRefreshRequest;
import com.sb.web.security.JwtTokenProvider;
import com.sb.web.service.dto.UserSignDTO;
import com.sb.web.utils.ApplicationUtils;
import com.sb.web.utils.SaversBasketConstants;

@Transactional
@Service
public class UserService {
	
  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);	

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;
  
  @Autowired
  private RoleRepository roleRepository;
  
  @Autowired
  private PlanRepository planRepository;
  
  @Autowired
  private VerificationTokenRepository tokenRepository;
  
  
  @Autowired
  private DeviceProfileService deviceProfileService;
  
  @Autowired
  private RefreshTokenService refreshTokenService;
  
  @Transactional  
  public VerificationToken getVerificationToken(final String VerificationToken) {
      return tokenRepository.findByToken(VerificationToken);
  }

  @Transactional  
  public User saveRegisteredUser(final User user) {
      User userUpdated = userRepository.save(user);      
      return userUpdated;
  }
  
  @Transactional  
  public void updateAuthenticationType(String username, String oauth2ClientName) {
  	AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
  	userRepository.updateAuthenticationType(username, authType);
  	System.out.println("Updated user's authentication type to " + authType);
  }
  
  /**
   * Creates and persists the refresh token for the user device. If device exists
   * already, we don't care. Unused devices with expired tokens should be cleaned
   * with a cron job. The generated token would be encapsulated within the jwt.
   * Remove the existing refresh token as the old one should not remain valid.
   */
  @Transactional  
  public Optional<RefreshToken> createAndPersistRefreshTokenForDevice(Authentication authentication, LoginRequest loginRequest) {
     
	  org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
	  User userLogged = userRepository.findByEmail(currentUser.getUsername());
	  
      deviceProfileService.findByUserId(userLogged.getIdUser())
              .map(DeviceProfile::getRefreshToken)
              .map(RefreshToken::getIdToken)
              .ifPresent(refreshTokenService::deleteByIdToken);
      
      /**
       * Create device
       */ 
     
      DeviceProfile deviceProfile = null;
      if (userLogged != null && userLogged.getIdDeviceProfile() != null) {
    	  deviceProfile = userLogged.getIdDeviceProfile();
      }
      else
      {
    	  deviceProfile = deviceProfileService.createUserDevice(loginRequest.getDeviceProfile());
      }
      
      RefreshToken refreshToken = refreshTokenService.createRefreshToken();
     
      deviceProfile.setUser(userLogged);
      deviceProfile.setRefreshToken(refreshToken);
      deviceProfile.setDeviceId(generateRandomUuid());
      refreshToken.setDeviceProfile(deviceProfile);
      refreshToken = refreshTokenService.save(refreshToken);      
      
      return Optional.ofNullable(refreshToken);
  }
  
  private static String generateRandomUuid() {
      return UUID.randomUUID().toString();
  }
  
  
  /**
   * Generates a JWT token for the validated client by userId
   */
  private String generateTokenFromUserEmail(String email) {
      return jwtTokenProvider.generateTokenFromUserEmail(email);
  }
  
  /**
   * Refresh the expired jwt token using a refresh token and device info. The
   * * refresh token is mapped to a specific device and if it is unexpired, can help
   * * generate a new jwt. If the refresh token is inactive for a device or it is expired,
   * * throw appropriate errors.
   */
  public Optional<String> refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
      String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

      return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
              .map(refreshToken -> {
                  refreshTokenService.verifyExpiration(refreshToken);
                  deviceProfileService.verifyRefreshAvailability(refreshToken);
                  refreshTokenService.increaseCount(refreshToken);
                  return refreshToken;
              })
              .map(RefreshToken::getDeviceProfile)
              .map(DeviceProfile::getUser)
              .map(User::getEmail).map(this::generateTokenFromUserEmail))
              .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in database.Please login again"));
  }
  
  /**
   * Refresh the expired jwt token using a refresh token and device info. The
   * * refresh token is mapped to a specific device and if it is unexpired, can help
   * * generate a new jwt. If the refresh token is inactive for a device or it is expired,
   * * throw appropriate errors.
   */
  public Optional<String> refreshJwtToken(String requestRefreshToken) {

      return Optional.of(refreshTokenService.findByToken(requestRefreshToken)
              .map(refreshToken -> {
                  refreshTokenService.verifyExpiration(refreshToken);
                  deviceProfileService.verifyRefreshAvailability(refreshToken);
                  refreshTokenService.increaseCount(refreshToken);
                  return refreshToken;
              })
              .map(RefreshToken::getDeviceProfile)
              .map(DeviceProfile::getUser)
              .map(User::getEmail).map(this::generateTokenFromUserEmail))
              .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Missing refresh token in database.Please login again"));
  }
    
  
  @Transactional  
  public final Account createAndInitializeSubscriptionPlan(Account account, String email) {
  	
	  	List<Subscription> subscriptions = new ArrayList<Subscription>();    	    	
	  	
	  	/**
	  	 * Free Subscription
	  	 */
	  	Subscription freeSubscription = new Subscription();
	  	freeSubscription.setDateSubscriptionStart(new Date());  	
	  	
	  	//retrieve free plan
	  	Plan freePlan = planRepository.retrieveAccountForUser("Dodo Free Plan");
	  	freeSubscription.setPlan(freePlan);
	  	
	  	subscriptions.add(freeSubscription);
	  	freePlan.setSubscriptions(subscriptions);	    	
	  	
	  	account.setSubscriptions(subscriptions);  	
	  	return account;
  	
  }
  
  
  /**
   * This method allow users to sign in application.
   * 
   * @param email
   * @param password
   * @return
   */
  @Auditable(actionType = AuditingActionType.INTERNAL_USER_SIGN_IN)
  public String signin(String email, String password) {
	String token = null;
 
	try {    
    	
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      
	  /**
	   * Generate token
	   */
      if (authentication != null) {    	  
    	  
    	  /** create device info */
	       LoginRequest loginRequest = new LoginRequest();
	       loginRequest.setUsername(email);
	       loginRequest.setPassword(password);
	       
	       DeviceProfile deviceProfile = new DeviceProfile();
	       deviceProfile.setDeviceType(DeviceType.DEVICE_TYPE_DESKTOP);	      
	       deviceProfile.setRefreshActive(Boolean.TRUE);
	       
	       loginRequest.setDeviceProfile(deviceProfile);  		    		       
	       Optional<RefreshToken> refreshTokenObject =  createAndPersistRefreshTokenForDevice(authentication, loginRequest); 	       
	       RefreshToken refreshToken = refreshTokenObject.get();
    	  
    	   token =  jwtTokenProvider.createToken(email, refreshToken.getToken(), userRepository.getUserAndRoleByEmail(email).getRoles());  
      } 
      
    } catch (AuthenticationException authEx) {
    	LOG.error("Bad Credentials provided by Sign-In");

    }
	return token;
  }
  

  @Transactional(readOnly = false,noRollbackFor= Exception.class, propagation = Propagation.REQUIRED)	
  @Modifying
  @Auditable(actionType = AuditingActionType.INTERNAL_USER_SIGN_UP)
  public UserSignDTO signup(User user) {
    
	  UserSignDTO userSignDTO = new UserSignDTO();
	 
	  try {
		  
		  if (!userRepository.existsByEmail(user.getEmail())) {
		      user.setPassword(passwordEncoder.encode(user.getPassword()));
		      
		      List<Role> roles = new ArrayList<Role>();
		      Role freeUserPlan = roleRepository.findByRole("ROLE_FREE_USER_PLAN");
		      roles.add(freeUserPlan);
		      
		      Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Indian/Mauritius"));
		      Date createUserDate = calendar.getTime();
		      
		      /**
		       * Set to default client role
		       */
		      user.setRoles(roles);
		      
		      /**
		       * Allocate 1 default basket
		       */		      
		      List<Basket> baskets = new ArrayList<Basket>();
		      Basket basket = new Basket();
		      basket.setBasketName("Default basket");
		      basket.setUser(user);
		      		      
		      basket.setDateBasketRegistered(createUserDate);
		      basket.setActive(Boolean.TRUE);
		      
		      baskets.add(basket);      
		      user.setBaskets(baskets);     
		      
		      /**
		       * Set default account
		       */
		      Account defautAccount = new Account("Default Account - " + user.getEmail());      
		      defautAccount.setDateAccountCreated(new Date());
		      defautAccount.setAccountType(AccountType.USER);		      
		     	     
		      user.setEnabled(Boolean.FALSE);
		      user.setDateUserRegistered(new Date());
              user.setLanguage("EN");
                          
              /**
		       * Link to free plan account
		       */              
		      Account accountUpdated = createAndInitializeSubscriptionPlan(defautAccount, user.getEmail());
		      user.setAccount(accountUpdated);	
		      
		      
		      /**
		       * Device profile
		       */
		      DeviceProfile deviceProfile = createAndInitializeDeviceProfile(user.getEmail());
		      deviceProfile.setUser(user);	        
		      user.setIdDeviceProfile(deviceProfile);    
		      
		      /** Authentication Type */
		      user.setAuthType(AuthenticationType.DATABASE);
		      
              /** Create User */
		      User savedUser = userRepository.save(user);
		      
		      if (savedUser != null) {		    
			      /**  Generate JWT Token */
			      String jwtToken = jwtTokenProvider.createToken(user.getUsername(), null, user.getRoles());
			      userSignDTO.setJwtToken(jwtToken);
		      }		      
		  }
		  else {
			  userSignDTO.setErrorMessage("User already exists, please choose another email.");
		  }
		  
	  } catch (Exception e) {
		  System.out.println(e.getMessage());
	  }      
      return userSignDTO; 
  }
  
  @Transactional
  public final DeviceProfile createAndInitializeDeviceProfile(String email) {
	          
  	  DeviceProfile deviceProfileCreated = new DeviceProfile();
  	  deviceProfileCreated.setDeviceId(generateRandomUuid());
  	  deviceProfileCreated.setDeviceType(DeviceType.DEVICE_TYPE_DESKTOP);
  	  deviceProfileCreated.setRefreshActive(Boolean.TRUE);
  	  deviceProfileCreated.setNotificationToken("");   
      
        return deviceProfileCreated;
  }

  public void delete(String email) {
    userRepository.deleteByUsername(email);
  }
  
  public User findByUsername(String username) {
	  User user = userRepository.findByUsername(username);
	  return user;
  } 
  
  @Transactional(readOnly = true,noRollbackFor= Exception.class, propagation = Propagation.REQUIRED)	 
  public User findByUserEmail(String email) {
	  User user = userRepository.getUserAndRoleByEmail(email);
	  return user;
  } 
  
  /**
   * 
   * @param username
   * @param oauth2ClientName
   */
  public void processOAuthPostLogin(String email, String oauth2ClientName, String username) {
      User existUser = userRepository.getUserAndRoleByEmail(email);       
      if (existUser == null) {
          User newUser = new User();
          //newUser.setUsername(ApplicationUtils.getStrippedEmailName(email));
          newUser.setUsername(username);
          newUser.setEmail(email);
          /** Check if user password is required for OAuth2 */
          newUser.setPassword(passwordEncoder.encode(SaversBasketConstants.SBASKET_DEFAULT_PASSWORD));
          
          /** Authentication Type */
          AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
          newUser.setAuthType(authType);
                   
          List<Role> roles = new ArrayList<Role>();
	      Role freeUserPlan = roleRepository.findByRole("ROLE_FREE_USER_PLAN");
	      roles.add(freeUserPlan);
	      
	      Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Indian/Mauritius"));
	      Date createUserDate = calendar.getTime();
	      
	      /** Set to default client role */
	      newUser.setRoles(roles);
	      /** Allocate 1 default basket */	      
	      List<Basket> baskets = new ArrayList<Basket>();
	      Basket basket = new Basket();
	      basket.setBasketName("Default basket");
	      basket.setUser(newUser);
	      		      
	      basket.setDateBasketRegistered(createUserDate);
	      basket.setActive(Boolean.TRUE);
	      
	      baskets.add(basket);      
	      newUser.setBaskets(baskets);     
	      
	      /** Set default account */
	      Account defautAccount = new Account("Default Account - " + newUser.getEmail());      
	      defautAccount.setDateAccountCreated(new Date());
	      defautAccount.setAccountType(AccountType.USER);		      
	     	     
	      newUser.setEnabled(Boolean.TRUE);
	      newUser.setDateUserRegistered(new Date());
	      newUser.setLanguage("EN");
                      
          /**  Link to free plan account */              
	      Account accountUpdated = createAndInitializeSubscriptionPlan(defautAccount, newUser.getEmail());
	      newUser.setAccount(accountUpdated);	
	      	      
	      /**  Device profile */
	      DeviceProfile deviceProfile = createAndInitializeDeviceProfile(newUser.getEmail());
	      deviceProfile.setUser(newUser);	        
	      newUser.setIdDeviceProfile(deviceProfile); 
           
          userRepository.save(newUser);        
      }       
  }

  public User whoami(HttpServletRequest req) {
    return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles()); 
  }
  
  public List<User> getAllValidUsers() {
	  List<User> users = userRepository.findAll();
	  return users;
  }
  
  public User getUserById(Integer userId) {
	 return  userRepository.getOne(userId);
  }

}
