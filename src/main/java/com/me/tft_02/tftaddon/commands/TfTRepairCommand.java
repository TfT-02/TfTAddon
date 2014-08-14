package com.me.tft_02.tftaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.locale.LocaleLoader;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class TfTRepairCommand implements CommandExecutor {
    private UserProfiles users = new UserProfiles();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LocaleLoader.getString("Commands.NoConsole"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("tftaddon.repair")) {
            return false;
        }

        int blacksmithsInstinctUnlock = Config.getInstance().getRepairBIUnlock();
        float repairValue = users.getSkillLevel(player, "REPAIR");

        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.0", "REPAIR"));
        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.1"));
        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.Effects"));

        if (repairValue < blacksmithsInstinctUnlock) {
            player.sendMessage(LocaleLoader.getString("Commands.Skills.Repair.0", blacksmithsInstinctUnlock));
        }
        else {
            player.sendMessage(LocaleLoader.getString("Commands.Skills.Repair.1"));
        }

        return true;
    }
}
