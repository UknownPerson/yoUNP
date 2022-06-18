package yoUNP.module.modules.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.main.Main;
import yoUNP.api.value.Numbers;
import yoUNP.api.value.Option;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.ui.Clickui;
import yoUNP.utils.render.RenderUtil;

public class ClickGui extends Module {
	
	public static Option<Boolean> rainbow = new Option<Boolean>("Rainbow", "Rainbow", false);
	public static Numbers<Double> r = new Numbers<Double>("Red", "Red", 255.0, 0.0, 255.0, 1.0);
	public static Numbers<Double> g = new Numbers<Double>("Green", "Green", 137.0, 0.0, 255.0, 1.0);
	public static Numbers<Double> b = new Numbers<Double>("Blue", "Blue", 128.0, 0.0, 255.0, 1.0);
	public static Numbers<Double> alpha = new Numbers<Double>("Alpha", "Alpha", 128.0, 0.0, 255.0, 1.0);

	public ClickGui() {
		super("ClickGui", new String[] { "clickui" }, ModuleType.Render);
		this.addValues( this.r, this.g, this.b, this.alpha, this.rainbow);
	}
	
	@Override
	public void onEnable() {
		this.mc.displayGuiScreen(new Clickui());
        Clickui.width=(float) (RenderUtil.width() * 0.618);
		Clickui.height=(float) (RenderUtil.height() * 0.618);
		this.setEnabled(false);
	}
}
