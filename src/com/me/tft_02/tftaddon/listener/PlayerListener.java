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

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.skills.Herbalism;
import com.me.tft_02.tftaddon.skills.Repair;
import com.me.tft_02.tftaddon.util.ItemChecks;

public class PlayerListener implements Listener {
    private TfTAddon plugin;

    public PlayerListener(final TfTAddon instance) {
        plugin = instance;
    }

    private final Repair repair = new Repair(plugin);
    private final Herbalism herbalism = new Herbalism(plugin);

    /**
     * Monitor PlayerInteract events.
     * 
     * @param event The event to watch
     */
    @SuppressWarnings({ "unused" })
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block block = event.getClickedBlock();
        ItemStack inHand = player.getItemInHand();
        Material material;

        /* Fix for NPE on interacting with air */
        if (block == null) {
            material = Material.AIR;
        } else {
            material = block.getType();
        }

        switch (action) {
        case LEFT_CLICK_AIR:
        case LEFT_CLICK_BLOCK:

            /* WEATHER CHECKS */
            if (player.isSneaking() && player.hasPermission("tftaddon.herbalism") && inHand.getType().equals(Material.SEEDS)) {
                herbalism.checkSunnyDay(player);
            }
            break;
        case RIGHT_CLICK_BLOCK:
            if ((ItemChecks.isHoe(inHand)) && (block.getTypeId() == 2)) {
                repair.checkDurability(player, inHand);
            }
            break;
        case RIGHT_CLICK_AIR:
            if (inHand.getType().equals(Material.BOW)) {
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
     * @param event The event to watch
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerItemSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int previousSlot = event.getPreviousSlot();
        int newSlot = event.getNewSlot();
        if (newSlot != previousSlot && repair.hasBeenWarned(player)) {
            Repair.warnedPlayers.remove(player.getName());
        }
    }

//    /**
//     * Monitor PlayerCommandPreprocessEvent events.
//     * 
//     * @param event The event to watch
//     */
//    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
//    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
//        Player player = event.getPlayer();
//        String command = event.getMessage();
//
//        if (command.equalsIgnoreCase("/herbalism")) {
//            player.sendMessage(ChatColor.YELLOW + "TfTAddon message " + ChatColor.GOLD + command + ChatColor.RED + " LOL.");
//        }
//    }
}
