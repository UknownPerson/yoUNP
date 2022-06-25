/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.module.modules.player;

import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventPreUpdate;
import yoUNP.api.value.Numbers;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;

public class Timer
extends Module {
    private Numbers<Double> timer = new Numbers<Double>("Timer", "timer", 1.0, 0.1, 5.0, 0.1);

    public Timer() {
        super("Timer", new String[]{"timer"}, ModuleType.Player);
        this.addValues(this.timer);
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        this.mc.timer.timerSpeed = timer.getValue().floatValue();
        ;
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
}

