package com.me.tft_02.tftaddon.skills;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.util.Permissions;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class Axes {
    private final UserProfiles users = new UserProfiles();

    private Random random = new Random();

    /* Gives a chance to have 1 durability loss instead of 2 when hitting entities with axes. 
     * Default configuration has a maximum of 50% at Axes level 100 */

    /**
     * Changing the durability for an axe.
     *
     * @param player Player to check.
     * @param is     The item in hand.
     */
    public void axeDurabilityCheck(Player player, ItemStack is) {
        if (!Permissions.axes(player)) {
            return;
        }

        int level_current = users.getSkillLevel(player, "AXES");
        int level_cap = Config.getInstance().getAxesDurabilityLevelCap();
        float chance_max = Config.getInstance().getAxesDurabilityChanceMax();
        float diceroll = random.nextInt(100);

        if (level_current <= 0) {
            return;
        }

        float chance = (level_current < level_cap) ? chance_max / level_cap * level_current : chance_max;

        if (chance > diceroll) {
            is.setDurability((short) Math.max(is.getDurability() - 1, 0));
        }
    }
}
