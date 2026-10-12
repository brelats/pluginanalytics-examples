package com.example.lobby;

import com.gamepathics.pluginanalytics.PluginAnalytics;
import com.google.inject.Inject;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.nio.file.Path;

/** A tiny Velocity plugin with PluginAnalytics: https://pluginanalytics.dev/docs/proxies-and-mods/ */
@Plugin(id = "lobbyexample", name = "LobbyExample", version = "1.0.0", authors = "Skilled")
public final class LobbyExample {

    private final ProxyServer server;
    private final Path dataDirectory;
    private PluginAnalytics analytics;

    @Inject
    public LobbyExample(ProxyServer server, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onInit(ProxyInitializeEvent event) {
        // The one required line: the proxy counts as one server; players are counted as they join.
        analytics = PluginAnalytics.start(this, server, dataDirectory, "pa_your_project_key");
        analytics.property("backend_servers", () -> server.getAllServers().size());

        server.getCommandManager().register("hub", (SimpleCommand) invocation -> {
            if (invocation.source() instanceof Player player) {
                analytics.track("hub_used", player);
                player.sendMessage(Component.text("Sending you to the hub..."));
            }
        });
    }
}
