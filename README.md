🚀 CucumberRestAssured
A structured API test automation framework built with Cucumber, Rest Assured, JUnit, and Java.
It follows BDD practices to ensure test scenarios are readable, maintainable, and reusable, while supporting JSON-based request payloads and modular Java utilities for API interactions.


🔗 Repository: [CucumberRestAssured](https://github.com/sharisroy/CucumberRestAssured)



📁 Project Structure

CucumberRestAssured
│── pom.xml                         # Maven project object model file
│── .gitignore                      # Specifies intentionally untracked files to ignore
│── Dockerfile                      # Defines the Docker image for the project
│── .dockerignore                   # Excludes specified files from the Docker image
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
```angular2html
1. mvn clean install
2. mvn test
3. mvn test -Dcucumber.filter.tags="@smoke"
```
# Dockerization
1. Install Docker
      ```
   Verify your installation with: docker --version
   ```
2. Create a Dockerfile: 
   In the project root (where pom.xml is located), create a file named Dockerfile (with no extension).
```angular17html
    # Use Maven with OpenJDK 17
    FROM maven:3.9.6-eclipse-temurin-17
    
    # Set working directory inside container
    WORKDIR /app
    
    # Copy pom.xml first (for dependency caching)
    COPY pom.xml .
    
    # Download dependencies
    RUN mvn dependency:go-offline
    
    # Copy all project files
    COPY src ./src
    
    # Run tests when container starts
    CMD ["mvn", "clean", "test"]

```
3. Create a .dockerignore file
   Create a file named .dockerignore in the project root to exclude unnecessary files from the Docker image.
```angular2html
    target
    .idea
    *.iml
    .git
```

4. Build the Docker Image
   ```
    docker build -t test-maven .
   ```

5. Run the Docker Image
   ```
   docker run --rm test-maven
   ```
6. Push to Docker Hub
   ```
   1. Login to Docker Hub: 
      --  docker login
   2. Build and tag your image with your Docker Hub username:
      --  docker build -t <your_docker_hub_username>/test-maven:1.0 .
   3. Push the image:
      --  docker push <your_docker_hub_username>/test-maven:1.0
   ```
7. Run Your Pushed Docker Image
```
   Pull the image:
     --  docker pull <your_docker_hub_username>/test-maven:1.0
   Run the image:
     --  docker run --rm <your_docker_hub_username>/test-maven:1.0
   Save Reports (if configured):
     --  docker run --rm -v ${PWD}/reports:/app/target <your_docker_hub_username>/test-maven:1.0
```
📬 Contribution & Support
Feel free to fork the repo, open issues, or contribute via pull requests. The GitHub repository is located at: https://github.com/sharisroy/CucumberRestAssured. This framework is ideal for small to medium-scale API testing and is extensible for larger use cases.

📄 License
This project is open-source and available under the MIT License.

Happy Testing! 💥