package com.me.tft_02.tftaddon.hooks;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.skills.repair.McMMOPlayerRepairCheckEvent;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.locale.LocaleLoader;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class McMMOListener implements Listener {

    final UserProfiles users = new UserProfiles();

    /**
     * Monitor McMMOPlayerLevelUpEvent.
     *
     * @param event The event to monitor
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLevelupEvent(McMMOPlayerLevelUpEvent event) {
        int levelRequired = Config.getInstance().getLevelAnnouncementPowerLevelInterval();
        double messageDistance = Config.getInstance().getLevelAnnouncementMessageRange();

        if (levelRequired <= 0) {
            return;
        }

        Player player = event.getPlayer();
        int power_level = users.getSkillLevel(player, null);

        if ((power_level % levelRequired) == 0) {
            if (messageDistance <= 0) {
                Bukkit.broadcastMessage(LocaleLoader.getString("Feature.LevelAnnouncement", player.getName(), power_level));
                return;
            }

            for (Player players : player.getWorld().getPlayers()) {
                if (players != player && players.getLocation().distance(player.getLocation()) < messageDistance) {
                    players.sendMessage(LocaleLoader.getString("Feature.LevelAnnouncement", player.getName(), power_level));
                }
            }
        }
    }

    /**
     * Check McMMOPlayerRepairCheckEvent.
     *
     * @param event The event to check
     */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onRepairEvent(McMMOPlayerRepairCheckEvent event) {
        int maximumEnchantLevel = Config.getInstance().getMaximumEnchantLevel();
        if (maximumEnchantLevel <= 0) {
            return;
        }

        ItemStack itemStack = event.getRepairedObject();
        Map<Enchantment, Integer> enchantments = itemStack.getEnchantments();

        if (enchantments.isEmpty()) {
            return;
        }

        for (Integer level : enchantments.values()) {
            if (level > maximumEnchantLevel) {
                event.getPlayer().sendMessage(LocaleLoader.getString("Feature.RepairEnchantment"));
                event.setCancelled(true);
            }
        }
    }
}
