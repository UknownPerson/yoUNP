
package yoUNP.module.modules.world;

import net.minecraft.network.play.client.C03PacketPlayer;
import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventPacketSend;
import yoUNP.api.events.world.EventPreUpdate;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.utils.Helper;
import yoUNP.utils.TimerUtil;

public class PacketMotior
extends Module {
    public static int packetcount;
    private TimerUtil time = new TimerUtil();

    public PacketMotior() {
        super("PacketMotior", new String[]{"PacketMotior"}, ModuleType.World);
    }

    @EventHandler
    public void OnUpdate(EventPreUpdate event) {
        if (this.time.delay(1000.0f)) {
            this.setSuffix((Object)("PPS:" + this.packetcount));
            if (this.packetcount > 22) {
                Helper.sendMessage((String) "���ķ�������������!");
                Helper.sendMessage("Thanks qianxia's help.");
            }
            this.packetcount = 0;
            this.time.reset();
        }
    }

    @EventHandler
    public void Packet(EventPacketSend event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            ++this.packetcount;
        }
    }
}

