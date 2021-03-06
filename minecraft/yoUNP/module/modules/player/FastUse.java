/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventPreUpdate;
import yoUNP.api.value.Option;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;

import java.awt.*;

public class FastUse
extends Module {
    private Option<Boolean> guardian = new Option<Boolean>("Guardian", "guardian", true);

    public FastUse() {
        super("FastUse", new String[]{"fasteat", "fuse"}, ModuleType.Player);
        this.addValues(this.guardian);
    }

    private boolean canConsume() {
        if (!(this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemPotion || this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood)) {
            return false;
        }
        return true;
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        this.setColor(new Color(100, 200, 200).getRGB());
        if (this.guardian.getValue().booleanValue()) {
            if (this.mc.thePlayer.getItemInUseDuration() == 1 && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed && !(this.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) && !(this.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword)) {
                int i = 0;
                while (i < 40) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround));
                    if (this.guardian.getValue().booleanValue() && this.mc.thePlayer.ticksExisted % 2 == 0) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ, false));
                    }
                    ++i;
                }
                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
        } else if (
                this.mc.thePlayer.getItemInUseDuration() == 16 && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed && !(this.mc.thePlayer.getItemInUse().getItem() instanceof ItemBow) && !(this.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword)) {
            int i = 0;
            while (i < 17) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, this.mc.thePlayer.onGround));
                ++i;
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}

