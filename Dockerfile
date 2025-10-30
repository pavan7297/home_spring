# Use a lightweight Java image
FROM eclipse-temurin:17-jdk

# Create app directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar home.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "home.jar"]