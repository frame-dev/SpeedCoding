package de.framedev.speedcoding.commands;

import de.framedev.speedcoding.main.Main;
import de.framedev.speedcoding.managers.KitManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * / This Plugin was Created by FrameDev
 * / Package : de.framedev.speedcoding.managers
 * / ClassName KitCMD
 * / Date: 15.08.21
 * / Project: SpeedCoding
 * / Copyrighted by FrameDev
 */

public class KitCMD implements CommandExecutor {

    public KitCMD(Main plugin) {
        plugin.getCommand("kits").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            String kitName = args[0];
            if(sender instanceof Player) {
                Player player = (Player) sender;
                KitManager kitManager = new KitManager();
                if(kitManager.existsKit(kitName))
                    kitManager.getKit(kitName, player);
            }
        }
        return false;
    }
}
