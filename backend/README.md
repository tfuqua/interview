# Java Spring Boot API Coding Exercise

## Steps to get started:

#### Prerequisites
- Maven
- Java 1.8 (or higher, update version in pom.xml if needed)

#### Clone the project locally
- `https://github.com/tfuqua/interview.git`

#### Create your own branch and push to origin
- `[your_name]_crud_api`

#### Import project into IDE
- Project root is located in `backend` folder

#### Build and run your app
- `mvn package && java -jar target/interview-1.0-SNAPSHOT.jar`

#### Test that your app is running
- `curl -X GET   http://localhost:8080/api/welcome`

#### After finishing the goals listed below create a PR

### Goals
1. Design basic CRUD API with data store using Spring Boot and in memory H2 database (pre-configured, see below)
2. API should include one object with create, read, update, and delete operations. Read should include fetching a single item and list of items.
3. Provide SQL create scripts for your object(s) in resources/data.sql
4. Demo API functionality using API client tool

#### H2 Configuration
- Console: http://localhost:8080/h2-console 
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: password

# Pre-requisites
**Java 11 (Long Term Support) JDK** needs to be installed on your system.

For installation details please check - https://adoptopenjdk.net

A local **Docker** installation can also be used if Java is not installed as the attached docker-compose file can build and run the project

# Toolkit
* Spring Boot 2.4.x
* Spring Boot Actuator
* Spring Data 
* Micrometer
* Swagger 2.0
* Java 11
* Prometheus
* Grafana
* Docker

# Build status
![Test](https://github.com/tfuqua/interview/workflows/maven-build-test/badge.svg?branch=feature/bogdan_mariesan_crud_api&event=push)

## Building and running the project locally
Please use the following maven command to run the backend application as a SpringBoot app

`mvn spring-boot:run`

If you have Docker installed locally you can run the following command to have the backend app, grafana and prometheus up and running

`docker-compose -f docker-compose.yaml up -d`

### Grafana & Prometheus

If you want to play around with the micrometer metrics, by starting the project using `docker-compose` you will also enable
a Grafana & Prometheus deployment.

Prometheus will scrape the `/actuator/prometheus` endpoint every second and gather metrics related to our app.
Grafana on the other hand has Prometheus configured as a datasource and thus can display dashboards based on those metrics.
The current project has a default dashboard containing micrometer exposed metrics.

To access Grafana once the Docker containers are up you need to navigate to `http://localhost:3000` and if this is the first 
time running the container you will have to use the following credentials - user - `admin` and password - `password`.

After several seconds the dashboard will automatically refresh and the charts should begin to render.

### Swagger documentation

Swagger 2.0 API documentation for the built resource can be accessed at http://localhost:8080/swagger-ui/

### Postman collection

The file `carshop.postman_collection.json` contains a sample collection built to run all endpoints exposed by the backend app

### Implemented features
* `Spring Boot Actuator` integration to expose health-checks and metrics
    * http://localhost:8080/actuator/prometheus - for Prometheus style metrics
    * http://localhost:8080/actuator/health - for Health checks - currently defaulting to standard Spring Boot checks
    * http://localhost:8080/actuator/metrics - Micrometer style metrics list
    * http://localhost:8080/actuator/metrics/{metricName} - To see specific metric details E.g. http://localhost:8080/actuator/metrics/system.cpu.usage
* `Swagger 2 integration` and `Swagger UI` to showcase implemented REST resources
    * configured to only register classes annotated with `@RestController`
* `CI using Github Actions` - https://github.com/tfuqua/interview/actions
* `Spring Boot Integration tests`
* Docker compose file capable of building the project and starting up `Grafana` and `Prometheus`