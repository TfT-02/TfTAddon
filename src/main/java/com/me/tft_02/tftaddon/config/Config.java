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

    /* SKILLS */
    public int getAxesDuraLevelCap() { return config.getInt("Skills.Axes.Durability_MaxBonusLevel", 250); }
    public int getAxesChanceMax() { return config.getInt("Skills.Axes.Durability_ChanceMax", 50); }

    public int getHerbalismSunnyDayCost() { return config.getInt("Skills.Herbalism.SunnyDay_Cost", 30); }
    public int getHerbalismSunnyDayUnlock() { return config.getInt("Skills.Herbalism.SunnyDay_UnlockLevel", 250); }

    public int getRepairBIUnlock() { return config.getInt("Skills.Repair.BlacksmithsInstinct_UnlockLevel", 800); }
    public double getRepairBIWarnPercentage() { return config.getDouble("Skills.Repair.BlacksmithsInstinct_WarnPercentage", 10.0); }

    public boolean getMiningSuperBreakerInstantObsidian() { return config.getBoolean("Skills.Mining.SuperBreaker_InstantBreakObsidian", false); }

    /* LEVEL UP ANNOUNCEMENTS */

    public int getLevelAnnouncementPowerLevelInterval() { return config.getInt("Announce_Level_Up.Power_Level", 1000); }
    public double getLevelAnnouncementMessageRange() { return config.getDouble("Announce_Level_Up.Message_Range", 75.0); }

    /* WORLDGUARD SETTINGS */
    public boolean getUseAsWhitelist() { return config.getBoolean("WorldGuard.Death_Penalty.Use_As_Whitelist", false); }
    public List<String> getRegionList() { return config.getStringList("WorldGuard.Death_Penalty.Regions"); }

    /* @formatter:on */
}
