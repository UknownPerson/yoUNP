/*
 * Decompiled with CFR 0_132.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package yoUNP.command.commands;

import yoUNP.Client;
import yoUNP.command.Command;
import yoUNP.management.ModuleManager;
import yoUNP.module.Module;
import yoUNP.utils.Helper;
import org.lwjgl.input.Keyboard;

public class Bind
extends Command {
    public Bind() {
        super("Bind", new String[]{"b"}, "", "sketit");
    }

    @Override
    public String execute(String[] args) {
        if (args.length >= 2) {
            Module m = Client.instance.getModuleManager().getAlias(args[0]);
            if (m != null) {
                int k = Keyboard.getKeyIndex((String)args[1].toUpperCase());
                m.setKey(k);
                Object[] arrobject = new Object[2];
                arrobject[0] = m.getName();
                arrobject[1] = k == 0 ? "none" : args[1].toUpperCase();
                Helper.sendMessage(String.format("> Bound %s to %s", arrobject));
            } else {
                Helper.sendMessage("> Invalid module name, double check spelling.");
            }
        } else {
            Helper.sendMessageWithoutPrefix("\u00a7bCorrect usage:\u00a77 .bind <module> <key>");
        }
        return null;
    }
}

