/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Listener
 */
package xyz.yooniks.protector.bukkit.listener.normal.basic;

import org.bukkit.event.Listener;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;

public class CustomListener
implements Listener {
    public final String PREFIX = MessageHelper.colored((String)(MessagesConfig.PREFIX + "&c"));

    public CustomListener() {
        CustomListener a;
    }
}

