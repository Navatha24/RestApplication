package org.spring.main;

import org.spring.dao.CustomerDaoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringBootAndCrudApplication {

	public static void main(String[] args) {
		
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"src/main/resources/bean.xml");
		CustomerDaoService customer = (CustomerDaoService) context.getBean("customerdao");
		try {
			System.out.println(customer.getAll());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
