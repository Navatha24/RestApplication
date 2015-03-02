package org.spring.interfaces;

import java.util.List;
import javax.sql.DataSource;
import org.spring.model.Customer;

public interface CustomerInterface {

	public void setDataSource(DataSource ds);

	// Create
	public void Create(String customername);

	// Read
	public List<Customer> getAll();

	// Read
	public Customer getCustomer(int customerid);

	// Update
	public void Update(int customerid, String customername);

	// Delete
	public void Delete(int customerid);

}
