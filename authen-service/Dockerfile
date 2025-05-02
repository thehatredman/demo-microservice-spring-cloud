FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/authen-service-1.0.1.jar /app/authen-service.jar
ENTRYPOINT ["java", "-jar", "/app/authen-service.jar"]
