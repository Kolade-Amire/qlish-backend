FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x ./mvnw && \
    sed -i 's/\r$//' ./mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
COPY target/classes ./target/classes

CMD ["./mvnw", "spring-boot:run"]



