FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/target/todo-api.jar app.jar
ENV APP_PORT=8080
EXPOSE 8080
USER 10001
ENTRYPOINT ["java", "-jar", "app.jar"]
