FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./pom.xml
COPY taskscheduler/pom.xml ./taskscheduler/pom.xml
WORKDIR /build/taskscheduler
RUN mvn dependency:go-offline -B
COPY taskscheduler/src ./src
RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/taskscheduler/target/*.jar app.jar
EXPOSE 8020
ENTRYPOINT ["java", "-jar", "app.jar"]
