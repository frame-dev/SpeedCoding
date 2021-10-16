package de.framedev.speedcoding.commands;

import de.framedev.speedcoding.main.Main;
import de.framedev.speedcoding.managers.MultiWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * =============================================
 * = This Plugin was Created by FrameDev       =
 * = Copyrighted by FrameDev                   =
 * = Please don't Change anything              =
 * =============================================
 * This Class was made at 06.12.2019, 21:17
 */

public class WorldCMD implements CommandExecutor {

    public WorldCMD() {
        Main.getInstance().getCommand("mv").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("mv")) {
            if(args[0].equalsIgnoreCase("create")) {
                if(args.length == 2) {
                    String worldname = args[1];
                    new MultiWorldManager(worldname).createWorld();
                } else if(args.length == 3) {
                    String worldname = args[1];
                    new MultiWorldManager(worldname).createWorld(World.Environment.valueOf(args[2]));
                } else if(args.length == 4) {
                    String worldname = args[1];
                    new MultiWorldManager(worldname).createWorld(WorldType.getByName(args[3]), World.Environment.valueOf(args[2]));
                }
            }  else if(args[0].equalsIgnoreCase("tp")) {
                if(args.length == 2) {
                    String worldname = args[1];
                    if(sender instanceof Player) {
                        ((Player) sender).teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                    }
                }
            }
        }
        return false;
    }
}
