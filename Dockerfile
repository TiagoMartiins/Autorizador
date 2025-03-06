FROM eclipse-temurin:17-jdk-alpine as build

# Instalar o Maven
RUN apk add --no-cache maven

WORKDIR /app

# Copiar o pom.xml e o código fonte
COPY pom.xml .

# Fazer o download das dependências sem compilar o código (para usar cache)
RUN mvn dependency:go-offline

# Copiar o código fonte
COPY src /app/src

# Compilar o aplicativo
RUN mvn clean package -DskipTests

# Agora cria a imagem para o runtime
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/Autorizador-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
