# Usa imagem oficial com Maven + JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Diretório da aplicação
WORKDIR /app

# Copia o pom.xml
COPY pom.xml .

# Copia o código-fonte
COPY src ./src

# Builda o projeto sem mvnw
RUN mvn -B -DskipTests clean package

# ------------------------------
# Imagem final para rodar o app
# ------------------------------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/quarkus-app ./quarkus-app/
COPY --from=build /app/target/quarkus-run.jar .

EXPOSE 8080

CMD ["java", "-jar", "quarkus-run.jar"]
