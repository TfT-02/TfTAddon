package com.me.tft_02.tftaddon.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.skills.Herbalism;
import com.me.tft_02.tftaddon.skills.Repair;
import com.me.tft_02.tftaddon.util.ItemChecks;

public class PlayerListener implements Listener {
    private final Repair repair = new Repair();
    private final Herbalism herbalism = new Herbalism();

    /**
     * Monitor PlayerInteract events.
     *
     * @param event The event to monitor
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        ItemStack inHand = player.getItemInHand();
        Material material;

        /* Fix for NPE on interacting with air */
        if (block == null) {
            material = Material.AIR;
        }
        else {
            material = block.getType();
        }

        switch (action) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                /* WEATHER CHECKS */
                if (player.isSneaking() && inHand.getType().equals(Material.SEEDS)) {
                    herbalism.checkSunnyDay(player);
                }
                break;
            case RIGHT_CLICK_BLOCK:
                if (repair.canUseBlacksmithsInstinct(player) && (ItemChecks.isHoe(inHand)) && (material == Material.GRASS)) {
                    repair.checkDurability(player, inHand);
                }
                break;
            case RIGHT_CLICK_AIR:
                if (repair.canUseBlacksmithsInstinct(player) && inHand.getType().equals(Material.BOW)) {
                    repair.checkDurability(player, inHand);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Monitor PlayerItemHeldEvent events.
     *
     * @param event The event to monitor
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerItemSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (!repair.canUseBlacksmithsInstinct(player)) {
            return;
        }

        if (event.getNewSlot() != event.getPreviousSlot() && repair.hasBeenWarned(player)) {
            Repair.warnedPlayers.remove(player.getName());
        }
    }
}
