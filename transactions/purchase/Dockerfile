FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./pom.xml
COPY transactions/purchase/pom.xml ./transactions/purchase/pom.xml
WORKDIR /build/transactions/purchase
RUN mvn dependency:go-offline -B
COPY transactions/purchase/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/transactions/purchase/target/*.jar app.jar
EXPOSE 8011
ENTRYPOINT ["java", "-jar", "app.jar"]
