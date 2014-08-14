package com.me.tft_02.tftaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.locale.LocaleLoader;
import com.me.tft_02.tftaddon.util.Permissions;

public class TfTAddonCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            return reloadConfiguration(sender);
        }

        if (!Permissions.tftaddon(sender)) {
            return false;
        }

        sender.sendMessage(LocaleLoader.getString("General.Plugin.Header", "TfT Addon"));
        sender.sendMessage(LocaleLoader.getString("General.Plugin.Author", "TfT_02"));
        sender.sendMessage(LocaleLoader.getString("General.Running_Version", TfTAddon.p.getDescription().getVersion()));
        sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.0"));
        sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.1"));
        sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.2"));
        sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.3"));
        sender.sendMessage(LocaleLoader.getString("Commands.TfTAddon.About.4"));
        sender.sendMessage(LocaleLoader.getString("General.Use_Help"));
        return true;
    }

    private boolean reloadConfiguration(CommandSender sender) {
        if (!Permissions.reload(sender)) {
            return false;
        }

        TfTAddon.p.reloadConfig();
        sender.sendMessage(LocaleLoader.getString("Commands.Reload.Success"));
        return true;
    }
}
