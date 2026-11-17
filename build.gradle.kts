plugins {
    application
    java
    id("org.danilopianini.gradle-java-qa") version "1.159.0"
 //   id("checkstyle")
}

repositories {
    mavenCentral()
}

// checkstyle {
//     toolVersion = "10.12.4"
//     configFile = file("${project.rootDir}/quality/checkstyle/checkstyle.xml")
//     maxWarnings = 0
//     configProperties["checkstyleSuppressionsPath"] = file("${project.rootDir}/quality/checkstyle/suppressions.xml").absolutePath
// }

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jfree:jfreechart:1.5.6")

    testImplementation(platform("org.junit:junit-bom:6.0.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("project.Main")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events(*org.gradle.api.tasks.testing.logging.TestLogEvent.values())
    }

    testLogging.showStandardStreams = true
}

tasks.javadoc {
    isFailOnError = false
}
