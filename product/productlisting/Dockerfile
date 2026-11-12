FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml ./pom.xml
COPY product/productlisting/pom.xml ./product/productlisting/pom.xml
WORKDIR /build/product/productlisting
RUN mvn dependency:go-offline -B
COPY product/productlisting/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/product/productlisting/target/*.jar app.jar
EXPOSE 8006
ENTRYPOINT ["java", "-jar", "app.jar"]
