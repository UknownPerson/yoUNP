/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package yoUNP.management;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import yoUNP.api.EventBus;
import yoUNP.api.EventHandler;
import yoUNP.api.events.misc.EventKey;
import yoUNP.api.events.rendering.EventRender2D;
import yoUNP.api.events.rendering.EventRender3D;
import yoUNP.api.value.Mode;
import yoUNP.api.value.Numbers;
import yoUNP.api.value.Option;
import yoUNP.api.value.Value;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;
import yoUNP.module.modules.combat.*;
import yoUNP.module.modules.movement.*;
import yoUNP.module.modules.player.*;
import yoUNP.module.modules.render.*;
import yoUNP.module.modules.world.*;
import yoUNP.utils.render.gl.GLUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager
implements Manager {
    public static List<Module> modules = new ArrayList<Module>();
    private boolean enabledNeededMod = true;
    public boolean nicetry = true;

    @Override
    public void init() {
    	
    	//combat
        modules.add(new AntiBot());
        modules.add(new AutoHeal());
        modules.add(new AutoSword());
        modules.add(new BowAimBot());
        modules.add(new Criticals());
        modules.add(new FastBow());
        modules.add(new Killaura());
        modules.add(new Regen());
        modules.add(new TPAura());

        //movement

        modules.add(new Flight());
        modules.add(new InvMove());
        modules.add(new Jesus());
        modules.add(new Longjump());
        modules.add(new NoSlow());
        modules.add(new Speed());
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Teleport());
        
        //player
   
        modules.add(new FastUse());
        modules.add(new Blink());
        modules.add(new Freecam());
        modules.add(new InvCleaner());
        modules.add(new MCF());
        modules.add(new NoFall());
        modules.add(new Teams());
        modules.add(new Timer());
        modules.add(new Velocity());
        modules.add(new Zoot());
        
        //render
        modules.add(new Animations());
        modules.add(new BlockOverlay());
        modules.add(new Chams());
        modules.add(new ChestESP());
        modules.add(new ClickGui());
        modules.add(new ESP());
        modules.add(new EveryThingBlock());
        modules.add(new FullBright());
        modules.add(new HUD());
        modules.add(new Nametags());
        modules.add(new NoRotate());
        modules.add(new TargetHUD());
        modules.add(new Tracers());
        modules.add(new Xray());

        //world
        modules.add(new AntiVoid());
        modules.add(new AutoArmor());
        modules.add(new ChestStealer());
        modules.add(new FastPlace());
        modules.add(new PacketMotior());
        modules.add(new Phase());
        modules.add(new PingSpoof());
        modules.add(new SafeWalk());
        modules.add(new Scaffold());
        
        this.readSettings();
        for (Module m : modules) {
            m.makeCommand();
        }
        EventBus.getInstance().register(this);
    }

    public static List<Module> getModules() {
        return modules;
    }

    public static Module getModuleByClass(Class<? extends Module> cls) {
        for (Module m : modules) {
            if (m.getClass() != cls) continue;
            return m;
        }
        return null;
    }

    public static Module getModuleByName(String name) {
        for (Module m : modules) {
            if (!m.getName().equalsIgnoreCase(name)) continue;
            return m;
        }
        return null;
    }

    public Module getAlias(String name) {
        for (Module f : modules) {
            if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
            String[] alias = f.getAlias();
            int length = alias.length;
            int i = 0;
            while (i < length) {
                String s = alias[i];
                if (s.equalsIgnoreCase(name)) {
                    return f;
                }
                ++i;
            }
        }
        return null;
    }

    public List<Module> getModulesInType(ModuleType t) {
        ArrayList<Module> output = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.getType() != t) continue;
            output.add(m);
        }
        return output;
    }

    @EventHandler
    private void onKeyPress(EventKey e) {
        for (Module m : modules) {
            if (m.getKey() != e.getKey()) continue;
            m.setEnabled(!m.isEnabled());
        }
    }

    @EventHandler
    private void onGLHack(EventRender3D e) {
        GlStateManager.getFloat(2982, (FloatBuffer)GLUtils.MODELVIEW.clear());
        GlStateManager.getFloat(2983, (FloatBuffer)GLUtils.PROJECTION.clear());
        GlStateManager.glGetInteger(2978, (IntBuffer)GLUtils.VIEWPORT.clear());
    }

    @EventHandler
    private void on2DRender(EventRender2D e) {
        if (this.enabledNeededMod) {
            this.enabledNeededMod = false;
            for (Module m : modules) {
                if (!m.enabledOnStartup) continue;
                m.setEnabled(true);
            }
        }
    }

    private void readSettings() {
        List<String> binds = FileManager.read("Binds.txt");
        for (String v : binds) {
            String name = v.split(":")[0];
            String bind = v.split(":")[1];
            Module m = ModuleManager.getModuleByName(name);
            if (m == null) continue;
            m.setKey(Keyboard.getKeyIndex((String)bind.toUpperCase()));
        }
        List<String> enabled = FileManager.read("Enabled.txt");
        for (String v : enabled) {
            Module m = ModuleManager.getModuleByName(v);
            if (m == null) continue;
            m.enabledOnStartup = true;
        }
        List<String> vals = FileManager.read("Values.txt");
        for (String v : vals) {
            String name = v.split(":")[0];
            String values = v.split(":")[1];
            Module m = ModuleManager.getModuleByName(name);
            if (m == null) continue;
            for (Value value : m.getValues()) {
                if (!value.getName().equalsIgnoreCase(values)) continue;
                if (value instanceof Option) {
                    value.setValue(Boolean.parseBoolean(v.split(":")[2]));
                    continue;
                }
                if (value instanceof Numbers) {
                    value.setValue(Double.parseDouble(v.split(":")[2]));
                    continue;
                }
                ((Mode)value).setMode(v.split(":")[2]);
            }
        }
    }
}

