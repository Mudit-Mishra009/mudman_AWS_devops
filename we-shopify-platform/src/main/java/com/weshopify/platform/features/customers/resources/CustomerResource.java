package com.weshopify.platform.features.customers.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weshopify.platform.features.customers.bean.CustomerBean;
import com.weshopify.platform.features.customers.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomerResource {

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/customers/{sortBy}", method = RequestMethod.GET)
	public @ResponseBody List<CustomerBean> viewCustomers(@PathVariable("sortBy") String sortBy) {
		log.info("i am inn viewCustomerDashBoard page");
		List<CustomerBean> customerList = customerService.getAllCustomers(sortBy);
		return customerList;
	}

	@RequestMapping(value = "/customers/{currentPage}/{noOfRecPerPage}/{sortBy}", method = RequestMethod.GET)
	public @ResponseBody List<CustomerBean> viewCustomersWithPagination(@PathVariable("currentPage") String currentPage,
			@PathVariable("noOfRecPerPage") String noOfRecPerPage, @PathVariable("sortBy") String sortBy) {
		log.info("i am inn viewCustomerDashBoard page");
		log.info("curent page is:\t" + currentPage);
		log.info("no.Of Rec Per Page is:\t" + noOfRecPerPage);
		List<CustomerBean> customerList = null;
		if (currentPage != null && !currentPage.isEmpty() && !currentPage.equals("null")) {
			int presentPage = Integer.valueOf(currentPage) - 1;
			customerList = customerService.getAllCustomers(presentPage, Integer.valueOf(noOfRecPerPage), sortBy);
			/*
			 * model.addAttribute("currentPage", currentPage);
			 * model.addAttribute("noOfRecPerPage", noOfRecPerPage);
			 * if(!CollectionUtils.isEmpty(customerList)) {
			 * model.addAttribute("customerData", customerList);
			 * model.addAttribute("nextPage", Integer.valueOf(currentPage)+1);
			 * if(Integer.valueOf(currentPage) > 1) { model.addAttribute("previousPage",
			 * Integer.valueOf(currentPage)-1); }else { model.addAttribute("previousPage",
			 * 1); } }else { model.addAttribute("message", "No Further Records Found"); }
			 * }else { model.addAttribute("previousPage", 1); model.addAttribute("message",
			 * "No Further Records Found"); }
			 */
		}
		List<Integer> pagesList = new ArrayList<>();
		int totalRecords = customerService.getAllCustomers().size();
		System.out.println("total records from db are:\t" + totalRecords);
		/**
		 * taken float and rounded the value to get the heiest value of the fraction
		 * that means, if the total o.of rec are 5,no.of rec per page is 2 the last
		 * record i.e. 5th record will come on 3 rd page, so to take that 3 rd page ,
		 * take the round value
		 */
		double noOfPages = Math.round(Float.valueOf(totalRecords) / Float.valueOf(noOfRecPerPage));

		System.out.println("noOfPages after calculation are:\t" + noOfPages);
		if (noOfPages == 0) {
			noOfPages = 1;
		}
		for (int i = 1; i <= noOfPages; i++) {
			pagesList.add(i);
		}
		// model.addAttribute("totalNoOfRecords", pagesList);

		// return "customer-dashboard-pagination";
		return customerList;
	}

	/*
	 * @RequestMapping("/add-customer-view") public String addCustomerViewPage(Model
	 * model) { log.info("i am inn addCustomerViewPage page");
	 * model.addAttribute("customerFormBean", new CustomerBean()); return
	 * "add-customer"; }
	 */

	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public @ResponseBody CustomerBean saveCustomer(@RequestBody CustomerBean customerBean) {
		log.info("i am inn addCustomerViewPage page");
		log.info(customerBean.toString());

		customerService.saveCustomer(customerBean);
		/*
		 * if(customerBean.getIsSelfReg() != null &&
		 * Boolean.valueOf(customerBean.getIsSelfReg())) {
		 * customerService.saveCustomer(customerBean); }else {
		 * customerService.saveCustomer(customerBean);
		 * 
		 * }
		 */
		// return "redirect:/view-customerBoard";
		return customerBean;
	}

	@RequestMapping(value = "/customers", method = RequestMethod.PUT)
	public @ResponseBody CustomerBean updateCustomer(@RequestBody CustomerBean customerBean) {
		log.info("i am inn addCustomerViewPage page");
		log.info(customerBean.toString());

		customerService.saveCustomer(customerBean);
		/*
		 * if(customerBean.getIsSelfReg() != null &&
		 * Boolean.valueOf(customerBean.getIsSelfReg())) {
		 * customerService.saveCustomer(customerBean); }else {
		 * customerService.saveCustomer(customerBean);
		 * 
		 * }
		 */
		// return "redirect:/view-customerBoard";
		return customerBean;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, timeout = 3, rollbackFor = Throwable.class)
	@RequestMapping(value = "/customers/{customerId}", method = RequestMethod.DELETE)
	public @ResponseBody List<CustomerBean> deleteCustomerById(@PathVariable("customerId") String customerId) {
		log.info("i am inn deleteCustomerPage:\t" + customerId);

		List<CustomerBean> remainingCustomers = customerService.deleteCustomerById(Integer.valueOf(customerId));
		// return "redirect:/view-customerBoard";
		return remainingCustomers;
	}

	/*
	 * @RequestMapping(value = "/edit-customer/{customerId}",method =
	 * RequestMethod.GET) public String editCustomer(@PathVariable("customerId")
	 * String customerId, Model model) {
	 * log.info("i am inn deleteCustomerPage:\t"+customerId);
	 * 
	 * CustomerBean customerBean =
	 * customerService.getCustomerById(Integer.valueOf(customerId));
	 * model.addAttribute("customerFormBean", customerBean); return "edit-customer";
	 * }
	 */

	@RequestMapping(value = "/customers/search/{searchKey}/{searchText}", method = RequestMethod.GET)
	public @ResponseBody List<CustomerBean> searchCustomersByKey(@PathVariable("searchKey") String searchKey,
			@PathVariable("searchText") String searchText) {
		log.info("search key is:\t" + searchKey);
		log.info("search text is:\t" + searchText);
		List<CustomerBean> customerList = customerService.searchCustomer(searchKey, searchText);
		// return "customer-dashboard-pagination";
		return customerList;
	}

}
