package com.me.tft_02.tftaddon.commands;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.util.UserProfiles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TfTHerbalismCommand implements CommandExecutor {

    private UserProfiles users = new UserProfiles();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command does not support console usage.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("tftaddon.herbalism")) {
            return false;
        }

        int summonAmount = Config.getInstance().getHerbalismSunnyDayCost();
        int sunnydayUnlock = Config.getInstance().getHerbalismSunnyDayUnlock();
        float herbaValue = users.getSkillLevel(player, "HERBALISM");

        player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT HERBALISM" + ChatColor.RED + "[]-----");
        player.sendMessage(ChatColor.GRAY + "Extra abilities from TfTAddon");
        player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "EFFECTS" + ChatColor.RED + "[]-----");
        if (herbaValue < sunnydayUnlock) {
            player.sendMessage(ChatColor.GRAY + "LOCKED UNTIL " + sunnydayUnlock + "+ SKILL (SUNNY DAY)");
        }
        else {
            player.sendMessage(ChatColor.DARK_AQUA + "Sunny Day: " + ChatColor.GREEN + "Makes the sun shine bright.");
            player.sendMessage(ChatColor.GRAY + "Crouch and left-click with " + summonAmount + " seeds in your hand.");
        }

        return true;
    }
}