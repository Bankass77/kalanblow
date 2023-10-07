# Stage 1: Build the application
FROM openjdk:17-jdk-slim-buster AS builder

# Install Maven
RUN apt-get update -y
RUN apt-get install -y binutils maven

# Define environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS JAVA_OPTS=""

# Set the working directory to /src
WORKDIR /src

# Copy the application source code
COPY . .

# Build your application (use the appropriate build command, e.g., "mvn package" or "./gradlew build")
#RUN mvn clean install

# Stage 2: Create the final runtime image
FROM openjdk:17-jdk-slim-buster AS runtime
ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} gestiondeseleves.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/gestiondeseleves.jar"]
