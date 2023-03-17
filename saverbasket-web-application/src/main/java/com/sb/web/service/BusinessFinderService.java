package com.sb.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sb.web.repositories.BusinessRepository;
import com.sb.web.repositories.BusinessRepository.Marker;
import com.sb.web.request.dto.BusinessRequestDTO;
import com.sb.web.service.dto.BusinessDTO;

@Service
public class BusinessFinderService {
	
	//@Value("${rmas.geofence.radius}")
	private String definedGeoFenceRadius = "";	
	
	@Autowired
	private BusinessRepository businessRepository;	
	
	@Autowired
	private ModelMapper modelMapper;
		 
	 
	/**
	 * Method to return all list of nearby businesses.
	 * 
	 * @return list of nearby locations
	 */
	public List<BusinessDTO> searchNearbyBusinesses(BusinessRequestDTO businessRequestDTO){		
		List<BusinessDTO> nearbyBusinesses = new ArrayList<BusinessDTO>();		
		/**  retrieve nearest points  */		
		Collection<Marker> markers = businessRepository.findNearestBusinesses(businessRequestDTO.getLatitude(), businessRequestDTO.getLongitude());
		
		/**
		 * Convert results
		 */
//		for (Marker marker : markers) {
//			 Optional<Business> optionalBusiness = businessRepository.findById(marker.getIdBusiness());
//			 Business roomEbusinessntity = optionalBusiness.get();
//			 BusinessDTO bDTO = modelMapper.map(roomEbusinessntity, BusinessDTO.class);
//			 
//		}
		
		markers.forEach(marker-> nearbyBusinesses.add(modelMapper.map(businessRepository.findById(marker.getIdBusiness()).get(), BusinessDTO.class))); 		
		return nearbyBusinesses;
	}

}
