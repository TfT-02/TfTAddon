package com.me.tft_02.tftaddon.skills;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class Axes {
    TfTAddon plugin;

    public Axes(final TfTAddon instance) {
        plugin = instance;
    }
    
    final UserProfiles users = new UserProfiles(plugin);

    private Random random = new Random();

    /* Gives a chance to have 1 durability loss instead of 2 when hitting entities with axes. 
     * Default configuration has a maximum of 50% at Axes level 100 */

    /**
     * Changing the durability for an axe.
     * 
     * @param player Player to check.
     * @param is The item in hand.
     */
    public void axeDurabilityCheck(Player player, ItemStack is) {
        if (!player.hasPermission("tftaddon.axes")) {
            return;
        }

        int level_current = users.getSkillLevel(player, "AXES");
//        int level_cap = plugin.getConfig().getInt("Skills.Axes.Dura_level_cap");
//        float chance_max = plugin.getConfig().getInt("Skills.Axes.Dura_percentage_max");
        int level_cap = 100;
        float chance_max = 50;

        float diceroll = random.nextInt(100);
        if (level_current <= 0) {
            return;
        }

        float chance = (level_current < level_cap) ? (float) (chance_max / level_cap) * level_current : chance_max;

        if (chance > diceroll) {
            is.setDurability((short) Math.max(is.getDurability() - 1, 0));

            if (TfTAddon.debug_mode) {
                player.sendMessage(ChatColor.BLUE + "Felt easy " + ChatColor.YELLOW + chance + " > " + diceroll);
            }
        } else {
            if (TfTAddon.debug_mode) {
                player.sendMessage(ChatColor.RED + "Failed " + ChatColor.YELLOW + chance + " < " + diceroll);
            }
        }
        return;
    }
}
