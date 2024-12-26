package com.weshopify.platform.features.customers.commons;

public enum CustomerSearchOptions {

	ByEmail("ByEmail","email"),
	ByUserName("ByUserName","userName"),
	ByMobile("ByMobile","mobileNumber");
	
	String searchType;
	String searchField;
	CustomerSearchOptions(String searchType, String searchField) {
		this.searchField=searchField;
		this.searchType=searchType;
	}
	
	
	
}
