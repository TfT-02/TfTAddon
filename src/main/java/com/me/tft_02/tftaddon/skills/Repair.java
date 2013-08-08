package com.me.tft_02.tftaddon.skills;

import java.util.ArrayList;
import java.util.List;

import com.me.tft_02.tftaddon.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.util.UserProfiles;

public class Repair {
    int BIUnlock = Config.getInstance().getRepairBIUnlock();
    double BIWarnPercentage = Config.getInstance().getRepairBIWarnPercentage();

    private final UserProfiles users = new UserProfiles();

    public static List<String> warnedPlayers = new ArrayList<String>();

    public boolean canUseBlacksmithsInstinct(Player player) {
        if (!player.hasPermission("tftaddon.repair")) {
            return false;
        }

        return (users.getSkillLevel(player, "REPAIR") >= BIUnlock);
    }

    public void checkDurability(Player player, ItemStack itemStack) {
        float durability = itemStack.getDurability();
        float maxDurability = itemStack.getType().getMaxDurability();
        float warningValue = Math.round((BIWarnPercentage / 100) * maxDurability);

        if (!hasBeenWarned(player)) {
            if ((durability + warningValue) > maxDurability) {
                warnedPlayers.add(player.getName());
                player.sendMessage(ChatColor.RED + "Durability low! - repair needed");
            }
        }
    }

    public boolean hasBeenWarned(Player player) {
        return warnedPlayers.contains(player.getName());
    }
}
