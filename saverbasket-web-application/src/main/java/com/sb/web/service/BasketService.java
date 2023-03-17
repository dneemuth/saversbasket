package com.sb.web.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.annotations.Auditable;
import com.sb.web.annotations.Auditable.AuditingActionType;
import com.sb.web.entities.BasketItem;
import com.sb.web.entities.Business;
import com.sb.web.entities.CreditContribution;
import com.sb.web.entities.Price;
import com.sb.web.entities.Product;
import com.sb.web.entities.ProductAttribute;
import com.sb.web.entities.ProductType;
import com.sb.web.entities.User;
import com.sb.web.entities.VerificationToken;
import com.sb.web.enumerations.ContributionType;
import com.sb.web.enumerations.ProductEntrySource;
import com.sb.web.enumerations.ProductStatus;
import com.sb.web.projections.ProductProjection;
import com.sb.web.repositories.BasketItemRepository;
import com.sb.web.repositories.BusinessRepository;
import com.sb.web.repositories.CreditContributionRepository;
import com.sb.web.repositories.ProductRepository;
import com.sb.web.repositories.ProductTypeRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.repositories.VerificationTokenRepository;
import com.sb.web.request.dto.ContributionRequestDTO;
import com.sb.web.response.dto.AddProductResponseDTO;
import com.sb.web.response.dto.CompareBasketResponseDTO;
import com.sb.web.response.dto.ContributedPriceResponseDTO;
import com.sb.web.response.dto.CreditUpdateForUserResponse;
import com.sb.web.response.dto.RetailerBasketResponse;
import com.sb.web.service.dto.AddProductDTO;
import com.sb.web.service.dto.BusinessDTO;
import com.sb.web.service.dto.ContributePriceDTO;
import com.sb.web.service.dto.ProductDTO;
import com.sb.web.service.dto.ProductTypeDTO;
import com.sb.web.utils.EncryptionUtil;
import com.sb.web.utils.SaversBasketConstants;

import pl.coderion.model.Ingredient;
import pl.coderion.model.Nutriments;
import pl.coderion.model.ProductResponse;
import pl.coderion.model.SelectedImages;
import pl.coderion.service.OpenFoodFactsWrapper;
import pl.coderion.service.impl.OpenFoodFactsWrapperImpl;

@Transactional
@Service
public class BasketService {
	
	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";
		
	@Autowired
    private BusinessService businessService;
		
	@Autowired
	private BasketItemRepository basketItemRepository;
	
	@Autowired
	private BusinessRepository businessRepository;
		
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;	
	
	@Autowired
	private CreditContributionRepository rewardContributionRepository;
	
	@Autowired
	private UserActionRewarderService userActionRewarderService;
		
	@Transactional
    public void createVerificationTokenForUser(final User user, final String token) {			
        final VerificationToken myToken = new VerificationToken(token, user);
        user.setVerificationToken(myToken);
        tokenRepository.save(myToken);       
    }
	
