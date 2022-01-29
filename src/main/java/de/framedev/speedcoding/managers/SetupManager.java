package de.framedev.speedcoding.managers;

import de.framedev.speedcoding.commands.*;
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

public class SetupManager {

    private final Main plugin;

    public SetupManager(Main plugin) {
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
        new BackPackCMD(plugin);
    }

    private void setupListeners() {
        new PlayerListeners(plugin);
    }
}
