package com.me.tft_02.tftaddon.util;

import java.util.LinkedList;

import org.bukkit.Location;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.config.Config;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionUtils {

    public static boolean getDeathConsequencesEnabled(Location location) {
        boolean isWhitelist = Config.getInstance().getUseAsWhitelist();

        if (isListedRegion(getRegion(location))) {
            return isWhitelist;
        }
        else if (!isWhitelist) {
            return true;
        }
        return isWhitelist;
    }

    private static boolean isListedRegion(String region) {
        for (String name : Config.getInstance().getRegionList()) {
            if (region.equalsIgnoreCase("[" + name + "]")) {
                return true;
            }
        }
        return false;
    }

    public static String getRegion(Location location) {
        RegionManager regionManager = TfTAddon.p.getWorldGuard().getRegionManager(location.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(location);
        LinkedList<String> parentNames = new LinkedList<String>();
        LinkedList<String> regions = new LinkedList<String>();

        for (ProtectedRegion region : set) {
            String id = region.getId();
            regions.add(id);
            ProtectedRegion parent = region.getParent();
            while (parent != null) {
                parentNames.add(parent.getId());
                parent = parent.getParent();
            }
        }

        for (String name : parentNames) {
            regions.remove(name);
        }
        return regions.toString();
    }
}
