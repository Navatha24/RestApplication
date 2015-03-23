package org.spring.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.dao.CustomerDaoService;
import org.spring.model.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/assurity/customers")
public class JSONCustomerController {

	private static final Logger LOG = LoggerFactory.getLogger(JSONCustomerController.class);

	private ApplicationContext context;

	CustomerDaoService customerDaoService;

	public JSONCustomerController() {
		context = new FileSystemXmlApplicationContext("classpath:bean.xml");
		customerDaoService = (CustomerDaoService) context
				.getBean("customerdao");
		LOG.debug("successfully intialised and loaded the bean");
	}

	@RequestMapping(value = "/registernewcustomer/{customername}", method = RequestMethod.POST)
	public @ResponseBody
	String registerNewCustomer(@PathVariable String customername) {

		LOG.debug("registering new customer:" + customername);

		if (isEmpty(customername)) {
			String eMessage = "Error creating customer - no customername is given to create";
			return eMessage;
		}

		try {
			customerDaoService.Create(customername);

		} catch (Exception e) {
			String eMessage = "Error creatng the customer";
			return eMessage;
		}

		return "Done Successfully";
	}

	@RequestMapping(value = "/getallcustomers", method = RequestMethod.GET)
	public @ResponseBody
	List<Customer> getAll() {
		
		LOG.debug("retreiving all new customers");

		List<Customer> customers = null;

		try {
			customers = customerDaoService.getAll();
		} catch (Exception e) {

			e.printStackTrace();
		}

		return customers;
	}

	@RequestMapping(value = "/deletecustomer/{customerid}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	void deleteCustomer(@PathVariable int customerid) {
		
		LOG.debug("deleting customer with customerid:" + customerid);

		try {
			customerDaoService.Delete(customerid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@RequestMapping(value = "/updatecustomer/{customerid}/{customername}", method = RequestMethod.PUT)
	public @ResponseBody
	void updateCustomer(@PathVariable int customerid,
			@PathVariable String customername) {
		
		LOG.debug("updating customername  to "+customername+" with customerid:" + customerid);
		
		try {
			customerDaoService.Update(customerid, customername);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/getcustomer/{customerid}", method = RequestMethod.GET)
	public @ResponseBody
	Customer getCustomer(@PathVariable int customerid) {
		
		LOG.debug("retreiving customer with customerid:"+customerid);

		Customer customer = null;

		try {
			customer = customerDaoService.getCustomer(customerid);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return customer;
	}
	
	public static boolean isEmpty(String name) {
		return (null == name) || name.trim().length() == 0;
	}

}