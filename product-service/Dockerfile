# Use a slim OpenJDK image as the base
FROM openjdk:21-jdk-slim as builder

# Set the working directory inside the container to the product-service directory
WORKDIR /app/product-service

# Copy the Maven wrapper files and pom.xml from the product-service directory
COPY product-service/mvnw .
COPY product-service/.mvn .mvn
COPY product-service/pom.xml .

# Copy the source code from the product-service directory
COPY product-service/src ./src

# Build the application using Maven
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install -DskipTests

# --- Second stage: Create a smaller runtime image ---
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR from the builder stage and rename it to sastachasma.jar
COPY --from=builder /app/product-service/target/*.jar sastachasma.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "sastachasma.jar"]
