	package yoUNP.module.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import yoUNP.Client;
import yoUNP.api.EventHandler;
import yoUNP.api.events.rendering.EventRender2D;
import yoUNP.api.value.Mode;
import yoUNP.api.value.Numbers;
import yoUNP.api.value.Option;
import yoUNP.management.ModuleManager;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.ui.font.CFontRenderer;
import yoUNP.ui.font.FontLoaders;
import yoUNP.utils.render.ColorUtils;
import yoUNP.utils.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends Module {

	private final Mode<Enum> mode = new Mode("Mode", "Mode", Mode1.values(), Mode1.None);
	private final Option<Boolean> rainbow = new Option<Boolean>("Rainbow", "Rainbow", false);
	private final Option<Boolean> suffix = new Option<Boolean>("Suffix", "Suffix", false);
	public Numbers<Double> r = new Numbers<Double>("Red", "Red", 255.0, 0.0, 255.0, 1.0);
	public Numbers<Double> g = new Numbers<Double>("Green", "Green", 255.0, 0.0, 255.0, 1.0);
	public Numbers<Double> b = new Numbers<Double>("Blue", "Blue", 255.0, 0.0, 255.0, 1.0);
	public Numbers<Double> alpha = new Numbers<Double>("Alpha", "Alpha", 0.0, 0.0, 255.0, 1.0);
	public Numbers<Double> linewide = new Numbers<Double>("LineWide", "LineWide", 1.0, 0.5, 3.0, 0.5);
	private static float x;
	private static int y;
	public static int ping;
	float red;
	float green;
	float blue;
	float lastx;
	float lasty;
	float lasty1 = 2.5f;
	float lasty1t = 2.5f;
	float lastlenth = 0;

	public HUD() {
		super("HUD", new String[]{"gui"}, ModuleType.Render);
		this.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
		this.setEnabled(true);
		this.setRemoved(true);


		this.addValues(this.mode, this.r, this.g, this.b, this.alpha, this.linewide, this.rainbow, this.suffix);
	}


	@Override
	public void onDisable() {
		lasty1 = 2.5f;
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

		if (suffix.getValue()) {
			sorted.sort((o1,
						 o2) -> font1.getStringWidth(
					o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s %s", o2.getName(), o2.getSuffix()))
					- font1.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName()
					: String.format("%s %s", o1.getName(), o1.getSuffix())));
		} else {
			sorted.sort((o1, o2) -> font1.getStringWidth(o2.getName()) - font1.getStringWidth(o1.getName()));
		}
		y = 5;
		if (mode.getValue() == Mode1.OutLine) {
			y += linewide.getValue().floatValue();
		}
		for (Module m : sorted) {
			if (suffix.getValue()) {
				name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s %s", m.getName(), m.getSuffix());
			} else {
				name = m.getName();
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

			float speedx = Math.abs(m.getAnimX() - font1.getStringWidth(name)) / 15;
			float speedx1 = (speedx < 0.01f ? 0.01f : speedx) * 1.5f;
			if (m.getAnimX() < font1.getStringWidth(name) && m.isEnabled()) {
				m.setAnimX(m.getAnimX() + speedx);
			}
			if (m.getAnimX() > -1024 && !m.isEnabled()) {
				m.setAnimX(m.getAnimX() - speedx1);
			}
			if (m.getAnimX() > font1.getStringWidth(name) && m.isEnabled()) {
				m.setAnimX(m.getAnimX() - speedx1);
			}

			float speedx3 = Math.abs(m.getAnwidth() - RenderUtil.width()) / 15;
			if (m.getAnwidth() < RenderUtil.width()) {
				m.setAnwidth(m.getAnwidth() + speedx3);
			}
			if (m.getAnwidth() > RenderUtil.width()) {
				m.setAnwidth(m.getAnwidth() - speedx3);
			}

			if (m.getAnimY() < 12 && m.isEnabled()) {
				m.setAnimY(m.getAnimY() + 0.3f);
			}
			if (m.getAnimY() != 0 && !m.isEnabled() && m.getAnimX() < font1.getStringWidth(name) * 0.618) {
				m.setAnimY(m.getAnimY() - 0.3f);
			}
			if (m.getAnimY() > 12 && m.isEnabled()) {
				m.setAnimY(12);
			}
			if (m.getAnimY() < 0 && !m.isEnabled()) {
				m.setAnimY(0);
			}

			x = m.getAnwidth() - m.getAnimX() - 2.5f;

			float y1 = m.getAnimY();

			float al = getAlpha() / 255.0f;
			float x1 = 0; // 大 起点x
			float x2 = 0; // 大 终点x
			float x3 = 0; // 小 起点x
			float x4 = 0; // 小 终点x
			float x5 = 0; // 文字起点 x
			if (mode.getValue() == Mode1.Left) {
				x1 = x - 5;
				x2 = x + font1.getStringWidth(name);
				x3 = x - (5 + linewide.getValue().floatValue());
				x4 = x - 5;
				x5 = x - 2;
			} else if (mode.getValue() == Mode1.Right) {
				x1 = x - (5 + linewide.getValue().floatValue());
				x2 = x + font1.getStringWidth(name) - linewide.getValue().floatValue();
				x3 = x + font1.getStringWidth(name) - linewide.getValue().floatValue();
				x4 = x + font1.getStringWidth(name);
				x5 = x - (3 + linewide.getValue().floatValue());
			} else if (mode.getValue() == Mode1.None || mode.getValue() == Mode1.OutLine) {
				if (mode.getValue() == Mode1.OutLine) {
					x -= linewide.getValue().floatValue();
				}
				x1 = x - 5;
				x2 = x + font1.getStringWidth(name);
				x5 = x - 2;
			}
			if (m.getAnimX() > -1024) {
				Gui.drawRect(x1, y, x2, y + y1, new Color(0, 0, 0, al).getRGB());
				if (!(mode.getValue() == Mode1.None) && !(mode.getValue() == Mode1.OutLine)) {
					Gui.drawRect(x3, y, x4, y + y1, new Color(red, green, blue).getRGB());
				} else if (mode.getValue() == Mode1.OutLine) {
					Gui.drawRect(x - (5 + linewide.getValue().floatValue()), y, x - 5, y + y1, new Color(red, green, blue).getRGB());
					Gui.drawRect(x + font1.getStringWidth(name) + linewide.getValue().floatValue(), y, x + font1.getStringWidth(name), y + y1, new Color(red, green, blue).getRGB());
					if (lastlenth <= font1.getStringWidth(name)) {
						Gui.drawRect(lastx, lasty + lasty1t, lastx + lastlenth + 5 + 2 * linewide.getValue().floatValue(), lasty + lasty1t - linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
						Gui.drawRect(x - 5 - linewide.getValue().floatValue(), y, x + font1.getStringWidth(name) + linewide.getValue().floatValue(), y - linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
					} else {
						if (lastx < (x - 5 - linewide.getValue().floatValue())) {
							if ((x - (5 + linewide.getValue().floatValue())) < (lastx + 5 + lastlenth + 2 * linewide.getValue().floatValue())) {
								Gui.drawRect(lastx, y, x - (5 + linewide.getValue().floatValue()), y + linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
							}
							if ((x - (5 + linewide.getValue().floatValue())) < (lastx + 5 + lastlenth + 2 * linewide.getValue().floatValue())) {
								if ((x + font1.getStringWidth(name) + linewide.getValue().floatValue()) > (lastx + lastlenth + 5 + 2 * linewide.getValue().floatValue())) {
									Gui.drawRect(lastx + (5 + 2 * linewide.getValue().floatValue()) + lastlenth, y, x + font1.getStringWidth(name) + linewide.getValue().floatValue(), y - linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
								}
							}
						} else {
							if (lastx > (x - (5 + linewide.getValue().floatValue()))) {
								if ((x + font1.getStringWidth(name) + linewide.getValue().floatValue()) > lastx) {
									Gui.drawRect(x - 5 - linewide.getValue().floatValue(), y - linewide.getValue().floatValue(), lastx, y, new Color(red, green, blue).getRGB());
								}
							}
						}

						if ((x - 5 - linewide.getValue().floatValue()) > (lastx + 2 * linewide.getValue().floatValue() + lastlenth + 5)) {
							Gui.drawRect(lastx + 2 * linewide.getValue().floatValue() + lastlenth + 5, y, x, y - linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
							Gui.drawRect(lastx + 2 * linewide.getValue().floatValue() + lastlenth + 5, y, lastx, y + linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
						}
						if ((x + linewide.getValue().floatValue() + font1.getStringWidth(name)) < lastx) {
							Gui.drawRect(x + linewide.getValue().floatValue() + font1.getStringWidth(name), y, x - 5 - linewide.getValue().floatValue(), y - linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
						}

						if ((x + font1.getStringWidth(name) + linewide.getValue().floatValue()) < (lastx + lastlenth + 5 + 2 * linewide.getValue().floatValue())) {
							Gui.drawRect(lastx + (5 + 2 * linewide.getValue().floatValue()) + lastlenth, y + linewide.getValue().floatValue(), x + font1.getStringWidth(name), y, new Color(red, green, blue).getRGB());
						}

					}
					if (2 * lasty1 == (float) y) {
						Gui.drawRect(x - 5 - linewide.getValue().floatValue(), y, x + font1.getStringWidth(name) + linewide.getValue().floatValue(), y - linewide.getValue().floatValue(), new Color(red, green, blue).getRGB());
					}
					lasty1 = y;
					lasty = y + linewide.getValue().floatValue();
					lasty1t = y1;
					lastx = x - (5 + linewide.getValue().floatValue());
					lastlenth = font1.getStringWidth(name);
				}
				font1.drawString(name, x5, y + (y1 - font1.getStringHeight(name)) / 2 + 1.0f, new Color(red, green, blue).getRGB());
				//font1.drawStringWithShadow(name, x5, y + 3, new Color(red, green, blue).getRGB());
				y += y1;

			}
		}
		lasty1 = 2.5f;
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