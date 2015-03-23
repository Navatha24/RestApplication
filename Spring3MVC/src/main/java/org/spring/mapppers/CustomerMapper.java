package org.spring.mapppers;



import java.sql.ResultSet;
import java.sql.SQLException;
import org.spring.model.Customer;
import org.springframework.jdbc.core.RowMapper;

public class CustomerMapper implements RowMapper {

	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Customer customer = new Customer();
	     customer.setCustomer_id(rs.getInt("customer_id"));
	     customer.setCustomer_name(rs.getString("customer_name"));
	     return customer;
	}

}
