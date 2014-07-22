package com.me.tft_02.tftaddon.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DuraCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command does not support console usage.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("tftaddon.dura")) {
            return false;
        }

        ItemStack itemInHand = player.getItemInHand();
        float durability = itemInHand.getDurability();

        player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Addon" + ChatColor.RED + "[]-----");
        player.sendMessage(ChatColor.RED + "Item durability is: " + ChatColor.YELLOW + durability);

        return true;
    }
}