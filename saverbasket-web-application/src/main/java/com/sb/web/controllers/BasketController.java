package com.sb.web.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Stopwatch;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.sb.web.email.EmailService;
import com.sb.web.entities.Account;
import com.sb.web.entities.Business;
import com.sb.web.entities.BusinessAddress;
import com.sb.web.entities.Country;
import com.sb.web.entities.SystemSetting;
import com.sb.web.entities.User;
import com.sb.web.enumerations.BusinessCategory;
import com.sb.web.enumerations.BusinessStatus;
import com.sb.web.enumerations.ContributionStatus;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.enumerations.ProductEntrySource;
import com.sb.web.receipt.TranscriptionContext;
import com.sb.web.receipt.WinnersTranscriptionStrategy;
import com.sb.web.repositories.AccountRepository;
import com.sb.web.repositories.CountryRepository;
import com.sb.web.repositories.SystemSettingRepository;
import com.sb.web.request.dto.ContactEmailRequestDTO;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.request.dto.UploadArtifactRequestDTO;
import com.sb.web.response.dto.AddBusinessResponseDTO;
import com.sb.web.response.dto.AddProductResponseDTO;
import com.sb.web.response.dto.CompareBasketResponseDTO;
import com.sb.web.response.dto.ContactEmailResponseDTO;
import com.sb.web.response.dto.ContributedPriceResponseDTO;
import com.sb.web.response.dto.ContributionResponseDTO;
import com.sb.web.response.dto.CreditUpdateForUserResponse;
import com.sb.web.response.dto.SearchBasketResponseDTO;
import com.sb.web.response.dto.SendMailInviteResponse;
import com.sb.web.response.dto.UpdateBasketItemResponse;
import com.sb.web.response.dto.UpdateSettingsResponseDTO;
import com.sb.web.response.dto.UploadArtifactResponseDTO;
import com.sb.web.service.AmazonServiceClient;
import com.sb.web.service.BasketService;
import com.sb.web.service.BusinessService;
import com.sb.web.service.CreditService;
import com.sb.web.service.InviteService;
import com.sb.web.service.PlanService;
import com.sb.web.service.SearchBasketService;
import com.sb.web.service.UserActionRewarderService;
import com.sb.web.service.UserContributionService;
import com.sb.web.service.UserService;
import com.sb.web.service.dto.AddBusinessDTO;
import com.sb.web.service.dto.AddProductDTO;
import com.sb.web.service.dto.BusinessDTO;
import com.sb.web.service.dto.ContributePriceDTO;
import com.sb.web.service.dto.ProcessedReceiptDTO;
import com.sb.web.service.dto.ProcessedReceiptLineDTO;
import com.sb.web.service.dto.ProductDTO;
import com.sb.web.service.dto.ProductTypeDTO;
import com.sb.web.service.dto.ReceiptLineDTO;
import com.sb.web.service.dto.ReceiptTicketDTO;
import com.sb.web.service.dto.UpdateSettingsDTO;
import com.sb.web.service.dto.UserContributionDTO;
import com.sb.web.utils.SaversBasketConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/basket")
@Api(tags = "basket")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Transactional
public class BasketController extends AbstractController {

	@Autowired
	private SearchBasketService searchBasketService;

	@Autowired
	private BasketService basketService;

	@Autowired
	private UserService userService;

	@Autowired
	private AmazonServiceClient amazonServiceClient;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserContributionService userContributionService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private InviteService inviteService;

	@Autowired
	private UserActionRewarderService userActionRewarderService;

	@Autowired
	private CreditService creditService;
	
	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private PlanService planService;

