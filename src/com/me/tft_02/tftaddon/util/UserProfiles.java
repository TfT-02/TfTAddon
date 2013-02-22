package com.me.tft_02.tftaddon.util;


import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;
import com.me.tft_02.tftaddon.TfTAddon;

public class UserProfiles {
    TfTAddon plugin;

    public UserProfiles(final TfTAddon instance) {
        plugin = instance;
    }

    /**
     * Checks what level a player is.
     * 
     * @param player Player to check
     * @param skill The Skilltype to check.
     */
    public int getSkillLevel(final Player player, final String skill) {
        int level = -1;
        if (skill != null)
            level = ExperienceAPI.getLevel(player, skill);
        else
            level = ExperienceAPI.getPowerLevel(player);
        return level;
    }

}
