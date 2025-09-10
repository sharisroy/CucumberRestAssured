package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utils.AllureReport;
import utils.ApiClient;
import utils.ConfigManager;
import utils.JsonUtils;

public class AddToCardSteps {
    private Response response;
    private String jsonBody;
    private final ApiClient apiClient = new ApiClient();

    @Given("I have a payload from {string}")
    public void i_have_a_payload_from(String fileName) {
        String filePath = "src/test/resources/testdata/" + fileName;
        jsonBody = JsonUtils.readJsonFromFile(filePath);
        System.out.println("✅ Loaded payload from: " + filePath);
        AllureReport.attachPayload(jsonBody);
    }

    // ✅ renamed step to make it unique
    @When("I send a POST request to add-to-cart {string}")
    public void i_send_a_post_request_to_add_to_cart(String endpoint) {
        String token = Hooks.getScenarioContext().get("authToken", String.class);

        response = apiClient
                .setBaseUri(ConfigManager.get("base.url"))
                .addHeader("Authorization", token)
                .setBody(jsonBody)
                .post(endpoint);

        // ✅ Store response for other steps
        Hooks.getScenarioContext().set("addToCartResponse", response);

        System.out.println("📦 Add to Cart Response:");
        System.out.println(response.prettyPrint());
        AllureReport.attachResponse("Add to Cart Response", response);
    }

    @Then("I should receive a {int} status")
    public void i_should_receive_status(Integer statusCode) {
        // ✅ Retrieve stored response
        Response savedResponse = Hooks.getScenarioContext().get("addToCartResponse", Response.class);
        savedResponse.then().statusCode(statusCode);
        System.out.println("✅ Verified Add-to-Cart API returned status: " + statusCode);
        AllureReport.attachStatusCode(statusCode);
    }
}
