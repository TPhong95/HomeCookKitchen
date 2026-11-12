FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./pom.xml
COPY user/customer/pom.xml ./user/customer/pom.xml
WORKDIR /build/user/customer
RUN mvn dependency:go-offline -B
COPY user/customer/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/user/customer/target/*.jar app.jar
EXPOSE 8003
ENTRYPOINT ["java", "-jar", "app.jar"]
