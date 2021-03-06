package de.framedev.speedcoding.main;

import de.framedev.speedcoding.commands.BackPackCMD;
import de.framedev.speedcoding.managers.KitManager;
import de.framedev.speedcoding.managers.MultiWorldManager;
import de.framedev.speedcoding.managers.VaultManager;
import de.framedev.speedcoding.managers.SetupManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    // VaultManager
    private VaultManager vaultManager;

    @Override
    public void onEnable() {
        instance = this;

        new SetupManager(this);
        new KitManager().createCustomConfig();
        
        if (Bukkit.getPluginManager().getPlugin("Vault") != null)
            vaultManager = new VaultManager(this);

        // Worlds Regenerate / Reloading
        MultiWorldManager.startWorlds();
        BackPackCMD.loadBackPacks();
    }

    @Override
    public void onDisable() {
        BackPackCMD.saveBackPacks();
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }

    public static Main getInstance() {
        return instance;
    }
}
