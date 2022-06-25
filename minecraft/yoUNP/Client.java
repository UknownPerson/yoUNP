/*
 * Decompiled with CFR 0_132.
 */
package yoUNP;

import net.minecraft.util.ResourceLocation;
import yoUNP.api.value.Value;
import yoUNP.management.CommandManager;
import yoUNP.management.FileManager;
import yoUNP.management.FriendManager;
import yoUNP.management.ModuleManager;
import yoUNP.module.Module;
import yoUNP.ui.login.AltManager;

public class Client {
    public final static String name = "yoUNP Alpha";
    public final static double version = 1.1;
    public final static String thx = "Thanks qianxia's help.";
    public static boolean publicMode = false;
    public static Client instance = new Client();
    public ModuleManager modulemanager;
    private CommandManager commandmanager;
    private AltManager altmanager;
    private FriendManager friendmanager;
    public static ResourceLocation CLIENT_CAPE = new ResourceLocation("yoUNP/cape.png");
    public static float yaw;
    public static float pitch;
    public static boolean fuck = false;

    public void initiate() {
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.friendmanager = new FriendManager();
        this.friendmanager.init();
        this.modulemanager = new ModuleManager();
        this.modulemanager.init();
        this.altmanager = new AltManager();
        AltManager.init();
        AltManager.setupAlts();
        FileManager.init();
    }

    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    public AltManager getAltManager() {
        return this.altmanager;
    }

    public void shutDown() {
        String values = "";
        instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            for (Value v : m.getValues()) {
                values = String.valueOf(values) + String.format("%s:%s:%s%s", m.getName(), v.getName(), v.getValue(), System.lineSeparator());
            }
        }
        FileManager.save("Values.txt", values, false);
        String enabled = "";
        instance.getModuleManager();
        for (Module m : ModuleManager.getModules()) {
            if (!m.isEnabled()) continue;
            enabled = String.valueOf(enabled) + String.format("%s%s", m.getName(), System.lineSeparator());
        }
        FileManager.save("Enabled.txt", enabled, false);
    }
    
    public static void setRotation(float y,float p){
    	yaw=y;
    	pitch=p;
    	fuck = true;
    }
}

