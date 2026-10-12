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
    // The PluginAnalytics SDK.
    maven("https://pluginanalytics.dev/maven")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("com.gamepathics:pluginanalytics-bukkit:1.7.0")
}

tasks.processResources {
    filesMatching("plugin.yml") { expand("version" to project.version) }
}

tasks.shadowJar {
    archiveClassifier = ""
    // Required: moves the SDK into your own package so it never clashes with another plugin's copy.
    relocate("com.gamepathics.pluginanalytics", "com.example.arena.libs.analytics")
}

tasks.build { dependsOn(tasks.shadowJar) }
