plugins {
  id("java")
  id("checkstyle")
  id("org.springframework.boot") version "3.3.3" apply false
  id("io.spring.dependency-management") version "1.1.6" apply false
}
java { toolchain { languageVersion.set(JavaLanguageVersion.of(21)) } }
allprojects { repositories { mavenCentral() } }
subprojects {
  apply(plugin = "java"); apply(plugin="checkstyle")
  tasks.test { useJUnitPlatform() }
  dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.13.0")
    testImplementation("org.assertj:assertj-core:3.26.3")
  }
  checkstyle { toolVersion = "10.17.0"; configFile = file("${rootDir}/config/checkstyle/checkstyle.xml") }
}
