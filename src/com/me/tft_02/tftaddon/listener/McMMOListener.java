package com.me.tft_02.tftaddon.listener;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class McMMOListener implements Listener {
    TfTAddon plugin;

    public McMMOListener(final TfTAddon instance) {
        plugin = instance;
    }

    final UserProfiles users = new UserProfiles(plugin);

    /**
     * Monitor McMMOPlayerLevelUpEvent events.
     * 
     * @param event The event to monitor
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLevelupEvent(McMMOPlayerLevelUpEvent event) {
        int levelRequired = plugin.getConfig().getInt("Announce_Level_Up.Power_Level");
        if (levelRequired == 0) levelRequired = 100;
        
        Player player = event.getPlayer();
        int powerlevel = ExperienceAPI.getPowerLevel(player);
        
        if (powerlevel / levelRequired > 1) {
            Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has just reached power level " + ChatColor.GREEN + powerlevel);
        }
    }

}
