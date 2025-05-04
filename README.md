#Project demo microservice spring cloud
##version spring cloud
```xml
<version>Greenwich.SR2</version>
```
##version spring boot
```xml
<version>2.1.13.RELEASE</version>
```
##version jdk
```xml
<java.version>11</java.version>
```
##priority level config
```html
Spring Boot value properties priority:

argument command line (--server.port=8086)

Environment (SERVER_PORT=8086)

application.yml / application.properties

default of framework

👉 Mean: if you send value by argument command line --server.port=8086, then value is override all other config.
```
### config env intelliJ or spring boot application
```yaml
--server.port=8082 --spring.cloud.config.uri=http://localhost:8081
```

```
curl -X POST http://localhost:8081/actuator/refresh
```

## Deploy docker container
###build docker image
####build docker image config-service
```shell script
cd /home/your-user/config-service
docker build -t config-service-image .
```
continue build docker image discovery-service
```shell script
cd /home/your-user/discovery-service
docker build -t discovery-service-image .

```
```shell script
cd /home/your-user/gateway-service
docker build -t gateway-service-image .

```
````
cd /home/your-user/authen-service
docker build -t authen-service-image .

````
### create docker network
```shell script
docker network create microservice-net
```
##cau truc lenh docker run
```shell script
docker run [docker-options] image-name [application-arguments]
docker run -d --name config-service config-service-image --server.port=8081 --spring.profiles.active=native

```
### run config-service
```shell script
docker run -d \
  --name config-service \
  --network microservice-net \
  -v ./config-repo:/app/config \
  -e SPRING_PROFILES_ACTIVE=native \
  -e SPRING_CLOUD_CONFIG_SERVER_NATIVE_SEARCH_LOCATIONS=file:/app/config \
  -p 8081:8081 \
  config-service-image \
  --server.port=8081

docker run -d \
  --name config-service \
  --network microservice-net \
  -v ./config-repo:/app/config \
  -p 8081:8081 \
  config-service-image \
  --server.port=8888 \
  --spring.profiles.active=native \
  --spring.cloud.config.server.native.search-locations=file:/app/config

```
###run discovery-service peer1
```shell script
docker run -d \
  --name discovery-peer1 \
  --network microservice-net \
  -e SERVER_PORT=8761 \
  -e SPRING_PROFILES_ACTIVE=native,peer1 \
  -e EUREKA_INSTANCE_HOSTNAME=peer1.local \
  -p 8761:8761 \
  discovery-service-image

docker run -d \
  --name discovery-peer1 \
  --hostname peer1.local \
  --network microservice-net \
  -e SPRING_PROFILES_ACTIVE=cluster \
  -e SERVER_PORT=8761 \
  -e EUREKA_INSTANCE_HOSTNAME=peer1.local \
  -e SPRING_CLOUD_CONFIG_URI=http://config-service:8081 \
  -p 8761:8761 \
  discovery-service-image

```
###run discovery-service peer2
```shell script
docker run -d \
  --name discovery-peer2 \
  --network microservice-net \
  -e SERVER_PORT=8762 \
  -e SPRING_PROFILES_ACTIVE=native,peer2 \
  -e EUREKA_INSTANCE_HOSTNAME=peer2.local \
  -p 8762:8762 \
  discovery-service-image

docker run -d \
  --name discovery-peer2 \
  --hostname peer2.local \
  --network microservice-net \
  -e SPRING_PROFILES_ACTIVE=cluster \
  -e SERVER_PORT=8762 \
  -e EUREKA_INSTANCE_HOSTNAME=peer2.local \
  -e SPRING_CLOUD_CONFIG_URI=http://config-service:8081 \
  -p 8762:8762 \
  discovery-service-image

```
###run discovery-service peer3
```shell script
docker run -d \
  --name discovery-peer3 \
  --network microservice-net \
  -e SERVER_PORT=8763 \
  -e SPRING_PROFILES_ACTIVE=native,peer3 \
  -e EUREKA_INSTANCE_HOSTNAME=peer3.local \
  -p 8763:8763 \
  discovery-service-image

docker run -d \
  --name discovery-peer3 \
  --hostname peer3.local \
  --network microservice-net \
  -e SPRING_PROFILES_ACTIVE=cluster \
  -e SERVER_PORT=8763 \
  -e EUREKA_INSTANCE_HOSTNAME=peer3.local \
  -e SPRING_CLOUD_CONFIG_URI=http://config-service:8081 \
  -p 8763:8763 \
  discovery-service-image


```
###run gateway-service
```shell script
docker run -d \
  --name gateway-service \
  --network microservice-net \
  -e SPRING_CLOUD_CONFIG_URI=http://config-service:8081 \
  -e SERVER_PORT=8084 \
  -p 8084:8084 \
  gateway-service-image
```
###run authen-service
```shell script
docker run -d \
  --name authen-service \
  --network microservice-net \
  -e SPRING_CLOUD_CONFIG_URI=http://config-service:8081 \
  -e SPRING_PROFILES_ACTIVE=native \
  -e SERVER_PORT=8086 \
  -p 8086:8086 \
  authen-service-image

```
###API refresh config 
```http request
http://127.0.0.2:8081/actuator/refresh
```

###config additional location
```shell script
docker run -v $(pwd)/external.properties:/app/config/external.properties -w /app myapp:latest \
  java -jar app.jar --spring.config.additional-location=file:./config/external.properties

```
#Reference source
```html
<li>
    spring.io
</li>
```