	@Autowired
	private SystemSettingRepository systemSettingRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@SuppressWarnings("unchecked")
	@PostMapping("/sendEmailInvites")
	@ApiOperation(value = "Send email invites to friends")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 422, message = "Invalid username/password supplied") })
	public SendMailInviteResponse sendEmailInvites(@ApiParam("inviteesEmail") @RequestParam String inviteesEmail) {

		SendMailInviteResponse sendMailInviteResponse = new SendMailInviteResponse();

		if (inviteesEmail == null) {
			sendMailInviteResponse.setInviteSent(Boolean.FALSE);
			sendMailInviteResponse.setNumberOfInviteSent(NumberUtils.INTEGER_ZERO);
			return sendMailInviteResponse;
		}

		return inviteService.sendEmailInvites(getPrincipal(), inviteesEmail);
	}

	@Transactional
	@PostMapping(value = "/getScannedProductInfo", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ProductDTO getScannedProductInfo(@RequestParam(value = "barcode", required = true) String barcode,@RequestParam(value = "changeInfo", required = true) boolean changeInfo ) {
		ProductDTO retrievedProduct = new ProductDTO();
		User user = userService.findByUserEmail(getPrincipal());

		retrievedProduct = basketService.retrieveProductFromSvbDatabase(barcode , true);
		if (!StringUtils.isEmpty(retrievedProduct.getProductBarCode())) {
			retrievedProduct.setProductEntrySource(ProductEntrySource.SAVERSBASKETAPI);
			retrievedProduct.setProductPresentInDatabase(true);			
		}
		else
	    {
			retrievedProduct = basketService.retrieveProductInformationFromAPI(barcode, user.getIdUser());			
			if (retrievedProduct == null) {
				retrievedProduct = new ProductDTO();
			}
			else {
				retrievedProduct.setProductEntrySource(ProductEntrySource.FOODFACTSAPI);
				retrievedProduct.setProductPresentInDatabase(false);				
			}
		}
		
		if (changeInfo) {
			retrievedProduct.setProductEntrySource(ProductEntrySource.FOODFACTSAPI);
			retrievedProduct.setProductPictureUrl(retrievedProduct.getImageUrl());
			retrievedProduct.setProductPresentInDatabase(false);	
		}
		
		retrievedProduct.setProductBarCode(barcode);
		
		return retrievedProduct;
	}

	@PostMapping("/retrieveProductToPopulateForm")
	@ApiOperation(value = "finds products to fill in smart list")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public ProductDTO retrieveProductToPopulateForm(@RequestParam Integer idProduct) {
		ProductDTO productDisplay = basketService.retrieveProductToPopulateForm(idProduct);
		return productDisplay;
	}

	@Transactional
	@PostMapping("/retrieveProductInformationFromAPI")
	@ApiOperation(value = "finds product information from openfoodfacts api")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public ProductDTO retrieveProductInformationFromAPI(@RequestParam String productCode) {
		User userLogged = userService.findByUserEmail(getPrincipal());
		Integer userId = null;
		if (userLogged != null) {
			userId = userLogged.getIdUser();
		}
		return basketService.retrieveProductInformationFromAPI(productCode, userId);
	}

	@RequestMapping(value = "/getSuggestedSimilarProducts", method = RequestMethod.GET)
	public @ResponseBody List<ProductDTO> getTags(@RequestParam String productName) {
		return basketService.getListSuggestedProducts(productName);
	}

	@Transactional
	@PostMapping("/findProducts")
	@ApiOperation(value = "finds products to fill in smart list")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public SearchBasketResponseDTO findProducts(@RequestParam String searchValue,
			@RequestParam String globalProductCategory, @RequestParam Integer pageNumber) {

		SearchBasketResponseDTO searchBasketResponseDTO = new SearchBasketResponseDTO();
		String userLogged = getPrincipal();
		Integer userId = null;

		if (userLogged != null && userLogged.equals("anonymousUser")) {

			// creates and starts a new stopwatch
			Stopwatch stopwatch = Stopwatch.createStarted();

			searchBasketResponseDTO = searchBasketService.find(searchValue, null, globalProductCategory, pageNumber);
			searchBasketResponseDTO.setUserAnonymous(Boolean.TRUE);

			// get elapsed time,expressed in milliseconds
			long timeElapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			searchBasketResponseDTO.setExecutionTimeElapsed(timeElapsed);

		} else {
			// creates and starts a new stopwatch
			Stopwatch stopwatch = Stopwatch.createStarted();

			// check user
			User user = userService.findByUserEmail(userLogged);

			if (user != null) {
				searchBasketResponseDTO = new SearchBasketResponseDTO();
				userId = user.getIdUser();
				searchBasketResponseDTO = searchBasketService.find(searchValue, userId, globalProductCategory,
						pageNumber);
				searchBasketResponseDTO.setUserAnonymous(Boolean.FALSE);

				stopwatch.stop();
			}

			// get elapsed time,expressed in milliseconds
			long timeElapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			searchBasketResponseDTO.setExecutionTimeElapsed(timeElapsed);
		}

		return searchBasketResponseDTO;
	}

	@Transactional
	@PostMapping("/retrieveAllBasketItemsForUser")
	@ApiOperation(value = "retrieves basket items for user")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public SearchBasketResponseDTO retrieveAllBasketItemsForUser() {
		SearchBasketResponseDTO searchBasketResponseDTO = null;
		// check user
		User user = userService.findByUserEmail(getPrincipal());
		if (user != null) {
			searchBasketResponseDTO = searchBasketService.retrieveAllBasketItemsForUser(user.getIdUser());
		}
		return searchBasketResponseDTO;
	}

	@Transactional
	@PostMapping("/calculatePricesFromBasketForSpecificRetailer")
	@ApiOperation(value = "compares smart list with stores")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public CompareBasketResponseDTO calculatePricesFromBasketForSpecificRetailer()
			throws InterruptedException, ExecutionException {
		return performBasketComparisons();
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
				compareBasketResponseDTO = basketService.calculatePricesFromBasketForSpecificRetailer(user.getIdUser());
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

	@Transactional
	@PostMapping("/performSearchfromBarcodesForSpecificRetailer")
	@ApiOperation(value = "compares smart list with stores")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public CompareBasketResponseDTO performSearchfromBarcodesForSpecificRetailer(
			@RequestParam List<String> productBarCodes) throws InterruptedException, ExecutionException {
		CompareBasketResponseDTO compareBasketResponseDTO = null;

		/** retrieve user info */
		User user = userService.findByUserEmail(getPrincipal());

		if (user != null) {

			/** check if user can perform basket comparison */
			if (planService.canPerformCompareBasketDaily(user.getIdUser())) {

				// creates and starts a new stopwatch
				Stopwatch stopwatch = Stopwatch.createStarted();

				/** measured execution time of method */
				compareBasketResponseDTO = basketService.calculatePricesFromBarCodeForSpecificRetailer(user.getIdUser(),
						productBarCodes);
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

	@Transactional
	@PostMapping("/retrieveSmartListStatistics")
	@ApiOperation(value = "get count for smart list")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public Integer retrieveSmartListStatistics(@RequestParam Integer smartList) {
		return searchBasketService.retrieveSmartListStatistics(smartList);
	}

	@Transactional
	@PostMapping("/getContributionDetailsForUser")
	@ApiOperation(value = "get total credits for user")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public CreditUpdateForUserResponse getContributionDetailsForUser() {
		String email = getPrincipal();
		CreditUpdateForUserResponse creditUpdateForUserResponse = null;
		if (email != null && !email.equals("anonymousUser")) {
			User user = userService.findByUserEmail(email);
			// credits
			creditUpdateForUserResponse = basketService.getCreditsForUser(user.getIdUser());
			creditUpdateForUserResponse.setUserAnonymous(Boolean.FALSE);
			// contributions
			Integer userContribs = userContributionService.getAllContributionsByUser(user.getIdUser());
			creditUpdateForUserResponse.setUserContributions(userContribs);
		} else {
			creditUpdateForUserResponse = new CreditUpdateForUserResponse();
			creditUpdateForUserResponse.setUserAnonymous(Boolean.TRUE);
		}

		return creditUpdateForUserResponse;
	}

	@Transactional
	@PostMapping("/addProductToUserBasket")
	@ApiOperation(value = "add products to user basket")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public AddProductResponseDTO addProductToUserBasket(@RequestParam Integer idProduct,
			@RequestParam Integer quantity) {
		// check user
		User user = userService.findByUserEmail(getPrincipal());
		AddProductResponseDTO addProductResponseDTO = null;
		if (user != null) {
			quantity = 1;
			addProductResponseDTO = searchBasketService.addProductToUserBasket(idProduct, quantity, user.getIdUser());
		}
		return addProductResponseDTO;
	}

	@Transactional
	@PostMapping("/updateBasketItemsCount")
	@ApiOperation(value = "return actual basket count")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public AddProductResponseDTO updateBasketItemsCount() {
		AddProductResponseDTO addProductResponseDTO = new AddProductResponseDTO();
		User user = userService.findByUserEmail(getPrincipal());
		if (user != null && user.getIdUser() != null) {
			addProductResponseDTO = searchBasketService.updateBasketItemsCount(user.getIdUser());
		} else {
			addProductResponseDTO.setNumberProductsInBasket(0);
		}
		return addProductResponseDTO;
	}

	@Transactional
	@RequestMapping("/updateQuantityForBasketItems")
	public UpdateBasketItemResponse updateQuantityForBasketItems(@RequestParam String[] values) {
		UpdateBasketItemResponse updateBasketItemResponse = new UpdateBasketItemResponse();
		String email = getPrincipal();

		if (email != null && email.equals("anonymousUser")) {
			updateBasketItemResponse.setUserAuthenticated(Boolean.FALSE);
		} else {
			basketService.updateBasketQuantityForUser(values);
			updateBasketItemResponse.setUserAuthenticated(Boolean.TRUE);
		}
		return updateBasketItemResponse;
	}

	@Transactional
	@RequestMapping("/deleteItemFromBasket")
	public void deleteItemFromBasket(@RequestParam Integer idBasket) {
		basketService.deleteItemFromBasket(idBasket);
	}

	@Transactional
	@PostMapping(value = "/contactTeamByEmail", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ContactEmailResponseDTO contactTeamByEmail(
			@ModelAttribute @Valid ContactEmailRequestDTO contactEmailRequestDTO, BindingResult result) {

		ContactEmailResponseDTO contactEmailResponseDTO = new ContactEmailResponseDTO();

		try {

			if (result.hasErrors()) {
				// Get error message
				Map<String, String> errorMessages = result.getFieldErrors().stream()
						.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

				contactEmailResponseDTO.setEmailSentStatus(false);
				contactEmailResponseDTO.setErrorMessages(errorMessages);
			} else {
				if (contactEmailRequestDTO != null && !StringUtils.isEmpty(contactEmailRequestDTO.getEmail())
						&& !StringUtils.isEmpty(contactEmailRequestDTO.getName())
						&& !StringUtils.isEmpty(contactEmailRequestDTO.getMessage())) {
					emailService.sendSimpleMessage(SaversBasketConstants.SBASKET_SUPPORT_EMAIL,
							"SaversBasket - Query Mail From " + contactEmailRequestDTO.getName(),
							contactEmailRequestDTO.getEmail() + ":" + contactEmailRequestDTO.getMessage());
					contactEmailResponseDTO.setEmailSentStatus(true);
				}
			}

		} catch (IOException | MessagingException e) {
			contactEmailResponseDTO.setEmailSentStatus(false);
		}

		return contactEmailResponseDTO;
	}

	@Transactional
	@PostMapping(value = "/uploadArtifact", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UploadArtifactResponseDTO uploadArtifact(@ModelAttribute UploadArtifactRequestDTO uploadArtifactRequestDTO,
			BindingResult result) {

		UserContributionDTO userContributionDTO = new UserContributionDTO();
		User user = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			user = userService.findByUserEmail(getPrincipal());
		}

		if (uploadArtifactRequestDTO.getFile() != null && uploadArtifactRequestDTO.getFile().getSize() > 0) {
			String receiptPictureUrl = this.amazonServiceClient.uploadFile("F", uploadArtifactRequestDTO.getFile());
			if (!StringUtils.isEmpty(receiptPictureUrl)) {
				userContributionDTO.setArtifactUrl(receiptPictureUrl);
			}
		}

		if (uploadArtifactRequestDTO.getContent() != null && uploadArtifactRequestDTO.getContent().equals("receipt")) {
			// userContributionDTO.setContributionType(ContributionType.RECEIPT);
		} else {
			userContributionDTO.setContributionType(ContributionType.CONTRIBUTE_CSV);
		}

		userContributionDTO.setContributionStatus(ContributionStatus.SUCCESS);
		userContributionDTO.setDateContributionAdded(new Date());
		userContributionDTO.setUpdatedDateContributionStatus(new Date());
		userContributionDTO.setLinkedBusinessId(uploadArtifactRequestDTO.getIdBusiness());

		if (user != null) {
			userContributionDTO.setIdUserContribution(user.getIdUser());
		}

		return userContributionService.uploadReceiptPicture(userContributionDTO);
	}
	
	
	@Transactional
	@PostMapping(value = "/updateUserSettings", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UpdateSettingsResponseDTO updateUserSettings(@ModelAttribute @Valid UpdateSettingsDTO updateSettingsDTO,
			BindingResult result) {
		UpdateSettingsResponseDTO updateSettingsResponseDTO = new UpdateSettingsResponseDTO();
		
		/** Retrieve active user */
		User user = userService.findByUserEmail(getPrincipal());
		
		Account account = user.getAccount();
		if (StringUtils.isNotEmpty(updateSettingsDTO.getBudgetAmount())) {
			account.setBudgetedAmount(new BigDecimal(updateSettingsDTO.getBudgetAmount()));
			accountRepository.save(account);
			updateSettingsResponseDTO.setValidated(true);
		}		
		
		return updateSettingsResponseDTO;
	}
	

	@Transactional
	@PostMapping(value = "/addBusiness", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AddBusinessResponseDTO addBusiness(@ModelAttribute @Valid AddBusinessDTO addBusinessDTO,
			BindingResult result) {

		AddBusinessResponseDTO response = new AddBusinessResponseDTO();
		String email = getPrincipal();

		if (result.hasErrors()) {
			// Get error message
			Map<String, String> errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

			response.setValidated(false);
			response.setErrorMessages(errors);

			if (email != null && !email.equals("anonymousUser")) {
				response.setUserAuthenticated(Boolean.TRUE);
			} else {
				response.setUserAuthenticated(Boolean.FALSE);
			}
		} else {

			User user = null;
			if (email != null && !email.equals("anonymousUser")) {
				// check user
				user = userService.findByUserEmail(email);
				if (user != null) {

					/**
					 * create business
					 */
					Business business = new Business();
					business.setRegisteredName(addBusinessDTO.getBusinessName());
					business.setBusinessCategory(BusinessCategory.fromInteger(addBusinessDTO.getIdBusinessCategory()));
					business.setDateBusinessCreated(new Date());
					business.setSponsored(Boolean.FALSE);
					business.setBusinessStatus(BusinessStatus.REVIEW);

					/**
					 * Upload business logo
					 */

					if (addBusinessDTO.getFile() != null && addBusinessDTO.getFile().getSize() > 0) {
						String awsPictureUrl = this.amazonServiceClient.uploadFile("B", addBusinessDTO.getFile());
						if (!StringUtils.isEmpty(awsPictureUrl)) {
							business.setBusinessLogoUrl(awsPictureUrl);
						}
					}

					/**
					 * create address
					 */
					BusinessAddress businessAddress = new BusinessAddress();
					businessAddress.setDistrict(addBusinessDTO.getIdDistrict());
					businessAddress.setAddress1(addBusinessDTO.getAddress1());
					businessAddress.setPhoneNumber(addBusinessDTO.getPhoneNumber());
					businessAddress.setEmail(addBusinessDTO.getEmail());
					
					Country country = countryRepository.retrieveSpecificCountry("Mauritius");
					if (country!= null) {
						businessAddress.setCountry(country);
					}
					businessAddress.setBusiness(business);

					List<BusinessAddress> businessAddresses = new ArrayList<BusinessAddress>();
					businessAddresses.add(businessAddress);
					business.setBusinessAddresses(businessAddresses);
							
				
					boolean saveBusinessFlag = businessService.saveBusinessFromAnonymousUser(business);
					if (saveBusinessFlag) {
						response.setValidated(Boolean.TRUE);
						SystemSetting systemSetting = systemSettingRepository
								.findBySystemKey(SaversBasketConstants.ENABLE_REWARD_FEATURE);
						if (systemSetting != null && systemSetting.getSystemValue().equals("1")) {
							/* Add reward */
							ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
							contributionRequestDTO.setUserIdToBeCredited(user.getIdUser());

							contributionRequestDTO.setContributionType(ContributionType.CONTRIBUTE_SETUP_BUSINESS);
							ContributionResponseDTO contributionResponseDTO = userActionRewarderService
									.grantRewardForUserAction(contributionRequestDTO);

							if (contributionResponseDTO != null
									&& contributionResponseDTO.getMonetaryReward() != null) {
								response.setMonetaryReward(contributionResponseDTO.getMonetaryReward());
							}
						}

					} else {
						response.setValidated(Boolean.FALSE);
					}
					response.setUserAuthenticated(Boolean.TRUE);
				}
			}

			else {
				/** User should be authenticated */
				response.setUserAuthenticated(Boolean.FALSE);
			}
		}
		return response;
	}

	@Transactional
	@PostMapping(value = "/contributePrice", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ContributedPriceResponseDTO contributePrice(@ModelAttribute @Valid ContributePriceDTO contributePriceDTO,
			BindingResult result) {

		ContributedPriceResponseDTO contributedPriceResponseDTO = new ContributedPriceResponseDTO();
		String email = getPrincipal();

		if (email != null && email.equals("anonymousUser")) {
			contributedPriceResponseDTO.setUserAuthenticated(Boolean.FALSE);
		} else {
			// check user
			User user = userService.findByUserEmail(email);

			// update user id
			contributePriceDTO.setIdUser(user.getIdUser());
			contributedPriceResponseDTO = basketService.contributePriceToProduct(contributePriceDTO);

			SystemSetting systemSetting = systemSettingRepository
					.findBySystemKey(SaversBasketConstants.ENABLE_REWARD_FEATURE);
			if (systemSetting != null && systemSetting.getSystemValue().equals("1")) {
				/**
				 * Add reward
				 */
				ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
				contributionRequestDTO.setContributionType(ContributionType.CONTRIBUTE_GROCERY_PRICE);
				contributionRequestDTO.setUserIdToBeCredited(user.getIdUser());

				ContributionResponseDTO contributionResponseDTO = userActionRewarderService
						.grantRewardForUserAction(contributionRequestDTO);
				if (contributionResponseDTO != null && contributionResponseDTO.getMonetaryReward() != null) {
					contributedPriceResponseDTO.setCreditedReward(contributionResponseDTO.getMonetaryReward());
				}
			}
		}

		return contributedPriceResponseDTO;
	}

	@Transactional
	@PostMapping(value = "/addProduct", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AddProductResponseDTO addProduct(@ModelAttribute @Valid AddProductDTO addProductDTO, BindingResult result) {		
		Map<String, String> errors = new HashMap<>();

		if (addProductDTO.getProductDateCreated() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Calendar calendar = Calendar.getInstance();
			String currentPriceNoted = sdf.format(calendar.getTime());
			// add current price date
			addProductDTO.setProductDateCreated(currentPriceNoted);
		}

		String email = getPrincipal();
		AddProductResponseDTO response = new AddProductResponseDTO();
		if (result.hasErrors()) {
			// Get error message
			errors = result.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			response.setValidated(false);
			response.setErrorMessages(errors);

			if (email != null && !email.equals("anonymousUser")) {
				response.setUserAuthenticated(Boolean.TRUE);
			} else {
				response.setUserAuthenticated(Boolean.FALSE);
			}

		} else {

			boolean productCheck = basketService.getProductInformationByBarcodeAndBusiness(addProductDTO.getProductBarcode(),addProductDTO.getIdBusiness());
			if (!productCheck) {

				User user = null;
				if (email != null && !email.equals("anonymousUser")) {
					// check user
					user = userService.findByUserEmail(email);
					if (user != null) {
						addProductDTO.setIdUser(user.getIdUser());
						if (addProductDTO.getFile() != null && addProductDTO.getFile().getSize() > 0) {
							String awsPictureUrl = this.amazonServiceClient.uploadFile("P", addProductDTO.getFile());
							if (!StringUtils.isEmpty(awsPictureUrl)) {
								addProductDTO.setUploadedPictureUrl(awsPictureUrl);
							}
						}
					
						response = basketService.addProductToStore(addProductDTO);
						response.setUserAuthenticated(Boolean.TRUE);

						SystemSetting systemSetting = systemSettingRepository
								.findBySystemKey(SaversBasketConstants.ENABLE_REWARD_FEATURE);
						if (systemSetting != null && systemSetting.getSystemValue().equals("1")) {
							/**
							 * Add reward
							 */
							ContributionResponseDTO contributionResponseDTO = null;
							ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
							contributionRequestDTO.setUserIdToBeCredited(user.getIdUser());

							if (StringUtils.isEmpty(addProductDTO.getUploadedPictureUrl())) {
								contributionRequestDTO
										.setContributionType(ContributionType.CONTRIBUTE_PRODUCTANDPICTURE);
								contributionResponseDTO = userActionRewarderService
										.grantRewardForUserAction(contributionRequestDTO);
							} else {
								contributionRequestDTO.setContributionType(ContributionType.CONTRIBUTE_PRODUCT_DETAILS);
								contributionResponseDTO = userActionRewarderService
										.grantRewardForUserAction(contributionRequestDTO);
							}
							response.setMonetaryReward(contributionResponseDTO.getMonetaryReward());
						}
					}
				} else {
					/** User should be authenticated */
					response.setUserAuthenticated(Boolean.FALSE);
					errors.put("USER_NOT_AUTHENTICATED", "User should be authenticated");
				}
			} else {
				/** Product exists in database */
				response.setValidated(false);
				response.setProductAlreadyPresent(true);
				errors.put("PRODUCT_ALREADY_LINKED", "Product already linked to business");
			}
		}

		return response;
	}

	@Transactional
	@PostMapping("/getAllProductTypes")
	public List<ProductTypeDTO> getAllProductTypes() {
		return basketService.getAllProductTypes();
	}

	@Transactional
	@PostMapping("/getAllValidBusinesses")
	public List<BusinessDTO> getAllValidBusinesses() {
		return basketService.getAllValidBusinesses();
	}

	@Transactional
	@PostMapping(value = "/processRawCSVLines", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ProcessedReceiptDTO processRawCSVLines(@ModelAttribute UploadArtifactRequestDTO uploadArtifactRequestDTO,
			BindingResult result) {
		ProcessedReceiptDTO processedReceiptDTO = new ProcessedReceiptDTO();
		// validate file
		if (uploadArtifactRequestDTO.getFile().isEmpty()) {

		} else {

			// parse CSV file to create a list of `User` objects
			try (Reader reader = new BufferedReader(
					new InputStreamReader(uploadArtifactRequestDTO.getFile().getInputStream()));
					CSVReader csvReader = new CSVReaderBuilder(reader)
							.withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
							// Skip the header
							.withSkipLines(1).build();) {

				List<ProcessedReceiptLineDTO> processedReceiptLines = new ArrayList<ProcessedReceiptLineDTO>();
				// Reading Records One by One in a String array
				String[] nextRecord;
				while ((nextRecord = csvReader.readNext()) != null) {
					ProcessedReceiptLineDTO processedReceiptLineDTO = new ProcessedReceiptLineDTO();

					if (StringUtils.isNotEmpty(nextRecord[1])) {
						String description = nextRecord[0];
						if (description != null) {
							processedReceiptLineDTO.setDescription(description);
						}
					}

					if (StringUtils.isNotEmpty(nextRecord[1])) {
						BigDecimal price = new BigDecimal(nextRecord[1]);
						if (price != null) {
							processedReceiptLineDTO.setPrice(price);
						}
					}
					processedReceiptLines.add(processedReceiptLineDTO);
				}

				// close readers
				csvReader.close();
				reader.close();
				processedReceiptDTO.setProcessedReceiptLines(processedReceiptLines);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return processedReceiptDTO;
	}

	@Transactional
	@PostMapping("/processRawReceiptLines")
	public ProcessedReceiptDTO processRawReceiptLines(@RequestParam(value = "rawReceiptList") String[] rawReceiptList) {
		TranscriptionContext ctx = new TranscriptionContext();
		ctx.setTranscriptionStrategy(new WinnersTranscriptionStrategy());

		List<String> formattedReceiptLines = Arrays.asList(rawReceiptList);
		ProcessedReceiptDTO processedReceiptDTO = ctx.createTranscription(formattedReceiptLines);

		return processedReceiptDTO;
	}

	@Transactional
	@PostMapping(value = "/saveProductsFromReceipt", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody AddProductResponseDTO saveProductsFromReceipt(
			@ModelAttribute @Valid ReceiptTicketDTO receiptTicketDTO, BindingResult result) {

		AddProductResponseDTO addProductResponseDTO = new AddProductResponseDTO();

		String email = getPrincipal();
		User user = userService.findByUserEmail(email);

		if (receiptTicketDTO != null && receiptTicketDTO.getReceiptList() != null
				&& receiptTicketDTO.getReceiptList().size() > 0) {

			for (ReceiptLineDTO receiptLineDTO : receiptTicketDTO.getReceiptList()) {

				AddProductDTO addProductDTO = new AddProductDTO();
				addProductDTO.setPrice(String.valueOf(receiptLineDTO.getPrice()));
				addProductDTO.setProductName(receiptLineDTO.getDescription());

				if (StringUtils.isEmpty(receiptTicketDTO.getDateproductUploaded())) {

					addProductDTO.setProductDateCreated(receiptTicketDTO.getDateproductUploaded());
				} else {

					String dateProductUploaded = receiptTicketDTO.getDateproductUploaded();
					if (!StringUtils.isBlank(dateProductUploaded)) {
						addProductDTO.setProductDateCreated(dateProductUploaded);
					}
				}

				addProductDTO.setIdBusiness(receiptTicketDTO.getLinkedBusinessId());
				if (user != null) {
					addProductDTO.setIdUser(user.getIdUser());
				}

				if (receiptTicketDTO.getDateproductUploaded() != null) {
					addProductDTO.setProductDateCreated(receiptTicketDTO.getDateproductUploaded());
				} else {

					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					String strDate = formatter.format(date);

					addProductDTO.setProductDateCreated(strDate);
				}

				/**
				 * Product picture URL
				 */
				if (receiptLineDTO != null && receiptLineDTO.getFile() != null
						&& receiptLineDTO.getFile().getSize() > 0) {
					String awsPictureUrl = this.amazonServiceClient.uploadFile("P", receiptLineDTO.getFile());
					if (!StringUtils.isEmpty(awsPictureUrl)) {
						addProductDTO.setUploadedPictureUrl(awsPictureUrl);
					}
				} else {
					addProductDTO.setUploadedPictureUrl(null);
				}

				// Implement business logic to save employee into database
				addProductResponseDTO = basketService.addProductToStore(addProductDTO);
				addProductResponseDTO.setValidated(Boolean.TRUE);
			}

		} else {
			addProductResponseDTO.setValidated(Boolean.FALSE);
		}

		return addProductResponseDTO;
	}

	@Transactional
	@PostMapping("/updateContributionStatus")
	public void updateContributionStatus(@RequestParam Integer contributionIdToUpdate, @RequestParam String error) {
		userContributionService.updateContributionStatus(contributionIdToUpdate, error);
	}
}
