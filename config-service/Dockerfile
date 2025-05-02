FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/config-service-1.0.1.jar /app/config-service.jar
ENTRYPOINT ["java", "-jar", "/app/config-service.jar"]
#, "--spring.cloud.config.server.native.search-locations=file:/app/config", "--server.port=8888"
