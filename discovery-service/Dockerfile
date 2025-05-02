FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/discovery-service-1.0.1.jar /app/discovery-service.jar
ENTRYPOINT ["java", "-jar", "/app/discovery-service.jar"]
