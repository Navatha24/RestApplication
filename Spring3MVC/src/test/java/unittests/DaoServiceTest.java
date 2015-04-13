package unittests;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.spring.dao.CustomerDaoService;
import org.spring.model.Customer;

public class DaoServiceTest {
	private CustomerDaoService customerDaoService;
	private final int CUSTOMER_ID = 1;
	private final String CUSTOMER1 = "Navatha";
	private final String CUSTOMER2 = "Assurity";

	@Before
	public void setUp() {
		//customerDaoService = mock(CustomerDaoService.class);
	}

	@Test
	public void add_NewCustomer_ShouldCreateCustomer() throws Exception {
		Customer customer = new Customer(CUSTOMER_ID, CUSTOMER1);
		customerDaoService.Create(CUSTOMER1);

		verify(customerDaoService, times(1)).Create(CUSTOMER1);
		verifyNoMoreInteractions(customerDaoService);

		Customer actual = customerDaoService.getCustomer(CUSTOMER_ID);

		assertThat(actual.getCustomer_id(), is(CUSTOMER_ID));
		assertThat(actual.getCustomer_name(), is(CUSTOMER1));

	}

	@Test
	public void getAll_ShouldReturnListOfCustomers() throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		Customer customer1 = new Customer(1, CUSTOMER1);
		Customer customer2 = new Customer(1, CUSTOMER2);
		customers.add(customer1);
		customers.add(customer2);

		when(customerDaoService.getAll()).thenReturn(customers);

		List<Customer> actual = customerDaoService.getAll();

		verify(customerDaoService, times(1)).getAll();
		verifyNoMoreInteractions(customerDaoService);

		assertThat(actual, is(customers));
	}

	@Test
	public void getCustomer_ShouldReturnCustomerWithId() throws Exception {
		Customer customer = new Customer(CUSTOMER_ID, CUSTOMER1);

		when(customerDaoService.getCustomer(CUSTOMER_ID)).thenReturn(customer);

		Customer actual = customerDaoService.getCustomer(CUSTOMER_ID);

		verify(customerDaoService, times(1)).getCustomer(CUSTOMER_ID);
		verifyNoMoreInteractions(customerDaoService);

		assertThat(actual, is(customer));
	}

	@Test
	public void deleteCustomer_ShouldDeleteCustomerWithId() throws Exception {
		Customer customer = new Customer(CUSTOMER_ID, CUSTOMER1);

		when(customerDaoService.getCustomer(CUSTOMER_ID)).thenReturn(customer);

		Customer actual = customerDaoService.Delete(CUSTOMER_ID);

		verify(customerDaoService, times(1)).getCustomer(CUSTOMER_ID);
		verify(customerDaoService, times(1)).Delete(CUSTOMER_ID);
		verifyNoMoreInteractions(customerDaoService);

		assertThat(actual, is(customer));
	}

	@Test()
	public void findById_CustomerNotFound_ShouldThrowException()
			throws Exception {

		when(customerDaoService.getCustomer(CUSTOMER_ID)).thenThrow(
				new Exception(CUSTOMER1));
		customerDaoService.getCustomer(CUSTOMER_ID);
		verify(customerDaoService, times(1)).getCustomer(CUSTOMER_ID);
		verifyNoMoreInteractions(customerDaoService);
	}

	@Test
	public void update_CustomerFound_ShouldUpdateCustomer() throws Exception {
		Customer customer1 = new Customer(CUSTOMER_ID, CUSTOMER1);

		when(customerDaoService.Update(CUSTOMER_ID,CUSTOMER1)).thenReturn(customer1);

		Customer actual = customerDaoService.Update(CUSTOMER_ID, CUSTOMER2);

		verify(customerDaoService, times(1)).Update(CUSTOMER_ID, CUSTOMER2);
		verifyNoMoreInteractions(customerDaoService);

		assertThat(actual.getCustomer_id(), is(CUSTOMER_ID));
		assertThat(actual.getCustomer_name(), is(CUSTOMER2));
	}

}
