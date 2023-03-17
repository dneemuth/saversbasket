package com.sb.web.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.entities.Basket;
import com.sb.web.entities.BasketItem;
import com.sb.web.entities.Price;
import com.sb.web.entities.Product;
import com.sb.web.entities.ProductAttribute;
import com.sb.web.entities.ProductType;
import com.sb.web.entities.User;
import com.sb.web.enumerations.BasketStatus;
import com.sb.web.projections.BasketItemProjection;
import com.sb.web.projections.BusinessProjection;
import com.sb.web.projections.PriceProjection;
import com.sb.web.projections.ProductProjection;
import com.sb.web.repositories.BasketItemRepository;
import com.sb.web.repositories.BasketRepository;
import com.sb.web.repositories.PageableProductRepository;
import com.sb.web.repositories.PriceRepository;
import com.sb.web.repositories.ProductAttributeRepository;
import com.sb.web.repositories.ProductRepository;
import com.sb.web.repositories.ProductTypeRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.request.dto.AddProductRequestDTO;
import com.sb.web.response.dto.AddProductResponseDTO;
import com.sb.web.response.dto.SearchBasketResponseDTO;
import com.sb.web.service.dto.ProductDTO;
import com.sb.web.service.dto.ProductTypeDTO;
import com.sb.web.utils.EncryptionUtil;
import com.sb.web.utils.SaversBasketConstants;

@Service
public class SearchBasketService {
	
	@Autowired
    private ProductAttributeRepository productAttributeRepository;
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private BasketItemRepository basketItemRepository;
	
	@Autowired
    private BasketRepository basketRepository;
	
	@Autowired
	private ModelMapper modelMapper;
		
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private PageableProductRepository pageableProductRepository;
	
	@Autowired
	private PriceRepository priceRepository;
	
	
	@Transactional
	public List<ProductTypeDTO> retrieveListProductType(){		
		List<ProductType> entityproductTypes = productTypeRepository.findAll();
		
		 Type listType = new TypeToken<List<ProductTypeDTO>>(){}.getType();
		 List<ProductTypeDTO> productTypeDtoList = modelMapper.map(entityproductTypes,listType);
		 return productTypeDtoList;		 
	}
	
	@Transactional
	public AddProductResponseDTO addProductToRetailStore(AddProductRequestDTO addProductRequestDTO) {
		AddProductResponseDTO  addProductResponseDTO = new AddProductResponseDTO();
		
		//create product
		Product product = new Product();	
		List<Price> priceCollection = new ArrayList<Price>();
		
		/**
		 * Price
		 */
		Price price = new Price();
		price.setPrice(addProductRequestDTO.getPrice());
		priceCollection.add(price);		
		
		
		/**
		 * Product Attributes
		 */
		List<ProductAttribute> productAttributeCollection = new ArrayList<ProductAttribute>();
		ProductAttribute productAttribute1 = new ProductAttribute();
		productAttribute1.setIdProduct(product);
		productAttribute1.setProductMapKey("name");
		productAttribute1.setProductMapValue(addProductRequestDTO.getProductName());
		
		ProductAttribute productAttribute2 = new ProductAttribute();
		productAttribute2.setIdProduct(product);
		productAttribute2.setProductMapKey("description");
		productAttribute2.setProductMapValue(addProductRequestDTO.getProductDescription());
		
		
		productAttributeCollection.add(productAttribute1);
		productAttributeCollection.add(productAttribute2);
			
		
		/**
		 * Retailer
		 */
		
		
		productRepository.save(product);
		
		return addProductResponseDTO;
	}
	
	@Transactional
	public String retrieveProductName(Product product) {
		
		String productName = "";
		List<ProductAttribute> productAttributes = product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			if (productAttribute.getProductMapKey().equals("NAME")) {
				productName = productAttribute.getProductMapValue();
			}
		}	
		
