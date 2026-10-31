# -------------------------------------
# Etapa 1 -- Build (Java 21)
# -------------------------------------
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar arquivos do Maven
COPY pom.xml .
COPY src ./src

# Compilar o projeto e gerar o JAR
RUN mvn clean package -DskipTests

# -------------------------------------
# Etapa 2 -- Runtime (seu estágio existente)
# -------------------------------------
FROM openjdk:21-ea-1-jdk-slim

WORKDIR /app

# Copia o JAR gerado no estágio anterior
COPY --from=build /app/target/ferramentas-0.0.1-SNAPSHOT.jar /app/ferramentas.jar


# Porta exposta
EXPOSE 8080

# Comando de inicialização
CMD ["java", "-jar", "/app/ferramentas.jar"]



