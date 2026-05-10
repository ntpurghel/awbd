import net.ltgt.gradle.errorprone.errorprone

plugins {
    java
    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("net.ltgt.errorprone") version "4.2.0" apply false
}

group = "com.github.narcispurghel"
version = "0.0.1-SNAPSHOT"
description = "awdb"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

subprojects {
    pluginManager.apply("net.ltgt.errorprone")

    plugins.withType<JavaPlugin> {
        dependencies {
            "errorprone"(rootProject.libs.nullaway)
            "errorprone"(rootProject.libs.errorprone.core)
            "implementation"(rootProject.libs.jspecify)
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.errorprone {
            checks.put("NullAway", net.ltgt.gradle.errorprone.CheckSeverity.ERROR)
            option("NullAway:AnnotatedPackages", "com.github.narcispurghel.${project.name.replace("-", "")}")
            option("NullAway:JSpecifyMode", "true")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
