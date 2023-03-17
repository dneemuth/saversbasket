package com.sb.web.projections;

import java.util.Date;
import java.util.List;

public interface ProductProjection {

	public Integer getIdProduct();
	
	public BusinessProjection getIdBusiness();
	
	public Date getDateProductAdded();
		
	public Integer getCreatedBy(); 	
	
	public List<PriceProjection> getPriceList();
}
