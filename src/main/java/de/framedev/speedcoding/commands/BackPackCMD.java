package de.framedev.speedcoding.commands;

import de.framedev.speedcoding.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * / This Plugin was Created by FrameDev
 * / Package : de.framedev.speedcoding.commands
 * / ClassName BackPackCMD
 * / Date: 29.01.22
 * / Project: SpeedCoding
 * / Copyrighted by FrameDev
 */

public class BackPackCMD implements CommandExecutor {

    private final Main plugin;
    public static HashMap<OfflinePlayer, Inventory> playerBackpack;
    private static File file;
    private static FileConfiguration cfg;

    public BackPackCMD(Main plugin) {
        this.plugin = plugin;
        playerBackpack = new HashMap<>();
        file = new File(plugin.getDataFolder(), "backpacks.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
        plugin.getCommand("backpack").setExecutor(this::onCommand);
    }

    public static void loadBackPacks() {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (cfg.contains(player.getName())) {
                String inventory = cfg.getString(player.getName());
                playerBackpack.put(player, inventoryFromBase64(inventory));
            }
        }
    }

    public static void saveBackPacks() {
        for (Map.Entry<OfflinePlayer, Inventory> entry : playerBackpack.entrySet()) {
            cfg.set(entry.getKey().getName(), inventoryToBase64(entry.getValue()));
        }

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String inventoryToBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Inventory inventoryFromBase64(String base64) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.createInventory(null, dataInput.readInt());

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cNur Spieler koennen diesen Befehl ausfuehren!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("speedcoding.backpack")) {
            player.sendMessage("§cKeine Permissions!");
            return true;
        }
        if (playerBackpack.containsKey(player)) {
            player.openInventory(playerBackpack.get(player));
        } else {
            Inventory inventory = Bukkit.createInventory(null, 3 * 9);
            player.openInventory(inventory);
            playerBackpack.put(player, inventory);
        }
        return true;
    }
}
