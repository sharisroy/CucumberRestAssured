
### 🚀 **Overview**

**CucumberRestAssured** is a robust API test automation framework built on **Java**, **Cucumber**, **Rest Assured**, and **JUnit**. It's engineered to follow **Behavior-Driven Development (BDD)** principles, which makes test scenarios highly readable, maintainable, and reusable.

-----

### ✨ **Key Features**

* **JSON-based Payloads**: Effortlessly handle complex request bodies using a clear, JSON-centric approach.
* **Modular API Utilities**: A well-organized suite of reusable Java classes for efficient and clean API interactions.
* **Dockerized Execution**: Ensure consistent and reproducible test runs across any environment with a self-contained Docker setup.
* **Allure Reporting**: Generate rich, interactive, and detailed test reports for a comprehensive view of your test results.
* **CI/CD Integration**: A fully automated testing pipeline using **GitHub Actions** for continuous integration and delivery.
* **Public Reporting**: Host and share your latest test reports effortlessly via **GitHub Pages**.

-----

### 📊 **Latest Test Report**

* [**View the Latest Test Report Here**](https://sharisroy.github.io/CucumberRestAssured/)

-----

### ⚙️ **Getting Started**

#### **Prerequisites**

* **Java 17+**
* **Maven**
* **Docker** (for Dockerized execution)

#### **1. Clone the Repository**

```bash
git clone https://github.com/sharisroy/CucumberRestAssured.git
cd CucumberRestAssured
```

-----

### ▶️ **Running the Tests**

#### **Run with Maven**

After cloning the repository, you can run tests directly using Maven commands.

| Command | Description |
| :--- | :--- |
| `mvn clean install` | Cleans the project, installs dependencies, and builds the project. |
| `mvn test` | Runs all tests in the project. |
| `mvn test -Dcucumber.filter.tags="@smoke"` | Runs only scenarios with the `@smoke` tag. |

#### **Generate Allure Report**

To view detailed test results, generate an Allure report and serve it locally.

```bash
mvn allure:serve
```

-----

### 🐳 **Docker Integration**

Run your tests in a consistent, containerized environment.

#### **1. Building the Docker Image**

Create a `Dockerfile` and a `.dockerignore` file in the root of your project to define your container's environment.

**Dockerfile**

```docker
# Use Maven with OpenJDK 17
FROM maven:3.9.6-eclipse-temurin-17

# Set the working directory
WORKDIR /app

# Copy pom.xml first to leverage Docker's layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy all project files
COPY src ./src

# Default command to run tests
CMD ["mvn", "clean", "test"]
```

**`.dockerignore`**

```docker
target
.idea
*.iml
.git
```

Now, build your Docker image from the project root.

```bash
docker build -t test-maven .
```

#### **2. Running the Docker Image**

```bash
docker run --rm test-maven
```

Use `--rm` to automatically remove the container after the test run.

#### **3. Docker Hub Integration**

For sharing your image, you can push it to Docker Hub.

1.  **Log in to Docker Hub**: `docker login`
2.  **Tag your image**: `docker build -t <your_docker_hub_username>/test-maven:1.0 .`
3.  **Push the image**: `docker push <your_docker_hub_username>/test-maven:1.0`

Now, others can easily pull and run your image.

```bash
# Pull the image
docker pull <your_docker_hub_username>/test-maven:1.0

# Run the image
docker run --rm <your_docker_hub_username>/test-maven:1.0
```

-----

### 🙏 **Contribution & Support**

Contributions are welcome\! Feel free to fork the repository, open an issue, or submit a pull request. This framework is perfect for small to medium-scale API testing projects and is designed to be easily extensible.

-----

### 📄 **License**

This project is open-source and available under the **MIT License**.

-----

### Happy Testing\! 💥