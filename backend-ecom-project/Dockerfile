FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn -B -q -e -C -DskipTests dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

RUN useradd -ms /bin/bash springuser

COPY --from=builder /app/target/*.jar app.jar

RUN chown springuser:springuser app.jar

USER springuser

EXPOSE 9090

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]