🚀 CucumberRestAssured
A structured API test automation framework built with Cucumber, Rest Assured, JUnit, and Java.
It follows BDD practices to ensure test scenarios are readable, maintainable, and reusable, while supporting JSON-based request payloads, modular Java utilities for API interactions, Dockerized execution, and rich Allure reporting for better test visibility

🔗 Repository: [CucumberRestAssured](https://github.com/sharisroy/CucumberRestAssured)
# Clone the Repository
```angular2html
git clone https://github.com/sharisroy/CucumberRestAssured.git
cd CucumberRestAssured
```


# Run with Maven
```angular2html

mvn clean install               # Install dependencies and build project
mvn test                        # Run all tests
mvn test -Dcucumber.filter.tags="@smoke"  # Run only tests with @smoke tag
mvn clean test                  # Clean and run all tests

```

# Allure report
```angular2html
   mvn allure:serve
```

# Run with Docker
```
Pull the image:
     --  docker pull <your_docker_hub_username>/test-maven:1.0
Run the image:
     --  docker run --rm <your_docker_hub_username>/test-maven:1.0
Save Reports (if configured):
     --  docker run --rm -v ${PWD}/reports:/app/target <your_docker_hub_username>/test-maven:1.0
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