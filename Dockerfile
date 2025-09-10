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
