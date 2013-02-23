package com.me.tft_02.tftaddon.skills;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class Herbalism {
    private TfTAddon plugin;

    public Herbalism(final TfTAddon instance) {
        plugin = instance;
    }

    private final UserProfiles users = new UserProfiles();

    private boolean sunnydayReady = true;

    public void checkSunnyDay(Player player) {
        ItemStack item = player.getItemInHand();
        Material summonItem = Material.SEEDS;
        int summonAmount = TfTAddon.getInstance().getConfig().getInt("Skills.Herbalism.SunnyDay_cost");
        int skillLvl = (users.getSkillLevel(player, "HERBALISM"));

        if (skillLvl >= 50 && item.getType().equals(summonItem)) {

            if (item.getAmount() < summonAmount) {
                player.sendMessage(ChatColor.DARK_RED + "You need more " + ChatColor.YELLOW + "Seeds");
                return;
            }

            if (sunnydayReady) {
                if (!player.getWorld().hasStorm()) {
                    player.setFireTicks(80);
                }
                sunnydayReady = false;
                String plName = player.getName();

                player.setItemInHand(new ItemStack(summonItem, item.getAmount() - summonAmount));
                player.updateInventory();   // Needed until replacement available
                player.getWorld().setStorm(false);
                player.getWorld().setThundering(false);
                player.sendMessage(ChatColor.GRAY + getWeatherMessages());
                player.getWorld().playEffect(player.getLocation(), Effect.GHAST_SHOOT, 1);
                player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                Location loc = player.getLocation();
                loc.setY(player.getWorld().getMaxHeight() + 30D);
                player.getWorld().strikeLightningEffect(loc);
                for (Player players : player.getWorld().getPlayers()) {
                    if (players != player) {
                        players.sendMessage(ChatColor.GOLD + plName + " used Sunny Day!");
                    }
                }
                Bukkit.getScheduler().scheduleSyncDelayedTask(TfTAddon.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        sunnydayReady = true;
                    }
                }, 20 * 2);
            }
        }
    }

    @SuppressWarnings("unused")
    String getWeatherMessages() {
        String[] defaultWeatherMessages = new String[] { "The sunlight is strong!", "The sunlight turned harsh!", "The sunlight got bright!" };
        List<String> weatherMessages = Arrays.asList(defaultWeatherMessages);
        Random random = new Random();
        List<String> messages = weatherMessages;
        String message;
        int messageSize = 3;
        return message = (messages.get(random.nextInt(messageSize)));
    }
}
