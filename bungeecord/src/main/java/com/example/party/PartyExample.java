package com.example.party;

import com.gamepathics.pluginanalytics.PluginAnalytics;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

/** A tiny BungeeCord plugin with PluginAnalytics: https://pluginanalytics.dev/docs/proxies-and-mods/ */
public final class PartyExample extends Plugin {

    private PluginAnalytics analytics;

    @Override
    public void onEnable() {
        // The one required line: the proxy counts as one server; players are counted as they join.
        analytics = PluginAnalytics.start(this, "pa_your_project_key");

        getProxy().getPluginManager().registerCommand(this, new Command("party") {
            @Override
            public void execute(CommandSender sender, String[] args) {
                if (sender instanceof ProxiedPlayer player) {
                    analytics.track("party_created", player, "size", String.valueOf(args.length + 1));
                    player.sendMessage(new TextComponent("Party created."));
                }
            }
        });
    }
}
