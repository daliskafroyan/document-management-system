## Prerequisites

- [`bun`](https://bun.sh/)
- [`Java 22`](https://openjdk.org/projects/jdk/22/)
- [`Docker`](https://www.docker.com/)

## Start Environment

- In a terminal, make sure you are inside `document-management-system` root folder;
- Run the following command to start docker compose services:
  ```
  docker compose up -d
  ```

## Running order-app using Maven & Npm

- **server**
  - Open a terminal and navigate to `server` folder;
  - Run the following `Maven` command to start the application:
    ```
    ./mvnw clean spring-boot:run
    ```

- **client**
  - Open another terminal and navigate to `client` folder;
  - Run the command below if you are running the application for the first time:
    ```
    bun install
    ```
  - Run the `bun` command below to start the application:
    ```
    bun dev
    ```

- **documentation**
  - Go to [localhost](http://localhost:8080/swagger-ui/index.html) to open the swagger UI  
