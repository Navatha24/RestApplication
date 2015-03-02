package org.spring.main;

import org.spring.dao.CustomerDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringBootAndCrudApplication {

	public static void main(String[] args) {
		
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"bean.xml");
		CustomerDao customer = (CustomerDao) context.getBean("customerdao");
//		// customer.Create("Navatha");
//		// customer.Delete(3);
//		// System.out.println(customer.getCustomer(2));
		System.out.println(customer.getAll());

		//SpringApplication.run(SpringBootAndCrudApplication.class, args);

	}
}
