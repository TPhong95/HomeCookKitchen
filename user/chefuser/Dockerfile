FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./pom.xml
COPY user/chefuser/pom.xml ./user/chefuser/pom.xml
WORKDIR /build/user/chefuser
RUN mvn dependency:go-offline -B
COPY user/chefuser/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/user/chefuser/target/*.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "app.jar"]
