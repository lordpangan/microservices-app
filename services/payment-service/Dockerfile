# enable buildkit
# syntax=docker/dockerfile:1.4 

# build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY . /app
RUN --mount=type=cache,target=/root/.gradle \
    bash -lc './gradlew :services:payment-service:bootJar --no-daemon'
# run (distroless)
FROM gcr.io/distroless/java21-debian12
WORKDIR /srv
COPY --from=build /app/services/payment-service/build/libs/*.jar app.jar
USER nonroot:nonroot
EXPOSE 8080
ENTRYPOINT ["java","-jar","/srv/app.jar"]
