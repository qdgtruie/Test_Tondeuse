
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.time.*

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/5.4.1/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java
    id("org.springframework.boot") version "2.1.5.RELEASE"
    idea
    // Apply the application plugin to add support for building an application
    application
    id("io.freefair.lombok") version "3.2.1"
    id("org.sonarqube") version "2.7.1"
    jacoco
}


group = "com.publicissapient.tondeuse"
version = "1.0.0-SNAPSHOT"


sourceSets {
    main {
        java {
            srcDir("src/main")
            //srcDir("src/test")
        }
    }
    test{
        java {
            srcDir("src/test")
        }
    }
}


tasks {
    test {                                  // (5)
        testLogging.showExceptions = true
        useJUnitPlatform {
            includeEngines("jqwik,junit-jupiter, junit-vintage")

        }
        testLogging {
            events("passed", "skipped", "failed")
        }

        // set heap size for the test JVM(s)
        minHeapSize = "128m"
        maxHeapSize = "512m"


        include("**/*Properties.class")
        include("**/*Test.class")
        include("**/*Tests.class")
    }
}




repositories {
    // Use jcenter for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // This dependency is found on compile classpath of this component and consumers.
    implementation("com.google.guava:guava:27.0.1-jre")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-access:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.26")
    implementation("com.google.guava:guava:27.1-jre")
    implementation("io.vavr:vavr:0.9.2")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Use JUnit test framework
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testCompile("org.junit.jupiter:junit-jupiter:5.4.2")
    testRuntime("org.junit.jupiter:junit-jupiter:5.4.2")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.4.2")


    implementation("org.junit.platform:junit-platform-commons:1.5.0-M1")
    testImplementation("org.junit.platform:junit-platform-commons:1.5.0-M1")
    testCompile("org.junit.platform:junit-platform-commons:1.5.0-M1")
    testRuntime("org.junit.platform:junit-platform-commons:1.5.0-M1")

    implementation("net.jqwik:jqwik:1.1.3")
    testImplementation("net.jqwik:jqwik:1.1.3")
    testCompile("net.jqwik:jqwik:1.1.3")
    testRuntime("net.jqwik:jqwik:1.1.3")


    implementation("net.jqwik:jqwik-engine:1.1.3")
    testImplementation("net.jqwik:jqwik-engine:1.1.3")
    testCompile("net.jqwik:jqwik-engine:1.1.3")
    testRuntime("net.jqwik:jqwik-engine:1.1.3")

}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        xml.destination = file("${buildDir}/jacocoxml")
        csv.isEnabled = false
        html.destination = file("${buildDir}/jacocoHtml")
    }
}

application {
    // Define the main class for the application
    mainClassName = "com.publicissapient.tondeuse.ConsoleApp"
}

apply(plugin = "io.spring.dependency-management")

tasks.getByName<Jar>("jar") {
    enabled = true
    manifest{
        attributes(
                mapOf( "Implementation-Title" to project.name,
                        "Implementation-Version" to version,
                        "Built-By" to System.getProperty("user.name"),
                        "Built-Date" to LocalDateTime.now(),
                        "Built-JDK" to System.getProperty("java.version"),
                        "Built-Gradle" to  gradle.gradleVersion)
        )
    }
}

tasks.getByName<BootJar>("bootJar") {
    classifier = "boot"
    mainClassName = "com.publicissapient.tondeuse.WebApp"
}


springBoot {
    mainClassName = "com.publicissapient.tondeuse.WebApp"
}