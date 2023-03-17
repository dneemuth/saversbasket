package com.sb.web.response.dto;

public class ApiBaseResponse {
	
	private Boolean success;
    private String message;
    private String jwtToken;
    private Object payload;

    public ApiBaseResponse(Boolean success, String message, String jwtToken) {
        this.success = success;
        this.message = message;
        this.jwtToken = jwtToken;
    }
    
    
    public ApiBaseResponse(Boolean success, String payload) {
        this.success = success;
        this.message = message;
        this.payload = payload;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
    
    

}
