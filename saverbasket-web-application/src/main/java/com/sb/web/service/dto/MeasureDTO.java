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

public class MeasureDTO  {
	
	private Integer idMeasure;

	@ApiModelProperty(position = 0) 
    private String value;
	
	private String name;
		
	 public MeasureDTO() {
    }

	public Integer getIdMeasure() {
		return idMeasure;
	}

	public void setIdMeasure(Integer idMeasure) {
		this.idMeasure = idMeasure;
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

    
}
