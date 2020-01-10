package com.example.football.dto;

import java.io.Serializable;

public class CountryDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3257904084464308856L;
	String countryId;
    String countryName;
	
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	

    
}
