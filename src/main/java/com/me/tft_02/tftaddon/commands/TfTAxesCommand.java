package com.me.tft_02.tftaddon.commands;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.util.UserProfiles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TfTAxesCommand implements CommandExecutor {
    private UserProfiles users = new UserProfiles();
    private String duraChance;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command does not support console usage.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("tftaddon.axes")) {
            return false;
        }

        float level_current = users.getSkillLevel(player, "AXES");
        dataCalculations(level_current);
        player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT AXES" + ChatColor.RED + "[]-----");
        player.sendMessage(ChatColor.GRAY + "Extra abilities from TfTAddon");
        player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "YOUR STATS" + ChatColor.RED + "[]-----");
        player.sendMessage(ChatColor.RED + "Chance to reduce durability: " + ChatColor.YELLOW + duraChance + "%");

        return true;
    }

    private void dataCalculations(float level_current) {
        int dura_level_cap = Config.getInstance().getAxesDurabilityLevelCap();
        int dura_percentage_max = Config.getInstance().getAxesDurabilityChanceMax();

        if (level_current < dura_level_cap) {
            duraChance = String.valueOf((dura_percentage_max / dura_level_cap) * level_current);
        }
        else if (level_current >= dura_level_cap) {
            duraChance = String.valueOf(dura_percentage_max);
        }
    }
}
