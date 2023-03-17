package com.sb.web.enumerations;

public enum BusinessCategory {	
	
	SUPERMARKET, GENERAL_STORE, MINISUPEMARKET;
	
	public static BusinessCategory fromInteger(int x) {
        switch(x) {
        case 0:
            return SUPERMARKET;
        case 1:
            return GENERAL_STORE;
        case 2:
            return MINISUPEMARKET;
        }
        
        return null;
    }
}
