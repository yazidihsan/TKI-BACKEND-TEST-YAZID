# Spring Boot REST API TKI-BACKEND_TEST

This project is a simple REST API built with Spring Boot and MongoDB. It allows users to perform CRUD operations on a collection of items. The API includes features such as filtering, sorting, and pagination.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Running Tests](#running-tests)
- [Docker Support](#docker-support)
- [Swagger UI](#swagger-ui)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

## Prerequisites

Make sure you have the following installed:

- **Java**: JDK 17 or later
- **Maven**: Version 3.6+
- **MongoDB**: Running locally or on a remote server
- **Git**: For cloning the repository

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/username/springboot-rest-api.git
    cd springboot-rest-api
    ```

2. **Build the project:**

    Use Maven to build the project and download all necessary dependencies:

    ```bash
    mvn clean install
    ```

## Configuration

Before running the application, you need to configure the connection to your MongoDB instance.

1. **MongoDB Configuration:**

    Open the `src/main/resources/application.properties` file and update the MongoDB connection settings:

    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/mydatabase
    ```

    Alternatively, if you are using `application.yml`:

    ```yaml
    spring:
      data:
        mongodb:
          uri: mongodb://localhost:27017/mydatabase
    ```

2. **Other Configuration:**

    - You can customize other properties such as server port, JWT settings, etc.
    - If you are using environment variables, you can place them in a `.env` file or directly export them in your shell.

## Running the Application

You can run the application using one of the following methods:

1. **Using Maven:**

    ```bash
    mvn spring-boot:run
    ```

2. **Using the jar file:**

    After building the project, you can run the jar file generated in the `target` directory:

    ```bash
    java -jar target/springboot-rest-api-0.0.1-SNAPSHOT.jar
    ```

## API Endpoints

  ## Swagger UI

This project comes with **Swagger UI** for API documentation and testing.

Once the application is up and running, you can access the Swagger UI to explore and test the API without the need for external tools like Postman.

### Steps to Access Swagger UI

1. **Start the Application:**

    First, make sure the application is running. If you haven't started it yet, use one of the following commands:

    - **Using Maven:**
      ```bash
      mvn spring-boot:run
      ```

    - **Using the jar file:**
      ```bash
      java -jar target/springboot-rest-api-0.0.1-SNAPSHOT.jar
      ```

2. **Open Swagger UI in Your Browser:**

    Once the application is running, open your web browser and navigate to the following URL:

    ```
    http://localhost:8080/swagger-ui/index.html
    ```

    > **Note:** Replace `localhost:8080` with your server's address and port if you are running the application in a different environment.

3. **Explore and Test API Endpoints:**

    - You will see an interactive user interface listing all available API endpoints.
    - Each endpoint includes details about the HTTP method, request parameters, and responses.
    - You can test the API directly from Swagger UI by clicking the **"Try it out"** button, filling in the required parameters, and executing the requests.

4. **Authorization with JWT (Optional):**

    If your API requires JWT token-based authentication, you can authorize your requests directly in Swagger UI.

    - Click on the **Authorize** button at the top of the Swagger page.
    - Enter your **Bearer token** (JWT) in the format: 
      ```
      Bearer <your_token>
      ```
    - Once authorized, you can make authenticated requests to the secured endpoints.

### Example:

Here is an example of how you can use Swagger UI to make a request to the **GET /items** endpoint:

1. Go to the `GET /items` section.
2. Click **Try it out**.
3. Optionally, add query parameters such as `page`, `size`, or `sort`.
4. Click **Execute** to send the request.
5. The response will appear below with status code, headers, and response body.

By using Swagger UI, you can quickly test and interact with your API without needing an external client like Postman.

### Enabling Swagger (If Disabled)

In case Swagger is disabled in your environment, make sure the following dependencies and configurations are added:

1. **Add Dependency in `pom.xml`:**

    ```xml
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.2.0</version>
    </dependency>
    ```

2. **Ensure Swagger UI is enabled in `application.properties`:**

    ```properties
    springdoc.api-docs.enabled=true
    springdoc.swagger-ui.enabled=true
    ```

    If you are using YAML configuration, add it in the `application.yml`:

    ```yaml
    springdoc:
      api-docs:
        enabled: true
      swagger-ui:
        enabled: true
    ```
    Each ENDPOINT using bearer token after auth.. it has to add authorization on swagger settings based on login.
   ** If You want to add role to register auth with "ROLE_ADMIN" or "ROLE_FINANCE", you only need to use "ADMIN" or "FINANCE" to fill request body in register auth and it will be "ROLE_ADMIN" and "ROLE_FINANCE" in value of the key for role object ** 
    
    This will ensure that Swagger UI is correctly configured and accessible.

---

### Base URL:
  http://localhost:8080/api/**
The base URL for all API requests is:

