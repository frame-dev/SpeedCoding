package de.framedev.speedcoding.commands;

import de.framedev.speedcoding.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This Plugin was Created by FrameDev
 * Package : de.framedev.speedcoding.commands
 * ClassName EconomyCMD
 * Date: 08.04.21
 * Project: SpeedCoding
 * Copyrighted by FrameDev
 */

public class EconomyCMD implements CommandExecutor {

    private final Main plugin;

    public EconomyCMD(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("economy").setExecutor(this);
        plugin.getCommand("balance").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("economy")) {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("§cDu musst dafuer ein Spieler sein!");
                        return true;
                    }
                    Player player = (Player) sender;
                    if (!player.hasPermission("speedcoding.economy.set")) {
                        player.sendMessage("§cKeine Permissions!");
                        return true;
                    }
                    try {
                        double amount = Double.parseDouble(args[1]);
                        plugin.getVaultManager().getEconomy().withdrawPlayer(player, plugin.getVaultManager().getEconomy().getBalance(player));
                        plugin.getVaultManager().getEconomy().depositPlayer(player, amount);
                        player.sendMessage("§aDein Geld wurde auf §6" + amount + " §agesetzt!");
                        return true;
                    } catch (Exception ignored) {
                        player.sendMessage("§6" + args[1] + " §cist keine Zahl!");
                        return true;
                    }
                }
            }
        }
        if (command.getName().equalsIgnoreCase("balance")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("§cDu musst dafuer ein Spieler sein!");
                    return true;
                }

                Player player = (Player) sender;
                if (!player.hasPermission("speedcoding.balance")) {
                    player.sendMessage("§cKeine Permissions!");
                    return true;
                }
                player.sendMessage("§aDein Geld beträgt : §6" + plugin.getVaultManager().getEconomy().getBalance(player));
                return true;
            } else if (args.length == 1) {
                @SuppressWarnings("deprecation")
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                if (!plugin.getVaultManager().getEconomy().hasAccount(player)) {
                    sender.sendMessage("§cDieser Spieler hat kein Account!");
                    return true;
                }
                if (!sender.hasPermission("speedcoding.balance.others")) {
                    sender.sendMessage("§cKeine Permissions!");
                    return true;
                }
                sender.sendMessage("§aDas Geld von §6" + player.getName() + " §abeträgt : §6" + plugin.getVaultManager().getEconomy().getBalance(player));
                return true;
            }
        }
        return false;
    }
}
