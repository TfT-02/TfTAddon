package com.me.tft_02.tftaddon.util;

import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.exceptions.McMMOPlayerNotFoundException;

public class UserProfiles {

    /**
     * Checks what level a player is.
     *
     * @param player Player to check
     * @param skill  The Skilltype to check.
     */
    public int getSkillLevel(final Player player, final String skill) {
        try {
            return (skill != null) ? ExperienceAPI.getLevel(player, skill) : ExperienceAPI.getPowerLevel(player);
        }
        catch (McMMOPlayerNotFoundException ignored) {
            return 0;
        }
    }
}
