/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sb.web.service.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author dilneemuth
 */

public class ProductTypeDTO  {
	
	
	@ApiModelProperty(position = 0) 
	private Integer idProductType;

	@ApiModelProperty(position = 1) 
    private String name;
	
	@ApiModelProperty(position = 2) 
    private String description;
	
	@ApiModelProperty(position = 3) 
	private String code;
	
		
	public Integer getIdProductType() {
		return idProductType;
	}
	public void setIdProductType(Integer idProductType) {
		this.idProductType = idProductType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
    
}
