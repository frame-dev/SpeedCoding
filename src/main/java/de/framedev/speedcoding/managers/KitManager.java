package de.framedev.speedcoding.managers;

import de.framedev.speedcoding.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * / This Plugin was Created by FrameDev
 * / Package : de.framedev.speedcoding.managers
 * / ClassName KitManager
 * / Date: 15.08.21
 * / Project: SpeedCoding
 * / Copyrighted by FrameDev
 */

public class KitManager {

    private static File customConfigFile;
    private static FileConfiguration customConfig;

    public static FileConfiguration getCustomConfig() {
        return customConfig;
    }

    public void createCustomConfig() {
        customConfigFile = new File(Main.getInstance().getDataFolder(), "kits.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Main.getInstance().saveResource("kits.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public boolean existsKit(String name) {
        return getCustomConfig().contains("Kits." + name);
    }

    public void getKit(String name, Player player) {
        Inventory inventory = Bukkit.createInventory(null, 4 * 9);
        try {
            if (!getCustomConfig().contains("Kits." + name)) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Kit doesn't exists");
                return;
            }
            for (String s : getCustomConfig().getStringList("Kits." + name)) {
                if (s.contains(",")) {
                    String[] x = s.split(",");
                    ItemStack itemStack = new ItemStack(Material.getMaterial(x[0].toUpperCase()), Integer.parseInt(x[1]));
                    inventory.addItem(itemStack);
                } else {
                    ItemStack itemStack = new ItemStack(Material.getMaterial(s.toUpperCase()));
                    inventory.addItem(itemStack);
                }
            }
            for(ItemStack itemStack : inventory.getContents()) {
                if(itemStack != null) player.getInventory().addItem(itemStack);
            }
            inventory.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Inventory getKit(String name) {
        Inventory inventory = Bukkit.createInventory(null, 4 * 9);
        try {
            if (!getCustomConfig().contains("Kits." + name)) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Kit doesn't exists");
                return null;
            }
            for (String s : getCustomConfig().getStringList("Kits." + name)) {
                if (s.contains(",")) {
                    String[] x = s.split(",");
                    ItemStack itemStack = new ItemStack(Material.getMaterial(x[0].toUpperCase()), Integer.parseInt(x[1]));
                    inventory.addItem(itemStack);
                } else {
                    ItemStack itemStack = new ItemStack(Material.getMaterial(s.toUpperCase()));
                    inventory.addItem(itemStack);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inventory;
    }
}
