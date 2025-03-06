# Usando a imagem oficial do OpenJDK
FROM openjdk:17-jdk-slim as build

# Diretório de trabalho
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
FROM openjdk:17-jdk-slim

# Diretório de trabalho onde a aplicação será copiada
WORKDIR /app

# Copiar o arquivo JAR gerado do primeiro estágio
COPY --from=build /app/target/Autorizador-0.0.1-SNAPSHOT.jar /app/app.jar

# Expor a porta que a aplicação irá rodar
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
