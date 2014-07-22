package com.me.tft_02.tftaddon.runnables;


import org.bukkit.scheduler.BukkitRunnable;

import com.me.tft_02.tftaddon.skills.Herbalism;

public class SunnyDayCooldownTask extends BukkitRunnable {

    @Override
    public void run() {
        Herbalism.sunnydayReady = true;
    }
}
