package unittests;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.spring.controller.CustomerDaoServiceController;
import org.spring.dao.CustomerDaoService;
import org.spring.model.Customer;
import org.springframework.test.web.servlet.MockMvc;

public class CustomerDaoServiceUnitTests {

	@Mock
	private CustomerDaoService customerDaoService;

	@InjectMocks
	private CustomerDaoServiceController customerDaoServiceController;

	private MockMvc mockMvc;

	@Test
	public void testgetAllCustomers() throws Exception {

		Customer customer1 = new Customer(1, "Navatha");
		Customer customer2 = new Customer(2, "Assurity");

		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);

		when(customerDaoService.getAll()).thenReturn(customers);
		
		List<Customer> actual=customerDaoServiceController.getAll();

		assertThat(actual,is(customers));

	}

}
