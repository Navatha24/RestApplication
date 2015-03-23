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

public class CustomerDaoService implements CustomerInterface {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public CustomerDaoService() {

	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void Create(String customername) throws Exception {
		String SQL = "insert into customers (customer_name) values (?)";

		jdbcTemplate.update(SQL, new Object[] { customername },
				new int[] { Types.VARCHAR });
		
	}

	public void Update(int customerid, String customername) throws Exception {
		String SQL = "update customers set customer_name = ? where customer_id = ?";

		jdbcTemplate.update(SQL, new Object[] { customername, customerid });

		return;

	}

	public int Delete(int customerid) throws Exception{
		String SQL = "delete from customers where customer_id = ?";

		int status = jdbcTemplate.update(SQL, new Object[] { customerid });

		return status;

	}

	public Customer getCustomer(int customerid) throws Exception {
		String SQL = "select * from customers where customer_id = ?";

		Customer customer = (Customer) jdbcTemplate.queryForObject(SQL,
				new Object[] { customerid }, new CustomerMapper());

		return customer;
	}

	public List<Customer> getAll() throws Exception {
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
