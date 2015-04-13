package unittests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.spring.model.Customer;

public class CustomerModelTest {

	@Test
	public void testCustomerModelWithoutData() {
		Customer customer = new Customer();
		assertEquals(0, customer.getCustomer_id());
		assertNull(customer.getCustomer_name());
	}

	@Test
	public void testCustomerModelWithData() {
		Customer customer = new Customer(1, "Navatha");
		assertEquals(1, customer.getCustomer_id());
		assertEquals("Navatha", customer.getCustomer_name());
	}
}
