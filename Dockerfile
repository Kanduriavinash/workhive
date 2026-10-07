# Build Stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/workhive-*.jar app.jar
RUN mkdir -p uploads/resumes
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
