/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.api.events.rendering;

import yoUNP.api.Event;

public class EventRender2D
extends Event {
    private float partialTicks;

    public EventRender2D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}

