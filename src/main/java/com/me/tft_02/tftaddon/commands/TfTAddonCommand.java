package com.me.tft_02.tftaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.locale.LocaleLoader;

public class TfTAddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            return reloadConfiguration(sender);
        }

        if (sender.hasPermission("tftaddon.tftaddon")) {
            sender.sendMessage(LocaleLoader.getString("General.Plugin.Header", "TfT Addon"));
            sender.sendMessage(LocaleLoader.getString("General.Plugin.Author", "TfT_02"));
            sender.sendMessage(LocaleLoader.getString("General.Running_Version", TfTAddon.p.getDescription().getVersion()));
            sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.0"));
            sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.1"));
            sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.2"));
            sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.3"));
            sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.4"));
            sender.sendMessage(LocaleLoader.getString("General.Use_Help"));
        }

        return true;
    }

    private boolean reloadConfiguration(CommandSender sender) {
        if (sender instanceof Player && !sender.hasPermission("tftaddon.commands.reload")) {
            return false;
        }

        TfTAddon.p.reloadConfig();
        sender.sendMessage(LocaleLoader.getString("Commands.Reload.Success"));
        return false;
    }
}
