package com.me.tft_02.tftaddon.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class Repair {
    private final UserProfiles users = new UserProfiles();

    public static List<String> warnedPlayers = new ArrayList<String>();

    public void checkRepair(Player player, ItemStack is) {
        if (!player.hasPermission("tftaddon.repair")) {
            return;
        }

        int level_unlock = TfTAddon.getInstance().getConfig().getInt("Skills.Repair.BlacksmithsInstinct_unlock_level");
        float level_current = users.getSkillLevel(player, "REPAIR");
        //      float level_unlock = 800;

        if (level_current > 0) {
            if (level_current >= level_unlock) {
                checkDurability(player, is);
            }
        }
    }

    public void checkDurability(Player player, ItemStack is) {
        float currentdura = is.getDurability();
        float maxdurability = is.getType().getMaxDurability();
        //        float configpercentageleft = 10;
        float configpercentageleft = TfTAddon.getInstance().getConfig().getInt("Skills.Repair.BlacksmithsInstinct_percentage_durability_left");
        float warningvalue = Math.round((configpercentageleft / 100) * maxdurability);

        if (!hasBeenWarned(player)) {
            if ((currentdura + warningvalue) > maxdurability) {
                warnedPlayers.add(player.getName());
                player.sendMessage(ChatColor.RED + "Durability low! - repair needed");
            }
            else {
                if (TfTAddon.debug_mode) {
                    player.sendMessage(ChatColor.GREEN + "Durability okay! - You're good to go");
                }
            }
        }
    }

    public boolean hasBeenWarned(Player player) {
        return warnedPlayers.contains(player.getName());
    }
}
