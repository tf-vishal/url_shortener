# Use a base image
FROM maven:3.9.9-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline


COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /app/target/urlshortener-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8000

CMD [ "java", "-jar","app.jar" ]