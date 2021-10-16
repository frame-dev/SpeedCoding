package de.framedev.speedcoding.managers;

import de.framedev.speedcoding.main.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * =============================================
 * = This Plugin was Created by FrameDev       =
 * = Copyrighted by FrameDev                   =
 * = Please don't Change anything              =
 * =============================================
 * This Class was made at 06.12.2019, 21:17
 */

public class MultiWorldManager {

    private final String worldname;
    private World world;

    public MultiWorldManager(String worldname) {
        this.worldname = worldname;
        if (Bukkit.getWorld(worldname) != null)
            this.world = Bukkit.getWorld(worldname);
    }

    public void createWorld() {
        WorldCreator wc = new WorldCreator(worldname);
        wc.environment(World.Environment.NORMAL);
        this.world = wc.createWorld();
        addWorld(this.world);
    }

    public void createWorld(World.Environment environment) {
        WorldCreator wc = new WorldCreator(worldname);
        wc.environment(environment);
        this.world = wc.createWorld();
        addWorld(this.world);
    }

    public void createWorld(WorldType worldType) {
        WorldCreator wc = new WorldCreator(worldname);
        wc.type(worldType);
        this.world = wc.createWorld();
        addWorld(this.world);
    }

    public void createWorld(WorldType worldType, World.Environment environment) {
        WorldCreator wc = new WorldCreator(worldname);
        wc.environment(environment);
        wc.type(worldType);
        this.world = wc.createWorld();
        addWorld(this.world);
    }

    public void setSpawnLocation(Location location) {
        world.setSpawnLocation(location);
    }

    public void setDifficulty(Difficulty difficulty) {
        world.setDifficulty(difficulty);
    }

    public Difficulty getDifficulty() {
        return world.getDifficulty();
    }

    private static File file = new File(Main.getInstance().getDataFolder(), "worlds.yml");
    private static FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public static boolean worldLoaded(String world) {
        return Bukkit.getWorld(world) != null;
    }

    public static void startWorlds() {
        if (cfg.contains("worlds"))
            for (String world : cfg.getStringList("worlds")) {
                new WorldCreator(world).createWorld();
            }
    }

    private void addWorld(World world) {
        if (!cfg.contains("worlds")) {
            ArrayList<String> worlds = new ArrayList<>();
            worlds.add(world.getName());
            cfg.set("worlds", worlds);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            List<String> worlds = cfg.getStringList("worlds");
            if (!worlds.contains(world.getName()))
                worlds.add(world.getName());
            cfg.set("worlds", worlds);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
