# Stage 1: build
FROM gradle:8.5.0-jdk21 AS build

WORKDIR /app
COPY . .

# Build source code using Gradle wrapper (skip tests if desired)
RUN ./gradlew spotlessApply
RUN ./gradlew build -x test


# Stage 2: create image
FROM amazoncorretto:21.0.4

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
