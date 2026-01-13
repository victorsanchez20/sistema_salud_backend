# Imagen base con Java 17
FROM eclipse-temurin:17-jdk

# Directorio de trabajo
WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Damos permisos al wrapper de Maven
RUN chmod +x mvnw

# Construimos el proyecto
RUN ./mvnw clean package -DskipTests

# Exponemos el puerto
EXPOSE 8080

# Comando para correr la app
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
