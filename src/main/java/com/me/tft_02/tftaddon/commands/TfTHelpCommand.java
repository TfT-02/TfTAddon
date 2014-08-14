package com.me.tft_02.tftaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.me.tft_02.tftaddon.locale.LocaleLoader;

public class TfTHelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 2:
                if (Integer.parseInt(args[1]) > 1) {
                    getHelpPage(Integer.parseInt(args[1]), sender);
                    return true;
                }
                else {
                    getHelpPage(1, sender);
                    return true;
                }

            default:
                getHelpPage(1, sender);
                return true;
        }
    }

    private void getHelpPage(int page, CommandSender sender) {
        int maxPages = 2;
        int nextPage = page + 1;

        if (page > maxPages) {
            sender.sendMessage(LocaleLoader.getString(LocaleLoader.getString("Help.Page_Does_Not_Exist"), maxPages));
            return;
        }

        sender.sendMessage(LocaleLoader.getString("Help.Page_Header", page, maxPages));
        switch (page) {
            case 1:
                sendHelpPage(sender, LocaleLoader.getString("Help.Page_0.Line_0"));

                if (sender.hasPermission("tftaddon.commands.reload")) {
                    sendHelpPage(sender, LocaleLoader.getString("Help.Page_0.Line_1"));
                }
                else if (sender.hasPermission("tftaddon.tftaddon")) {
                    sendHelpPage(sender, LocaleLoader.getString("Help.Page_0.Line_2"));
                }
                sendHelpPage(sender, LocaleLoader.getString("Help.Page_0.Line_3"));
                sendHelpPage(sender, LocaleLoader.getString("Help.Page_0.Line_4"));
                return;

            case 2:
                sendHelpPage(sender, LocaleLoader.getString("Help.Page_1.Line_0"));

                if (sender.hasPermission("tftaddon.axes")) {
                    sendHelpPage(sender, LocaleLoader.getString("Help.Page_1.Line_1"));
                }

                if (sender.hasPermission("tftaddon.herbalism")) {
                    sendHelpPage(sender, LocaleLoader.getString("Help.Page_1.Line_2"));
                }

                if (sender.hasPermission("tftaddon.repair")) {
                    sendHelpPage(sender, LocaleLoader.getString("Help.Page_1.Line_3"));
                }

                if (sender.hasPermission("tftaddon.dura")) {
                    sendHelpPage(sender, LocaleLoader.getString("Help.Page_1.Line_4"));
                }
                return;

            default:
                if (nextPage <= maxPages) {
                    sender.sendMessage(LocaleLoader.getString("Help.Page_Ending", "/tfthelp", nextPage));
                }
                return;
        }
    }

    /**
     * Send a string, but only if .length > 0
     */
    private void sendHelpPage(CommandSender sender, String string) {
        if (string.length() > 0) {
            sender.sendMessage(string);
        }
    }
}
