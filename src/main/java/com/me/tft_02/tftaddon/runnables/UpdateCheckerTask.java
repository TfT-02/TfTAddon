package com.me.tft_02.tftaddon.runnables;

import com.me.tft_02.tftaddon.TfTAddon;
import com.me.tft_02.tftaddon.util.UpdateChecker;

/**
 * Async task
 */
public class UpdateCheckerTask implements Runnable {
    @Override
    public void run() {
        try {
            TfTAddon.p.updateCheckerCallback(UpdateChecker.updateAvailable());
        }
        catch (Exception e) {
            TfTAddon.p.updateCheckerCallback(false);
        }
    }
}
