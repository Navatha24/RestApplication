package unittests;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import java.util.List;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(ConcordionRunner.class)
public class CustomerDaoServiceControllerIntegrationTests {

	private String baseUri = "http://localhost:8080/SpringMVC/rest/assurity/customers/";
	private static final Logger LOG = LoggerFactory
			.getLogger(CustomerDaoServiceControllerIntegrationTests.class);
	
	public String getGreeting() {
        return "Hello World!";
    }
	
	
	public void testGetAllCustomers() {

		Integer[] actual = { 10, 11, 13, 14, 15, 16 };
		String[] expected = { "Sam", "Sam", "BokChoy", "BokChoy", "Keka",
				"SunStone" };

		// Then
		expect().statusCode(200)
				.body("customer_id", hasItems(actual), "customer_name",
						hasItems(expected))
				.header("Content-Type", "application/json")

				// Given
				.when()

				// When
				.get(baseUri + "getallcustomers");
	}

	
	public void testStatusNotFound() {
		expect().statusCode(404).when().get(baseUri + "notfound");
	}

	
	public void testGetCustomerWithId() {

		int customerId = 11;
		String expectedCustomerName = "Sam";

		given().pathParam("customer_id", customerId).expect()
				.body("customer_id", equalTo(customerId))
				.body("customer_name", equalTo(expectedCustomerName)).when()
				.get(baseUri + "getcustomer/{customer_id}");
	}

	
	public void testUpdateCustomerWithName() {
		int customerId = 11;
		String customerName = "Sam";
		String newCustomerName = "Navatha";

		given().pathParam("customer_id", customerId)
				.pathParam("customer_name", newCustomerName)
				.expect()
				.statusCode(200)
				.body("customer_id", equalTo(customerId), "customer_name",
						equalTo(newCustomerName))
				.put(baseUri + "updatecustomer/{customer_id}/{customer_name}");

		given().pathParam("customer_id", customerId)
				.pathParam("customer_name", customerName)
				.expect()
				.statusCode(200)
				.body("customer_id", equalTo(customerId), "customer_name",
						equalTo(customerName))
				.put(baseUri + "updatecustomer/{customer_id}/{customer_name}");

	}

	
	public void testGetCustomerIdWithName() {
		String customerName = "Sam";
		Integer[] actual = { 10, 11 };
		given().pathParam("customer_name", customerName).expect()
				.statusCode(200).body("customer_id", hasItems(actual))
				.get(baseUri + "getcustomerid/{customer_name}");

	}


	public void testRegisterNewCustomerAndDeleteNewCustomer() {
		String newCustomer = "Jasmine";

		given().pathParam("customername", newCustomer).expect().statusCode(200)
				.body("customer_name", hasItems(newCustomer))
				.post(baseUri + "registernewcustomer/{customername}");

		Response response = given().pathParam("customername", newCustomer).get(
				baseUri + "getcustomerid/{customername}");
		String json = response.asString();
		JsonPath jp = new JsonPath(json);
		List<?> customerIds = jp.get("customer_id");

		for (Object customerid : customerIds) {
			given().pathParam("customerid", customerid).expect()
					.statusCode(200)
					.delete(baseUri + "/deletecustomer/{customerid}");
		}

		for (Object customerid : customerIds) {
			given().pathParam("customer_id", customerid).expect()
					.statusCode(500).get(baseUri + "getcustomer/{customer_id}");
		}

	}

}
