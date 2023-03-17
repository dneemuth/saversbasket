package com.sb.web.projections;

import java.math.BigDecimal;
import java.util.Date;

public interface PriceProjection {
	
	public BigDecimal getPrice();
	
	public Date getDatePriceTimeNoted();
	
	public Date getEndPriceDate();
	
	public String getCurrencyCode();
	
}
