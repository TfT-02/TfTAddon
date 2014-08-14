package com.me.tft_02.tftaddon.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.api.AbilityAPI;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.skills.Repair;
import com.me.tft_02.tftaddon.util.ItemChecks;

public class BlockListener implements Listener {
    private final Repair repair = new Repair();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        // Blacksmiths instinct
        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();

        if (repair.canUseBlacksmithsInstinct(player) && ItemChecks.isRepairable(itemStack)) {
            repair.checkDurability(player, itemStack);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockDamageHigher(BlockDamageEvent event) {
        Player player = event.getPlayer();

        if (!Config.getInstance().getMiningSuperBreakerInstantObsidian()) {
            return;
        }

        if (!ItemChecks.isPickaxe(player.getItemInHand())) {
            return;
        }

        if (!AbilityAPI.superBreakerEnabled(player)) {
            return;
        }

        if (event.getBlock().getType() != Material.OBSIDIAN) {
            return;
        }

        event.setInstaBreak(true);
    }
}
