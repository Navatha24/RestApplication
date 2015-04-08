package org.spring.controller;

import org.slf4j.*;

import java.util.List;

import org.spring.dao.CustomerDaoService;
import org.spring.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/assurity/customers")
public class CustomerDaoServiceController {

	private static final Logger LOG = LoggerFactory
			.getLogger(CustomerDaoServiceController.class);
	private ApplicationContext context;
	private CustomerDaoService customerDaoService;
	private Customer customer;
	private List<Customer> customers;

	public CustomerDaoServiceController() {
		LOG.info("intialising and loading the bean " + customerDaoService);

		context = new FileSystemXmlApplicationContext("classpath:bean.xml");
		customerDaoService = (CustomerDaoService) context
				.getBean("customerdao");

		LOG.info("successfully intialised and loaded the bean "
				+ customerDaoService);
	}

	@RequestMapping(value = "/registernewcustomer/{customername}", method = RequestMethod.POST)
	public @ResponseBody
	List<Customer> registerNewCustomer(@PathVariable String customername)
			throws Exception {

		LOG.info("registering new customer:" + customername);

		if (isEmpty(customername)) {
			throw new Exception("You need to give a customer name");
		}

		customerDaoService.Create(customername);
		customers = customerDaoService.getAll();

		LOG.info("successfully registered new customer:" + customername);

		return customers;
	}

	@RequestMapping(value = "/getallcustomers", method = RequestMethod.GET, headers = "Accept=application/json", produces = { "application/json" })
	public @ResponseBody
	List<Customer> getAll() throws Exception {
		LOG.info("retreiving all customers");

		customers = customerDaoService.getAll();

		LOG.info("successfully retreived all customers" + customers.get(1));

		return customers;
	}

	@RequestMapping(value = "/deletecustomer/{customerid}", method = RequestMethod.DELETE, headers = "Accept=application/xml, application/json")
	public @ResponseBody
	String deleteCustomer(@PathVariable int customerid) throws Exception {

		LOG.info("deleting customer with customerid:" + customerid);

		customer = customerDaoService.Delete(customerid);

		LOG.info("deleted customer with customerid:"
				+ customer.getCustomer_id() + " and customername:"
				+ customer.getCustomer_name());

		return "successfully deleted the customer " + customer;
	}

	@RequestMapping(value = "/updatecustomer/{customerid}/{newcustomername}", method = RequestMethod.PUT)
	public @ResponseBody
	Customer updateCustomer(@PathVariable int customerid,
			@PathVariable String newcustomername) throws Exception {

		LOG.info("updating customername to " + newcustomername
				+ " with customerid:" + customerid);

		customer = customerDaoService.Update(customerid, newcustomername);

		LOG.info("updated customername to " + customer.getCustomer_name()
				+ " with customerid:" + customer.getCustomer_id());

		return customer;
	}

	@RequestMapping(value = "/getcustomer/{customerid}", method = RequestMethod.GET)
	public @ResponseBody
	Customer getCustomer(@PathVariable int customerid) throws Exception {

		LOG.info("retreiving customer with customerid:" + customerid);

		customer = customerDaoService.getCustomer(customerid);

		LOG.info("successfully retreived customer with customerid:"
				+ customerid);

		return customer;
	}

	@RequestMapping(value = "/getcustomerid/{customername}", method = RequestMethod.GET)
	public @ResponseBody
	List<?> getCustomer(@PathVariable String customername) throws Exception {

		LOG.info("retreiving customerid with customername:" + customername);

		List<?> customerId = customerDaoService.getCustomerId(customername);

		LOG.info("successfully retreived customer with customerid:"
				+ customerId);

		return customerId;
	}

	public static boolean isEmpty(String name) {
		return (null == name) || name.trim().length() == 0;
	}

}