FROM openjdk:17-jdk-slim

WORKDIR /opt/app/backend
COPY . ./

ARG JAR_FILE=./target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]