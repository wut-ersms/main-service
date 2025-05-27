plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.13"
    id("com.bmuschko.docker-spring-boot-application") version "9.4.0"
    id("pl.allegro.tech.build.axion-release") version "1.18.18"
}

group = "com.wut.ersms"
version = scmVersion.version

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.apache.kafka:kafka-clients:3.9.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.6")
    implementation("org.apache.httpcomponents.client5:httpclient5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

docker {
    springBootApplication {
        baseImage.set("eclipse-temurin:17-jdk-alpine")
    }
}
