/*
 * Decompiled with CFR 0_132.
 */
package yoUNP.command.commands;

import yoUNP.Client;
import yoUNP.command.Command;
import yoUNP.management.ModuleManager;
import yoUNP.module.Module;
import yoUNP.module.modules.render.Xray;
import yoUNP.utils.Helper;
import yoUNP.utils.math.MathUtil;
import java.util.Arrays;
import java.util.List;

public class Xraycmd
extends Command {
    public Xraycmd() {
        super("xray", new String[]{"oreesp"}, "", "nigga");
    }

    @Override
    public String execute(String[] args) {
        Xray xray = (Xray)Client.instance.getModuleManager().getModuleByClass(Xray.class);
        if (args.length == 2) {
            if (MathUtil.parsable(args[1], (byte)4)) {
                int id = Integer.parseInt(args[1]);
                if (args[0].equalsIgnoreCase("add")) {
                    xray.blocks.add(id);
                    Helper.sendMessage("Added Block ID " + id);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    xray.blocks.remove(id);
                    Helper.sendMessage("Removed Block ID " + id);
                } else {
                    Helper.sendMessage("Invalid syntax");
                }
            } else {
                Helper.sendMessage("Invalid block ID");
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            Arrays.toString(xray.blocks.toArray());
        }
        return null;
    }
}

