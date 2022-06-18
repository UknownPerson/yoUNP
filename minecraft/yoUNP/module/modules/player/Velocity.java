/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.module.modules.player;

import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventPacketRecieve;
import yoUNP.api.value.Numbers;
import yoUNP.api.value.Value;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import java.awt.Color;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity
extends Module {
    private Numbers<Double> percentage = new Numbers<Double>("Percentage", "percentage", 0.0, 0.0, 100.0, 5.0);

    public Velocity() {
        super("Velocity", new String[]{"antivelocity", "antiknockback", "antikb"}, ModuleType.Player);
        this.addValues(this.percentage);
        this.setColor(new Color(191, 191, 191).getRGB());
    }

    @EventHandler
    private void onPacket(EventPacketRecieve e) {
        if (e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion) {
            if (this.percentage.getValue().equals(0.0)) {
                e.setCancelled(true);
            } else {
                S12PacketEntityVelocity packet = (S12PacketEntityVelocity)e.getPacket();
                packet.motionX = (int)(this.percentage.getValue() / 100.0);
                packet.motionY = (int)(this.percentage.getValue() / 100.0);
                packet.motionZ = (int)(this.percentage.getValue() / 100.0);
            }
        }
    }
}

