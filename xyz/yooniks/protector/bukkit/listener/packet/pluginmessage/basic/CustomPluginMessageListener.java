/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.ListenerOptions
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.basic;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import java.util.Collections;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;

public class CustomPluginMessageListener
extends PacketAdapter {
    public final String PREFIX;
    public final ConnectionCloser connectionCloser;

    public CustomPluginMessageListener(ProtectorSpigot a, ListenerOptions a2) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, Collections.singletonList(PacketType.Play.Client.CUSTOM_PAYLOAD), (ListenerOptions[])new ListenerOptions[]{a2});
        CustomPluginMessageListener a3;
        a3.PREFIX = MessageHelper.colored((String)(MessagesConfig.PREFIX + "&c"));
        a3.connectionCloser = a.getConnectionCloser();
    }

    public CustomPluginMessageListener(ProtectorSpigot a) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, (PacketType[])new PacketType[]{PacketType.Play.Client.CUSTOM_PAYLOAD});
        CustomPluginMessageListener a2;
        a2.PREFIX = MessageHelper.colored((String)(MessagesConfig.PREFIX + "&c"));
        a2.connectionCloser = a.getConnectionCloser();
    }
}

