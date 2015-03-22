package org.spring.main;

import java.net.URLClassLoader;
import java.util.Arrays;

import org.spring.dao.CustomerDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import sun.tools.jar.Main;

public class SpringBootAndCrudApplication {

	public static void main(String[] args) {
		
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src/main/resources/bean.xml");
		CustomerDao customer = (CustomerDao) context.getBean("customerdao");
//		// customer.Create("Navatha");
//		// customer.Delete(3);
//		// System.out.println(customer.getCustomer(2));
		System.out.println(customer.getAll());

		//SpringApplication.run(SpringBootAndCrudApplication.class, args);
		
		


	}
}
