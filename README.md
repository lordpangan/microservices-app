# Microservices Apps (Portfolio CI/CD)

This repo contains three **Spring Boot 3.3+ microservices** designed to showcase a **CI/CD pipeline** with **GitHub Actions, Kustomize, and GitHub-native notifications**.

## Services
- **orders-service** → orchestrates inventory + payment
- **inventory-service** → stock management (with Postgres in component tests)
- **payment-service** → mock payments (supports latency/fail toggles)

Each service:
- JDK 21, Gradle 8.x
- REST API + OpenAPI 3.1 (Swagger UI)
- Health/readiness/liveness endpoints (Spring Boot Actuator)
- Dockerfile (multi-stage → distroless, non-root)

## CI/CD Overview
- **CI (this repo):**
  - Per-service matrix build/test/scan (only changed services are built)
  - Unit + component tests (JUnit 5, Mockito, Testcontainers, WireMock)
  - SBOM (Syft) + vulnerability scan (Trivy SARIF)
  - Images published to `${REGISTRY}/<service>:sha-<gitsha>`
  - GitHub-native notifications: Checks, Job Summary, Deployments(ci)

- **CD (in [`platform-cd`](https://github.com/lordpangan/platform-cd)):**
  - Receives `repository_dispatch` per changed service
  - Deploys tagged images to `uat` via Kustomize
  - Runs Postman/Newman UAT (order happy path, insufficient inventory, payment failure)
  - If green: creates “ready-for-prd” Issue with digests
  - Manual approval → promote to `prd` (digest-pinned, immutable)

## Repo Layout
```
microservices-app/
├── services/
│   ├── orders-service/
│   │   ├── src/
│   │   ├── Dockerfile
│   │   ├── build.gradle
│   │   └── README.md
│   │
│   ├── inventory-service/
│   │   ├── src/
│   │   ├── Dockerfile
│   │   ├── build.gradle
│   │   └── README.md
│   │
│   └── payment-service/
│       ├── src/
│       ├── Dockerfile
│       ├── build.gradle
│       └── README.md
│
├── .github/
│   ├── actions/
│   │   ├── sbom-and-scan/
│   │   │   └── action.yml
│   │   └── build-and-push/
│   │       └── action.yml
│   │
│   └── workflows/
│       ├── ci-matrix.yml
│       └── on-main-dispatch-to-cd.yml
│
├── .gitignore
├── devbox.json
└── README.md
```
## Setting Up Development Environment
```
# this will install all tools needed for development
devbox shell

# configure JAVA_HOME
sed -i '' '/^org.gradle.java.home=/d' ~/.gradle/gradle.properties 2>/dev/null || true
sed -i '' '/^org.gradle.java.home=/d' ./gradle.properties 2>/dev/null || true
export JAVA_HOME="$PWD/.devbox/nix/profile/default"
echo $JAVA_HOME
"$JAVA_HOME/bin/java" -version

gradle -Dorg.gradle.java.home="$JAVA_HOME" wrapper --gradle-version 9.1
```