package yoUNP.ui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import yoUNP.Client;
import yoUNP.api.EventHandler;
import yoUNP.api.events.misc.EventKey;
import yoUNP.api.value.Mode;
import yoUNP.api.value.Numbers;
import yoUNP.api.value.Option;
import yoUNP.api.value.Value;
import yoUNP.management.ModuleManager;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.module.modules.combat.Killaura;
import yoUNP.module.modules.render.ClickGui;
import yoUNP.module.modules.render.HUD;
import yoUNP.ui.font.FontLoaders;
import yoUNP.utils.RenderUtils;
import yoUNP.utils.TimerUtil;
import yoUNP.utils.render.ColorUtils;
import yoUNP.utils.render.RenderUtil;

import org.lwjgl.input.Mouse;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;

public class Clickui extends GuiScreen {

	public static float windowX = 0, windowY = 0;
	public static float width = 500;
	public static float height = 300;
	public static Module currentMod;
	public static ModuleType modCategory = ModuleType.Combat;
	public static float mRole, vRole;
	public TimerUtil timer = new TimerUtil();
	public float dragX, dragY;
	public int keyy = 0;

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		int dWheel = Mouse.getDWheel();
		if (isHovered(windowX + 200, windowY + 40, windowX + width, windowY + height, mouseX, mouseY)) {
			if (dWheel < 0 && Math.abs(vRole) + 170 < (currentMod.values.size() * 25)) {
				vRole -= 25;
			}
			if (dWheel > 0 && vRole < 0) {
				vRole += 25;
			}
		}

		if (isHovered(windowX, windowY + 40, windowX + 200, windowY + height - 20, mouseX, mouseY)) {
			if (dWheel < 0 && (Math.abs(mRole) + 200) < (Client.instance.getModuleManager().getModulesInType(modCategory).size() * 30)) {
				mRole -= 25;
			}
			if (dWheel > 0 && mRole < 0) {
				mRole += 25;
			}
		}

		windowX = (RenderUtil.width() / 2 - width / 2);
		windowY = (RenderUtil.height() / 2 - height / 2);
		Clickui.width = (float) (RenderUtil.width() * 0.618);
		Clickui.height = (float) (RenderUtil.height() * 0.618);
		// 娑撹崵鐛ユ担锟�
		// RenderUtils.drawRoundedRect(windowX, windowY, windowX + width, windowY +
		// height, 5,new Color(255, 0, 0, 255).getRGB());
		// RenderUtil.blurArea(windowX, windowY, width, height, 12);
		Gui.drawRect(windowX, windowY + 40, windowX + width, windowY + height,
				new Color(255, 255, 255, getAlpha()).getRGB());
		Gui.drawRect(windowX, windowY, windowX + width, windowY + 40,
				new Color(getRed(), getGreen(), getBlue(), getAlpha()).getRGB());
		// Categories
		float cx = (width / 10 + windowX);
		for (ModuleType mc : ModuleType.values()) {
			if (true) {
				float l = FontLoaders.Comfortaa28.getStringWidth(mc.name());
				float h = FontLoaders.Comfortaa28.getStringHeight(mc.name());
				// RenderUtils.drawImage(cx, windowY + 10, 16, 16, new
				// ResourceLocation("client/blurlogo/" + mc.name() + ".png"), modCategory == mc
				// ? new Color(255, 255, 255) : new Color(100, 100, 100));
				Gui.drawRect(cx - width / 10, windowY, cx + width / 10, windowY + 40,
						modCategory == mc ? new Color(255, 255, 255, 100).getRGB() : new Color(0, 0, 0, 0).getRGB());
				FontLoaders.Comfortaa28.drawString(mc.name(), cx - l / 2, windowY + (20 + h) / 2,
						modCategory == mc ? new Color(255, 255, 255).getRGB() : new Color(100, 100, 100).getRGB());
				if (isHovered(cx - width / 10, windowY, cx + width / 10, windowY + 40, mouseX, mouseY)
						&& Mouse.isButtonDown(0) && timer.delay(300)) {
					modCategory = mc;
					mRole = 0;
					vRole = 0;
					timer.reset();
					currentMod = null;
				}
				// cx += (width - 116) / (ModuleType.values().length - 2);
				cx += width / 5;
			}
		}
		// 閸掑棗澹婄痪锟�
		Gui.drawRect(windowX + width * 0.2495f, windowY + 50, windowX + width * 0.2505f, windowY + height - 10,
				new Color(150, 150, 150, 100).getRGB());

