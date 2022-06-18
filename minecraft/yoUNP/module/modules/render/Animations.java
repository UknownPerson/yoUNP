package yoUNP.module.modules.render;

import yoUNP.api.EventHandler;
import yoUNP.api.events.world.EventPreUpdate;
import yoUNP.api.value.Mode;
import yoUNP.api.value.Option;
import yoUNP.api.value.Value;
import yoUNP.module.Module;
import yoUNP.module.ModuleType;

public class Animations extends Module {
  public static Mode<Enum> mode = new Mode("Mode", "mode", (Enum[])renderMode.values(), renderMode.Sigma);
  public static Option<Boolean> smooth = new Option("Smooth", "Smooth", Boolean.valueOf(false));
  
  public Animations() {
    super("Animations", new String[] { "BlockHitanimations" }, ModuleType.Render);
    addValues(new Value[] { (Value)mode, (Value)smooth });
  }
  
  @EventHandler
  public void OnUpdate(EventPreUpdate event) {
		this.setSuffix((Object) (this.mode.getValue()));
	}
	
  public enum renderMode {
	  Sigma,old,Vanilla,Luna,Jigsaw,Swang,Swank,Swong;
  }
}
