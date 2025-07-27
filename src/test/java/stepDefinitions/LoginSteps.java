package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.json.JSONObject;
import utils.ApiClient;
import utils.ConfigManager;
import utils.JsonUtils;
import hooks.Hooks;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class LoginSteps {

    private Response response;
    private String jsonBody;
    private final ApiClient apiClient = new ApiClient();

    @Given("I have a JSON payload from {string}")
    public void i_have_a_json_payload_from(String fileName) {
        String filePath = "src/test/resources/testdata/" + fileName;
        jsonBody = JsonUtils.readJsonFromFile(filePath);
    }

    @Given("I update the email to {string} and password to {string}")
    public void i_update_the_email_and_password(String email, String password) {
        JSONObject jsonObject = new JSONObject(jsonBody);
        jsonObject.put("userEmail", email);
        jsonObject.put("userPassword", password);
        jsonBody = jsonObject.toString();
        System.out.println("🔄 Updated payload: " + jsonBody);
    }

    @When("I send a POST request to endpoint {string}")
    public void i_send_a_post_request_to_endpoint(String endpoint) {
        response = apiClient
                .setBaseUri(ConfigManager.get("base.url"))
                .setBody(jsonBody)
                .post(endpoint);

        // ✅ Store response in ScenarioContext for other steps to use
        Hooks.getScenarioContext().set("loginResponse", response);
    }

    @Then("I should receive a {int} status code")
    public void i_should_receive_status_code(Integer statusCode) {
        // ✅ Retrieve response from context
        Response savedResponse = Hooks.getScenarioContext().get("loginResponse", Response.class);
        savedResponse.then().statusCode(statusCode);
    }

    @Then("I should get a token in the response")
    public void i_should_get_a_token_in_response() {
        Response savedResponse = Hooks.getScenarioContext().get("loginResponse", Response.class);
        savedResponse.then().body("token", notNullValue());
        String token = savedResponse.jsonPath().getString("token");
        String userId = savedResponse.jsonPath().getString("userId");

        // ✅ Store token in context for other API calls
        Hooks.getScenarioContext().set("authToken", token);
        Hooks.getScenarioContext().set("userId", userId);
        System.out.println("✅ Token stored in ScenarioContext: " + token);
        System.out.println("✅ UserID stored in ScenarioContext: " + userId);

    }

    @Then("I should see an error message in the response")
    public void i_should_see_error_message_in_the_response() {
        Response savedResponse = Hooks.getScenarioContext().get("loginResponse", Response.class);
        savedResponse.then().body("message", equalTo("Incorrect email or password."));
        System.out.println("❌ Login failed as expected: " + savedResponse.jsonPath().getString("message"));
    }
}
