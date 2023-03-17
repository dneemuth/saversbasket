package com.sb.web.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.web.entities.UserContribution;
import com.sb.web.enumerations.ContributionStatus;
import com.sb.web.repositories.UserContributionRepository;
import com.sb.web.response.dto.UploadArtifactResponseDTO;
import com.sb.web.service.dto.UserContributionDTO;

@Service
public class UserContributionService {
	
	@Autowired
	private UserContributionRepository userContributionRepository;	
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public Integer getAllContributionsByUser(Integer idUserContributor) {		
		return userContributionRepository.getAllContributionsByUser(idUserContributor).size();		
	}
	
	@Transactional
	public UploadArtifactResponseDTO uploadReceiptPicture(UserContributionDTO userContributionServiceDTO) {
		UploadArtifactResponseDTO uploadArtifactResponseDTO = new UploadArtifactResponseDTO();		
		
		UserContribution userContribution = modelMapper.map(userContributionServiceDTO, UserContribution.class);
		UserContribution userContribSaved =  userContributionRepository.save(userContribution);
		
		if (userContribSaved != null) {
			uploadArtifactResponseDTO.setArtifactUrl(userContribSaved.getArtifactUrl());
			uploadArtifactResponseDTO.setDateContributionAdded(userContribSaved.getDateContributionAdded());
			uploadArtifactResponseDTO.setContentType(userContribSaved.getContributionType().name());
			uploadArtifactResponseDTO.setIdUserContribution(userContribSaved.getIdUserContribution());
		}		
		
		return uploadArtifactResponseDTO;
	}
	
	
	@Transactional
	@Modifying
	public void updateContributionStatus(Integer contributionIdToUpdate,String error) {
		
		UserContribution userContribToUpdate =  userContributionRepository.getOne(contributionIdToUpdate);

		if (error.equals("true")) {
			userContribToUpdate.setContributionStatus(ContributionStatus.FAILED);
		} else {
			userContribToUpdate.setContributionStatus(ContributionStatus.SUCCESS);
		}
		
		userContribToUpdate.setUpdatedDateContributionStatus(new Date());
		
		userContributionRepository.save(userContribToUpdate);
		
	}

}
