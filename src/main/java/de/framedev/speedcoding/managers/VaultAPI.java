package de.framedev.speedcoding.managers;

import de.framedev.speedcoding.main.Main;
import net.milkbowl.vault.economy.AbstractEconomy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This Plugin was Created by FrameDev
 * Package : de.framedev.speedcoding.managers
 * ClassName VaultAPI
 * Date: 08.04.21
 * Project: SpeedCoding
 * Copyrighted by FrameDev
 */

public class VaultAPI extends AbstractEconomy {

    private final File file = new File(Main.getInstance().getDataFolder() + "/money", "eco.yml");
    private final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    private void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> accounts = getAccounts();

    public List<String> getAccounts() {
        if (cfg.contains("accounts")) {
            this.accounts = cfg.getStringList("accounts");
        } else {
            this.accounts = new ArrayList<>();
        }
        return accounts;
    }

    @Override
    public boolean isEnabled() {
        return Main.getInstance().isEnabled();
    }

    @Override
    public String getName() {
        return Main.getInstance().getDescription().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return "";
    }

    @Override
    public String currencyNamePlural() {
        return "";
    }

    @Override
    public String currencyNameSingular() {
        return "";
    }

    @Override
    public boolean hasAccount(String player) {
        return accounts.contains(player);
    }

    @Override
    public boolean hasAccount(String player, String world) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String player) {
        return cfg.getDouble(player + ".money");
    }

    @Override
    public double getBalance(String player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean has(String player, String world, double amount) {
        return has(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String player, double amount) {
        if (!hasAccount(player))
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The Player has no Account!");
        double money = getBalance(player);
        money -= amount;
        cfg.set(player + ".money", money);
        save();
        return new EconomyResponse(amount, money, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String player, String world, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String player, double amount) {
        if (!hasAccount(player))
            return new EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.FAILURE, "The Player has no Account!");
        double money = getBalance(player);
        money += amount;
        cfg.set(player + ".money", money);
        save();
        return new EconomyResponse(amount, money, EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String player, String world, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String player) {
        if (accounts.contains(player)) return false;
        accounts.add(player);
        cfg.set("accounts", accounts);
        save();
        return true;
    }

    @Override
    public boolean createPlayerAccount(String player, String world) {
        return createPlayerAccount(player);
    }
}
