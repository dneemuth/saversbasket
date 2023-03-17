package com.sb.web.utils;

import org.springframework.beans.factory.annotation.Value;

public class SaversBasketConstants {
	
	@Value("${saversbasket-hostname-cookie}")
	public static final String DOMAIN_COOKIE="";
	
	public static final String JWT_TOKEN_COOKIE = "JWT-TOKEN";
	
	/**
	 * Support email
	 */
	public static final String SBASKET_SUPPORT_EMAIL = "info.svbasket@gmail.com";
	
	
	/**
	 * Default Password
	 */
	public static final String SBASKET_DEFAULT_PASSWORD = "PPa55word@SvB";
	
	
	/**
	 * No Product Picture
	 */
	public static final String NO_PRODUCT_IMAGE = "/images/product/no-product-picture.png";
	
	/**
	 * Pagination
	 */
	public static final int SEARCH_RESULT_PAGINATION_MAX = 20;
	
	/**
	 * Scale
	 */
	public static final int SCALE = 2;	
	
	/* System Settings Constant */
	public static final String LOTTERY_DRAW_PARTICIPATION_LIMIT = "LOTTERY_DRAW_PARTICIPATION_LIMIT";
	public static final String ADMIN_INITIALIZE_FLAG= "ADMIN_INITIALIZE_FLAG";
	public static final String RUN_INITIAL_SEQUENCE_LOAD= "RUN_INITIAL_SEQUENCE_LOAD";
	public static final String ENABLE_REWARD_FEATURE= "ENABLE_REWARD_FEATURE";
	public static final String SITE_MAINTENANCE_FLAG_ON= "SITE_MAINTENANCE_FLAG_ON";
	public static final String BARCODE_API_KEY="BARCODE_API_KEY";

}
