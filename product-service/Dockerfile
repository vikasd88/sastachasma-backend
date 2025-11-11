# Use a slim OpenJDK image as the base
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS builder

# Set the working directory inside the container to the product-service directory
WORKDIR /app

# Copy the Maven wrapper files and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application using Maven
RUN --mount=type=cache,target=/root/.m2 ./mvnw clean install -DskipTests

# --- Second stage: Create a smaller runtime image ---
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

# Set the working directory
WORKDIR /app

# Copy the built JAR from the builder stage and rename it to sastachasma.jar
COPY --from=builder /app/target/*.jar sastachasma.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "sastachasma.jar"]
