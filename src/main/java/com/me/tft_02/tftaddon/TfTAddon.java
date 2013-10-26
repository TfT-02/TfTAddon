package com.me.tft_02.tftaddon;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.me.tft_02.tftaddon.commands.Commands;
import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.hooks.McMMOListener;
import com.me.tft_02.tftaddon.listener.BlockListener;
import com.me.tft_02.tftaddon.listener.EntityListener;
import com.me.tft_02.tftaddon.listener.PlayerListener;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.mcstats.Metrics;

public class TfTAddon extends JavaPlugin {
    public static TfTAddon p;

    public boolean mcMMOEnabled = false;
    public boolean worldGuardEnabled = false;


    public static TfTAddon getInstance() {
        return p;
    }

    /**
     * Run things on enable.
     */
    @Override
    public void onEnable() {
        p = this;

        if (!setupMcMMO()) {
            return;
        }

        setupWorldGuard();

        registerEvents();

        registerCommands();

        if (Config.getInstance().getStatsTrackingEnabled()) {
            try {
                Metrics metrics = new Metrics(this);
                metrics.start();
            }
            catch (IOException e) {}
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new EntityListener(), this);
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new BlockListener(), this);
        pm.registerEvents(new McMMOListener(), this);
    }

    /**
     * Register all the command and set Executor.
     */
    private void registerCommands() {
        getCommand("tftaddon").setExecutor(new Commands(this));
        getCommand("tfthelp").setExecutor(new Commands(this));
        getCommand("tftaxes").setExecutor(new Commands(this));
        getCommand("tftherbalism").setExecutor(new Commands(this));
        getCommand("tftrepair").setExecutor(new Commands(this));
        getCommand("dura").setExecutor(new Commands(this));
    }

    /**
     * Run things on disable.
     */
    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }

    public void debug(String message) {
        getLogger().info("[Debug] " + message);
    }

    private boolean setupMcMMO() {
        PluginManager pm = getServer().getPluginManager();
        if (pm.getPlugin("mcMMO") == null || !pm.isPluginEnabled("mcMMO")) {
            this.getLogger().log(Level.WARNING, " requires mcMMO to run, please download mcMMO");
            pm.disablePlugin(this);
            return false;
        }
        else {
            return true;
        }
    }

    private void setupWorldGuard() {
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
            worldGuardEnabled = true;
            debug("WorldGuard found!");
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    }

    }
}