		float vY = windowY + 60 + vRole;
		if (currentMod != null) {
			FontLoaders.Comfortaa50.drawString(currentMod.getName(), windowX + width * 0.3f, vY, 1);
			vY += 45;
			for (Value v : currentMod.values) {
				if (((vY + 30) < (windowY + 70))) {
					vY += 25;
				}
				if (!((vY + 30) < (windowY + 70)) && !((vY + 30) > (windowY + height))) {
					if (v instanceof Option) {
						FontLoaders.Comfortaa16.drawString(v.getName(), windowX + width * 0.3f, vY, 1);
						RenderUtils.drawFilledCircle(windowX + 0.95f * width, vY + 4, 5, new Color(100, 100, 100, 255));
						RenderUtils.drawFilledCircle(windowX + 0.95f * width, vY + 4, 4,
								((Boolean) v.getValue()) ? new Color(255, 255, 255) : new Color(100, 100, 100));
						// Gui.drawRect(windowX + 0.95f*width - 3.75, vY+0.25, windowX + 0.95f*width
						// +4.25, vY + 8.25,-1);
						if (isHovered(windowX + 0.95f * width - 3.75f, vY + 0.25f, windowX + 0.95f * width + 4.25f,
								vY + 8.25f, mouseX, mouseY) && Mouse.isButtonDown(0) && timer.delay(300)) {
							v.setValue(!((Boolean) v.getValue()));
							timer.reset();
						}
					}

					if (v instanceof Mode) {
						FontLoaders.Comfortaa16.drawString(v.getName(), windowX + width * 0.3f, vY, 1);
						// RenderUtils.drawRoundedRect(windowX + width - 80, vY - 5, windowX + width -
						// 10, vY + 10, new Color(100, 100, 100, 200).getRGB());
						Gui.drawRect(windowX + 0.95f * width - 60, vY - 5, windowX + 0.95f * width + 10, vY + 10,
								new Color(100, 100, 100, getAlpha()).getRGB());
						FontLoaders.Comfortaa16.drawCenteredStringWithShadow(((Mode<?>) v).getModeAsString(),
								windowX + 0.95f * width - 25, vY, new Color(255, 255, 255).getRGB());
						if (isHovered(windowX + 0.95f * width - 60, vY - 5, windowX + 0.95f * width + 10, vY + 10,
								mouseX, mouseY) && Mouse.isButtonDown(0) && timer.delay(300)) {
							if (Arrays.binarySearch(((Mode<?>) v).getModes(), (v.getValue()))
									+ 1 < ((Mode<?>) v).getModes().length) {
								v.setValue(((Mode<?>) v)
										.getModes()[Arrays.binarySearch(((Mode<?>) v).getModes(), (v.getValue())) + 1]);
							} else {
								v.setValue(((Mode<?>) v).getModes()[0]);
							}
							timer.reset();
						}

					}

					if (v instanceof Numbers) {
						FontLoaders.Comfortaa16.drawString(v.getName(), windowX + width * 0.3f, vY, 1);
						FontLoaders.Comfortaa16.drawString(v.getValue().toString(), windowX + 0.95f * width
								- FontLoaders.Comfortaa16.getStringWidth(v.getValue().toString()) + 15, vY, 1);

						Gui.drawRect(windowX + width * 0.3f, vY + 9, windowX + 0.95f * width + 10, vY + 10,
								new Color(120, 120, 120).getRGB());
						RenderUtils.drawFilledCircle(
								windowX + width * 0.3f
										+ (windowX + 0.95f * width + 10 - windowX - width * 0.3f)
												* ((((Number) v.getValue()).floatValue()
														- ((Numbers<?>) v).getMinimum().floatValue())
														/ (((Numbers) v).getMaximum().floatValue()
																- ((Numbers<?>) v).getMinimum().floatValue())),
								vY + 9.15f, 3, new Color(getRed(), getGreen(), getBlue()));
						Gui.drawRect(windowX + width * 0.3f, vY + 9, windowX + width * 0.3f + (windowX + 0.95f * width + 10
								- windowX - width * 0.3f)
								* ((((Number) v.getValue()).floatValue() - ((Numbers<?>) v).getMinimum().floatValue())
										/ (((Numbers) v).getMaximum().floatValue()
												- ((Numbers<?>) v).getMinimum().floatValue())),
								vY + 10, new Color(getRed(), getGreen(), getBlue()).getRGB());

						if (isHovered(windowX + width * 0.3f, vY + 7, windowX + 0.95f * width + 10, vY + 12, mouseX, mouseY)
								&& Mouse.isButtonDown(0)) {
							float render2 = ((Numbers) v).getMinimum().floatValue();
							double max = ((Numbers) v).getMaximum().doubleValue();
							double inc = ((Numbers) v).getIncrement().doubleValue();
							double valAbs = (double) mouseX - windowX - width * 0.3f;
							double perc = valAbs / (((windowX + 0.95f * width + 10) - (windowX + width * 0.3f)));
							perc = Math.min(Math.max(0.0D, perc), 1.0D);
							double valRel = (max - render2) * perc;
							double val = render2 + valRel;
							val = Math.round(val * (1.0D / inc)) / (1.0D / inc);
							// val = getnumm(val,inc);
							((Numbers) v).setValue(Double.valueOf(val));
						}

					}

					vY += 25;
				}
			}
		}

//ModuleManager.getModsByCategory(modCategory)
		float mY = windowY + 50 + mRole;
		for (Module m : Client.instance.getModuleManager().getModulesInType(modCategory)) {
			if (((mY + 30) < (windowY + 70))) {
				mY += 30;
			}
			if (!((mY + 30) < (windowY + 70)) && !((mY + 30) > (windowY + height))) {
				if (m.isEnabled()) {
					Gui.drawRect(windowX + 10, mY, windowX + width * 0.24f, mY + 25,
							new Color(255, 255, 255, 255).getRGB());
				} else {
					// Gui.drawRect(windowX + 10, mY, windowX + width*0.24f, mY + 25, new
					// Color(0,0,0,0).getRGB());
				}

				if (isHovered(windowX + 10, mY, windowX + width * 0.2f, mY + 25, mouseX, mouseY)
						&& Mouse.isButtonDown(0) && timer.delay(300)) {
					// m.setStage(!m.getState());
					m.setEnabled(!m.isEnabled());
					timer.reset();
				} else if (isHovered(windowX + 10, mY, windowX + width * 0.2f, mY + 25, mouseX, mouseY)
						&& Mouse.isButtonDown(1) && timer.delay(300)) {
					currentMod = m;
					timer.reset();
				}
				FontLoaders.Comfortaa18.drawString(m.getName(), windowX + 20, mY + 10, 1);
				// RenderUtils.drawGradientRect(windowX + 10, mY + 25, windowX + 190, mY + 27,
				// new Color(0, 0, 0, 0).getRGB(), new Color(0, 0, 0, 100).getRGB());
				// FontLoaders.Comfortaa18.drawString(m.getName(), windowX + 15, mY + 5, -1);
				// FontLoaders.Comfortaa14.drawString(m.getDescription(), windowX + 20, mY + 15,
				// new Color(230, 230, 230).getRGB());
				mY += 30;
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
	}

	public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
	}

	public int getRed() {
		if (ClickGui.rainbow.getValue()) {
			int red = (int) (ColorUtils.getRainbow().getRed());
			return red;
		} else {
			return ClickGui.r.getValue().intValue();
		}
	}

	public int getGreen() {
		if (ClickGui.rainbow.getValue()) {
			int green = (int) (ColorUtils.getRainbow().getGreen());
			return green;
		} else {
			return ClickGui.g.getValue().intValue();
		}
	}

	public int getBlue() {
		if (ClickGui.rainbow.getValue()) {
			int blue = (int) (ColorUtils.getRainbow().getBlue());
			return blue;
		} else {
			return ClickGui.b.getValue().intValue();
		}
	}

	public int getAlpha() {
		return ClickGui.alpha.getValue().intValue();
	}

	/*
	 * @EventHandler private void onKeyPress(EventKey e) { if (e.getKey() ==
	 * KeyEvent.VK_LEFT) { keyy = 1; }else if(e.getKey() == KeyEvent.VK_RIGHT) {
	 * keyy =2; } }
	 * 
	 * public double getnumm (double v,double i) { double re = v; if(keyy==1) {
	 * re=v+i; }else if(keyy == 2) { re=v-i; } return re; }
	 */
}
