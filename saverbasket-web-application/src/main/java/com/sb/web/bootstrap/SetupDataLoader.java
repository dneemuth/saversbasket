package com.sb.web.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sb.web.entities.Account;
import com.sb.web.entities.Country;
import com.sb.web.entities.Credit;
import com.sb.web.entities.DeviceProfile;
import com.sb.web.entities.Plan;
import com.sb.web.entities.Privilege;
import com.sb.web.entities.ProductType;
import com.sb.web.entities.Role;
import com.sb.web.entities.Subscription;
import com.sb.web.entities.SystemSetting;
import com.sb.web.entities.User;
import com.sb.web.enumerations.AccountType;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.enumerations.CreditType;
import com.sb.web.enumerations.DeviceType;
import com.sb.web.enumerations.PlanType;
import com.sb.web.repositories.AccountRepository;
import com.sb.web.repositories.CountryRepository;
import com.sb.web.repositories.CreditRepository;
import com.sb.web.repositories.PlanRepository;
import com.sb.web.repositories.PrivilegeRepository;
import com.sb.web.repositories.ProductTypeRepository;
import com.sb.web.repositories.RoleRepository;
import com.sb.web.repositories.SubscriptionRepository;
import com.sb.web.repositories.SystemSettingRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.response.dto.ContributionResponseDTO;
import com.sb.web.service.UserActionRewarderService;
import com.sb.web.utils.SaversBasketConstants;

@Component
public class SetupDataLoader  implements ApplicationListener<ContextRefreshedEvent> {	
	  
	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private RoleRepository roleRepository;
	    
	    @Autowired
	    private CreditRepository creditRepository;

	    @Autowired
	    private PrivilegeRepository privilegeRepository;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @Autowired
	    private ProductTypeRepository productTypeRepository;
	    
	    @Autowired
	    private PlanRepository planRepository;
	    
	    @Autowired
	    private CountryRepository countryRepository;
	    
	    @Autowired
	    private AccountRepository accountRepository;
	    
	    @Autowired
	    private SubscriptionRepository subscriptionRepository;	   
	    
	    @Autowired
	    private SystemSettingRepository systemSettingRepository;	
	    
	    @Autowired
		private UserActionRewarderService userActionRewarderService;	
	 
	    // API

