package com.me.tft_02.tftaddon.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TfTHelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("tftaddon.tfthelp")) {
            sender.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Help" + ChatColor.RED + "[]-----");
            sender.sendMessage(ChatColor.GOLD + "Commands:");
        }
        if (sender.hasPermission("tftaddon.commands.reload")) {
            sender.sendMessage(ChatColor.GREEN + "/tftaddon [reload]" + ChatColor.GRAY + " Displays general info, or reload the config file.");
        }
        else if (sender.hasPermission("tftaddon.tftaddon")) {
            sender.sendMessage(ChatColor.GREEN + "/tftaddon" + ChatColor.GRAY + " Displays general info.");
        }

        if (sender instanceof Player) {
            if (sender.hasPermission("tftaddon.axes")) {
                sender.sendMessage(ChatColor.GREEN + "/tftaxes" + ChatColor.GRAY + " Check Axes addon info.");
            }
            if (sender.hasPermission("tftaddon.herbalism")) {
                sender.sendMessage(ChatColor.GREEN + "/tftherbalism" + ChatColor.GRAY + " Check Herbalism addon info.");
            }
            if (sender.hasPermission("tftaddon.repair")) {
                sender.sendMessage(ChatColor.GREEN + "/tftrepair" + ChatColor.GRAY + " Check Repair addon info.");
            }
            if (sender.hasPermission("tftaddon.dura")) {
                sender.sendMessage(ChatColor.GREEN + "/dura" + ChatColor.GRAY + " Check durability of item in hand.");
            }
        }
        return true;
    }
}
