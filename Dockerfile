FROM openjdk:26

# Set working directory inside the container to /tmp
WORKDIR /tmp

# Copy your JAR file from host target folder to /tmp in container
COPY target/rest-management.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","app.jar"]