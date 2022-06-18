/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.module.modules.world;

import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventTick;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import java.awt.Color;
import net.minecraft.client.Minecraft;

public class FastPlace
extends Module {
    public FastPlace() {
        super("FastPlace", new String[]{"fplace", "fc"}, ModuleType.World);
        this.setColor(new Color(226, 197, 78).getRGB());
    }

    @EventHandler
    private void onTick(EventTick e) {
        this.mc.rightClickDelayTimer = 0;
    }
}

