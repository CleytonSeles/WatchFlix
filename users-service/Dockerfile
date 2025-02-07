# Use uma imagem base do OpenJDK 17 em Alpine (versão leve)
FROM openjdk:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copie o arquivo JAR gerado para o container
# O argumento JAR_FILE permite flexibilidade para o nome do JAR gerado
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Expõe a porta que a aplicação usa (por padrão, 8080)
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
