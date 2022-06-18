/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.module.modules.render;

import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventTick;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class FullBright
extends Module {
    private float old;

    public FullBright() {
        super("FullBright", new String[]{"fbright", "brightness", "bright"}, ModuleType.Render);
        this.setColor(new Color(244, 255, 149).getRGB());
    }

    @Override
    public void onEnable() {
        this.old = this.mc.gameSettings.gammaSetting;
    }

    @EventHandler
    private void onTick(EventTick e) {
        this.mc.gameSettings.gammaSetting = 1.5999999E7f;
    }

    @Override
    public void onDisable() {
        this.mc.gameSettings.gammaSetting = this.old;
    }
}

