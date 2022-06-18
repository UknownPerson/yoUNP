package yoUNP.module.modules.render;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import yoUNP.Client;
import yoUNP.api.EventHandler;
import yoUNP.api.events.rendering.EventRender2D;
import yoUNP.api.value.Mode;
import yoUNP.api.value.Numbers;
import yoUNP.api.value.Option;
import yoUNP.management.ModuleManager;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.module.modules.combat.Killaura;
import yoUNP.ui.font.CFontRenderer;
import yoUNP.ui.font.FontLoaders;
import yoUNP.utils.render.ColorUtils;
import yoUNP.utils.render.RenderUtil;

public class HUD extends Module {

	private final Mode<Enum> mode = new Mode("Mode", "Mode", Mode1.values(), Mode1.None);
	private final Option<Boolean> rainbow = new Option<Boolean>("Rainbow", "Rainbow", false);
	private final Option<Boolean> suffix = new Option<Boolean>("Suffix", "Suffix", false);
	public Numbers<Double> r = new Numbers<Double>("Red", "Red", 255.0, 0.0, 255.0, 1.0);
	public Numbers<Double> g = new Numbers<Double>("Green", "Green", 255.0, 0.0, 255.0, 1.0);
	public Numbers<Double> b = new Numbers<Double>("Blue", "Blue", 255.0, 0.0, 255.0, 1.0);
	public Numbers<Double> alpha = new Numbers<Double>("Alpha", "Alpha", 0.0, 0.0, 255.0, 1.0);
	private static float x;
	private static int y;
	public static int ping;
	float red;
	float green;
	float blue;

	public HUD() {
		super("HUD", new String[] { "gui" }, ModuleType.Render);
		this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
		this.setEnabled(true);
		this.setRemoved(true);
	this.addValues(this.mode, this.r, this.g, this.b, this.alpha, this.rainbow,this.suffix);
	}

