package stepDefinitions;

import utils.ApiClient;
import utils.ConfigManager;
import utils.JsonUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.json.JSONObject;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class LoginSteps {

    private Response response;
    private String jsonBody;
    private ApiClient apiClient = new ApiClient();

    // ✅ Store token for future API calls
    public static String authToken;

    @Given("I have a JSON payload from {string}")  // ✅ fixed the extra space
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
        String baseUrl = ConfigManager.get("base.url");
        response = apiClient
                .setBaseUri(baseUrl)
                .setBody(jsonBody)
                .post(endpoint);
    }

    @Then("I should receive a {int} status code")
    public void i_should_receive_status_code(Integer statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("I should get a token in the response")
    public void i_should_get_a_token_in_response() {
        response.then().body("token", notNullValue());
        authToken = response.jsonPath().getString("token");
        System.out.println("✅ Token stored: " + authToken);
    }

    @Then("I should see an error message in the response")
    public void i_should_see_error_message_in_the_response() {
        response.then().body("message", equalTo("Incorrect email or password."));
        System.out.println("❌ Login failed as expected: " + response.jsonPath().getString("message"));
    }
}
