package org.spring.mapppers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.spring.model.CustomersAddressses;
import org.springframework.jdbc.core.RowMapper;

public class CustomersAddressRowMapper implements RowMapper {

	public CustomersAddressses mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CustomersAddressses customer = new CustomersAddressses();
		customer.setCustomer_id(rs.getInt("customer_id"));
		customer.setCustomer_name(rs.getString("cutomer_name"));
		customer.setCustomer_addressid(rs.getInt("customer_addressid"));
		customer.setCustomer_address(rs.getString("cutomer_address"));
		return customer;
	}

}
