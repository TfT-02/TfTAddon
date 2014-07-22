package com.me.tft_02.tftaddon.commands;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.util.UserProfiles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TfTRepairCommand implements CommandExecutor {
    private UserProfiles users = new UserProfiles();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command does not support console usage.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("tftaddon.repair")) {
            return false;
        }

        int blacksmithsinstinctUnlock = Config.getInstance().getRepairBIUnlock();
        float repairValue = users.getSkillLevel(player, "REPAIR");

        player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Repair" + ChatColor.RED + "[]-----");
        player.sendMessage(ChatColor.GRAY + "Extra abilities from TfTAddon");
        player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "EFFECTS" + ChatColor.RED + "[]-----");
        if (repairValue < blacksmithsinstinctUnlock) {
            player.sendMessage(ChatColor.GRAY + "LOCKED UNTIL " + blacksmithsinstinctUnlock + "+ SKILL (BLACKSMITH'S INSTINCT)");
        }
        else {
            player.sendMessage(ChatColor.DARK_AQUA + "Blacksmith's instinct: " + ChatColor.GREEN + "Sense when tools need a repair.");
        }

        return true;
    }
}
