FROM maven:3.8.5-openjdk-17 AS build
LABEL authors="DzOnZi"
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/posganize-0.0.1-SNAPSHOT.jar posganize.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "posganize.jar"]