/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package yoUNP.module.modules.render;

import yoUNP.api.EventHandler;
import yoUNP.api.events.rendering.EventPostRenderPlayer;
import yoUNP.api.events.rendering.EventPreRenderPlayer;
import yoUNP.api.value.Mode;
import yoUNP.api.value.Value;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import java.awt.Color;
import org.lwjgl.opengl.GL11;

public class Chams
extends Module {
    public Mode<Enum> mode = new Mode("Mode", "mode", (Enum[])ChamsMode.values(), (Enum)ChamsMode.Textured);

    public Chams() {
        super("Chams", new String[]{"seethru", "cham"}, ModuleType.Render);
        this.addValues(this.mode);
        this.setColor(new Color(159, 190, 192).getRGB());
    }

    @EventHandler
    private void preRenderPlayer(EventPreRenderPlayer e) {
        GL11.glEnable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)-1100000.0f);
    }

    @EventHandler
    private void postRenderPlayer(EventPostRenderPlayer e) {
        GL11.glDisable((int)32823);
        GL11.glPolygonOffset((float)1.0f, (float)1100000.0f);
    }

    public static enum ChamsMode {
        Textured,
        Normal;
    }

}

