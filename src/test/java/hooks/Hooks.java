package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import utils.ApiClient;
import utils.ConfigManager;
import utils.ScenarioContext;

public class Hooks {

    // ThreadLocal ensures parallel scenarios don’t mix data
    private static final ThreadLocal<ScenarioContext> scenarioContextThreadLocal = new ThreadLocal<>();

    @Before
    public void setup(Scenario scenario) {
        System.out.println("🔧 Starting scenario: " + scenario.getName());
        scenarioContextThreadLocal.set(new ScenarioContext());

        // ✅ Skip auto-login for scenarios tagged with @login
        if (scenario.getSourceTagNames().contains("@login")) {
            System.out.println("⏭ Skipping auto-login for scenario tagged @login");
            return;
        }

        // ✅ Perform login for all other scenarios
        ApiClient apiClient = new ApiClient();
        Response loginResponse = apiClient
                .setBaseUri(ConfigManager.get("base.url"))
                .setBody("{ \"userEmail\": \"" + ConfigManager.get("login.email") + "\", " +
                        "\"userPassword\": \"" + ConfigManager.get("login.password") + "\" }")
                .post("auth/login");

        // ✅ Verify login was successful
        loginResponse.then().statusCode(200);
        // ✅ Extract token & userId
        String token = loginResponse.jsonPath().getString("token");
        String userId = loginResponse.jsonPath().getString("userId");

        // ✅ Safety check to avoid null issues
        if (token == null) {
            System.out.println("❌ ERROR: token is NULL in login response!");
        } else {
            System.out.println("✅ Token stored in ScenarioContext: " + token);
        }

        if (userId == null) {
            System.out.println("❌ ERROR: userId is NULL in login response!");
        } else {
            System.out.println("✅ UserId stored in ScenarioContext: " + userId);
        }

        // ✅ Store in ScenarioContext for later use
        getScenarioContext().set("authToken", token);
        getScenarioContext().set("userId", userId);
    }

    @After
    public void teardown() {
        System.out.println("🧹 Cleaning up after scenario...");
        scenarioContextThreadLocal.remove(); // ✅ Avoids memory leaks in parallel runs
    }

    public static void attachResponse(Response response, String name) {
        if (response != null) {
            Allure.addAttachment(name, response.asPrettyString());
        }
    }


    public static ScenarioContext getScenarioContext() {
        return scenarioContextThreadLocal.get();
    }
}
