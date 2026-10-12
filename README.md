# PluginAnalytics examples

Small, complete Minecraft plugins that use [PluginAnalytics](https://pluginanalytics.dev): product analytics for
Minecraft plugins and mods. One line of code gives you a private dashboard with servers and players, events,
funnels, retention, errors grouped by stack trace, logs, per-method performance and how your SpigotMC or Modrinth
listing turns views into installs.

| Folder | Platform | Artifact |
|---|---|---|
| [`paper/`](paper) | Paper, Spigot, Purpur, Folia | `com.gamepathics:pluginanalytics-bukkit` |
| [`velocity/`](velocity) | Velocity | `com.gamepathics:pluginanalytics-velocity` |
| [`bungeecord/`](bungeecord) | BungeeCord, Waterfall | `com.gamepathics:pluginanalytics-bungee` |

Fabric and NeoForge mods use `pluginanalytics-fabric` and `pluginanalytics-neoforge`: see
[Proxies and mods](https://pluginanalytics.dev/docs/proxies-and-mods/).

## The one line

```java
analytics = PluginAnalytics.start(this, "pa_your_project_key");
```

Get the project key at [pluginanalytics.dev](https://pluginanalytics.dev) (free for one plugin or mod). Then:

```java
analytics.track("arena_joined", player, "mode", "solo");   // a player event, with a property
analytics.property("storage", () -> config.getString("storage")); // a value per server
analytics.time("load_arenas", this::loadArenas);                 // time a piece of code
analytics.captureError(e);                                        // an exception you caught yourself
```

Commands, uncaught errors, your plugin's log lines and its main-thread time are collected without any code.

## Adding it to your build

```kotlin
plugins {
    id("com.gradleup.shadow") version "8.3.9"
}

repositories {
    maven("https://pluginanalytics.dev/maven")
}

dependencies {
    implementation("com.gamepathics:pluginanalytics-bukkit:1.7.0")
}

tasks.shadowJar {
    // Required: an SDK left in its own package turns itself off, so two plugins never clash.
    relocate("com.gamepathics.pluginanalytics", "me.you.yourplugin.libs.analytics")
}
```

Maven and Groovy Gradle: [Installation](https://pluginanalytics.dev/docs/install/).

## Building these examples

```bash
./gradlew build
```

The jars land in each folder's `build/libs/`. Gradle downloads the Java version each example needs.

## Privacy

No player names, UUIDs, IPs or chat are ever sent; players are a salted hash that differs on every server. Server
owners turn it off for every plugin at once with `enabled: false` in `plugins/PluginAnalytics/config.yml`.
[Exactly what is sent](https://pluginanalytics.dev/privacy/).

## Links

- [Quickstart](https://pluginanalytics.dev/docs/)
- [The SDK on SpigotMC](https://www.spigotmc.org/resources/139577/)
- [SDK reference](https://pluginanalytics.dev/docs/api/)
- [Pricing](https://pluginanalytics.dev/pricing/)
- [Founding developer program](https://pluginanalytics.dev/founding/): the first 50 developers get Pro free for 90 days

Made by Skilled. The examples are MIT licensed: copy anything you like.
