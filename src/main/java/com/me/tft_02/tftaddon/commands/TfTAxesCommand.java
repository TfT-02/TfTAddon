package com.me.tft_02.tftaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.locale.LocaleLoader;
import com.me.tft_02.tftaddon.util.Permissions;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class TfTAxesCommand implements CommandExecutor {
    private UserProfiles users = new UserProfiles();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LocaleLoader.getString("Commands.NoConsole"));
            return true;
        }

        Player player = (Player) sender;

        if (!Permissions.axes(player)) {
            return false;
        }

        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.0", "AXES"));
        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.1"));
        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.Stats"));
        player.sendMessage(LocaleLoader.getString("Commands.Skills.Axes.0", dataCalculations(player)));
        return true;
    }

    private String dataCalculations(Player player) {
        float level_current = users.getSkillLevel(player, "AXES");
        int dura_level_cap = Config.getInstance().getAxesDurabilityLevelCap();
        int dura_percentage_max = Config.getInstance().getAxesDurabilityChanceMax();

        if (level_current < dura_level_cap) {
            return String.valueOf((dura_percentage_max / dura_level_cap) * level_current) + "%";
        }
        else {
            return String.valueOf(dura_percentage_max) + "%";
        }
    }
}
