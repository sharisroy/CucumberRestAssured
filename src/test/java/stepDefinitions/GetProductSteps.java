package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utils.ApiClient;
import utils.ConfigManager;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class GetProductSteps {

    private Response response;
    private final ApiClient apiClient = new ApiClient();

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String endpoint) {
        // ✅ Retrieve token from ScenarioContext
        String token = Hooks.getScenarioContext().get("authToken", String.class);

        if (token == null || token.isEmpty()) {
            throw new RuntimeException("❌ No authToken found in ScenarioContext. Make sure login ran successfully.");
        }

        // ✅ Make request with token
        response = apiClient
                .setBaseUri(ConfigManager.get("base.url"))
                .addHeader("Authorization", token)
                .post(endpoint);

        // ✅ Store response in ScenarioContext for other steps if needed later
        Hooks.getScenarioContext().set("getProductResponse", response);
    }

    @Then("the product API should return a {int} status code")
    public void the_product_api_should_return_a_status_code(Integer expectedStatus) {
        response.then().statusCode(expectedStatus);
        System.out.println("✅ Verified status code: " + expectedStatus);
    }

    @Then("the response should contain a product list")
    public void the_response_should_contain_a_product_list() {
        response.then().body("count", greaterThan(0));
        response.then().body("data[0]._id", notNullValue());

        int productCount = response.jsonPath().getInt("count");
        System.out.println("✅ Total Products: " + productCount);

        // ✅ Optional: Store product count for next steps
        Hooks.getScenarioContext().set("productCount", productCount);
    }
}
