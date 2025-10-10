plugins { id("org.springframework.boot"); id("io.spring.dependency-management") }
dependencies {
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.actuator)
  implementation(libs.springdoc.ui)
  testImplementation(libs.testcontainers.junit)
}
