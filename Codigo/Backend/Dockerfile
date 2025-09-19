# Stage 1: build
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia apenas o pom para baixar dependências primeiro
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Build do projeto, ignorando testes
RUN mvn clean package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copia o .jar gerado
COPY --from=build /app/target/appAluguel-0.0.1-SNAPSHOT.jar app.jar

# Ajuste de timezone
ENV TZ=America/Sao_Paulo

# Usa a porta definida no .env (SPRING_SERVER_PORT)
ENV SERVER_PORT=${SPRING_SERVER_PORT}

# Entry point
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
