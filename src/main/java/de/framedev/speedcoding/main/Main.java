package de.framedev.speedcoding.main;

import de.framedev.speedcoding.managers.KitManager;
import de.framedev.speedcoding.managers.MultiWorldManager;
import de.framedev.speedcoding.managers.VaultManager;
import de.framedev.speedcoding.utils.Setup;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    // VaultManager
    private VaultManager vaultManager;

    @Override
    public void onEnable() {
        instance = this;

        new Setup(this);
        new KitManager().createCustomConfig();
        
        if (Bukkit.getPluginManager().getPlugin("Vault") != null)
            vaultManager = new VaultManager(this);

        // Worlds Regenerate / Reloading
        MultiWorldManager.startWorlds();
    }

    @Override
    public void onDisable() {

    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }

    public static Main getInstance() {
        return instance;
    }
}
