package com.sb.web.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.annotations.Auditable;
import com.sb.web.annotations.Auditable.AuditingActionType;
import com.sb.web.entities.Account;
import com.sb.web.entities.Business;
import com.sb.web.entities.Price;
import com.sb.web.entities.Product;
import com.sb.web.entities.ProductAttribute;
import com.sb.web.entities.User;
import com.sb.web.projections.BasketItemProjection;
import com.sb.web.projections.BusinessProjection;
import com.sb.web.repositories.BasketItemRepository;
import com.sb.web.repositories.BusinessAddressRepository;
import com.sb.web.repositories.BusinessRepository;
import com.sb.web.repositories.CountryRepository;
import com.sb.web.repositories.ProductAttributeRepository;
import com.sb.web.repositories.ProductRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.request.dto.SetupBusinessRequestDTO;
import com.sb.web.response.dto.RetailerBasketResponse;
import com.sb.web.service.dto.BusinessDTO;
import com.sb.web.service.dto.ProductDTO;

import me.xdrop.fuzzywuzzy.FuzzySearch;

@Transactional
@Service
public class BusinessService {
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private BusinessRepository businessRepository;	
	
	@Autowired
	private BusinessAddressRepository businessAddressRepository;	
	
	@Autowired
    private BasketItemRepository basketItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductAttributeRepository productAttributeRepository;
	
	@Autowired
	private CountryRepository countryRepository;	
		
	@Autowired
	private ModelMapper modelMapper;
	
	
	/**
	 * Setup business by anonymous user
	 * 
	 * @return
	 */

	@Transactional
	@Auditable(actionType = AuditingActionType.INTERNAL_SETUP_NEW_BUSINESS)
	@Modifying
	public Boolean saveBusinessFromAnonymousUser(Business business) {
	
		/**
		 * persist business
		 */	
		Business savedBusiness = businessRepository.save(business);
		
		if (savedBusiness != null) {
			return Boolean.TRUE;
		}
	
		return Boolean.FALSE ;
	}
		 
	 
	/**
	 * saves business
	 * 
	 * @return list of nearby locations
	 */
	@Transactional
	@Auditable(actionType = AuditingActionType.INTERNAL_SETUP_NEW_BUSINESS)
	public Boolean saveBusiness(String username , SetupBusinessRequestDTO setupBusinessRequestDTO){
		
		User user = modelMapper.map(userRepository.findByUsername(username), User.class);		
		Business business = modelMapper.map(setupBusinessRequestDTO, Business.class);		    
			
		//user.getBusinessCollection().add(business);		
		User userSaved = userRepository.save(user);
		
		if (userSaved != null) {
			return Boolean.TRUE;
		}
	
		return Boolean.FALSE ;
	}
	
	
	@Transactional
	public BusinessDTO getBusinessById(Integer idBusiness) {
		Business business = businessRepository.getOne(idBusiness);
		return modelMapper.map(business,BusinessDTO.class);
	}
	
