package de.framedev.speedcoding.managers;

import de.framedev.speedcoding.main.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.ServicePriority;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This Plugin was Created by FrameDev
 * Package : de.framedev.speedcoding.managers
 * ClassName VaultManager
 * Date: 08.04.21
 * Project: SpeedCoding
 * Copyrighted by FrameDev
 */

public class VaultManager {

    private final Economy economy;
    private final File file = new File(Main.getInstance().getDataFolder() + "/money", "eco.yml");
    private final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);


    public VaultManager(Main plugin) {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (Bukkit.getServer().getOnlineMode()) {
            if (!cfg.contains("accounts")) {
                ArrayList<String> accounts = new ArrayList<>();
                accounts.add("2f8f4d80-277a-4ee0-9224-3257e88ba0dc");
                cfg.set("accounts", accounts);
            }
        } else {
            if (!cfg.contains("accounts")) {
                ArrayList<String> accounts = new ArrayList<>();
                accounts.add("FramePlays");
                cfg.set("accounts", accounts);
            }
        }
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        plugin.getServer().getServicesManager().register(Economy.class, new VaultAPI(), plugin, ServicePriority.High);
        economy = new VaultAPI();
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!economy.hasAccount(player))
                economy.createPlayerAccount(player);
        });
    }

    public Economy getEconomy() {
        return economy;
    }
}
