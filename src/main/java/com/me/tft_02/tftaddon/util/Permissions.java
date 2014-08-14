package com.me.tft_02.tftaddon.util;

import org.bukkit.permissions.Permissible;

public class Permissions {

    public static boolean tftaddon(Permissible permissible) {
        return permissible.hasPermission("tftaddon.tftaddon");
    }

    public static boolean reload(Permissible permissible) {
        return permissible.hasPermission("tftaddon.commands.reload");
    }

    public static boolean tfthelp(Permissible permissible) {
        return permissible.hasPermission("tftaddon.tfthelp");
    }

    public static boolean axes(Permissible permissible) {
        return permissible.hasPermission("tftaddon.axes");
    }

    public static boolean herbalism(Permissible permissible) {
        return permissible.hasPermission("tftaddon.herbalism");
    }

    public static boolean repair(Permissible permissible) {
        return permissible.hasPermission("tftaddon.repair");
    }

    public static boolean dura(Permissible permissible) {
        return permissible.hasPermission("tftaddon.dura");
    }
}
