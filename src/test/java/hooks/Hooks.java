package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import utils.ApiClient;
import utils.ConfigManager;
import stepDefinitions.LoginSteps;

import static io.restassured.RestAssured.given;

public class Hooks {

    @Before
    public void setup(Scenario scenario) {
        System.out.println("🔧 Setting up before scenario: " + scenario.getName());

        // ✅ Skip login for scenarios tagged with @login
        if (scenario.getSourceTagNames().contains("@login")) {
            System.out.println("⏭ Skipping auto-login for scenario tagged with @login");
            return;
        }

        // ✅ Only login if token is not already set
        if (LoginSteps.authToken == null) {
            ApiClient apiClient = new ApiClient();
            String loginEmail = ConfigManager.get("login.email");
            String loginPassword = ConfigManager.get("login.password");

            // ✅ Log full request & response for debugging
            Response loginResponse = given()
                    .log().all()   // 🔍 log request details
                    .baseUri(ConfigManager.get("base.url"))
                    .header("Content-Type", "application/json")
                    .body("{ \"userEmail\": \"" + loginEmail + "\", \"userPassword\": \"" + loginPassword + "\" }")
                    .when()
                    .post("auth/login")
                    .then()
                    .log().all()   // 🔍 log response details
                    .statusCode(200)
                    .extract()
                    .response();

            // ✅ Store the token globally for later steps
            LoginSteps.authToken = loginResponse.jsonPath().getString("token");
            System.out.println("✅ Token set in Hooks: " + LoginSteps.authToken);
        }
    }

    @After
    public void teardown() {
        System.out.println("🧹 Cleaning up after scenario...");
    }
}
