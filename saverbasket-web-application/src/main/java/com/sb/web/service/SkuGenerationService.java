package com.sb.web.service;

import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.entities.Product;
import com.sb.web.entities.ProductAttribute;
import com.sb.web.repositories.ProductRepository;
import com.sb.web.repositories.ProductTypeRepository;
import com.sb.web.service.dto.ProductDTO;
import com.sb.web.service.dto.SkuProductDTO;

@Service
@Transactional
public class SkuGenerationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SkuGenerationService.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductTypeRepository productTypeRepository;	

	@Autowired
	private ModelMapper modelMapper;
	
	public ProductDTO enrichProductWithProductKey(Integer idProduct) {
		
		ProductDTO productDTO = new ProductDTO();
		
		Product product = productRepository.getOne(idProduct);
		productDTO = modelMapper.map(product, ProductDTO.class);
		
		productDTO.setProductKey(createSalt());
		
		return 	productDTO;
	}
	
	
	public ProductDTO enrichProductWithSku(Integer idProduct) {
		ProductDTO productDTO = new ProductDTO();
		
		Product product = productRepository.getOne(idProduct);
		productDTO = modelMapper.map(product, ProductDTO.class);
		
		StringTokenizer st = new StringTokenizer(productDTO.getSkuIdentifier() ,"-");  
	     while (st.hasMoreTokens()) {  
	         productDTO.setType(st.nextToken()); 
	         productDTO.setSubType(st.nextToken());
	         productDTO.setBrand(st.nextToken());
	         productDTO.setAmount(st.nextToken());	  
	         productDTO.setMeasure(st.nextToken());
	     }  
		
		return 	productDTO;
	}
	
	
	public SkuProductDTO createSkuProduct(Integer idProduct) {
		
		SkuProductDTO skuProductDTO = new SkuProductDTO();
		
		String productType ="";
		String productSubType ="";
		String brand ="";
		String measure ="";
		String amount = "";
		
		Product product = productRepository.getOne(idProduct);		
		List<ProductAttribute> productAttributes=  product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			
			if (productAttribute.getProductMapKey().equals("TYPE")) {
				productType = productTypeRepository.findCodeById(Integer.valueOf(productAttribute.getProductMapValue()));
			}
			if (productAttribute.getProductMapKey().equals("AMOUNT")) {
				amount = productAttribute.getProductMapValue();	
			}
			if (productAttribute.getProductMapKey().equals("MEASURE")) {
				//measure = measureRepository.findCodeById(Integer.valueOf(productAttribute.getProductMapValue()));		
			}
			
		}
		
		skuProductDTO.setTypeCode(productType);
		skuProductDTO.setSubTypeCode(productSubType);
		skuProductDTO.setBrand(brand);
		skuProductDTO.setAmount(amount);
		skuProductDTO.setMeasure(measure);		
		
		return skuProductDTO;
		
	}
	
	
	public String generateUniqueProductIdentifier() {		
		return createSalt();
	}
	
	/**
	 * This adds a bit more randomness to the UUID generation but ensures each generated id is the same length
	 * @return
	 */
	public String createSalt() {
	    String ts = String.valueOf(System.currentTimeMillis());
	    String rand = UUID.randomUUID().toString();
	    return DigestUtils.sha256Hex(ts + rand);
	}
	
	public String generateSkuForProduct(Product product) {
		
		StringBuffer skuIdentifier = new StringBuffer();
		
		String productType ="";
		String productSubType ="";
		String brand ="";
		String amount = "";
		String measure ="";				
	
		List<ProductAttribute> productAttributes=  product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			
			if (productAttribute.getProductMapKey().equals("TYPE")) {
				productType = productTypeRepository.findCodeById(Integer.valueOf(productAttribute.getProductMapValue()));
			}		
			if (productAttribute.getProductMapKey().equals("AMOUNT")) {
				amount = productAttribute.getProductMapValue();	
			}
			if (productAttribute.getProductMapKey().equals("MEASURE")) {
				//measure = measureRepository.findCodeById(Integer.valueOf(productAttribute.getProductMapValue()));		
			}
			
		}
		
		skuIdentifier.append(productType);
		skuIdentifier.append("-");
		skuIdentifier.append(productSubType);
		skuIdentifier.append("-");
		skuIdentifier.append(brand);
		skuIdentifier.append("-");
		skuIdentifier.append(amount);
		skuIdentifier.append("-");
		skuIdentifier.append(measure);
		
		return skuIdentifier.toString();
	}
	
	
	
	public String generateSkuForProduct(Integer idProduct) {
		
		StringBuffer skuIdentifier = new StringBuffer();
		
		String productType ="";
		String productSubType ="";
		String brand ="";
		String amount = "";
		String measure ="";		
		
		Product product = productRepository.getOne(idProduct);		
		List<ProductAttribute> productAttributes=  product.getProductAttributeList();
		for (ProductAttribute productAttribute : productAttributes) {
			
			if (productAttribute.getProductMapKey().equals("TYPE")) {
				productType = productTypeRepository.findCodeById(Integer.valueOf(productAttribute.getProductMapValue()));
			}		
			if (productAttribute.getProductMapKey().equals("AMOUNT")) {
				amount = productAttribute.getProductMapValue();	
			}
			if (productAttribute.getProductMapKey().equals("MEASURE")) {
				//measure = measureRepository.findCodeById(Integer.valueOf(productAttribute.getProductMapValue()));		
			}			
		}
		
		skuIdentifier.append(productType);
		skuIdentifier.append("-");
		skuIdentifier.append(productSubType);
		skuIdentifier.append("-");
		skuIdentifier.append(brand);
		skuIdentifier.append("-");
		skuIdentifier.append(amount);
		skuIdentifier.append("-");
		skuIdentifier.append(measure);
		
		return skuIdentifier.toString();
	}

}
