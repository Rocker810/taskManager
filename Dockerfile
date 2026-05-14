# ─── Stage 1: Build the JAR ────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

# Copy build files first (helps Docker layer caching)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Pre-download dependencies (cached unless pom.xml changes)
RUN chmod +x mvnw && ./mvnw -q dependency:go-offline

# Now copy source and build
COPY src ./src
RUN ./mvnw -q -DskipTests package

# ─── Stage 2: Runtime ──────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy only the JAR from the builder stage
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]