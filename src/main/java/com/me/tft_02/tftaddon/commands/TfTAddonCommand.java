package com.me.tft_02.tftaddon.commands;

import com.me.tft_02.tftaddon.TfTAddon;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TfTAddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (player == null) {
            sender.sendMessage("TfTAddon adds extra features for mcMMO.");
            sender.sendMessage("Type /tfthelp for a list of all the commands.");
        }
        else {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                return reloadConfiguration(sender);
            }
            if (player.hasPermission("tftaddon.tftaddon")) {
                player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Addon" + ChatColor.RED + "[]-----");
                player.sendMessage(ChatColor.GOLD + "TfTAddon has these extra features for mcMMO:");
                player.sendMessage(ChatColor.GREEN + "Reduce durability for Axes.");
                player.sendMessage(ChatColor.GREEN + "Change the weather with Herbalism.");
                player.sendMessage(ChatColor.GREEN + "Receive Repair warning, of the item in use.");
                player.sendMessage(ChatColor.GOLD + "type /tfthelp for a list of all the commands.");
            }
        }
        return true;

    }

    private boolean reloadConfiguration(CommandSender sender) {
        if (sender instanceof Player && !sender.hasPermission("tftaddon.commands.reload")) {
            return false;
        }

        TfTAddon.p.reloadConfig();
        sender.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
        return false;
    }
}