		return productName;
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
	 
	 
	 public PriceProjection retrieveLatestPriceForProduct(ProductProjection productProjection) {
	    	
		    PriceProjection lastElement = null;	    	
	    	for (Iterator collectionItr = productProjection.getPriceList().iterator(); collectionItr.hasNext(); ) {
	      	  lastElement = (PriceProjection) collectionItr.next();
	      	}
	      	
	      	if (lastElement != null ) {
	      		return lastElement;
	      	}	    	
	    	return null;
		 }
	
	 @Transactional
	 private List<BasketItemProjection> getItemsFromCurrentBasket(Integer idUser){				
		return  basketItemRepository.retrieveBasketItemsForUser(idUser);	
	}	
		
	@Transactional
	public SearchBasketResponseDTO find(String searchValue, Integer userId, String globalProductCategory,Integer pageNumber){
		SearchBasketResponseDTO searchBasketResponseDTO = new SearchBasketResponseDTO();
		List<ProductDTO> productDtos = new ArrayList<ProductDTO>();
		List<Integer> productsInUserBasket = null;
		
		if (userId != null ) {
			
			List<BasketItemProjection> userItemBaskets = getItemsFromCurrentBasket(userId);
			productsInUserBasket = new ArrayList<Integer>();
			if (userItemBaskets != null && userItemBaskets.size() > 0 ) {
				for (BasketItemProjection basketItem : userItemBaskets) {
					if (basketItem != null && basketItem.getIdProduct() != null ) {
						productsInUserBasket.add(basketItem.getIdProduct());
					}
				}
			}			
		}
		
		/**
		 * Handle pagination
		 */
		Pageable pageable = PageRequest.of(pageNumber, SaversBasketConstants.SEARCH_RESULT_PAGINATION_MAX);
		searchBasketResponseDTO.setMaxTotalElementsPerPage(SaversBasketConstants.SEARCH_RESULT_PAGINATION_MAX);
		
		/**
		 * retrieve search results based on filter
		 */
		Page<ProductProjection> entityProducts = null;
		if (globalProductCategory.equals("0")) {
			entityProducts  = pageableProductRepository.searchProductsWithoutFilter(searchValue, pageable);
		}		
		else
		{
			/**
			 * search keyword is empty.
			 */
			if(StringUtils.isEmpty(searchValue)) {
				entityProducts = pageableProductRepository.searchProductsByCategoryOnFilter(globalProductCategory, pageable);				
			}
			else
			/**
			 * search keyword not null.
			 */
			{
				//@TODO check sql here - 03/08/2020
				entityProducts = pageableProductRepository.searchProductsWithoutFilter(searchValue, pageable);				
			}		
		}
		
		/**
		 * Get number of pages
		 */
		searchBasketResponseDTO.setNumberOfPages(entityProducts.getTotalPages());
		
		/**
		 * Total number of elements
		 */
		searchBasketResponseDTO.setNumberOfTotalElements(entityProducts.getTotalElements());
		
		 
		for (ProductProjection product : entityProducts) {
			
			ProductDTO productDTO = new ProductDTO();
			
			/**
			 * Matching done in user basket
			 */
			if (productsInUserBasket != null) {
				if (productsInUserBasket.contains(product.getIdProduct())){
					productDTO.setAddedToBasket(Boolean.TRUE);
				}else {
					productDTO.setAddedToBasket(Boolean.FALSE);
				}
			}
			
			
			
			/**
			 * Retrieve business logo & name
			 */
			if (product != null && product.getIdBusiness() != null) {
				BusinessProjection business = product.getIdBusiness();
				
				String bizLogoUrl = business.getBusinessLogoUrl();
				if (bizLogoUrl != null & !StringUtils.isEmpty(bizLogoUrl)) {
					productDTO.setBusinessLogoUrl(bizLogoUrl);
				} else {
					productDTO.setBusinessLogoUrl("/images/about-us/shop-green.png");
				}	
				
				productDTO.setBusinessName(business.getRegisteredName());
			} else {
				BusinessProjection business = product.getIdBusiness();
				
				productDTO.setBusinessLogoUrl("/images/about-us/shop-green.png");
				productDTO.setBusinessName(business.getRegisteredName());
			}
			
			productDTO.setIdProduct(product.getIdProduct());
			
			/**
			 * Note user id and username that uploaded product.
			 */
			productDTO.setIdUser(product.getCreatedBy());			
			productDTO.setUsername(userRepository.getUserAndRole(product.getCreatedBy()).getUsername());
			
			
			ProductAttribute productAttribute = productAttributeRepository.retrieveProductAttributes(product.getIdProduct(),"NAME");			
			productDTO.setProductDescription(productAttribute.getProductMapValue());
			
			/**
			 * Price projection
			 */
			PriceProjection priceProjection = retrieveLatestPriceForProduct(product);			
			if (priceProjection != null) {
				
				productDTO.setUpdatedprice(priceProjection.getPrice());
				productDTO.setDatePriceNoted(priceProjection.getDatePriceTimeNoted());
				productDTO.setCurrencyCode(priceProjection.getCurrencyCode());
				productDTO.setDatePriceEnd(priceProjection.getEndPriceDate());
			}
			
			productDTO.setDateProductAdded(product.getDateProductAdded());
			
			ProductAttribute productImageAttribute = productAttributeRepository.retrieveProductAttributes(product.getIdProduct(),"IMG");
			if (productImageAttribute != null) {
				productDTO.setImageUrl(productImageAttribute.getProductMapValue());
			} else {
				productDTO.setImageUrl("/images/product/no-product-picture.png");
			}
				
			
			/** Encrypt Product ID */
			String encryptedProductKey = EncryptionUtil.encrypt(productDTO.getIdProduct().toString()).replace("+", "ENC");			
			productDTO.setEncryptedProductKey(encryptedProductKey);
			
			/**
			 * Check difference in Product Price to show sales tag.
			 */
			
			List<Price> pricesForProduct = priceRepository.retrievePricesForProduct(productDTO.getIdProduct());
			if (pricesForProduct != null && !pricesForProduct.isEmpty() && pricesForProduct.size() > 1) {
				BigDecimal lastPrice =  pricesForProduct.get(pricesForProduct.size() - 1).getPrice();
				BigDecimal previousPrice =  pricesForProduct.get(pricesForProduct.size() - 2).getPrice();
				
				if (previousPrice != null && lastPrice != null && previousPrice.intValue() > 0 && lastPrice.intValue() > 0) {
					BigDecimal percentagePriceDifference = (lastPrice.subtract(previousPrice).divide(previousPrice, 2, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(100));
					productDTO.setPercentageDifferencePrice(percentagePriceDifference);
				}
			}
			
			productDtos.add(productDTO);
		}
		
		searchBasketResponseDTO.setProducts(productDtos);
		return searchBasketResponseDTO;
	}
	
	
	public Integer retrieveSmartListStatistics(Integer idSmartList) {		
		//List<ProductItem> productItems =  productItemRepository.find(idSmartList);
		//return productItems.size();
		return null;
	}
	
	
	@Transactional
	public AddProductResponseDTO updateBasketItemsCount(Integer userId) {
		AddProductResponseDTO addProductResponseDTO = new AddProductResponseDTO();		
		
		User user = userRepository.getUserAndRole(userId);
		
		List<BasketItemProjection> basketItems = getItemsFromCurrentBasket(user.getIdUser());
		if (basketItems != null && basketItems.size() > 0) {
		
			/**
			 * calculate aggregated price
			 */
			BigDecimal aggregatedPrice = new BigDecimal(0);
			for (BasketItemProjection basketItem : basketItems) {
				Integer idProduct = basketItem.getIdProduct();
				if (idProduct != null) {
					Product product = productRepository.getOne(idProduct);
					if (product != null) {
						BigDecimal latestPrice = retrieveLatestPrice(product);
						
						if (basketItem != null && basketItem.getQuantity() != null && latestPrice != null) {
							Integer quantity = basketItem.getQuantity();					
							aggregatedPrice = aggregatedPrice.add(latestPrice.multiply(new BigDecimal(quantity)));
						}
						
					}
				}							
			}
			
			addProductResponseDTO.setNumberProductsInBasket(basketItems.size());
			addProductResponseDTO.setTotalPrice(aggregatedPrice);			
		}
		
		return addProductResponseDTO;		
	}

	
	@Transactional
	public AddProductResponseDTO addProductToUserBasket(Integer idProduct, Integer quantity, Integer userId) {
		
		AddProductResponseDTO addProductResponseDTO = null;
		
		User user = userRepository.getUserAndRole(userId);
		if (user != null) {
			addProductResponseDTO = new AddProductResponseDTO();
			
			Basket basketToUpdate = null;
			if (basketRepository.retrieveBasketForUser(userId).size() <= 0) {	
				
				basketToUpdate = new Basket();				
			
				basketToUpdate.setUser(user);
				basketToUpdate.setDateBasketRegistered(new Date());				
				
			} else {
				
				basketToUpdate = user.getBaskets().get(0);				
				
				basketToUpdate.setUser(user);
				basketToUpdate.setDateBasketRegistered(new Date());				
			}
						
			/**
			 * Add to basket before check.
			 */
						
			List<BasketItem> basketItems = new ArrayList<BasketItem>();
			
			BasketItem basketItem = new BasketItem();
			
			basketItem.setIdProduct(idProduct);
			basketItem.setDateProductAdded(new Date());	
			basketItem.setQuantity(quantity);
			basketItem.setIsActive(Boolean.TRUE);
			basketItem.setBasketStatus(BasketStatus.APPROVED);
			basketItem.setBasket(basketToUpdate);
			
			basketToUpdate.setBasketItems(basketItems);
			
			BasketItem savedBasketItem = basketItemRepository.save(basketItem);
			
			
			if (savedBasketItem != null) {
				addProductResponseDTO.setNumberProductsInBasket(getItemsFromCurrentBasket(userId).size());
			}
			
		}
		
		return addProductResponseDTO;
	}
	
	@Transactional
	public SearchBasketResponseDTO retrieveAllBasketItemsForUser(Integer idUser) {
		
		SearchBasketResponseDTO searchBasketResponseDTO = new SearchBasketResponseDTO();
		List<ProductDTO> products = new ArrayList();
		
		List<BasketItemProjection> basketItems = getItemsFromCurrentBasket(idUser);
		for (BasketItemProjection basketItem : basketItems) {
			ProductDTO productDTO = new ProductDTO();
			Product product =  productRepository.getOne(basketItem.getIdProduct());
			
			productDTO.setIdBasketItem(basketItem.getIdBasketItem());
			productDTO.setIdProduct(product.getIdProduct());
			productDTO.setProductName(retrieveProductName(product)); 
			productDTO.setImageUrl(retrieveProductImage(product));	
			productDTO.setDateProductAdded(product.getDateProductAdded());
			
			productDTO.setProductInStock(0);
			
			if (basketItem.getQuantity() != null && basketItem.getQuantity().intValue() > 0) {
				productDTO.setQuantity(basketItem.getQuantity());
			} else {
				productDTO.setQuantity(new Integer(0));
			}			
			
			productDTO.setUpdatedprice(toPrecision(retrieveLatestPrice(product),2));
			
			products.add(productDTO);
		}		
		
		searchBasketResponseDTO.setProducts(products);
		return searchBasketResponseDTO;
	}
	
	/**
	 * convenience method to scale up product price
	 * 
	 * @param dec
	 * @param precision
	 * @return
	 */
	private static BigDecimal toPrecision(BigDecimal dec, int precision) {
		BigDecimal convertedValue = null;
		if (dec != null) {
			if (dec.scale() != 2 ) {
		    String plain = dec.movePointRight(precision).toPlainString();
		    convertedValue = new BigDecimal(plain.substring(0, plain.indexOf("."))).movePointLeft(precision);
			}
			else
			{
				convertedValue = dec;
			}
		}		
		return convertedValue;
	}
	
	@Transactional	
	public String retrieveProductImage(Product product) {
		
		String productName = "";
		List<ProductAttribute> productAttributes = product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			if (productAttribute.getProductMapKey().equals("IMG")) {
				productName = productAttribute.getProductMapValue();
			}
		}	
		
		return productName;
	}
	
}
