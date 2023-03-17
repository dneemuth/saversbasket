package com.sb.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sb.web.response.dto.DashboardResponseDTO;
import com.sb.web.service.DashboardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/dashboard")
@Api(tags = "dashboard")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@PostMapping("/retrieveStatistics")
	@ApiOperation(value = "retrieve all statistics from SaversBasket") 
	 @ApiResponses(value = {//
	      @ApiResponse(code = 400, message = "Something went wrong"), //
	      @ApiResponse(code = 403, message = "Access denied"), //
	      @ApiResponse(code = 422, message = "Username is already in use"), //
	      @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
	public DashboardResponseDTO retrieveStatistics(){	
		return dashboardService.gatherAllAnalytics();	
	}	  

}
