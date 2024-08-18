FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY .mvn/ ./mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

# Build the application
RUN ./mvnw package

CMD ["./mvnw", "spring-boot:run"]



