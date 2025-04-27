FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/restaurante-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
