import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.21"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.duncte123"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("MainKt")
}

tasks.withType<Wrapper> {
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.BIN
}

tasks.withType<ShadowJar> {
    // Configure the file name to be "BruhMark.jar"
    archiveBaseName.set("BruhMark")
    archiveClassifier.set("")
    archiveVersion.set("")
}
