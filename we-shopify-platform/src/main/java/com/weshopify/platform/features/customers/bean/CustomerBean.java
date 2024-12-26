package com.weshopify.platform.features.customers.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private String mobileNumber;
	private String isSelfReg="false";

}
