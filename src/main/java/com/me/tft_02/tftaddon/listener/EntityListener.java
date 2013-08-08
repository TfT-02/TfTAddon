package com.me.tft_02.tftaddon.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.skills.Axes;
import com.me.tft_02.tftaddon.skills.Repair;
import com.me.tft_02.tftaddon.util.ItemChecks;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class EntityListener implements Listener {
    private final Axes axes = new Axes();
    private final Repair repair = new Repair();
    final UserProfiles users = new UserProfiles();

    /**
     * Monitor EntityDamageByEntity events.
     * 
     * @param event The event to monitor
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamage() == 0 || event.getEntity().isDead()) {
            return;
        }

        if (event.getEntity() instanceof LivingEntity) {
            Entity damager = event.getDamager();
            EntityType damagerType = damager.getType();

            switch (damagerType) {
                case PLAYER:
                    Player attacker = (Player) event.getDamager();
                    ItemStack itemInHand = attacker.getItemInHand();

                    if (ItemChecks.isAxe(itemInHand)) {
                        axes.axeDurabilityCheck(attacker, itemInHand);
                    }

                    if (repair.canUseBlacksmithsInstinct(attacker) && ItemChecks.isRepairable(itemInHand)) {
                        repair.checkDurability(attacker, itemInHand);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
