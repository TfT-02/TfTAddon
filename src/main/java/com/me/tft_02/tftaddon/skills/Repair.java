package com.me.tft_02.tftaddon.skills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.locale.LocaleLoader;
import com.me.tft_02.tftaddon.util.Permissions;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class Repair {
    private final UserProfiles users = new UserProfiles();

    public static List<String> warnedPlayers = new ArrayList<String>();

    public boolean canUseBlacksmithsInstinct(Player player) {
        if (!Permissions.repair(player)) {
            return false;
        }

        int BIUnlock = Config.getInstance().getRepairBIUnlock();

        return (users.getSkillLevel(player, "REPAIR") >= BIUnlock);
    }

    public void checkDurability(Player player, ItemStack itemStack) {
        float durability = itemStack.getDurability();
        float maxDurability = itemStack.getType().getMaxDurability();
        double BIWarnPercentage = Config.getInstance().getRepairBIWarnPercentage();
        float warningValue = Math.round((BIWarnPercentage / 100) * maxDurability);

        if (hasBeenWarned(player)) {
            return;
        }

        if ((durability + warningValue) > maxDurability) {
            warnedPlayers.add(player.getName());
            player.sendMessage(LocaleLoader.getString("Repair.BlacksmithsInstinct.Warning"));
        }
    }

    public boolean hasBeenWarned(Player player) {
        return warnedPlayers.contains(player.getName());
    }
}
