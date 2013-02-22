package com.me.tft_02.tftaddon;

import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.me.tft_02.tftaddon.listener.BlockListener;
import com.me.tft_02.tftaddon.listener.EntityListener;
import com.me.tft_02.tftaddon.listener.McMMOListener;
import com.me.tft_02.tftaddon.listener.PlayerListener;

public class TfTAddon extends JavaPlugin {

    private final EntityListener entityListener = new EntityListener(this);
    private final PlayerListener playerListener = new PlayerListener(this);
    private final BlockListener blockListener = new BlockListener(this);
    private final McMMOListener mcmmoListener = new McMMOListener(this);

    public static boolean debug_mode = false;

    /**
     * Run things on enable.
     */
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        if (pm.getPlugin("mcMMO") == null || !pm.isPluginEnabled("mcMMO")) {
            this.getLogger().log(Level.WARNING, " requires mcMMO to run, please download mcMMO");
            pm.disablePlugin(this);
            return;
        }
        if (getConfig().getBoolean("General.debug_mode_enabled")) {
            this.getLogger().log(Level.WARNING, "Debug mode is enabled, this is only for advanced users!");
            debug_mode = true;
            printConfiguration();
        }
        setupConfiguration();
        //Register events
        pm.registerEvents(entityListener, this);
        pm.registerEvents(playerListener, this);
        pm.registerEvents(blockListener, this);
        pm.registerEvents(mcmmoListener, this);

        registerCommands();
        if (getConfig().getBoolean("General.stats_tracking_enabled")) {
            try {
                Metrics metrics = new Metrics(this);
                metrics.start();
            } catch ( IOException e) {
                System.out.println("Failed to submit stats.");
            }
        }
    }

    private void setupConfiguration() {
        FileConfiguration config = this.getConfig();
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

    private void printConfiguration() {
        this.getLogger().log(Level.INFO, " ");
        this.getLogger().log(Level.INFO, "Debug mode");
        this.getLogger().log(Level.INFO, "Printing the full config file:");
        this.getLogger().log(Level.INFO, "General.debug_mode_enabled: " + getConfig().getBoolean("General.debug_mode_enabled"));
        this.getLogger().log(Level.INFO, "General.stats_tracking_enabled " + getConfig().getBoolean("General.stats_tracking_enabled"));
        this.getLogger().log(Level.INFO, " ");
        this.getLogger().log(Level.INFO, "Skills.Axes.Dura_level_cap " + getConfig().getInt("Skills.Axes.Dura_level_cap"));
        this.getLogger().log(Level.INFO, "Skills.Axes.Dura_percentage_max " + getConfig().getInt("Skills.Axes.Dura_percentage_max"));
        this.getLogger().log(Level.INFO, " ");
        this.getLogger().log(Level.INFO, "Skills.Herbalism.SunnyDay_unlock_level " + getConfig().getInt("Skills.Herbalism.SunnyDay_unlock_level"));
        this.getLogger().log(Level.INFO, "Skills.Herbalism.SunnyDay_cost " + getConfig().getInt("Skills.Herbalism.SunnyDay_cost"));
        this.getLogger().log(Level.INFO, " ");
        this.getLogger().log(Level.INFO, "Skills.Repair.BlacksmithsInstinct_unlock_level " + getConfig().getInt("Skills.Repair.BlacksmithsInstinct_unlock_level"));
        this.getLogger().log(Level.INFO, "Skills.Repair.BlacksmithsInstinct_percentage_durability_left " + getConfig().getInt("Skills.Repair.BlacksmithsInstinct_percentage_durability_left"));
        this.getLogger().log(Level.INFO, " ");
        this.getLogger().log(Level.INFO, "Announce_Level_Up.Power_Level " + getConfig().getInt("Announce_Level_Up.Power_Level"));
    }
}