plugins { id("org.springframework.boot"); id("io.spring.dependency-management") }
dependencies {
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.actuator)
  implementation(libs.spring.boot.starter.validation)
  implementation(libs.springdoc.ui)
  implementation(libs.resilience4j.spring.boot3)
  testImplementation(libs.testcontainers.junit)
}
