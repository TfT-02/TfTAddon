package com.me.tft_02.tftaddon.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.skills.Repair;
import com.me.tft_02.tftaddon.util.ItemChecks;

public class BlockListener implements Listener {
    private TfTAddon plugin;

    public BlockListener(final TfTAddon instance) {
        plugin = instance;
    }

    private final Repair repair = new Repair(plugin);

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        // Blacksmiths instinct
        Player player = event.getPlayer();
        ItemStack is = player.getItemInHand();
        if (ItemChecks.isRepairable(is)) {
            repair.checkRepair(player, is);
        }
    }
}
