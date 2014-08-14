package com.me.tft_02.tftaddon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.me.tft_02.tftaddon.config.Config;
import com.me.tft_02.tftaddon.locale.LocaleLoader;
import com.me.tft_02.tftaddon.util.Permissions;
import com.me.tft_02.tftaddon.util.UserProfiles;

public class TfTHerbalismCommand implements CommandExecutor {

    private UserProfiles users = new UserProfiles();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LocaleLoader.getString("Commands.NoConsole"));
            return true;
        }

        Player player = (Player) sender;

        if (!Permissions.herbalism(player)) {
            return false;
        }

        int summonAmount = Config.getInstance().getHerbalismSunnyDayCost();
        int sunnydayUnlock = Config.getInstance().getHerbalismSunnyDayUnlock();
        float herbaValue = users.getSkillLevel(player, "HERBALISM");

        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.0", "HERBALISM"));
        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.1"));
        player.sendMessage(LocaleLoader.getString("Commands.Skills.Header.Effects"));

        if (herbaValue < sunnydayUnlock) {
            player.sendMessage(LocaleLoader.getString("Commands.Skills.Herbalism.0", sunnydayUnlock));
        }
        else {
            player.sendMessage(LocaleLoader.getString("Commands.Skills.Herbalism.1"));
            player.sendMessage(LocaleLoader.getString("Commands.Skills.Herbalism.2", summonAmount));
        }

        return true;
    }
}