FROM maven:3-amazoncorretto-20-debian-bullseye as build-stage

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:20-jdk-bullseye

WORKDIR /app

COPY --from=build-stage /app/target/document-management-system-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]