package org.spring.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Types;

import javax.sql.DataSource;

import org.spring.interfaces.CustomerInterface;
import org.spring.mapppers.*;
import org.spring.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;


public class CustomerDao implements CustomerInterface {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public CustomerDao()
	{
		
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void Create(String customername) {
		String SQL = "insert into customers (customer_name) values (?)";

		jdbcTemplate.update(SQL, new Object[] { customername },
				new int[] { Types.VARCHAR });


		return;
	}

	@Override
	public void Update(int customerid, String customername) {
		String SQL = "update customers set customer_name = ? where customer_id = ?";

		jdbcTemplate.update(SQL, new Object[] {customername, customerid});

		return;

	}

	@Override
	public void Delete(int customerid) {
		String SQL = "delete from customers where customer_id = ?";

		jdbcTemplate.update(SQL, new Object[] { customerid },
				new int[] { Types.BIGINT });
		return;

	}

	@Override
	public Customer getCustomer(int customerid) {
		String SQL = "select * from customers where customer_id = ?";

		Customer customer = (Customer) jdbcTemplate.queryForObject(SQL,
				new Object[] { customerid }, new CustomerMapper());

		return customer;
	}

	@Override
	public List<Customer> getAll() {
		String SQL = "select * from customers";
		List<Customer> customerList = new ArrayList<Customer>();

		List<Map<String, Object>> empRows = jdbcTemplate.queryForList(SQL);

		for (Map<String, Object> empRow : empRows) {
			Customer customer = new Customer();
			customer.setCustomer_id((Integer) empRow.get("customer_id"));
			customer.setCustomer_name(String.valueOf(empRow
					.get("customer_name")));
			customerList.add(customer);
		}

		return customerList;

	}

}
