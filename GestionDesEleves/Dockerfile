# Stage 1: Build the application
FROM openjdk:17-jdk-slim-buster AS builder

# Install Maven
RUN apk --no-cache add \
      binutils maven

# Define environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS JAVA_OPTS=""

# Set the working directory to /GestionDesEleves
WORKDIR /app/gestiondesincriptions

# Copy the application source code
COPY ../GestionDesEleves/target/gestiondesinscriptions-0.0.1-SNAPSHOT.jar gestiondesincriptions.jar

# Build your application (use the appropriate build command, e.g., "mvn package" or "./gradlew build")
RUN mvn clean install

# Stage 2: Create the final runtime image
FROM openjdk:17-jdk-slim-buster AS runtime
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} gestiondesincriptions-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/gestiondesincriptions.jar"]