	@Transactional
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
            .getTime() - cal.getTime()
            .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }
        user.setEnabled(true);        
        userRepository.save(user);
        return TOKEN_VALID;
    }
	
	@Transactional
	public CreditUpdateForUserResponse getCreditsForUser(Integer idUserContributor)  {
		CreditUpdateForUserResponse creditUpdateForUserResponse = new CreditUpdateForUserResponse();
		BigDecimal totalCredits = new BigDecimal(0);
			
		List<CreditContribution> creditContributions = rewardContributionRepository.retrieveCreditsForContributionUser(idUserContributor);
		
		for (CreditContribution creditContribution : creditContributions)
		{
			totalCredits = totalCredits.add(creditContribution.getCreditGranted());
			creditUpdateForUserResponse.setLastCreditDate(creditContribution.getLastModifiedDate());
		}
		
		creditUpdateForUserResponse.setTotalCredits(totalCredits);	
		
		return creditUpdateForUserResponse;
	}
	
	@Transactional
	public List<String> getAllBarcodesForSpecificUserBasket(Integer idUser) {
		List<String> barcodes = new ArrayList<>();
		List<Integer> allRelatedProductIds =  basketItemRepository.retrieveBasketItemIdsForUser(idUser);
		for (Integer idProduct : allRelatedProductIds) {
			barcodes.add(productRepository.getOne(idProduct).getProductBarCode());
		}		
		return barcodes;
	}
	
	
	@Auditable(actionType = AuditingActionType.INTERNAL_SCAN_PRODUCT_BARCODE)
	@Transactional
	public ProductDTO retrieveProductInformationFromAPI(String productCode, Integer idUser) {		
	  if (idUser != null) {
		  ContributionRequestDTO contributionRequestDTO = new ContributionRequestDTO();
		  contributionRequestDTO.setContributionType(ContributionType.SCAN_PRODUCT_BARCODE);
		  contributionRequestDTO.setScannedBarCode(productCode);
		  contributionRequestDTO.setUserIdToBeCredited(idUser);		
		  userActionRewarderService.grantRewardForUserAction(contributionRequestDTO);			
	  }	  	  
	  ProductDTO productDisplay = getProductInformationFromFoodFactsApi(productCode);
	  return productDisplay;
	}

	private ProductDTO getProductInformationFromFoodFactsApi(String productCode) {
		ProductDTO productDisplay = new ProductDTO();
		  OpenFoodFactsWrapper wrapper = new OpenFoodFactsWrapperImpl();
		  ProductResponse productResponse = wrapper.fetchProductByCode(productCode);

		    if (!productResponse.isStatus()) {
		      System.out.println("Status: " + productResponse.getStatusVerbose());
		      return null;
		    }

		    pl.coderion.model.Product product = productResponse.getProduct();

		    System.out.println("Name: " + product.getProductName());
		    productDisplay.setProductName(product.getProductName());
		    System.out.println("Generic name: " + product.getGenericName());
		    System.out.println("Product code: " + product.getCode());
		    productDisplay.setProductBarCode(product.getCode());

		    System.out.println("=== Ingredients ===");
		    if (product.getIngredients() != null ) {
			    for (Ingredient ingredient : product.getIngredients()) {
			      System.out.println(" * " + ingredient.getText());
			    }
		    }

		    System.out.println("=== Nutriments ===");
		    Nutriments nutriments = product.getNutriments();
		    if (nutriments != null) {
		      System.out.println(
		          String.format(
		              " * Calcium=%s%s", nutriments.getCalciumValue(), nutriments.getCalciumUnit()));
		      System.out.println(
		          String.format(" * Sugars=%s%s", nutriments.getSugarsValue(), nutriments.getSugarsUnit()));
		      System.out.println(String.format(" * Energy=%skcal", nutriments.getEnergyKcal()));
		    }

		    //System.out.println("=== Images ===");
		    SelectedImages selectedImages = product.getSelectedImages();
		    if (selectedImages != null) {
		      System.out.println(" * Front: " + selectedImages.getFront().getDisplay().getUrl());
		      productDisplay.setProductPictureUrl(selectedImages.getFront().getDisplay().getUrl());
		      //System.out.println(" * Ingredients: " + selectedImages.getIngredients().getDisplay().getUrl());
		      //System.out.println(" * Nutrition: " + selectedImages.getNutrition().getDisplay().getUrl());
		    }
		return productDisplay;
	}
	
	@Transactional
	public boolean getProductInformationByBarcodeAndBusiness(String barcode, Integer idBusiness) {		
		Product product = productRepository.getProductInformationByBarcodeAndBusiness(barcode, idBusiness);
		if (product != null) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public ProductDTO retrieveProductFromSvbDatabase(String barcode, boolean includeSameProducts) {
		 ProductDTO productDto = new ProductDTO();
		 List<Product> products = productRepository.getProductInformationByBarcode(barcode);		
		 if (products != null && products.size() > 0) {
			 Product product  = (Product) products.get(0);
			 enrichProductInfo(product , productDto);
			 
			 /** include same barcode products */
			 List<ProductDTO> sameProductList = new ArrayList<>();
			 if (includeSameProducts) {
				 products.remove(0);
				 for (Product sameProduct : products) {
					 ProductDTO sameProductDto = new ProductDTO();
					 enrichProductInfo(sameProduct , sameProductDto);
					 sameProductList.add(sameProductDto);
				 }
			 }
			 
			 productDto.setSameProducts(sameProductList);
		 }		 
		 return productDto;
	}
	
	/**
	 * Enrich with product information
	 * 
	 * @param product
	 * @param productDto
	 */
	private void enrichProductInfo(Product product, ProductDTO productDto) {
		productDto.setProductName(retrieveProductName(product.getIdProduct()));
		productDto.setUpdatedprice(retrieveLatestPrice(product.getIdProduct()));		 
		productDto.setLinkedIdBusiness(product.getIdBusiness().getIdBusiness());
		productDto.setProductBarCode(product.getProductBarCode());
		 
		/** Encrypt Product ID */
		productDto.setEncryptedProductKey(EncryptionUtil.encrypt(product.getIdProduct().toString()));
		/** Updated Price */
		productDto.setUpdatedprice(retrieveLatestPrice(product.getIdProduct()));
		/** Business name */
		if (product.getIdBusiness()!=null && !StringUtils.isEmpty(product.getIdBusiness().getRegisteredName())) {
			productDto.setBusinessName(product.getIdBusiness().getRegisteredName());
		}		
		 
		productDto.setType(retrieveProductTypeid(product));
		productDto.setImageUrl(retrieveProductImage(product));			
	}	
	
	@Transactional
	public ProductDTO retrieveProductToPopulateForm(Integer idProduct) {
		 Optional<Product> product = productRepository.findById(idProduct);
		 ProductDTO productDto = null;
		 if (product.isPresent()) {
			 Product convertedProduct = product.get();			 
			 productDto = modelMapper.map(convertedProduct, ProductDTO.class);
			 
			 productDto.setProductDescription(retrieveProductName(convertedProduct.getIdProduct()));
			 productDto.setUpdatedprice(retrieveLatestPrice(convertedProduct.getIdProduct()));		 
			 productDto.setLinkedIdBusiness(convertedProduct.getIdBusiness().getIdBusiness());		
			 
			 productDto.setType(retrieveProductTypeid(convertedProduct));
			 productDto.setImageUrl(retrieveProductImage(convertedProduct));	
			 productDto.setProductBarCode(convertedProduct.getProductBarCode());
		 }		 
		 return productDto;
	}
		
	@Transactional
	public List<ProductDTO> getListSuggestedProducts(String searchValue){
		List<ProductProjection> suggestedProducts = productRepository.searchProductsWithoutFilter(searchValue);
		
		Type listType = new TypeToken<List<ProductDTO>>(){}.getType();
		List<ProductDTO> suggestedProductDtos = modelMapper.map(suggestedProducts,listType);
		
		for (ProductDTO productDto : suggestedProductDtos) {
			productDto.setProductDescription(retrieveProductName(productDto.getIdProduct()));
		}
		
		return suggestedProductDtos;
	}	
	
	/**
	 * Compares user smart list to products found in retail shop.
	 * 
	 * @param idRetailer
	 * @param idSmartList
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Transactional
	@Auditable(actionType = AuditingActionType.INTERNAL_PERFORM_BASKETS_COMPARISON)
	public CompareBasketResponseDTO calculatePricesFromBasketForSpecificRetailer(Integer idUser) throws InterruptedException, ExecutionException {
		CompareBasketResponseDTO compareBasketResponseDTO = new CompareBasketResponseDTO();			
			
		List<BusinessDTO> businessList = businessService.retrieveAllBusinesses();
		
		List<CompletableFuture<RetailerBasketResponse>> completableFutures =
				businessList.stream().map(businessDTO -> businessService.calculatePricesFromBasketForSpecificRetailer(businessDTO, idUser)).collect(Collectors.toList());
		
		CompletableFuture<Void> allFutures = CompletableFuture
		        .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
		
		CompletableFuture<List<RetailerBasketResponse>> allCompletableFuture = allFutures.thenApply(future -> {
		    return completableFutures.stream()
		            .map(completableFuture -> completableFuture.join())
		            .collect(Collectors.toList());
		});
					
		CompletableFuture.allOf(allCompletableFuture).join();				
	    compareBasketResponseDTO.setRetailerBasketResponses(allCompletableFuture.get());
		
		
		return compareBasketResponseDTO;
	}
	
	
	/**
	 * Retrieves list of products based on barcodes from businesses.
	 * 
	 * @param idRetailer
	 * @param idSmartList
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	@Transactional
	@Auditable(actionType = AuditingActionType.INTERNAL_PRODUCT_BARCODE_COMPARISON)
	public CompareBasketResponseDTO calculatePricesFromBarCodeForSpecificRetailer(Integer idUser, List<String> productBarCodes) throws InterruptedException, ExecutionException {
		CompareBasketResponseDTO compareBasketResponseDTO = new CompareBasketResponseDTO();			
			
		List<BusinessDTO> businessList = businessService.retrieveAllBusinesses();
		
		List<CompletableFuture<RetailerBasketResponse>> completableFutures =
				businessList.stream().map(businessDTO -> businessService.calculatePricesPerProductBarcodesSpecificRetailer(businessDTO, productBarCodes, idUser)).collect(Collectors.toList());
		
		CompletableFuture<Void> allFutures = CompletableFuture
		        .allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));
		
		CompletableFuture<List<RetailerBasketResponse>> allCompletableFuture = allFutures.thenApply(future -> {
		    return completableFutures.stream()
		            .map(completableFuture -> completableFuture.join())
		            .collect(Collectors.toList());
		});
					
		CompletableFuture.allOf(allCompletableFuture).join();				
	    compareBasketResponseDTO.setRetailerBasketResponses(allCompletableFuture.get());		
				
		return compareBasketResponseDTO;
	}

	
	@Transactional
	public void updateBasketQuantityForUser(String[] values) {
		
		  StringBuffer sb = new StringBuffer();
	      for(int i = 0; i < values.length; i++) {
	         sb.append(values[i]);
	      }
	      String str = sb.toString();
		
		 List<String> basketTokens = new ArrayList<>();
	     StringTokenizer tokenizer = new StringTokenizer(str, "@");
	     while (tokenizer.hasMoreElements()) {
	    	basketTokens.add(tokenizer.nextToken());
	     }
	     
	     for (String token : basketTokens) {
	    	 
	    	  StringTokenizer basketItemTokenizer = new StringTokenizer(token, ".");
	    	  while (basketItemTokenizer.hasMoreElements()) {
	  	    	String idBasketItem = basketItemTokenizer.nextToken();	  	    	
	  	    	BasketItem basketItemToUpdate = basketItemRepository.getOne(Integer.valueOf(idBasketItem));	  	    	
	  	    	basketItemToUpdate.setQuantity(Integer.valueOf(basketItemTokenizer.nextToken()));
	  	    	
	  	        basketItemRepository.save(basketItemToUpdate);
	  	     } 	    	 
	     }		
	}	
	
	@Transactional
	public void deleteItemFromBasket(Integer idBasketItem) {	
		basketItemRepository.deleteBasketItemById(idBasketItem);
	}	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)	
	@Modifying
	public ContributedPriceResponseDTO contributePriceToProduct(ContributePriceDTO contributePriceDTO) {
		
		ContributedPriceResponseDTO contributedPriceResponseDTO = new ContributedPriceResponseDTO();		
		contributedPriceResponseDTO.setUserAuthenticated(Boolean.TRUE);
		
		/** Decrypt Product ID if Product ID is not provided **/
		if (contributePriceDTO.getIdProduct() == null) {
			String decryptedProductKey = contributePriceDTO.getEncryptedProductKey().replace("ENC","+");
			contributePriceDTO.setIdProduct(Integer.decode(EncryptionUtil.decrypt(decryptedProductKey)));
		}		
				
		if (contributePriceDTO != null && contributePriceDTO.getIdProduct() != null) {			
				Product product = productRepository.getOne(contributePriceDTO.getIdProduct());
		
				/**
				 * New Price
				 */
				Price price = new Price();				
				price.setPrice(new BigDecimal(contributePriceDTO.getContributedPrice()));
				price.setCurrencyCode("MUR");
							
				/**
				 * date price noted.
				 */
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Date dateTimePriceNoted = null;
				if (!StringUtils.isBlank(contributePriceDTO.getProductPriceNoted())) {			
					try {
						
						dateTimePriceNoted = sdf.parse(contributePriceDTO.getProductPriceNoted());						
						
					} catch (ParseException e) {}
				}else {				
					dateTimePriceNoted = new Date();
				}
				price.setDatePriceTimeNoted(dateTimePriceNoted);	
				
				if (product.getPriceList() != null && product.getPriceList().size() > 0) {
					product.getPriceList().add(price);			
					price.setIdProduct(product);
					
					/**
					 * Persist product
					 */
					Product productSaved = productRepository.save(product);					
					if (productSaved != null) {
						contributedPriceResponseDTO.setValidated(Boolean.TRUE);
					}
				}			
		}
		else 
		{
			contributedPriceResponseDTO.setValidated(Boolean.FALSE);
		}
		
		return contributedPriceResponseDTO;
	}
	
	
	@Auditable(actionType = AuditingActionType.INTERNAL_ADD_NEW_PRODUCT)
	@Transactional(propagation = Propagation.REQUIRES_NEW)	
	@Modifying
	public AddProductResponseDTO addProductToStore(AddProductDTO addProductDTO) {
		AddProductResponseDTO addProductResponseDTO = new AddProductResponseDTO();
		Calendar calendar = Calendar.getInstance();
		
		List<ProductAttribute> productAttributeList = new ArrayList<ProductAttribute>();
		Product product = new Product();
		
		if (addProductDTO.getIdBusiness() != null) {			
			Business business = businessRepository.getOne(addProductDTO.getIdBusiness().intValue());
			product.setIdBusiness(business);
		}
		
		/*
		 * Name
		 */
		ProductAttribute nameProductAttribute = new ProductAttribute();
		nameProductAttribute.setIdProduct(product);
		nameProductAttribute.setProductMapKey("NAME");
		nameProductAttribute.setProductMapValue(addProductDTO.getProductName());
		productAttributeList.add(nameProductAttribute);
		
		/*
		 * Product Type
		 */
		if (addProductDTO.getIdProductType() != null) {
			
			ProductAttribute typeProductAttribute = new ProductAttribute();
			typeProductAttribute.setIdProduct(product);
			typeProductAttribute.setProductMapKey("TYPE");
			typeProductAttribute.setProductMapValue(String.valueOf(addProductDTO.getIdProductType()));
			productAttributeList.add(typeProductAttribute);			
		}		
				
		/**
		 * Image Url
		 */
		
		ProductAttribute imgUrlProductAttribute = new ProductAttribute();
		imgUrlProductAttribute.setIdProduct(product);
		imgUrlProductAttribute.setProductMapKey("IMG");
		
		if (!StringUtils.isBlank(addProductDTO.getUploadedPictureUrl())) {
			imgUrlProductAttribute.setProductMapValue(addProductDTO.getUploadedPictureUrl());
		} else {
			
			if (addProductDTO.getModeOfEntry().equals("scanned")) {
				imgUrlProductAttribute.setProductMapValue(addProductDTO.getPictureUrlFromScanning());
			}
			else
			{
				imgUrlProductAttribute.setProductMapValue(SaversBasketConstants.NO_PRODUCT_IMAGE);
			}
			
		}
		productAttributeList.add(imgUrlProductAttribute);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dateProductCreate = null;
		try {			
			if (!StringUtils.isBlank(addProductDTO.getProductDateCreated())) {
				dateProductCreate = sdf.parse(addProductDTO.getProductDateCreated());
				calendar.setTime(sdf.parse(addProductDTO.getProductDateCreated()));				
				
			}else {				
				dateProductCreate = new Date();
				//calendar
				calendar.setTime(sdf.parse(addProductDTO.getProductDateCreated()));
			}		
		} catch (ParseException e) {}
		
		/**
		 * Set date product added
		 */
		product.setDateProductAdded(dateProductCreate);		
		
		/**
		 * Set product barcode
		 */
		product.setProductBarCode(addProductDTO.getProductBarcode());
		
		/**
		 * Mode Of Entry
		 */
		if (!StringUtils.isNotEmpty(addProductDTO.getModeOfEntry()) && addProductDTO.getModeOfEntry().equalsIgnoreCase("manual")) {
			product.setProductEntrySource(ProductEntrySource.SAVERSBASKETAPI);
		}else {
			product.setProductEntrySource(ProductEntrySource.FOODFACTSAPI);
		}
			
		/**
		 * Price
		 */
		List<Price> priceList = new ArrayList<Price>();		
		Price price = new Price();
		price.setIdProduct(product);
		price.setPrice(new BigDecimal(addProductDTO.getPrice()));	
		price.setCurrencyCode("MUR");	
		price.setDatePriceTimeNoted(dateProductCreate);
		
		/**
		 * Check price validity
		 */
		/**
		Integer priceDaysValidity = addProductDTO.getPriceDaysValidity();
		
		String endPriceStringDate = null;
		Date endPriceDate = null;
		try {
			
			if (priceDaysValidity != null) {
			
				//Number of Days to add
		        calendar.add(Calendar.DAY_OF_MONTH, priceDaysValidity);  
		        //Date after adding the days to the current date
		        endPriceStringDate = sdf.format(calendar.getTime()); 
		        
		        //convert string date
		        endPriceDate = sdf.parse(endPriceStringDate);
				price.setEndPriceDate(endPriceDate);						
					
			}
			else
			{
				//Number of Days to add
		        calendar.add(Calendar.DAY_OF_MONTH, 30);  
		        //Date after adding the days to the current date
		        endPriceStringDate = sdf.format(calendar.getTime()); 
		        
		        //convert string date
		        endPriceDate = sdf.parse(endPriceStringDate);
				price.setEndPriceDate(endPriceDate);			
			}
		
		}
		catch (ParseException e) {}
		*/
		
		priceList.add(price);		
		product.setProductAttributeList(productAttributeList);			
		product.setPriceList(priceList);
		
		/**
		 * Need to add product control mechanism
		 */
		product.setProductStatus(ProductStatus.APPROVED);
		
		
		/**
		 * call method to save product
		 */
		Product productCreated = productRepository.save(product);
		/**
		 * add validation flags
		 */		
		if (productCreated != null) {
			addProductResponseDTO.setValidated(true);			
		} else {
			addProductResponseDTO.setValidated(false);			
		}
		
		return addProductResponseDTO;
	}
	
	public List<ProductTypeDTO> getAllProductTypes(){		
		List<ProductType> lstProductType = productTypeRepository.findAllByOrderByCodeAsc();		
		Type listType = new TypeToken<List<ProductTypeDTO>>(){}.getType();
		List<ProductTypeDTO> listProductTypeDto = modelMapper.map(lstProductType,listType);
		
		return listProductTypeDto;
	}
	
	PropertyMap<Business, BusinessDTO> skipModifiedFieldsMap = new PropertyMap<Business, BusinessDTO>() {
	      protected void configure() {
	         //skip().setProductList(null);	       
	     }
	   };
	
	   
	 @Transactional(noRollbackFor= Exception.class, propagation = Propagation.REQUIRES_NEW)	
	public List<BusinessDTO> getAllValidBusinesses(){
		
		if (skipModifiedFieldsMap == null) {
			this.modelMapper.addMappings(skipModifiedFieldsMap);
		}		
		List<Business> lstBusiness = businessRepository.findAll();		
		Type listType = new TypeToken<List<BusinessDTO>>(){}.getType();
		List<BusinessDTO> listBusinessDTO = modelMapper.map(lstBusiness,listType);
		
		return listBusinessDTO;
	}
	

	public String retrieveProductName(Integer idProduct) {
		String productName = "";
		Product product = productRepository.getOne(idProduct);
		
		List<ProductAttribute> productAttributes = product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			if (productAttribute.getProductMapKey().equals("NAME")) {
				productName = productAttribute.getProductMapValue();
			}
		}			
		return productName;
	}
	
	public BigDecimal retrieveLatestPrice(Integer idProduct) {		 
	    Product product = productRepository.getOne(idProduct);    	
    	Price lastElement = null;	    	
    	for (Iterator collectionItr = product.getPriceList().iterator(); collectionItr.hasNext(); ) {
      	  lastElement = (Price) collectionItr.next();
      	}
      	
      	if (lastElement != null && lastElement.getPrice() != null) {
      		return lastElement.getPrice() ;
      	}	    	
    	return null;
	}
	
	@Transactional
	public String retrieveProductImage(Product product) {		
		String productImage = "";
		List<ProductAttribute> productAttributes = product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			if (productAttribute.getProductMapKey().equals("IMG")) {
				productImage = productAttribute.getProductMapValue();
			}
		}			
		return productImage;
	}
	
	@Transactional
	public String retrieveProductTypeid(Product product) {
		
		String productTypeKey = "";
		List<ProductAttribute> productAttributes = product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			if (productAttribute.getProductMapKey().equals("TYPE")) {
				productTypeKey = String.valueOf(productAttribute.getIdProductAttribute());
			}
		}
		
		return productTypeKey;
	}
}
