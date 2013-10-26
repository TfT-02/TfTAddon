package com.me.tft_02.tftaddon.skills;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.runnables.SunnyDayCooldownTask;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class Herbalism {
    Random random = new Random();
    private final UserProfiles users = new UserProfiles();

    public static boolean sunnydayReady = true;

    public void checkSunnyDay(Player player) {
        if (!player.hasPermission("tftaddon.herbalism")) {
            return;
        }

        ItemStack item = player.getItemInHand();
        Material summonItem = Material.SEEDS;
        int summonAmount = Config.getInstance().getHerbalismSunnyDayCost();
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
                String playerName = player.getName();

                player.setItemInHand(new ItemStack(summonItem, item.getAmount() - summonAmount));
                player.updateInventory();
                player.getWorld().setStorm(false);
                player.getWorld().setThundering(false);
                player.sendMessage(ChatColor.GRAY + getWeatherMessages());
                player.getWorld().playEffect(player.getLocation(), Effect.GHAST_SHOOT, 1);
                player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
                Location location = player.getLocation();
                location.setY(player.getWorld().getMaxHeight() + 30D);
                player.getWorld().strikeLightningEffect(location);
                for (Player players : player.getWorld().getPlayers()) {
                    if (players != player) {
                        players.sendMessage(ChatColor.GOLD + playerName + " used Sunny Day!");
                    }
                }
                new SunnyDayCooldownTask().runTaskLater(TfTAddon.p, 20 * 2);
            }
        }
    }

    private String getWeatherMessages() {
        List<String> weatherMessages = new ArrayList<String>();
        weatherMessages.add("The sunlight is strong!");
        weatherMessages.add("The sunlight turned harsh!");
        weatherMessages.add("The sunlight got bright!");

        return weatherMessages.get(random.nextInt(weatherMessages.size()));
    }
}
