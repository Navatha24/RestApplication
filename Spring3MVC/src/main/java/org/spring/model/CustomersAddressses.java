package org.spring.model;

public class CustomersAddressses {
	private int customer_id;
	private String customer_name;
	private int customer_addressid;
	private String customer_address;
	
	public CustomersAddressses() {	}
		
	public CustomersAddressses(int customer_addressid, String customer_address,
			int customer_id, String customer_name) {
		
		this.customer_addressid = customer_addressid;
		this.customer_address = customer_address;
		this.customer_id = customer_id;
		this.customer_name = customer_name;
	}
	
	public int getCustomer_addressid() {
		return customer_addressid;
	}
	public void setCustomer_addressid(int customer_addressid) {
		this.customer_addressid = customer_addressid;
	}
	public String getCustomer_address() {
		return customer_address;
	}
	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	@Override
	public String toString() {
		return "CustomersAddress [customer_addressid=" + customer_addressid
				+ ", customer_address=" + customer_address + ", customer_id="
				+ customer_id + ", customer_name=" + customer_name + "]";
	}
		
	

}
