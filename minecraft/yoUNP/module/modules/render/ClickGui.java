package yoUNP.module.modules.render;

import yoUNP.api.value.Mode;
import yoUNP.api.value.Numbers;
import yoUNP.api.value.Option;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.ui.Clickui;
import yoUNP.utils.Helper;
import yoUNP.utils.render.RenderUtil;

public class ClickGui extends Module {
	private final Mode<Enum> mode = new Mode("Mode", "Mode", Mode1.values(), Mode1.LeftUp);
	public static Option<Boolean> rainbow = new Option<Boolean>("Rainbow", "Rainbow", false);
	public static Numbers<Double> r = new Numbers<Double>("Red", "Red", 255.0, 0.0, 255.0, 1.0);
	public static Numbers<Double> g = new Numbers<Double>("Green", "Green", 137.0, 0.0, 255.0, 1.0);
	public static Numbers<Double> b = new Numbers<Double>("Blue", "Blue", 128.0, 0.0, 255.0, 1.0);
	public static Numbers<Double> alpha = new Numbers<Double>("Alpha", "Alpha", 128.0, 0.0, 255.0, 1.0);
	float x, y;

	public ClickGui() {
		super("ClickGui", new String[]{"clickui"}, ModuleType.Render);
		this.addValues(this.mode, this.r, this.g, this.b, this.alpha, this.rainbow);
	}

	@Override
	public void onEnable() {
		if (mode.getValue() == Mode1.Middle) {
			x = 0.5f;
			y = 0.5f;
		} else if (mode.getValue() == Mode1.LeftUp) {
			x = 0f;
			y = 0f;
		} else if (mode.getValue() == Mode1.LeftDown) {
			x = 0f;
			y = 1f;
		} else if (mode.getValue() == Mode1.RightUp) {
			x = 1f;
			y = 0f;
		} else if (mode.getValue() == Mode1.RightDown) {
			x = 1f;
			y = 1f;
		}
		Helper.sendMessage("Thanks qianxia's help.");
		this.mc.displayGuiScreen(new Clickui());
		Clickui.windowX = x * RenderUtil.width();
		Clickui.windowY = y * RenderUtil.height();
		Clickui.width = 0.1f;
		Clickui.height = 0.1f;
		this.setEnabled(false);
	}

	public enum Mode1 {
		Middle, LeftUp, LeftDown, RightUp, RightDown
	}
}
