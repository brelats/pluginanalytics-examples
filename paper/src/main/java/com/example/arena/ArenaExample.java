package com.example.arena;

import com.gamepathics.pluginanalytics.PluginAnalytics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A tiny arena plugin with PluginAnalytics: https://pluginanalytics.dev/docs/
 *
 * <p>Everything below the start line is optional. With only that line you already get servers, players,
 * versions, every command, errors, logs and performance.
 */
public final class ArenaExample extends JavaPlugin {

    private PluginAnalytics analytics;
    private final Map<UUID, String> playing = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // The one required line. It never throws and never blocks, and it stops by itself on disable.
        analytics = PluginAnalytics.start(this, getConfig().getString("analytics-key", ""));

        // A value per server, shown as the share of servers per value.
        analytics.property("arenas", () -> loadedArenas());

        // Timing a piece of code: Performance shows it per version, across servers.
        analytics.time("load_arenas", this::loadArenas);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Players only.");
            return true;
        }
        String action = args.length > 0 ? args[0] : "join";
        switch (action) {
            case "join" -> {
                String mode = args.length > 1 ? args[1] : getConfig().getString("default-mode", "solo");
                playing.put(player.getUniqueId(), mode);
                // A player event with a property: Events shows it per mode; Funnels can start from it.
                analytics.track("arena_joined", player, "mode", mode);
                player.sendMessage("Joined a " + mode + " arena.");
            }
            case "kit" -> {
                String kit = args.length > 1 ? args[1] : "warrior";
                analytics.track("kit_selected", player, "kit", kit);
                player.sendMessage("Kit: " + kit);
            }
            case "leave" -> {
                String mode = playing.remove(player.getUniqueId());
                if (mode != null) analytics.track("arena_left", player, "mode", mode);
                player.sendMessage("Left the arena.");
            }
            default -> player.sendMessage("Usage: /arena <join|leave|kit> [mode|kit]");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length == 1 ? List.of("join", "leave", "kit") : List.of();
    }

    private void loadArenas() {
        try {
            // Your real loading code goes here.
            getLogger().info("Loaded " + loadedArenas() + " arenas");
        } catch (RuntimeException e) {
            // Uncaught exceptions are reported on their own; this is for ones you catch and handle.
            analytics.captureError(e);
        }
    }

    private int loadedArenas() {
        return 4;
    }
}
