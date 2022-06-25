package yoUNP.module.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import yoUNP.Client;
import yoUNP.api.EventHandler;
import yoUNP.api.events.rendering.EventRender2D;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.module.modules.combat.Killaura;

public class TargetHUD extends Module {

	public TargetHUD() {
		super("TargetHUD", new String[]{"targethud"}, ModuleType.Render);
	}

	@EventHandler
	public void onRender(EventRender2D event) {
		ScaledResolution sr = new ScaledResolution(mc);
		final FontRenderer font2 = mc.fontRendererObj;
		if (Killaura.target != null && Client.instance.getModuleManager().getModuleByClass(TargetHUD.class).isEnabled()
				& Client.instance.getModuleManager().getModuleByClass(Killaura.class).isEnabled()) {
			final String name = Killaura.target.getName() + " " + Killaura.target.hurtTime;
			font2.drawStringWithShadow(name, (float) (sr.getScaledWidth() / 2) - (font2.getStringWidth(name) / 2),
					(float) (sr.getScaledHeight() / 2 - 30), -1);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
			int i = 0;
			while ((float) i < Killaura.target.getMaxHealth() / 2.0f) {
				Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect((float) (sr.getScaledWidth() / 2)
								- Killaura.target.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float) (i * 10),
						(float) (sr.getScaledHeight() / 2 - 16), 16, 0, 9, 9);
				++i;
			}
			i = 0;
			while ((float) i < Killaura.target.getHealth() / 2.0f) {
				Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect((float) (sr.getScaledWidth() / 2)
								- Killaura.target.getMaxHealth() / 2.0f * 10.0f / 2.0f + (float) (i * 10),
						(float) (sr.getScaledHeight() / 2 - 16), 52, 0, 9, 9);
				++i;
			}
		}
	}
}
