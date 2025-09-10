package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.AllureReport;
import utils.ApiClient;
import utils.ConfigManager;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderSteps {
    private Response response;
    private final ApiClient apiClient = new ApiClient();

   @When("I send a GET request to get product {string}")
    public void i_send_a_GET_request_to_get_product(String endpoint) {
       String token = Hooks.getScenarioContext().get("authToken", String.class);
       String UserId = Hooks.getScenarioContext().get("userId", String.class);

       if (token == null || token.isEmpty()) {
           throw new RuntimeException("❌ No authToken found in ScenarioContext. Make sure login ran successfully.");
       }
       if (UserId == null || UserId.isEmpty()) {
           throw new RuntimeException("❌ No UserID found in ScenarioContext. Make sure login ran successfully.");
       }

       response = apiClient
               .setBaseUri(ConfigManager.get("base.url"))
               .addHeader("Authorization", token)
               .get(endpoint + UserId);

       // ✅ Store response in ScenarioContext for other steps if needed later
       Hooks.getScenarioContext().set("getProductResponse", response);
       AllureReport.attachResponse(response);
   }
   @Then("the order API should return a {int} status code")
    public void the_order_API_should_return_aStatusCode(int statusCode) {
       response.then().statusCode(statusCode);
       System.out.println("--Verified status code: " + statusCode);
   }
   @Then("the response should contain a order list")
    public void the_response_should_contain_a_order_list() {
       response.then().body("count", greaterThan(0));
       response.then().body("data[0]._id", notNullValue());

       int productCount = response.jsonPath().getInt("count");
       System.out.println("✅ Total Products: " + productCount);
   }
}
