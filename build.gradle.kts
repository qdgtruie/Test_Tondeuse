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
    `junit-test-suite`
    // Apply the application plugin to add support for building an application
    application
    id("io.freefair.lombok") version "3.2.1"
    id("org.sonarqube") version "2.7.1"
    jacoco
    id("com.github.johnrengelman.shadow") version "5.0.0"
    //id("com.rodrigodev.jbehave")

}


//compileJava.options.fork = true
//compileJava.options.forkOptions.executable = /path_to_javac

group = "com.publicissapient.tondeuse"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}


sourceSets {
    main {
        java {
            srcDir("src/main")
            //srcDir("src/test")
        }
    }
    test {
        java {
            srcDir("src/test/java")
        }
        resources{
            srcDir("src/test/resources")
        }
    }
}


tasks.test {
        useJUnitPlatform {}
}

tasks.withType<Test> {

    doFirst{
            file("target").mkdirs()
        }

    doLast {
            file("target").delete()
        }
    }


tasks.test {
    extensions.configure(JacocoTaskExtension::class) {
        //destinationFile = file("$buildDir/jacoco/jacocoTest.exec")
        classDumpDir = file("$buildDir/jacoco/classpathdumps")
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
    implementation("commons-io:commons-io:2.4")

    // Use JUnit test framework
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testCompile("org.junit.jupiter:junit-jupiter:5.4.2")
    testRuntime("org.junit.jupiter:junit-jupiter:5.4.2")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    testRuntime("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")


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
    testCompile("org.jbehave:jbehave-core:4.5.1")
    testCompile("com.github.valfirst:jbehave-junit-runner:2.2.1")
    testCompile("junit:junit:4.12")
    testCompile("org.mockito:mockito-core:2.+")
}


jacoco {
    applyTo(tasks.run.get())
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        xml.destination = file("${buildDir}/jacocoxml.xml")
        csv.isEnabled = false
        csv.destination = file("${buildDir}/jacococsv.csv")
        html.isEnabled = false
        html.destination = file("${buildDir}/jacocoHtml")
    }
}

tasks.test{finalizedBy("jacocoTestReport")}
tasks.check{dependsOn("jacocoTestCoverageVerification")}

tasks.register<JacocoReport>("applicationCodeCoverageReport") {
    executionData(tasks.run.get())
    sourceSets(sourceSets.main.get())
}

application {
    // Define the main class for the application
    mainClassName = "com.publicissapient.tondeuse.WebApp"
}

apply(plugin = "io.spring.dependency-management")

tasks.getByName<Jar>("jar") {
    enabled = true

    manifest {
        attributes(
                mapOf(
                        "Main-Class" to "com.publicissapient.tondeuse.WebApp",
                        "Implementation-Title" to project.name,
                        "Implementation-Version" to archiveVersion,
                        "Built-By" to System.getProperty("user.name"),
                        "Built-Date" to LocalDateTime.now(),
                        "Built-JDK" to System.getProperty("java.version"),
                        "Built-Gradle" to gradle.gradleVersion)
        )
    }


}




/*tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }

        rule {
            enabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}*/

tasks.getByName<BootJar>("bootJar") {
    mainClassName = "com.publicissapient.tondeuse.WebApp"

}

springBoot {
    mainClassName = "com.publicissapient.tondeuse.WebApp"
}

