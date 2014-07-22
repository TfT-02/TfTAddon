package com.me.tft_02.tftaddon;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.me.tft_02.tftaddon.commands.DuraCommand;
import com.me.tft_02.tftaddon.commands.TfTAddonCommand;
import com.me.tft_02.tftaddon.commands.TfTAxesCommand;
import com.me.tft_02.tftaddon.commands.TfTHelpCommand;
import com.me.tft_02.tftaddon.commands.TfTHerbalismCommand;
import com.me.tft_02.tftaddon.commands.TfTRepairCommand;
import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.hooks.McMMOListener;
import com.me.tft_02.tftaddon.listener.BlockListener;
import com.me.tft_02.tftaddon.listener.EntityListener;
import com.me.tft_02.tftaddon.listener.PlayerListener;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.mcstats.Metrics;

public class TfTAddon extends JavaPlugin {
    public static TfTAddon p;

    private boolean mcMMOEnabled = false;
    private boolean worldGuardEnabled = false;

    /**
     * Run things on enable.
     */
    @Override
    public void onEnable() {
        p = this;

        setupMcMMO();

        if (!isMcMMOEnabled()) {
            this.getLogger().log(Level.WARNING, " requires mcMMO to run, please download mcMMO");
            getServer().getPluginManager().disablePlugin(this);
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
            catch (IOException e) {
            }
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
        getCommand("tftaddon").setExecutor(new TfTAddonCommand());
        getCommand("tfthelp").setExecutor(new TfTHelpCommand());
        getCommand("tftaxes").setExecutor(new TfTAxesCommand());
        getCommand("tftherbalism").setExecutor(new TfTHerbalismCommand());
        getCommand("tftrepair").setExecutor(new TfTRepairCommand());
        getCommand("dura").setExecutor(new DuraCommand());
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

    private void setupMcMMO() {
        if (getServer().getPluginManager().isPluginEnabled("mcMMO")) {
            mcMMOEnabled = true;
            debug("mcMMO found!");
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

    public boolean isWorldGuardEnabled() {
        return worldGuardEnabled;
    }

    public boolean isMcMMOEnabled() {
        return mcMMOEnabled;
    }
}
