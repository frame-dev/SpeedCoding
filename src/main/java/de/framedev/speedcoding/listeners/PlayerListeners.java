package de.framedev.speedcoding.listeners;

import de.framedev.speedcoding.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This Plugin was Created by FrameDev
 * Package : de.framedev.speedcoding.listeners
 * ClassName PlayerListeners
 * Date: 08.04.21
 * Project: SpeedCoding
 * Copyrighted by FrameDev
 */

public class PlayerListeners implements Listener {

    private final Main plugin;

    public PlayerListeners(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getVaultManager() != null)
            if (!plugin.getVaultManager().getEconomy().hasAccount(event.getPlayer()))
                plugin.getVaultManager().getEconomy().createPlayerAccount(event.getPlayer());
    }
}
