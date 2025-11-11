# First stage: Build the application
FROM mcr.microsoft.com/openjdk:21-jdk-windowsservercore-1809 AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper files and pom.xml
COPY mvnw.cmd .
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src src/

# Build the application
RUN ./mvnw.cmd clean install -DskipTests

# Second stage: Runtime image
FROM mcr.microsoft.com/openjdk:21-jre-windowsservercore-1809

WORKDIR /app

# Copy the built JAR file
COPY --from=builder /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
