/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.api.events.rendering;

import yoUNP.api.Event;
import shadersmod.client.Shaders;

public class EventRender3D
extends Event {
    private float ticks;
    private boolean isUsingShaders;

    public EventRender3D() {
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public EventRender3D(float ticks) {
        this.ticks = ticks;
        this.isUsingShaders = Shaders.getShaderPackName() != null;
    }

    public float getPartialTicks() {
        return this.ticks;
    }

    public boolean isUsingShaders() {
        return this.isUsingShaders;
    }
}

