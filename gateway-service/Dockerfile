FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/gateway-service-1.0.1.jar /app/gateway-service.jar
ENTRYPOINT ["java", "-jar", "/app/gateway-service.jar"]
