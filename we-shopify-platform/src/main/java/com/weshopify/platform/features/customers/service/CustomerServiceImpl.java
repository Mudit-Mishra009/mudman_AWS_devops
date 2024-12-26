package com.weshopify.platform.features.customers.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.weshopify.platform.features.customers.bean.CustomerBean;
import com.weshopify.platform.features.customers.commons.CustomerSearchOptions;
import com.weshopify.platform.features.customers.domain.Customer;
import com.weshopify.platform.features.customers.repo.CustomerRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Value("${weshopify.app.search.keys}")
	private String searchKeys;

	@Override
	public CustomerBean saveCustomer(CustomerBean customerBean) {
		Customer customerDomain = new Customer();
		/**
		 * Convert the bean to domain as per the repository design, it will only
		 * accesspts the domains which are etities.
		 */
		BeanUtils.copyProperties(customerBean, customerDomain);
		customerRepo.save(customerDomain);
		BeanUtils.copyProperties(customerDomain, customerBean);

		return customerBean;
	}

	@Override
	public CustomerBean updateCustomer(CustomerBean customerBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerBean> getAllCustomers() {
		log.info("i am in getAllCustomers Method");
		Iterator<Customer> customerIt = customerRepo.findAll().iterator();
		List<CustomerBean> custBeanList = new ArrayList<>();
		while (customerIt.hasNext()) {
			Customer customer = customerIt.next();
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);
			custBeanList.add(custBean);
		}
		return custBeanList;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public List<CustomerBean> deleteCustomerById(int id) {
		log.info("iam in delete customer by id method");
		CustomerBean customerBean = getCustomerById(id);

		if (customerBean != null) {
			customerRepo.deleteById(id);
		} else {

		}

		return getAllCustomers();
	}

	@Override
	public List<CustomerBean> deleteCustomer(CustomerBean customerBean) {

		return null;
	}

	@Override
	public CustomerBean getCustomerById(int id) {
		log.info("i am in get CustomerById Method");
		Optional<Customer> customerOptional = customerRepo.findById(id);
		if (customerOptional == null || customerOptional.isEmpty()) {
			throw new RuntimeException("No Customer Found with the customer Id:\t" + id);
		}
		Customer customerDomain = customerOptional.get();
		CustomerBean custBean = new CustomerBean();
		BeanUtils.copyProperties(customerDomain, custBean);
		return custBean;
	}

	@Override
	public List<CustomerBean> getAllCustomers(int curret_page, int noOfRecPerPage, String sortBy) {
		PageRequest pageReq = PageRequest.of(curret_page, noOfRecPerPage, Direction.ASC, sortBy);

		Iterator<Customer> customerIt = customerRepo.findAll(pageReq).iterator();
		List<CustomerBean> custBeanList = new ArrayList<>();
		while (customerIt.hasNext()) {
			Customer customer = customerIt.next();
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);
			custBeanList.add(custBean);
		}
		return custBeanList;
	}

	@Override
	public List<CustomerBean> searchCustomer(String searchKey, String searchText) {
		List<Customer> customersList = null;
		// step-1: if the incoming search key is matching with the configured search key
		// then
		// do the search based on the configured search key dynamically
		if (CustomerSearchOptions.ByEmail.name().equals(searchKey)) {
			customersList = customerRepo.searchByEmail(searchText);
		} else if (CustomerSearchOptions.ByUserName.name().equals(searchKey)) {
			customersList = customerRepo.searchByUserName(searchText);
		} else if (CustomerSearchOptions.ByMobile.name().equals(searchKey)) {
			customersList = customerRepo.searchByMobile(searchText);
		} else {
			customersList = customerRepo.findAll();
		}
		List<CustomerBean> custBeanList = new ArrayList<>();
		customersList.forEach(customer -> {
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);
			custBeanList.add(custBean);
		});

		return custBeanList;
	}

	@Override
	public List<CustomerBean> getAllCustomers(String sortBy) {
		Sort sortOption = Sort.by(Direction.ASC, sortBy);
		List<Customer> customersList = customerRepo.findAll(sortOption);
		List<CustomerBean> custBeanList = new ArrayList<>();
		customersList.forEach(customer -> {
			CustomerBean custBean = new CustomerBean();
			BeanUtils.copyProperties(customer, custBean);
			custBeanList.add(custBean);
		});
		return custBeanList;
	}

}
