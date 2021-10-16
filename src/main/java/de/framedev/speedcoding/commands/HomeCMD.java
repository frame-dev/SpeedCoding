package de.framedev.speedcoding.commands;

import de.framedev.speedcoding.main.Main;
import de.framedev.speedcoding.managers.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCMD implements CommandExecutor {

    public HomeCMD(Main plugin) {
        plugin.getCommand("sethome").setExecutor(this);
        plugin.getCommand("home").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cNur Spieler koennen das!");
            return true;
        }
        Player player = (Player) sender;
        if(command.getName().equalsIgnoreCase("sethome")) {
            if(args.length == 0) {
                new LocationManager().setLocation(player.getName() + ".home.home",player.getLocation());
                player.sendMessage("§aHome gesetzt!");
                return true;
            } else if(args.length == 1) {
                new LocationManager().setLocation(player.getName() + ".home." + args[0].toLowerCase(),player.getLocation());
                player.sendMessage("§aHome §6" + args[0].toLowerCase() + " §agesetzt!");
                return true;
            }
        }
        if(command.getName().equalsIgnoreCase("home")) {
            if(args.length == 0) {
                if(new LocationManager().getLocation(player.getName() + ".home.home") != null) {
                    player.teleport(new LocationManager().getLocation(player.getName() + ".home.home"));
                    player.sendMessage("§aTeleportet!");
                    return true;
                } else {
                    player.sendMessage("§cDieses Home existiert nicht!");
                    return true;
                }
            } else if(args.length == 1) {
                if(new LocationManager().getLocation(player.getName() + ".home." + args[0].toLowerCase()) != null) {
                    player.teleport(new LocationManager().getLocation(player.getName() + ".home." + args[0].toLowerCase()));
                    player.sendMessage("§aTeleportet!");
                    return true;
                } else {
                    player.sendMessage("§cDieses Home existiert nicht!");
                    return true;
                }
            }
        }
        return false;
    }
}
