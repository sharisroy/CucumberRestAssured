🚀 CucumberRestAssured
A structured API test automation framework built with Cucumber, Rest Assured, JUnit, and Java. It follows BDD practices to write readable and maintainable test scenarios, using JSON files for request payloads and reusable Java utilities for API interactions.

📁 Project Structure

CucumberRestAssured
│── pom.xml                         # Maven project object model file
│── .gitignore                      # Specifies intentionally untracked files to ignore
└── src
├── main
│   └── java
│       └── com.api.automation
│           ├── Main.java               # (Optional) Entry point for standalone test execution
│           └── utils
│                ├── JsonUtils.java     # Utility class to read JSON files from testdata/
│                └── ApiClient.java     # Optional reusable methods for API requests
└── test
├── java
│   ├── runners
│   │     └── TestRunner.java       # Cucumber JUnit runner class
│   ├── stepDefinitions
│   │     ├── PostSteps.java        # Step definitions for POST API testing
│   │     └── GetSteps.java         # Step definitions for GET API testing (future)
│   └── hooks
│         └── Hooks.java            # Optional hooks for setup/teardown
└── resources
├── features
│    ├── post.feature           # Feature file for POST scenarios
│    └── get.feature            # Feature file for GET scenarios (future)
└── testdata
├── loginPayload.json      # Sample request body for login API
└── anotherRequest.json    # Additional test data for other APIs

⚙️ Technologies Used

Java 11+

Maven

Cucumber JVM

Rest Assured

JUnit

JSON (for payloads)

🧪 How to Run the Tests

✅ Prerequisites

Java SDK installed

Maven installed

IDE like IntelliJ IDEA or Eclipse

🔧 Run with Maven

mvn clean install
mvn test

🏷️ Run Scenarios by Tags
Edit TestRunner.java to include Cucumber options like:

@CucumberOptions(
features = "src/test/resources/features",
glue = {"stepDefinitions", "hooks"},
tags = "@SmokeTest"
)

📝 Writing a New Test

Create a new .feature file in src/test/resources/features/

Add the test data (if needed) in src/test/resources/testdata/

Create step definitions in stepDefinitions/ or extend existing ones

Add or modify API request logic in ApiClient.java if reusability is needed

Run the test using the runner

🔧 Utilities
JsonUtils.java
Used to load JSON payloads from the testdata/ folder:

String payload = JsonUtils.readJsonFile("loginPayload.json");
```ApiClient.java` (Optional)
Reusable API methods like:
```java
public static Response post(String endpoint, String body) { ... }
public static Response get(String endpoint) { ... }

🧼 Hooks (Optional)
Located in hooks/Hooks.java, useful for setup/cleanup:

@Before
public void setup() { ... }

@After
public void teardown() { ... }

🚧 Future Improvements

Add GET/PUT/DELETE scenarios

Environment-based config using config.properties

Token-based authentication handling

JSON Schema validation

Reporting with Extent or Allure

📬 Contribution & Support
Feel free to fork the repo, open issues, or contribute via pull requests.
This framework is ideal for small to medium-scale API testing and is extensible for larger use cases.

📄 License
This project is open-source and available under the MIT License.

Happy Testing! 💥