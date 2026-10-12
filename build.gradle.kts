plugins {
    application
    java
    id("org.danilopianini.gradle-java-qa") version "1.159.0"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.9.8")
}
/*
application {
    this will be useful once we know what the main class is. 
    We will need to put here 
    mainClass.set("name of the main file")
    to make it so gradle knows what is the main
}*/

tasks.javadoc {
    isFailOnError = false
}
