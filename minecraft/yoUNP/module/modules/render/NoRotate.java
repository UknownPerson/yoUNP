/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.module.modules.render;

import yoUNP.Client;
import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventPacketSend;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate
extends Module {
    public NoRotate() {
        super("NoRotate", new String[]{"rotate"}, ModuleType.Render);
        this.setColor(new Color(17, 250, 154).getRGB());
    }

    @EventHandler
    private void onPacket(EventPacketSend e) {
    	Client.fuck=false;
    }
}

