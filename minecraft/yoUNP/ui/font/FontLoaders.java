/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.ui.font;

import yoUNP.ui.font.CFontRenderer;
import java.awt.Font;
import java.io.InputStream;
import java.io.PrintStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public abstract class FontLoaders {
    public static CFontRenderer kiona14 = new CFontRenderer(FontLoaders.getKiona(14), true, true);
    public static CFontRenderer kiona16 = new CFontRenderer(FontLoaders.getKiona(16), true, true);
    public static CFontRenderer kiona18 = new CFontRenderer(FontLoaders.getKiona(18), true, true);
    public static CFontRenderer kiona20 = new CFontRenderer(FontLoaders.getKiona(20), true, true);
    public static CFontRenderer kiona22 = new CFontRenderer(FontLoaders.getKiona(22), true, true);
    public static CFontRenderer kiona24 = new CFontRenderer(FontLoaders.getKiona(24), true, true);
    public static CFontRenderer kiona26 = new CFontRenderer(FontLoaders.getKiona(26), true, true);
    public static CFontRenderer kiona28 = new CFontRenderer(FontLoaders.getKiona(28), true, true);
    public static CFontRenderer kiona40 = new CFontRenderer(FontLoaders.getKiona(40), true, true);
    
	public static CFontRenderer Comfortaa14 = new CFontRenderer(FontLoaders.getComfortaa(14), true, true);
	public static CFontRenderer Comfortaa16 = new CFontRenderer(FontLoaders.getComfortaa(16), true, true);
	public static CFontRenderer Comfortaa17 = new CFontRenderer(FontLoaders.getComfortaa(17), true, true);
	public static CFontRenderer Comfortaa18 = new CFontRenderer(FontLoaders.getComfortaa(18), true, true);
	public static CFontRenderer Comfortaa20 = new CFontRenderer(FontLoaders.getComfortaa(20), true, true);
	public static CFontRenderer Comfortaa22 = new CFontRenderer(FontLoaders.getComfortaa(22), true, true);
	public static CFontRenderer Comfortaa24 = new CFontRenderer(FontLoaders.getComfortaa(24), true, true);
	public static CFontRenderer Comfortaa26 = new CFontRenderer(FontLoaders.getComfortaa(26), true, true);
	public static CFontRenderer Comfortaa28 = new CFontRenderer(FontLoaders.getComfortaa(28), true, true);
	public static CFontRenderer Comfortaa50 = new CFontRenderer(FontLoaders.getComfortaa(50), true, true);

    private static Font getKiona(int size) {
        Font font;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("yoUNP/raleway.ttf")).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
    
	private static Font getComfortaa(int size) {
		Font font;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("yoUNP/Comfortaa.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}
}

