// Three standalone examples: copy any one folder into your own project.
plugins {
    // Downloads the Java version an example needs if your machine doesn't have it.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

rootProject.name = "pluginanalytics-examples"

include("paper", "velocity", "bungeecord")
