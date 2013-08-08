package com.me.tft_02.tftaddon.runnables;


import com.me.tft_02.tftaddon.skills.Herbalism;
import org.bukkit.scheduler.BukkitRunnable;

public class SunnyDayCooldownTask extends BukkitRunnable {

    @Override
    public void run() {
        Herbalism.sunnydayReady = true;
    }
}
