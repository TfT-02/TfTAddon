package com.me.tft_02.tftaddon.config;

import java.util.List;

public class Config extends AutoUpdateConfigLoader {
    private static Config instance;

    private Config() {
        super("config.yml");
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }

        return instance;
    }

    @Override
    protected void loadKeys() {}

    /* @formatter:off */

    /* GENERAL SETTINGS */
//    public String getLocale() { return config.getString("General.Locale", "en_us"); }
//    public int getSaveInterval() { return config.getInt("General.Save_Interval", 10); }
    public boolean getStatsTrackingEnabled() { return config.getBoolean("General.Stats_Tracking", true); }
    public boolean getUpdateCheckEnabled() { return config.getBoolean("General.Update_Check", true); }
    public boolean getVerboseLoggingEnabled() { return config.getBoolean("General.Verbose_Logging", false); }
    public boolean getConfigOverwriteEnabled() { return config.getBoolean("General.Config_Update_Overwrite", true); }

    /* WORLDGUARD SETTINGS */
    public boolean getUseAsWhitelist() { return config.getBoolean("WorldGuard.Use_As_Whitelist", false); }
    public List<String> getRegionList() { return config.getStringList("WorldGuard.Regions"); }

    /* @formatter:on */
}
