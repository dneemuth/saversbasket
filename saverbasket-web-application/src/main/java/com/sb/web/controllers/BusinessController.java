package com.sb.web.controllers;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sb.web.entities.User;
import com.sb.web.exception.CustomException;
import com.sb.web.request.dto.BusinessRequestDTO;
import com.sb.web.request.dto.SetupBusinessRequestDTO;
import com.sb.web.request.dto.SetupCampaignRequestDTO;
import com.sb.web.response.dto.ApiBaseResponse;
import com.sb.web.response.dto.BusinessResponseDTO;
import com.sb.web.response.dto.UserResponseDTO;
import com.sb.web.service.BusinessFinderService;
import com.sb.web.service.BusinessService;
import com.sb.web.service.UserService;
import com.sb.web.service.dto.UserDataDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/business")
@Api(tags = "users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BusinessController {

	@Autowired
	private UserService userService;

	@Autowired
	private BusinessService businessService;

	@Autowired
	private BusinessFinderService businessFinderService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/findNearestLocations")
	@ApiOperation(value = "Return list of nearest locations to user position", response = BusinessResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public List<BusinessResponseDTO> findNearestLocations(
			@ApiParam("Business Locations Request") @RequestBody BusinessRequestDTO businessRequestDTO) {

		Type listType = new TypeToken<List<BusinessResponseDTO>>() {
		}.getType();
		List<BusinessResponseDTO> response = modelMapper
				.map(businessFinderService.searchNearbyBusinesses(businessRequestDTO), listType);
		return response;
	}

	@PostMapping("/setupBusiness")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "Setup business for user", response = String.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public ResponseEntity<?> setupBusiness(HttpServletRequest req,
			@ApiParam("Setup Business Request") @RequestBody SetupBusinessRequestDTO setupBusinessRequestDTO) {
		UserResponseDTO userResponseDTO = modelMapper.map(userService.whoami(req), UserResponseDTO.class);
		Boolean response = businessService.saveBusiness(userResponseDTO.getUsername(), setupBusinessRequestDTO);

		return ResponseEntity.ok(new ApiBaseResponse(response, "Business created:" + response, null));
	}

	@PostMapping("/setupTextAdCampaign")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "Create text ad campaign", response = String.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 422, message = "Username is already in use"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public ResponseEntity<?> setupTextAdCampaign(HttpServletRequest req,
			@ApiParam("Setup Text Campaign Request") @RequestBody SetupCampaignRequestDTO setupCampaignRequestDTO) {
		UserResponseDTO userResponseDTO = modelMapper.map(userService.whoami(req), UserResponseDTO.class);
		// Boolean response =
		// campaignService.saveTextualCampaign(userResponseDTO.getUsername(),
		// setupCampaignRequestDTO);

		// return ResponseEntity.ok(new ApiBaseResponse(response, "campaign created:" +
		// response, null));
		return null;
	}

	@DeleteMapping(value = "/{email}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "Deletes specific user by username")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 404, message = "The user doesn't exist"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public String delete(@ApiParam("Username") @PathVariable String username) {
		userService.delete(username);
		return username;
	}


	@GetMapping(value = "/me")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ApiOperation(value = "Returns current user's data", response = UserResponseDTO.class)
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), //
			@ApiResponse(code = 403, message = "Access denied"), //
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	public UserResponseDTO whoami(HttpServletRequest req) {
		return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
	}

}
