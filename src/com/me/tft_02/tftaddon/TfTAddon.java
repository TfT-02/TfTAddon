package com.me.tft_02.tftaddon;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.hooks.McMMOListener;
import com.me.tft_02.tftaddon.listener.BlockListener;
import com.me.tft_02.tftaddon.listener.EntityListener;
import com.me.tft_02.tftaddon.listener.PlayerListener;
import com.me.tft_02.tftaddon.runnables.UpdateCheckerTask;
import com.me.tft_02.tftaddon.util.Metrics;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class TfTAddon extends JavaPlugin {
    public static TfTAddon p;

    public boolean worldGuardEnabled = false;

    // Update Check
    public boolean updateAvailable;

    public static boolean debug_mode = false;

    public static TfTAddon getInstance() {
        return p;
    }

    /**
     * Run things on enable.
     */
    @Override
    public void onEnable() {
        p = this;

        setupMcMMO();

        if (getConfig().getBoolean("General.debug_mode_enabled")) {
            this.getLogger().log(Level.WARNING, "Debug mode is enabled, this is only for advanced users!");
            debug_mode = true;
        }
        setupConfiguration();
        registerEvents();

        registerCommands();
        checkForUpdates();

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

    private void setupConfiguration() {
        final FileConfiguration config = this.getConfig();
        config.addDefault("General.debug_mode_enabled", false);
        config.addDefault("General.stats_tracking_enabled", true);

        config.addDefault("Skills.Axes.Dura_level_cap", 100);
        config.addDefault("Skills.Axes.Dura_percentage_max", 50);

        config.addDefault("Skills.Herbalism.SunnyDay_unlock_level", 50);
        config.addDefault("Skills.Herbalism.SunnyDay_cost", 30);

        config.addDefault("Skills.Repair.BlacksmithsInstinct_unlock_level", 800);
        config.addDefault("Skills.Repair.BlacksmithsInstinct_percentage_durability_left", 10);

        config.addDefault("Announce_Level_Up.Power_Level", 100);

        config.options().copyDefaults(true);
        saveConfig();
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

    private void setupMcMMO() {
        PluginManager pm = getServer().getPluginManager();
        if (pm.getPlugin("mcMMO") == null || !pm.isPluginEnabled("mcMMO")) {
            this.getLogger().log(Level.WARNING, " requires mcMMO to run, please download mcMMO");
            pm.disablePlugin(this);
            return;
        }
    }

    private void setupWorldGuard() {
        if (getServer().getPluginManager().isPluginEnabled("WorldGuard")) {
            worldGuardEnabled = true;
//            debug("WorldGuard found!");
// Schedule region check
        }
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    private void checkForUpdates() {
        if (!Config.getInstance().getUpdateCheckEnabled()) {
            return;
        }

        getServer().getScheduler().runTaskAsynchronously(this, new UpdateCheckerTask());
    }

    public void updateCheckerCallback(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
        if (updateAvailable) {
            getLogger().info(ChatColor.GRAY + "You are using an outdated version of " + ChatColor.GOLD + "TfTAddon!");
            getLogger().info(ChatColor.GRAY + "There is a new version available on BukkitDev.");
        }
    }
}
