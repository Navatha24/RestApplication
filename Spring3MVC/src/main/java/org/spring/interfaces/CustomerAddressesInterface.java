package org.spring.interfaces;

import java.util.List;

import javax.sql.DataSource;

import org.spring.model.Customer;
import org.spring.model.CustomersAddressses;

public interface CustomerAddressesInterface {
	
	public void setDataSource(DataSource dataSource);
	
	//Create
	public void CreateCustomer(String customerName,int customerAddress);
	
	//Read
	public List<CustomersAddressses> getAllCustomersAddress() ;
	
	//Update
	public void UpdateCustomer(int customerId, String customerName,int customeraddressId);
	
	//Delete
	public void DeleteCustomer(int customerId);

	public void CreateAddress(String address);
	public void UpdateAddress(int addressId,String customerNewAddress);
	public void DeleteAddress(int addressId);
	

}
