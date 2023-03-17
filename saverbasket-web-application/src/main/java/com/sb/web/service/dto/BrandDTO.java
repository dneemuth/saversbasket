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

public class BrandDTO  {
	
	private Integer idBrand;

	@ApiModelProperty(position = 0) 
    private String name;
	
	@ApiModelProperty(position = 1) 
    private String description;
		

    public Integer getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(Integer idBrand) {
		this.idBrand = idBrand;
	}

	public BrandDTO() {
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

    
}
