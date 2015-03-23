package org.spring.interfaces;

import java.util.List;
import javax.sql.DataSource;
import org.spring.model.Customer;

public interface CustomerInterface {

	public void setDataSource(DataSource ds);

	// Create
	public void Create(String customername) throws Exception;

	// Read
	public List<Customer> getAll() throws Exception;

	// Read
	public Customer getCustomer(int customerid)throws Exception;

	// Update
	public void Update(int customerid, String customername)throws Exception;

	// Delete
	public int Delete(int customerid)throws Exception;

}
