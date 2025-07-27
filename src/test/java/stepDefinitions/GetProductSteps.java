package stepDefinitions;  // ✅ Must match folder name exactly

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
        response = apiClient
                .setBaseUri(ConfigManager.get("base.url"))
                .addHeader("Authorization", LoginSteps.authToken) // ✅ use token from Hooks
                .addHeader("Content-Type", "application/json")
                .post(endpoint);
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
        System.out.println("✅ Total Products: " + response.jsonPath().getInt("count"));
    }
}
