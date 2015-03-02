package org.spring.controller;

import java.util.List;

import org.spring.dao.CustomerDao;
import org.spring.model.Customer;
import org.spring.model.Shop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;


@Controller
@RequestMapping("/kfc/brands")
public class JSONController {

	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	public @ResponseBody
	Shop getShopInJSON(@PathVariable String name) {

		Shop shop = new Shop();
		shop.setName(name);
		shop.setStaffName(new String[] { "mkyong1", "mkyong2" });

		return shop;

	}
	
	@RequestMapping(value="/getallcustomers",method = RequestMethod.GET)
	public @ResponseBody  List<Customer> getAll() {
		
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"bean.xml");
		CustomerDao customer = (CustomerDao) context.getBean("customerdao");
			
		List<Customer> customers = customer.getAll();
		//System.out.println("Hello"+customers.get(1));
		return customers;
		
	}

}