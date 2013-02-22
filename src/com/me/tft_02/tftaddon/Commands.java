package com.me.tft_02.tftaddon;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.me.tft_02.tftaddon.util.UserProfiles;

public class Commands implements CommandExecutor {
    TfTAddon plugin;

    public Commands(final TfTAddon instance) {
        plugin = instance;
    }

    UserProfiles users = new UserProfiles(plugin);

    float level_current;
    String duraChance;

    float dura_level_cap;
    float dura_percentage_max;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;

        dura_level_cap = plugin.getConfig().getInt("Skills.Axes.Dura_level_cap");
        dura_percentage_max = plugin.getConfig().getInt("Skills.Axes.Dura_percentage_max");

        if (sender instanceof Player) {
            player = (Player) sender;
        }

        if (cmd.getName().equalsIgnoreCase("tftaddon")) {
            if (player == null) {
                sender.sendMessage("TfTAddon adds extra features for mcMMO.");
                sender.sendMessage("Type /tfthelp for a list of all the commands.");
            } else {
                if (player.hasPermission("tftaddon.tftaddon")) {
                    String plName = player.getName();
                    plugin.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + plName + ": /tftaddon");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Addon" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.GOLD + "TfTAddon has these extra features for mcMMO:");
                    player.sendMessage(ChatColor.GREEN + "Reduce durability for Axes.");
                    player.sendMessage(ChatColor.GREEN + "Change the weather with Herbalism.");
                    player.sendMessage(ChatColor.GREEN + "Receive Repair warning, of the item in use.");
                    player.sendMessage(ChatColor.GOLD + "type /tfthelp for a list of all the commands.");
                }
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("tfthelp")) {
            if (player == null) {
                sender.sendMessage("Available commands: /tftaddon /tfthelp");
            } else {
                if (player.hasPermission("tftaddon.tfthelp")) {
                    String plName = player.getName();
                    plugin.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + plName + ": /tfthelp");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Help" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.GOLD + "Commands:");

                    if (player.hasPermission("tftaddon.tftaddon")) {
                        player.sendMessage(ChatColor.GREEN + "/tftaddon" + ChatColor.GRAY + " Displays general info.");
                    }
                    if (player.hasPermission("tftaddon.axes")) {
                        player.sendMessage(ChatColor.GREEN + "/tftaxes" + ChatColor.GRAY + " Check Axes addon info.");
                    }
                    if (player.hasPermission("tftaddon.herbalism")) {
                        player.sendMessage(ChatColor.GREEN + "/tftherbalism" + ChatColor.GRAY + " Check Herbalism addon info.");
                    }
                    if (player.hasPermission("tftaddon.dura")) {
                        player.sendMessage(ChatColor.GREEN + "/tftdura" + ChatColor.GRAY + " Check durability of item in hand.");
                    }
                }
            }
            return true;

        } else if (cmd.getName().equalsIgnoreCase("tftaxes")) {
            if (player == null) {
                sender.sendMessage("This command does not support console usage.");
            } else {
                if (player.hasPermission("tftaddon.axes")) {
                    level_current = users.getSkillLevel(player, "AXES");
                    dataCalculations(level_current);
                    String plName = player.getName();
                    plugin.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + plName + ": /tftaxes");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT AXES" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.GRAY + "Extra abilities from TfTAddon");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "YOUR STATS" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.RED + "Chance to reduce durability: " + ChatColor.YELLOW + duraChance + "%");
                }
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("tftherbalism")) {
            if (player == null) {
                sender.sendMessage("This command does not support console usage.");
            } else {
                if (player.hasPermission("tftaddon.herbalism")) {
                    int summonAmount = plugin.getConfig().getInt("Skills.Herbalism.SunnyDay_cost");
                    int sunnydayUnlock = plugin.getConfig().getInt("Skills.Herbalism.SunnyDay_unlock_level");
                    float herbaValue = users.getSkillLevel(player, "HERBALISM");
                    String plName = player.getName();

                    plugin.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + plName + ": /tftherbalism");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT HERBALISM" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.GRAY + "Extra abilities from TfTAddon");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "EFFECTS" + ChatColor.RED + "[]-----");
                    if (herbaValue < sunnydayUnlock) {
                        player.sendMessage(ChatColor.GRAY + "LOCKED UNTIL " + sunnydayUnlock + "+ SKILL (SUNNY DAY)");
                    } else {
                        player.sendMessage(ChatColor.DARK_AQUA + "Sunny Day: " + ChatColor.GREEN + "Makes the sun shine bright.");
                        player.sendMessage(ChatColor.GRAY + "Crouch and left-click with " + summonAmount + " seeds in your hand.");
                    }
                }
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("tftfishing")) {
            if (player == null) {
                sender.sendMessage("This command does not support console usage.");
            } else {
                if (player.hasPermission("tftaddon.fishing")) {
                    float fishlevel = users.getSkillLevel(player, "FISHING");
                    String plName = player.getName();

                    plugin.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + plName + ": /tftfishing");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT FISHING" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.GRAY + "Extra abilities from TfTAddon");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "EFFECTS" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.DARK_AQUA + "Fisherman's Diet: " + ChatColor.GREEN + "Improves hunger restored from cooked fish.");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "YOUR STATS" + ChatColor.RED + "[]-----");
                    if (fishlevel >= 80) {
                        player.sendMessage(ChatColor.RED + "Fisherman's Diet: " + ChatColor.YELLOW + "Rank 4");
                    } else if (fishlevel >= 60) {
                        player.sendMessage(ChatColor.RED + "Fisherman's Diet: " + ChatColor.YELLOW + "Rank 3");
                    } else if (fishlevel >= 40) {
                        player.sendMessage(ChatColor.RED + "Fisherman's Diet: " + ChatColor.YELLOW + "Rank 2");
                    } else if (fishlevel >= 20) {
                        player.sendMessage(ChatColor.RED + "Fisherman's Diet: " + ChatColor.YELLOW + "Rank 1");
                    } else {
                        player.sendMessage(ChatColor.RED + "Fisherman's Diet: " + ChatColor.YELLOW + "Rank 0");
                    }
                }
            }
            return true;

        } else if (cmd.getName().equalsIgnoreCase("tftrepair")) {
            if (player == null) {
                sender.sendMessage("This command does not support console usage.");
            } else {
                if (player.hasPermission("tftaddon.repair")) {

                    int blacksmithsinstinctUnlock = plugin.getConfig().getInt("Skills.Repair.BlacksmithsInstinct_unlock_level");
                    float repairValue = users.getSkillLevel(player, "REPAIR");
                    String plName = player.getName();

                    plugin.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + plName + ": /tftrepair");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Repair" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.GRAY + "Extra abilities from TfTAddon");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "EFFECTS" + ChatColor.RED + "[]-----");
                    if (repairValue < blacksmithsinstinctUnlock) {
                        player.sendMessage(ChatColor.GRAY + "LOCKED UNTIL " + blacksmithsinstinctUnlock + "+ SKILL (BLACKSMITH'S INSTINCT)");
                    } else {
                        player.sendMessage(ChatColor.DARK_AQUA + "Blacksmith's instinct: " + ChatColor.GREEN + "Sense when tools need a repair.");
                    }
                }
            }
            return true;

        } else if (cmd.getName().equalsIgnoreCase("dura")) {
            if (player == null) {
                sender.sendMessage("This command does not support console usage.");
            } else {
                if (player.hasPermission("tftaddon.dura")) {
                    ItemStack itemInHand = player.getItemInHand();
                    float durability = itemInHand.getDurability();
                    String plName = player.getName();
                    plugin.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + plName + ": /dura");
                    player.sendMessage(ChatColor.RED + "-----[]" + ChatColor.GREEN + "TfT Addon" + ChatColor.RED + "[]-----");
                    player.sendMessage(ChatColor.RED + "Item durability is: " + ChatColor.YELLOW + durability);
                }
            }
            return true;
        }
        return false;
    }

    private void dataCalculations(float level_current) {
        dura_level_cap = plugin.getConfig().getInt("Skills.Axes.Dura_level_cap");
        dura_percentage_max = plugin.getConfig().getInt("Skills.Axes.Dura_percentage_max");
        if (level_current < dura_level_cap) {
            duraChance = String.valueOf((dura_percentage_max / dura_level_cap) * level_current);
        } else if (level_current >= dura_level_cap) {
            duraChance = String.valueOf(dura_percentage_max);
        }
    }
}