	    @Override
	    @Transactional
	    public void onApplicationEvent(final ContextRefreshedEvent event) {
	    	
	    	SystemSetting systemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.ADMIN_INITIALIZE_FLAG);
	        if (systemSetting != null && systemSetting.getSystemValue().equalsIgnoreCase("1")) {
	            return;
	        } else {
	        	initializeSystemVariables();
	        	initializeAdminInformation(); 
	        	createAndInitializeCountries();
	        }
	    }	    
	    
	    @Transactional
	    public void initializeSystemVariables() {
	    	
	    	List<SystemSetting> systemSettings = new ArrayList<SystemSetting>();
	    	
	    	SystemSetting adminSystemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.ADMIN_INITIALIZE_FLAG);
	    	if (adminSystemSetting == null) {
	    		SystemSetting systemSettingAdminFlag = new SystemSetting();
		    	systemSettingAdminFlag.setIdSystemSetting(1);
		    	systemSettingAdminFlag.setSystemKey("ADMIN_INITIALIZE_FLAG");
		    	systemSettingAdminFlag.setSystemValue("1");
		    	systemSettings.add(systemSettingAdminFlag);
	    	}	    	
 	        
	    	SystemSetting lotterySystemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.LOTTERY_DRAW_PARTICIPATION_LIMIT);
	    	if (lotterySystemSetting == null) {
	    		SystemSetting systemSettingLotteryDraw = new SystemSetting();
		    	systemSettingLotteryDraw.setIdSystemSetting(2);
		    	systemSettingLotteryDraw.setSystemKey("LOTTERY_DRAW_PARTICIPATION_LIMIT");
		    	systemSettingLotteryDraw.setSystemValue("-100");
		    	systemSettings.add(systemSettingLotteryDraw);
	    	}
	    	
	    	SystemSetting runSequenceSystemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.RUN_INITIAL_SEQUENCE_LOAD);
	    	if (runSequenceSystemSetting == null) {
	    		SystemSetting systemSettingSequenceLoad = new SystemSetting();
		    	systemSettingSequenceLoad.setIdSystemSetting(3);
		    	systemSettingSequenceLoad.setSystemKey("RUN_INITIAL_SEQUENCE_LOAD");
		    	systemSettingSequenceLoad.setSystemValue("1");
		    	systemSettings.add(systemSettingSequenceLoad);
	    	}	    
	    	
	    	SystemSetting rewardSystemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.ENABLE_REWARD_FEATURE);
	    	if (rewardSystemSetting == null) {
	    		SystemSetting systemSettingReward = new SystemSetting();
	    		systemSettingReward.setIdSystemSetting(4);
	    		systemSettingReward.setSystemKey("ENABLE_REWARD_FEATURE");
	    		systemSettingReward.setSystemValue("0");
	    		systemSettingReward.setDateSettingCreated(new Date());
	    		systemSettingReward.setSettingByUser("admin");
		    	systemSettingRepository.save(systemSettingReward);
	    	}
	    	
	    	SystemSetting siteMaintenanceSystemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.SITE_MAINTENANCE_FLAG_ON);
	    	if (siteMaintenanceSystemSetting == null) {
	    		SystemSetting maintenanceSystemSetting = new SystemSetting();
	    		maintenanceSystemSetting.setIdSystemSetting(5);
	    		maintenanceSystemSetting.setSystemKey("SITE_MAINTENANCE_FLAG_ON");
	    		maintenanceSystemSetting.setSystemValue("N");
	    		maintenanceSystemSetting.setDateSettingCreated(new Date());
	    		maintenanceSystemSetting.setSettingByUser("admin");
		    	systemSettingRepository.save(maintenanceSystemSetting);
	    	}
	    	
	    	SystemSetting barcodeAPISystemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.BARCODE_API_KEY);
	    	if (barcodeAPISystemSetting == null) {
	    		SystemSetting bcodeAPISystemSetting = new SystemSetting();
	    		bcodeAPISystemSetting.setIdSystemSetting(6);
	    		bcodeAPISystemSetting.setSystemKey("BARCODE_API_KEY");
	    		bcodeAPISystemSetting.setSystemValue("sry6r3XPkTtPwgMTNS7DDB4MxCl23F5F");
	    		bcodeAPISystemSetting.setDateSettingCreated(new Date());
	    		bcodeAPISystemSetting.setSettingByUser("admin");
		    	systemSettingRepository.save(bcodeAPISystemSetting);
	    	}
 	        
 	        systemSettingRepository.saveAll(systemSettings);	    	
	    }
	    
	    @Transactional
	    public void initializeAdminInformation() {
	        // == create initial privileges
	        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
	        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
	        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

	        // == create initial roles
	        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
	        final List<Privilege> userPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
	        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
	        createRoleIfNotFound("ROLE_FREE_USER_PLAN", userPrivileges);
	        
	        /**
	         * create plan
	         */
	        createAndInitializeFreePlan();

	        // == create initial user
	        createUserIfNotFound("info.saversbasket", "password","info.saversbasket@gmail.com", new ArrayList<Role>(Arrays.asList(adminRole)));
	        createUserIfNotFound("admin", "merveilles","dil.neemuth@gmail.com", new ArrayList<Role>(Arrays.asList(adminRole)));
	          
	        
	        // == create Credits
	        createAndInitializeCredits();
	        
	        // ==add product types
	        createAndInitializeProductTypes();
	        
	        //link to Plan & Subscription
	        //subscriptionRepository.deleteAll();
	        createAndInitializeSubscriptionPlan("info.saversbasket@gmail.com");
	        createAndInitializeSubscriptionPlan("dil.neemuth@gmail.com");
	        
	        //grant credits
	        /**
	        grantBeneficiaryCreditsForAdmin("info.saversbasket@gmail.com");
	        grantBeneficiaryCreditsForAdmin("dil.neemuth@gmail.com");
	        */
	    }
	    
	    
	    @Transactional
	    private final ContributionResponseDTO grantBeneficiaryCreditsForAdmin(final String email) {
	    	
	    	ContributionResponseDTO contributionResponseDTO = null;
	        User user = userRepository.findByEmail(email);
	        if (user != null) {
	        	
	        	ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
			    contributionRequestDTO.setContributionType(ContributionType.GRANT_BENEFICIARY_CREDITS);
			    contributionRequestDTO.setUserIdToBeCredited(user.getIdUser());
			  
				contributionResponseDTO = userActionRewarderService.grantRewardForUserAction(contributionRequestDTO);
	        }
	        
	        return contributionResponseDTO;	      
	    }
	    	   

	    @Transactional
	    private final Privilege createPrivilegeIfNotFound(final String name) {
	        Privilege privilege = privilegeRepository.findByName(name);
	        if (privilege == null) {
	            privilege = new Privilege(name);
	            privilege = privilegeRepository.save(privilege);
	        }
	        return privilege;
	    }

	    @Transactional
	    private final Role createRoleIfNotFound(final String name, final List<Privilege> privileges) {
	        Role role = roleRepository.findByRole(name);
	        if (role == null) {
	            role = new Role(name);
	        }
	        role.setPrivileges(privileges);
	        role = roleRepository.save(role);
	        return role;
	    }
	    
	    @Transactional
	    public final Plan createAndInitializeFreePlan(){	    	
	    	
	    	Plan freePlan = planRepository.retrieveAccountForUser("Dodo Free Plan");	    	
	    	/**
	    	 * Free Plan
	    	 */
	    	if (freePlan == null) {	    	
		    	freePlan = new Plan();
		    	
		    	freePlan.setNumberOfBasketsAllowed(1);
		    	freePlan.setNumberOfCreditsAllowed(-1);
		    	freePlan.setNumberOfItemsPerBasketAllowed(-1);
		    	freePlan.setNumberOfDailyScansAllowed(-1);
		    	freePlan.setNumberOfBasketComparisonsAllowed(100);
		    	
		    	freePlan.setPlanDescription("Dodo Free Plan - No limit on credits and basket.");
		    	freePlan.setPlanName("Dodo Free Plan");
		    	freePlan.setPlanType(PlanType.DODO);
		    	
		    	planRepository.save(freePlan);
	    	}
	    	
	    	return freePlan;
	    }
	    
	    public static String generateRandomUuid() {
	        return UUID.randomUUID().toString();
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
	    
	    
	    @Transactional
	    public final void createAndInitializeSubscriptionPlan(String email) {
	    	
	    	
	    	List<Subscription> subscriptions = subscriptionRepository.retrieveSubscriptionsForUser(email) ;  	
	    	
	    	if (subscriptions != null && subscriptions.size() > 0) {
	    		return;
	    	}
	    	else
	    	{
	    		//create new subscriptions.
	    		subscriptions = new ArrayList<>();
	    		
	    		/**
		    	 * Free Subscription
		    	 */
		    	Subscription freeSubscription = new Subscription();
		    	freeSubscription.setDateSubscriptionStart(new Date());		    	
		    	
		    	//set free plan	    
		    	Plan freePlan = planRepository.retrieveAccountForUser("Dodo Free Plan");
		    	freeSubscription.setPlan(freePlan);
		    	
		    	subscriptions.add(freeSubscription);
		    	freePlan.setSubscriptions(subscriptions);	    	
		    	
		    	Account accountUpdate = accountRepository.retrieveAccountForUser(email);
		    	accountUpdate.setSubscriptions(subscriptions);
		    	
		    	freeSubscription.setAccount(accountUpdate);		    	
		    	accountRepository.save(accountUpdate);
	    		
	    	}	    	
	    }
	   
	    
	    @Transactional
	    public final void createAndInitializeProductTypes() {	    	
	    	    	
	    	List<ProductType> productTypes = new ArrayList<ProductType>();
	    	
	    	//None
	    	if (productTypeRepository.findProductTypeByCode("0") == null) {
		    	ProductType productType1 = new ProductType();
		    	productType1.setName("None");
		    	productType1.setCode("0");
		    	productTypes.add(productType1);
	    	}
	    	
	    	//Fruits
	    	if (productTypeRepository.findProductTypeByCode("1") == null) {
		    	ProductType productType2 = new ProductType();
		    	productType2.setName("Fruits");
		    	productType2.setCode("1");
		    	productTypes.add(productType2);
	    	}
	    	
	    	//Vegetables
	    	if (productTypeRepository.findProductTypeByCode("2") == null) {
		    	ProductType productType3 = new ProductType();
		    	productType3.setName("Vegetables");
		    	productType3.setCode("2");
		    	productTypes.add(productType3);
	    	}
	    	
	    	//Breakfast
	    	if (productTypeRepository.findProductTypeByCode("3") == null) {
		    	ProductType productType4 = new ProductType();
		    	productType4.setName("Breakfast");
		    	productType4.setCode("3");
		    	productTypes.add(productType4);
	    	}
	    	
	    	
	    	//Meat
	    	if (productTypeRepository.findProductTypeByCode("4") == null) {
		    	ProductType productType5 = new ProductType();
		    	productType5.setName("Meat");
		    	productType5.setCode("4");
		    	productTypes.add(productType5);
	    	}
	    	
	    	//seafood
	    	if (productTypeRepository.findProductTypeByCode("5") == null) {
		    	ProductType productType6 = new ProductType();
		    	productType6.setName("Seafood");
		    	productType6.setCode("5");
		    	productTypes.add(productType6);
	    	}
	    	
	    	//Frozen
	    	if (productTypeRepository.findProductTypeByCode("6") == null) {
		    	ProductType productType7 = new ProductType();
		    	productType7.setName("Frozen");
		    	productType7.setCode("6");
		    	productTypes.add(productType7);
	    	}
	    	
	    	//Canned Food
	    	if (productTypeRepository.findProductTypeByCode("7") == null) {
		    	ProductType productType8 = new ProductType();
		    	productType8.setName("Canned Food");
		    	productType8.setCode("7");
		    	productTypes.add(productType8);
	    	}
	    	
	    	
	    	//Baking
	    	if (productTypeRepository.findProductTypeByCode("8") == null) {
		    	ProductType productType9 = new ProductType();
		    	productType9.setName("Baking");
		    	productType9.setCode("8");
		    	productTypes.add(productType9);
	    	}
	    	
	    	//Snacks
	    	if (productTypeRepository.findProductTypeByCode("9") == null) {
		    	ProductType productType10 = new ProductType();
		    	productType10.setName("Snacks");
		    	productType10.setCode("9");
		    	productTypes.add(productType10);
	    	}
	    	
	    	//Bakery
	    	if (productTypeRepository.findProductTypeByCode("10") == null) {
		    	ProductType productType11 = new ProductType();
		    	productType11.setName("Bakery");
		    	productType11.setCode("10");
		    	productTypes.add(productType11);
	    	}
	    	
	    	//Pasta & Rice
	    	if (productTypeRepository.findProductTypeByCode("11") == null) {
		    	ProductType productType12 = new ProductType();
		    	productType12.setName("Pasta & Rice");
		    	productType12.setCode("11");
		    	productTypes.add(productType12);
	    	}
	    	
	    	//Cans & Jars
	    	if (productTypeRepository.findProductTypeByCode("12") == null) {
		    	ProductType productType13 = new ProductType();
		    	productType13.setName("Cans & Jars");
		    	productType13.setCode("12");
		    	productTypes.add(productType13);
	    	}
	    	
	    	//Refrigerated
	    	if (productTypeRepository.findProductTypeByCode("13") == null) {
		    	ProductType productType14 = new ProductType();
		    	productType14.setName("Refrigerated");
		    	productType14.setCode("13");
		    	productTypes.add(productType14);
	    	}
	    	
	    	//Seasoning
	    	if (productTypeRepository.findProductTypeByCode("14") == null) {
		    	ProductType productType15 = new ProductType();
		    	productType15.setName("Seasoning");
		    	productType15.setCode("14");
		    	productTypes.add(productType15);
	    	}
	    	
	    	//Sauces & Condiment
	    	if (productTypeRepository.findProductTypeByCode("15") == null) {
		    	ProductType productType16 = new ProductType();
		    	productType16.setName("Sauces & Condiment");
		    	productType16.setCode("15");
		    	productTypes.add(productType16);
	    	}
	    	
	    	//Drinks
	    	if (productTypeRepository.findProductTypeByCode("16") == null) {
		    	ProductType productType17 = new ProductType();
		    	productType17.setName("Drinks");
		    	productType17.setCode("16");
		    	productTypes.add(productType17);
	    	}
	    	
	    	//Paper Products
	    	if (productTypeRepository.findProductTypeByCode("17") == null) {
		    	ProductType productType18 = new ProductType();
		    	productType18.setName("Paper Products");
		    	productType18.setCode("17");
		    	productTypes.add(productType18);
	    	}
	    	
	    	//Dairy Products
	    	if (productTypeRepository.findProductTypeByCode("18") == null) {
		    	ProductType productType19 = new ProductType();
		    	productType19.setName("Dairy Products");
		    	productType19.setCode("18");
		    	productTypes.add(productType19);
	    	}
	    	
	    	//Personal Care
	    	if (productTypeRepository.findProductTypeByCode("19") == null) {
		    	ProductType productType20 = new ProductType();
		    	productType20.setName("Personal Care");
		    	productType20.setCode("19");
		    	productTypes.add(productType20);
	    	}

	    	//Cleaning
	    	if (productTypeRepository.findProductTypeByCode("20") == null) {
		    	ProductType productType21 = new ProductType();
		    	productType21.setName("Cleaning");
		    	productType21.setCode("20");
		    	productTypes.add(productType21);
	    	}
	    	
	    	productTypeRepository.saveAll(productTypes);
	    }    
	    
	    
	    @Transactional
	    public final void createAndInitializeCountries() {
	    	List<Country> countries = new ArrayList<>();	    	
	    	if (countryRepository.findAll().size() <= 0 ) {	    		
	    		/** Mauritius */
	    		Country country1 = new Country();
	    		country1.setCountryCode("MRU");
	    		country1.setCountryName("Mauritius");	    		
	    		
	    		countries.add(country1);
	    		countryRepository.saveAll(countries);
	    	}	    	
	    }
	    
	    
	    @Transactional
	    public final void createAndInitializeCredits() {
	    	
	    	//CreditRepository.deleteAll();	    		    	
	    	List<Credit> credits = new ArrayList<Credit>();
	    	
	    	//grocery price	    	
	    	if (creditRepository.getCreditByContributionType(ContributionType.CONTRIBUTE_GROCERY_PRICE) == null) {
	    		Credit creditGroceryPrice = new Credit();
	    		creditGroceryPrice.setContributionType(ContributionType.CONTRIBUTE_GROCERY_PRICE);
	    		creditGroceryPrice.setCreditType(CreditType.WORKED_FOR_ACTION_CREDITS);
	    		creditGroceryPrice.setPoints(new BigDecimal(10));
		    	credits.add(creditGroceryPrice);
	    	}
	    	
	    	//setup business
	    	if (creditRepository.getCreditByContributionType(ContributionType.CONTRIBUTE_SETUP_BUSINESS) == null) {
	    		Credit creditSetupBusiness = new Credit();
	    		creditSetupBusiness.setContributionType(ContributionType.CONTRIBUTE_SETUP_BUSINESS);
	    		creditSetupBusiness.setCreditType(CreditType.WORKED_FOR_ACTION_CREDITS);
	    		creditSetupBusiness.setPoints(new BigDecimal(5));
		    	credits.add(creditSetupBusiness);
	    	}
	    	
	    	//product details
	    	Credit CreditProductDetails = new Credit();
	    	if (creditRepository.getCreditByContributionType(ContributionType.CONTRIBUTE_PRODUCT_DETAILS) == null) {
		    	CreditProductDetails.setContributionType(ContributionType.CONTRIBUTE_PRODUCT_DETAILS);
		    	CreditProductDetails.setCreditType(CreditType.WORKED_FOR_ACTION_CREDITS);
		    	CreditProductDetails.setPoints(new BigDecimal(10));	 
		    	credits.add(CreditProductDetails);
	    	}
	    	
	    	//product details & picture
	    	if (creditRepository.getCreditByContributionType(ContributionType.CONTRIBUTE_PRODUCTANDPICTURE) == null) {
		    	Credit CreditProductAndPicture = new Credit();
		    	CreditProductAndPicture.setContributionType(ContributionType.CONTRIBUTE_PRODUCTANDPICTURE);
		    	CreditProductAndPicture.setCreditType(CreditType.WORKED_FOR_ACTION_CREDITS);
		    	CreditProductAndPicture.setPoints(new BigDecimal(15));
		    	credits.add(CreditProductAndPicture);	 
	    	}
	    	
	    	//perform product comparison
	    	if (creditRepository.getCreditByContributionType(ContributionType.PERFORM_PRODUCT_COMPARISON) == null) {
		    	Credit CreditProductComparison = new Credit();
		    	CreditProductComparison.setContributionType(ContributionType.PERFORM_PRODUCT_COMPARISON);
		    	CreditProductComparison.setCreditType(CreditType.CONSUMED_BY_ACTION_CREDITS);
		    	CreditProductComparison.setPoints(new BigDecimal(30));
		    	credits.add(CreditProductComparison);
	    	}
	    	
	    	//perform product comparison
	    	if (creditRepository.getCreditByContributionType(ContributionType.GRANT_BENEFICIARY_CREDITS) == null) {
		    	Credit CreditBeneficiaryCredits = new Credit();
		    	CreditBeneficiaryCredits.setContributionType(ContributionType.GRANT_BENEFICIARY_CREDITS);
		    	CreditBeneficiaryCredits.setCreditType(CreditType.GRANTED_BY_CREDITS);
		    	CreditBeneficiaryCredits.setPoints(new BigDecimal(10));
		    	credits.add(CreditBeneficiaryCredits);
	    	}
	    	
	    	//send mail invites
	    	if (creditRepository.getCreditByContributionType(ContributionType.SEND_INVITES_BENEFICIARY_CREDITS) == null) {
		    	Credit CreditBeneficiaryMailInviteCredits = new Credit();
		    	CreditBeneficiaryMailInviteCredits.setContributionType(ContributionType.SEND_INVITES_BENEFICIARY_CREDITS);
		    	CreditBeneficiaryMailInviteCredits.setCreditType(CreditType.GRANTED_BY_CREDITS);
		    	CreditBeneficiaryMailInviteCredits.setPoints(new BigDecimal(5));
		    	credits.add(CreditBeneficiaryMailInviteCredits);
	    	}	 
	    	
	    	//lottery raffle
	    	if (creditRepository.getCreditByContributionType(ContributionType.ELECTED_LOTTERY_RAFFLE) == null) {
		    	Credit creditLotteryRaffleCredits = new Credit();
		    	creditLotteryRaffleCredits.setContributionType(ContributionType.ELECTED_LOTTERY_RAFFLE);
		    	creditLotteryRaffleCredits.setCreditType(CreditType.CONSUMED_BY_ACTION_CREDITS);
		    	SystemSetting systemSetting = systemSettingRepository.findBySystemKey(SaversBasketConstants.LOTTERY_DRAW_PARTICIPATION_LIMIT);
		    	creditLotteryRaffleCredits.setPoints(new BigDecimal(systemSetting.getSystemValue()));
		    	credits.add(creditLotteryRaffleCredits);
	    	}	 
	    	
	    	creditRepository.saveAll(credits);	    	
	    }
	       

	    @Transactional
	    private final User createUserIfNotFound(final String username, final String password, final String email, final List<Role> roles) {
	        User user = userRepository.getUserAndRoleByEmail(email);
	        if (user == null) {	        	
	        	/**
		         * Set default account
		         */
		        Account defautAccount = new Account(email);      
		        defautAccount.setDateAccountCreated(new Date());
		        defautAccount.setAccountType(AccountType.USER);			      
	        	
	            user = new User();
	            
	            user.setUsername(username);	           
	            user.setPassword(passwordEncoder.encode(password));	   
	            user.setEmail(email);
	            user.setDateUserRegistered(new Date());
	            user.setLanguage("EN");	           
	            user.setEnabled(Boolean.TRUE);
	           	            
	            //link to account
	            user.setAccount(defautAccount);		            
	        }
	        
	        /**
	         * create roles
	         */
	        user.setRoles(roles);
	        
	        /**
	         * Device profile
	         */
	        DeviceProfile deviceProfile = createAndInitializeDeviceProfile(email);
	        deviceProfile.setUser(user);	        
	        user.setIdDeviceProfile(deviceProfile);        
	       	        
	        user = userRepository.save(user);	        
	        return user;
	    }

}
