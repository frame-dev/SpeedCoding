package de.framedev.speedcoding.commands;

import de.framedev.speedcoding.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatClearCMD implements CommandExecutor {

    public ChatClearCMD(Main plugin) {
        plugin.getCommand("chatclear").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("speedcoding.chatclear")) {
            sender.sendMessage("§cKeine Permissions§4§l!");
            return true;
        }
        for(int i = 0; i < 300; i++) {
            Bukkit.broadcastMessage(" ");
        }
        Bukkit.broadcastMessage("§aDer Chat wurde von §6" + sender.getName() + " §ageleert!");
        return true;
    }
}
