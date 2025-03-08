FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy built JAR file into the container
COPY target/PokedexAPI-1.0.0.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]