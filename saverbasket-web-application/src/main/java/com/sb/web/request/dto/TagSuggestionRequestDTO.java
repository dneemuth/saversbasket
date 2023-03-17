package com.sb.web.request.dto;

import java.util.List;

public class TagSuggestionRequestDTO {
	
	private List<String> existing;
	private String term;
	public List<String> getExisting() {
		return existing;
	}
	public void setExisting(List<String> existing) {
		this.existing = existing;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
	

}
