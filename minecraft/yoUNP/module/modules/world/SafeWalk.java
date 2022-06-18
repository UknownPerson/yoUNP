/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.module.modules.world;

import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import java.awt.Color;

public class SafeWalk
extends Module {
    public SafeWalk() {
        super("SafeWalk", new String[]{"eagle", "parkour"}, ModuleType.World);
        this.setColor(new Color(198, 253, 191).getRGB());
    }
}

