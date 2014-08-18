package com.me.tft_02.tftaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.me.tft_02.tftaddon.locale.LocaleLoader;
import com.me.tft_02.tftaddon.util.Permissions;

public class DuraCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LocaleLoader.getString("Commands.NoConsole"));
            return true;
        }

        Player player = (Player) sender;

        if (!Permissions.dura(player)) {
            return false;
        }

        player.sendMessage(LocaleLoader.getString("General.Plugin.Header", "TfT Addon"));
        player.sendMessage(LocaleLoader.getString("Commands.Dura.0", player.getItemInHand().getDurability()));
        return true;
    }
}
