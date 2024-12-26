package com.weshopify.platform.features.customers.service;

import java.util.List;

import com.weshopify.platform.features.customers.bean.CustomerBean;

public interface CustomerService {

	public CustomerBean saveCustomer(CustomerBean customerBean);
	public CustomerBean updateCustomer(CustomerBean customerBean);
	public List<CustomerBean> getAllCustomers();
	public List<CustomerBean> getAllCustomers(int curret_page,int noOfRecPerPage, String sortBy);
	public List<CustomerBean> getAllCustomers(String sortBy);
	public List<CustomerBean> deleteCustomerById(int id);
	public List<CustomerBean> deleteCustomer(CustomerBean customerBean);
	public CustomerBean getCustomerById(int id);
	public List<CustomerBean> searchCustomer(String searchKey, String searchText);
	
}
