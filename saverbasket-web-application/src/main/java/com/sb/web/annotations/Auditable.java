package com.sb.web.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Auditable {

	AuditingActionType actionType();
	
	public enum AuditingActionType {
		INTERNAL_USER_REGISTRATION("Internal User Registration"),
		INTERNAL_USER_SIGN_IN("Internal User Signing IN"),
		INTERNAL_USER_SIGN_UP("Internal User Signing Up"),
		INTERNAL_ADD_NEW_PRODUCT("Internal User Add New Product"),
		INTERNAL_SETUP_NEW_BUSINESS("Internal User Setup_New_Business"),
		INTERNAL_PERFORM_BASKETS_COMPARISON("Perform Basket Comparison"),
		INTERNAL_PRODUCT_BARCODE_COMPARISON("Perform Product Barcode Search"),
		INTERNAL_SCAN_PRODUCT_BARCODE("EAN-13 Barcode Scan");
		
		private String description;
		
		/**
	     * @param text
	     */
		AuditingActionType(final String description) {
	        this.description = description;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return description;
	    }
		
		
	};
}
