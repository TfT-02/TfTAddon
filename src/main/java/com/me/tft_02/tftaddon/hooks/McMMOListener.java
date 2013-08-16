package com.me.tft_02.tftaddon.hooks;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.hardcore.McMMOPlayerDeathPenaltyEvent;
import com.gmail.nossr50.events.skills.repair.McMMOPlayerRepairCheckEvent;
import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.util.RegionUtils;
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
            if (messageDistance > 0) {
                for (Player players : player.getWorld().getPlayers()) {
                    if (players != player && players.getLocation().distance(player.getLocation()) < messageDistance) {
                        players.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has just reached power level " + ChatColor.GREEN + power_level);
                    }
                }
            }
            else {
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has just reached power level " + ChatColor.GREEN + power_level);
            }
        }
    }

    /**
     * Check McMMOPlayerDeathPenaltyEvent.
     *
     * @param event The event to check
     */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onDeathPenaltyEvent(McMMOPlayerDeathPenaltyEvent event) {
        if (!TfTAddon.p.worldGuardEnabled) {
            return;
        }

        if (!RegionUtils.getDeathConsequencesEnabled(event.getPlayer().getLocation())) {
            event.setCancelled(true);
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
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot Repair this item. Enchantment level is too high!");
                event.setCancelled(true);
            }
        }
    }
}
