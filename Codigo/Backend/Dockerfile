FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/app.jar app.jar

ENV TZ=America/Sao_Paulo
ENV SPRING_PROFILES_ACTIVE=dev

ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE app.jar"]