	@Transactional
	public List<BusinessDTO> retrieveAllBusinesses(){
	   List<BusinessProjection> entityBizs = businessRepository.retrieveAllValidBusinesses();
	   
	   Type listType = new TypeToken<List<BusinessDTO>>(){}.getType();
	   List<BusinessDTO> postDtoList = modelMapper.map(entityBizs,listType);
		
	   return postDtoList;
	}
	
	
	@Transactional
	public ProductAttribute retrieveProductAttributes(Integer idProduct,String productMapKey) {		
		return productAttributeRepository.retrieveProductAttributes(idProduct, productMapKey);
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

	
	PropertyMap<Product, ProductDTO> skipModifiedFieldsMap = new PropertyMap<Product, ProductDTO>() {
	      protected void configure() {
	       //  skip().setIdBasketItem(null);	      
	     }
	   };
	   
	   
	public List<BasketItemProjection> getItemsFromCurrentBasket(Integer idUser){		
		return  basketItemRepository.retrieveBasketItemsForUser(idUser);		
	}
	
	
	/**
	 * Compares user smart list to products found in retail shop.
	 * 
	 * @param idRetailer
	 * @param idSmartList
	 */
	@Async("asyncExecutor")
	@Transactional
	public CompletableFuture<RetailerBasketResponse> calculatePricesFromBasketForSpecificRetailer(BusinessDTO businessDTO, Integer idUser) {
		
		RetailerBasketResponse retailerBasketReponse = new RetailerBasketResponse();
		List<ProductDTO> productsMatchedFromBusiness = null;
		List<Integer> productKeysMatched = null;
						
		if (skipModifiedFieldsMap == null) {
			this.modelMapper.addMappings(skipModifiedFieldsMap);
		}
		
		/**
		 * Retrieve basket items.
		 */
		List<Integer> productKeys = basketItemRepository.retrieveBasketItemIdsForUser(idUser);
		
		if (productKeys != null && productKeys.size() > 0) {
			
			/**
			 * Pre-fill total number of items in basket.
			 */
			retailerBasketReponse.setTotalProductItemsInBasket(productKeys.size());
			
			/**
			 * Match exact products for business
			 */
			List<Object[]> productsMatched = productRepository.matchExactProductsForBusiness(businessDTO.getIdBusiness(), productKeys, idUser);
				
			
			if (productsMatched != null && productsMatched.size() > 0) {
								
				productKeysMatched = new ArrayList<Integer>();	
				productsMatchedFromBusiness = new ArrayList<ProductDTO>();
				
				for (Object[] productMatched : productsMatched) {
					ProductDTO newProductDTO = new ProductDTO();
					
					Integer idProduct =(Integer) productMatched[0];
					Integer idBusiness =(Integer) productMatched[1];
					Integer quantity = (Integer)productMatched[2];					
												
					newProductDTO.setIdProduct(idProduct);
					newProductDTO.setLinkedIdBusiness(idBusiness);	
					
					/**
					 * Add to keys found.
					 */
					productKeysMatched.add(idProduct);
					
					//updated price
					newProductDTO.setUpdatedprice(retrieveLatestPrice(idProduct));
					//quantity
					newProductDTO.setQuantity(quantity);
					 //Image
					newProductDTO.setImageUrl(retrieveProductAttributes(idProduct,"IMG").getProductMapValue());
					 //product name
					newProductDTO.setProductDescription(retrieveProductAttributes(idProduct,"NAME").getProductMapValue());
					
					productsMatchedFromBusiness.add(newProductDTO);
				}				
			}			
		}
	
		
		/**
		 * Retrieve similar products
		 */
		List<ProductDTO> productSuggestionsFromBusiness = new ArrayList<ProductDTO>();
		for (Integer productKey : productKeys) {
			String productDesc = retrieveProductAttributes(productKey,"NAME").getProductMapValue();
			
			//check if any empty string at end of product description
			String splitProductDesc = "";
			if (productDesc.trim().lastIndexOf(" ") > 0) {			
				splitProductDesc = productDesc.substring(0, productDesc.trim().lastIndexOf(" "));
			}
			else
			{
				splitProductDesc = productDesc.trim();
			}
				
			List<Object[]> productSuggestions =  productRepository.lookupSimilarProductsForBusiness(businessDTO.getIdBusiness(), splitProductDesc, productKeysMatched);
			  
			if (productSuggestions != null && productSuggestions.size() > 0) {
				for (Object[] productSuggested : productSuggestions) {
					ProductDTO newProductDTO = new ProductDTO();
					
					Integer idProduct =(Integer) productSuggested[0];
					Integer idBusiness =(Integer) productSuggested[1];	
					Double relevance = (Double)productSuggested[2];	
					
					//set relevance to 2 d.p
					DecimalFormat df = new DecimalFormat("#.##");
									
					newProductDTO.setIdProduct(idProduct);
					newProductDTO.setLinkedIdBusiness(idBusiness);	
					newProductDTO.setRelevance(new BigDecimal(df.format(relevance)));
					
					//Integer quantityForProduct = basketItemRepository.getQuantityForBasketItem(idProduct, idUser);
					newProductDTO.setQuantity(1);
					
					//updated price
					newProductDTO.setUpdatedprice(retrieveLatestPrice(idProduct));
					//Image
					newProductDTO.setImageUrl(retrieveProductAttributes(idProduct,"IMG").getProductMapValue());
					 //product name
					String newProductDesc = retrieveProductAttributes(idProduct,"NAME").getProductMapValue();
					newProductDTO.setProductDescription(newProductDesc);
					
					/**
					 * verify if description matches.
					 */
					Integer confidenceLevelSuggestedMatching = FuzzySearch.ratio(productDesc,newProductDesc);					
					
					if (!productSuggestionsFromBusiness.contains(newProductDTO) && confidenceLevelSuggestedMatching > 85) {
						productSuggestionsFromBusiness.add(newProductDTO);
					}
				}
			} 		
		}		
		
		retailerBasketReponse.setBusinessName(businessDTO.getRegisteredName());
		
		//check for business logo
		if (businessDTO.getBusinessLogoUrl() != null) {
			retailerBasketReponse.setBusinessLogoUrl(businessDTO.getBusinessLogoUrl());
		}
		else
		{
			retailerBasketReponse.setBusinessLogoUrl("/img/logo/shop-green.png");
		}		
		
		retailerBasketReponse.setRetailerName(businessDTO.getRegisteredName());
		retailerBasketReponse.setProductsTracked(productsMatchedFromBusiness);
		retailerBasketReponse.setSuggestedProductsFromStore(productSuggestionsFromBusiness);
		retailerBasketReponse.setTotalBasketPricePerRetailer(retailerBasketReponse.getTotalBasketPricePerRetailer());
        
        return CompletableFuture.completedFuture(retailerBasketReponse);		
	}
	
	/**
	 * Retrieves list of product and prices from barcodes provided for specific business
	 * 
	 * @param businessDTO
	 * @param productBarCodes
	 * @param idUser
	 * @return
	 */
	@Async("asyncExecutor")
	@Transactional
	public CompletableFuture<RetailerBasketResponse> calculatePricesPerProductBarcodesSpecificRetailer(BusinessDTO businessDTO, List<String> productBarCodes, Integer idUser) {
		
		RetailerBasketResponse retailerBasketReponse = new RetailerBasketResponse();
		List<ProductDTO> productsMatchedFromBusiness = null;
		List<Integer> productKeysMatched = null;
						
		if (skipModifiedFieldsMap == null) {
			this.modelMapper.addMappings(skipModifiedFieldsMap);
		}
		
		/**
		 * Retrieve basket items.
		 */
		
		if (productBarCodes != null && productBarCodes.size() > 0) {
			
			/**
			 * Pre-fill total number of items in basket.
			 */
			retailerBasketReponse.setTotalProductItemsInBasket(productBarCodes.size());
			
			/**
			 * Match exact products for business
			 */
			List<Object[]> productsMatched = productRepository.matchExactProductsByBarcode(businessDTO.getIdBusiness(), productBarCodes);
				
			
			if (productsMatched != null && productsMatched.size() > 0) {
								
				productKeysMatched = new ArrayList<Integer>();	
				productsMatchedFromBusiness = new ArrayList<ProductDTO>();
				
				for (Object[] productMatched : productsMatched) {
					ProductDTO newProductDTO = new ProductDTO();
					
					Integer idProduct =(Integer) productMatched[0];
					Integer idBusiness =(Integer) productMatched[1];
					Integer quantity = (Integer)productMatched[2];
												
					newProductDTO.setIdProduct(idProduct);
					newProductDTO.setLinkedIdBusiness(idBusiness);	
					
					/**
					 * Add to keys found.
					 */
					productKeysMatched.add(idProduct);
					
					//updated price
					newProductDTO.setUpdatedprice(retrieveLatestPrice(idProduct));
					//quantity
					newProductDTO.setQuantity(quantity);				
					 //Image
					newProductDTO.setImageUrl(retrieveProductAttributes(idProduct,"IMG").getProductMapValue());
					 //product name
					newProductDTO.setProductDescription(retrieveProductAttributes(idProduct,"NAME").getProductMapValue());
					
					productsMatchedFromBusiness.add(newProductDTO);
				}				
			}			
		}			
		
		retailerBasketReponse.setBusinessName(businessDTO.getRegisteredName());
		
		//check for business logo
		if (businessDTO.getBusinessLogoUrl() != null) {
			retailerBasketReponse.setBusinessLogoUrl(businessDTO.getBusinessLogoUrl());
		}
		else
		{
			retailerBasketReponse.setBusinessLogoUrl("/images/about-us/shop-green.png");
		}		
		
		retailerBasketReponse.setRetailerName(businessDTO.getRegisteredName());
		retailerBasketReponse.setProductsTracked(productsMatchedFromBusiness);		
		retailerBasketReponse.setTotalBasketPricePerRetailer(retailerBasketReponse.getTotalBasketPricePerRetailer());
		
		/** Calculate savings */
		User user = userRepository.getOne(idUser);
		Account account = user.getAccount();
		if (account != null && account.getBudgetedAmount() != null && retailerBasketReponse.getTotalBasketPricePerRetailer() != null) {
			retailerBasketReponse.setPredictedSavings(account.getBudgetedAmount().subtract(retailerBasketReponse.getTotalBasketPricePerRetailer()));
		}
        
        return CompletableFuture.completedFuture(retailerBasketReponse);		
	}
	
	
	
	 public BigDecimal retrieveLatestPrice(Product product) {	    	
    	Price lastElement = null;	    	
    	for (Iterator collectionItr = product.getPriceList().iterator(); collectionItr.hasNext(); ) {
      	  lastElement = (Price) collectionItr.next();
      	}      	
      	if (lastElement != null && lastElement.getPrice() != null) {
      		return lastElement.getPrice() ;
      	}	    	
    	return null;
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
	
}
