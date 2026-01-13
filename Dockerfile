# Imagen base con Java 17
FROM eclipse-temurin:17-jdk

# Directorio de trabajo
WORKDIR /app

# Copiamos archivos del proyecto
COPY . .

# Construimos el proyecto
RUN ./mvnw clean package -DskipTests

# Exponemos el puerto
EXPOSE 8080

# Ejecutamos el JAR
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
