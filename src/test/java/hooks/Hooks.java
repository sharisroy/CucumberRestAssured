package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import utils.ApiClient;
import utils.ConfigManager;
import utils.ScenarioContext;

public class Hooks {

    private static final ThreadLocal<ScenarioContext> scenarioContextThreadLocal = new ThreadLocal<>();

    @Before
    public void setup(Scenario scenario) {
        System.out.println("🔧 Starting scenario: " + scenario.getName());
        scenarioContextThreadLocal.set(new ScenarioContext());

        // ✅ Skip login for scenarios tagged with @login
        if (scenario.getSourceTagNames().contains("@login")) {
            System.out.println("⏭ Skipping auto-login for scenario tagged @login");
            return;
        }

        // ✅ Login once and store token for other APIs
        ApiClient apiClient = new ApiClient();
        Response loginResponse = apiClient
                .setBaseUri(ConfigManager.get("base.url"))
                .setBody("{ \"userEmail\": \"" + ConfigManager.get("login.email") + "\", " +
                        "\"userPassword\": \"" + ConfigManager.get("login.password") + "\" }")
                .post("auth/login");

        loginResponse.then().statusCode(200);

        // ✅ store token for use in GetProductSteps
        String token = loginResponse.jsonPath().getString("token");
        getScenarioContext().set("authToken", token);

        System.out.println("✅ Token stored in ScenarioContext: " + token);
    }

    @After
    public void teardown() {
        System.out.println("🧹 Cleaning up after scenario...");
        scenarioContextThreadLocal.remove();
    }

    public static ScenarioContext getScenarioContext() {
        return scenarioContextThreadLocal.get();
    }
}
