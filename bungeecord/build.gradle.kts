plugins {
    java
    id("com.gradleup.shadow")
}

group = "com.example"
version = "1.0.0"

java { toolchain { languageVersion = JavaLanguageVersion.of(17) } }

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    // BungeeCord's API depends on Mojang's Brigadier.
    maven("https://libraries.minecraft.net")
    maven("https://pluginanalytics.dev/maven")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.21-R0.4")
    implementation("com.gamepathics:pluginanalytics-bungee:1.7.0")
}

tasks.processResources {
    filesMatching("bungee.yml") { expand("version" to project.version) }
}

tasks.shadowJar {
    archiveClassifier = ""
    relocate("com.gamepathics.pluginanalytics", "com.example.party.libs.analytics")
}

tasks.build { dependsOn(tasks.shadowJar) }
