plugins {
    java
    id("com.gradleup.shadow")
}

group = "com.example"
version = "1.0.0"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://pluginanalytics.dev/maven")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    implementation("com.gamepathics:pluginanalytics-velocity:1.7.0")
}

tasks.shadowJar {
    archiveClassifier = ""
    relocate("com.gamepathics.pluginanalytics", "com.example.lobby.libs.analytics")
}

tasks.build { dependsOn(tasks.shadowJar) }
