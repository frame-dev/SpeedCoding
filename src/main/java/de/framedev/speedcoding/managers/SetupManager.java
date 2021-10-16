package de.framedev.speedcoding.utils;

import de.framedev.speedcoding.commands.ChatClearCMD;
import de.framedev.speedcoding.commands.EconomyCMD;
import de.framedev.speedcoding.commands.HomeCMD;
import de.framedev.speedcoding.commands.KitCMD;
import de.framedev.speedcoding.commands.WorldCMD;
import de.framedev.speedcoding.listeners.PlayerListeners;
import de.framedev.speedcoding.main.Main;

/**
 * / This Plugin was Created by FrameDev
 * / Package : de.framedev.speedcoding.utils
 * / ClassName Setup
 * / Date: 03.07.21
 * / Project: SpeedCoding
 * / Copyrighted by FrameDev
 */

public class Setup {

    private final Main plugin;

    public Setup(Main plugin) {
        this.plugin = plugin;
        setupCommands();
        setupListeners();
    }

    private void setupCommands() {
        new WorldCMD();
        new ChatClearCMD(plugin);
        new HomeCMD(plugin);
        new EconomyCMD(plugin);
        new KitCMD(plugin);
    }

    private void setupListeners() {
        new PlayerListeners(plugin);
    }
}
