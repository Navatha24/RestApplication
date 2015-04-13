package integrationtests;

import static com.jayway.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

@RunWith(ConcordionRunner.class)
public class CustomerDaoServiceControllerIntegrationTests {

	private String baseUri = "http://localhost:8080/SpringMVC/rest/assurity/customers";
	private int statuscode = 0;

	public String testGetCustomerWithId(int customerId) {
		Response response = given().pathParam("customerid", customerId).get(
				baseUri + "/getcustomer/{customerid}");

		JsonPath jp = extractJson(response);
		String customer_name = jp.get("customer_name");

		return customer_name;
	}

	public int testStatusCodes(String uri) {
		Response response = given().get(baseUri + uri);

		int statuscode = response.getStatusCode();

		return statuscode;
	}

	public List<String> testGetAllCustomers() {

		Response response = given().get(baseUri + "/getallcustomers");

		JsonPath jp = extractJson(response);
		List<String> customernames = jp.getList("customer_name");

		return customernames;
	}

	public List<?> testGetCustomerIdWithName(String customerName) {
		Response response = given().pathParam("customername", customerName)
				.get(baseUri + "/getcustomerid/{customername}");

		JsonPath jp = extractJson(response);
		List<?> customerid = jp.getList("customer_id");

		return customerid;
	}

	public List<String> testUpdateCustomerForIdWithName(int customerId,
			String newCustomerName) {
		List<String> customer = new ArrayList<String>();
		Response response = given()
				.pathParam("customerid", customerId)
				.pathParam("newcustomername", newCustomerName)
				.put(baseUri + "/updatecustomer/{customerid}/{newcustomername}");

		JsonPath jp = extractJson(response);
		customer.add(jp.get("customer_id").toString());
		customer.add(jp.getString("customer_name"));

		return customer;
	}

	public int testRegisterNewCustomer(String newCustomerName) {

		Response response = given().pathParam("customername", newCustomerName)
				.post(baseUri + "/registernewcustomer/{customername}");

		statuscode = response.statusCode();

		return statuscode;
	}

	public int testDeleteCustomer(String customername) {

		List<?> customerIds = testGetCustomerIdWithName(customername);

		for (Object customerid : customerIds) {
			Response response = given().pathParam("customerid", customerid)
					.delete(baseUri + "/deletecustomer/{customerid}");
			statuscode = response.getStatusCode();
		}

		return statuscode;

	}

	public boolean searchForCustomerName(String name) {
		List<String> customers = testGetAllCustomers();
		boolean isPresent = customers.contains(name);

		return isPresent;
	}

	private JsonPath extractJson(Response response) {
		String json = response.asString();
		JsonPath jp = new JsonPath(json);
		return jp;
	}

}
