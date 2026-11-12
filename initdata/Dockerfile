FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./pom.xml
COPY initdata/pom.xml ./initdata/pom.xml
WORKDIR /build/initdata
RUN mvn dependency:go-offline -B
COPY initdata/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/initdata/target/*.jar app.jar
EXPOSE 8022
ENTRYPOINT ["java", "-jar", "app.jar"]
