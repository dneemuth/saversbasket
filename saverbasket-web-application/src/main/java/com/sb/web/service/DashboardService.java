package com.sb.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sb.web.entities.Business;
import com.sb.web.entities.Product;
import com.sb.web.entities.User;
import com.sb.web.repositories.BusinessRepository;
import com.sb.web.repositories.ProductRepository;
import com.sb.web.repositories.UserRepository;
import com.sb.web.response.dto.DashboardResponseDTO;

@Service
public class DashboardService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BusinessRepository businessRepository;
	
	@Autowired
	private ProductRepository productRepository;
		
	public DashboardResponseDTO gatherAllAnalytics() {		
		DashboardResponseDTO dashboardResponseDTO = new DashboardResponseDTO();
	
		/**
		 * Retrieves list of registered users
		 */
		List<User> userCollection = userRepository.findAll();
		dashboardResponseDTO.setNumberOfUsersRegistered(userCollection.size());
		
		/**
		 * Retrieves all businesses
		 */
		List<Business> businessCollection = businessRepository.findAll();
		dashboardResponseDTO.setNumberOfBusinessRegistered(businessCollection.size());
		
		
		/**
		 * List of uploaded products
		 */
		List<Product> productCollection = productRepository.findAll();
		dashboardResponseDTO.setNumberOfProductsUploaded(productCollection.size());
		
			
		return dashboardResponseDTO;
	}
	
	
	
}
