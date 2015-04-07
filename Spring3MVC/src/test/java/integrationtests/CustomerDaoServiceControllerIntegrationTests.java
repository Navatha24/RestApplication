package integrationtests;

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

	private String baseUri = "http://localhost:8080/SpringMVC/rest/assurity/customers";

	public String testGetCustomerWithId(int customerId) {
		Response response = given().pathParam("customerid",customerId).get(	baseUri + "/getcustomer/{customerid}");
		JsonPath jp = extractJson(response);
		String customer_name=jp.get("customer_name");
		return customer_name;
	}
		
	public int testStatusCodes(String uri) {
		Response response = given().get(baseUri+uri );
		int statuscode=response.getStatusCode();
		return statuscode;
	}
	
	
	public List<String> testGetAllCustomers() {

		Response response = given().get(baseUri + "/getallcustomers");
		JsonPath jp = extractJson(response);
		List<String> customernames=jp.getList("customer_name");
		
		return customernames;
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
		JsonPath jp = extractJson(response);
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
	
	private JsonPath extractJson(Response response) {
		String json = response.asString();
		JsonPath jp = new JsonPath(json);
		return jp;
	}

}