	@EventHandler
	public void renderHud(EventRender2D event) {
		if (mc.gameSettings.showDebugInfo) {
			return;
		}
		CFontRenderer font = FontLoaders.Comfortaa16;
		CFontRenderer font1 = FontLoaders.Comfortaa16;
		String name;
		String direction;
		ArrayList<Module> sorted = new ArrayList<Module>();
		Client.instance.getModuleManager();
		for (Module m : ModuleManager.getModules()) {
			if (m.wasRemoved())
				continue;
			sorted.add(m);
		}

		if(suffix.getValue()) {
			sorted.sort((o1,
			o2) -> font1.getStringWidth(
					o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix()))
					- font1.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName()
							: String.format("%s %s", o1.getName(), o1.getSuffix())));
		}else {
			sorted.sort((o1, o2) -> font1.getStringWidth(o2.getName()) - font1.getStringWidth( o1.getName()));
		}
		y = 5;
		for (Module m : sorted) {
			if(suffix.getValue()) {
				name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
			}else {
				name =m.getName();
			}
			ping = mc.getNetHandler().getPlayerInfo(Minecraft.thePlayer.getUniqueID()).getResponseTime();
			if (this.rainbow.getValue()) {
				red = ColorUtils.getRainbow().getRed() / 255.0f;
				green = ColorUtils.getRainbow().getGreen() / 255.0f;
				blue = ColorUtils.getRainbow().getBlue() / 255.0f;
			} else {
				red = getRed() / 255.0f;
				green = getGreen() / 255.0f;
				blue = getBlue() / 255.0f;
			}

			float speedx=Math.abs(m.getAnimX() - font1.getStringWidth(name))/20;
			float speedx1 = (speedx <0.05f ? 0.05f : speedx)*1.5f;
			if(m==ModuleManager.getModuleByClass(Killaura.class) && speedx1>0.01f) {
				//System.out.println(speedx);
			}
			if (m.getAnimX() < font1.getStringWidth(name) && m.isEnabled()) {
				m.setAnimX(m.getAnimX() + speedx);
			}
			if (m.getAnimX() > -1 && !m.isEnabled()) {
				m.setAnimX(m.getAnimX() - speedx1);
			}
			if (m.getAnimX() > font1.getStringWidth(name) && m.isEnabled()) {
				m.setAnimX(font1.getStringWidth(name));
			}
			

			if (m.getAnimY() < 10 && m.isEnabled()) {
				m.setAnimY(m.getAnimY() + 0.4f);
			}
			if (m.getAnimY() !=0 && !m.isEnabled()&&m.getAnimX()<font1.getStringWidth(name)/2) {
				m.setAnimY(m.getAnimY() - 0.4f);
			}
			if (m.getAnimY() > 10 && m.isEnabled()) {
				m.setAnimY(10);
			}
			if (m.getAnimY() < 0 && !m.isEnabled()) {
				m.setAnimY(0);
			}
			x = RenderUtil.width() - m.getAnimX() - 2.5f;
			float y1=m.getAnimY();

			float a = 255.0f;
			float al = getAlpha() / 255.0f;
			float x1 = 0; // 大 起点x
			float x2 = 0; // 大 终点x
			float x3 = 0; // 小 起点x
			float x4 = 0; // 小 终点x
			float x5 = 0; // 文字起点 x
			if (mode.getValue() == Mode1.Left) {
				x1 = x - 5;
				x2 = x + font1.getStringWidth(name);
				x3 = x - 8;
				x4 = x - 5;
				x5 = x - 2;
			} else if (mode.getValue() == Mode1.Right) {
				x1 = x - 8;
				x2 = x + font1.getStringWidth(name) - 3;
				x3 = x + font1.getStringWidth(name) - 3;
				x4 = x + font1.getStringWidth(name);
				x5 = x - 5;
			} else if (mode.getValue() == Mode1.None || mode.getValue() == Mode1.OutLine) {
				x1 = x - 5;
				x2 = x + font1.getStringWidth(name);
				x5 = x - 2;
				a = 0.0f;
			}
			if (m.getAnimX() > -1) {
				Gui.drawRect(x1, y, x2, y + 10, new Color(0, 0, 0, al).getRGB());
				if (!(mode.getValue() == Mode1.None) && !(mode.getValue() == Mode1.OutLine)) {
					Gui.drawRect(x3, y, x4, y + 10, new Color(red, green, blue).getRGB());
				}else if(mode.getValue() == Mode1.OutLine){

				}
				font1.drawString(name, x5, y + 3, new Color(red, green, blue).getRGB());
				//font1.drawStringWithShadow(name, x5, y + 3, new Color(red, green, blue).getRGB());
				y += y1;
			}
		}
		this.drawPotionStatus(new ScaledResolution(mc));
	}

	private void drawPotionStatus(ScaledResolution sr) {
		int y = 0;
		for (PotionEffect effect : Minecraft.thePlayer.getActivePotionEffects()) {
			int ychat;
			Potion potion = Potion.potionTypes[effect.getPotionID()];
			String PType = I18n.format(potion.getName());
			switch (effect.getAmplifier()) {
			case 1: {
				PType = PType + " II";
				break;
			}
			case 2: {
				PType = PType + " III";
				break;
			}
			case 3: {
				PType = PType + " IV";
				break;
			}
			}
			if (effect.getDuration() < 600 && effect.getDuration() > 300) {
				PType = PType + "\u00a77:\u00a76 " + Potion.getDurationString(effect);
			} else if (effect.getDuration() < 300) {
				PType = PType + "\u00a77:\u00a7c " + Potion.getDurationString(effect);
			} else if (effect.getDuration() > 600) {
				PType = PType + "\u00a77:\u00a77 " + Potion.getDurationString(effect);
			}
			int n = ychat = -10;
			mc.fontRendererObj.drawStringWithShadow(PType,
					sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(PType) - 2,
					sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT + y - 12 - ychat - 20,
					potion.getLiquidColor());
			y -= 10;
		}
	}

	public int getRed() {
		return this.r.getValue().intValue();
	}

	public int getGreen() {
		return this.g.getValue().intValue();
	}

	public int getBlue() {
		return this.b.getValue().intValue();
	}

	public int getAlpha() {
		return this.alpha.getValue().intValue();
	}

	// public String getS(Module o) {
	// }

	public enum Mode1 {
		OutLine , Left, Right, None
	}
}
