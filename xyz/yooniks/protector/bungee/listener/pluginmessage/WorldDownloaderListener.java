/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.Connection
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 *  net.md_5.bungee.api.event.PluginMessageEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener.pluginmessage;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class WorldDownloaderListener
implements Listener {
    private final List<String> bannedChannels = Arrays.asList("wdl|control", "wdl|init", "wdl|request", "wdl:control", "wdl:init", "wdl:request", "wdl");
    private final String message;

    public WorldDownloaderListener(String message) {
        this.message = message;
    }

    @EventHandler
    public void onPacket(PluginMessageEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer)event.getSender();
        String channel = event.getTag().toLowerCase();
        String data = new String((byte[])event.getData(), (Charset)StandardCharsets.UTF_8).toLowerCase();
        if (!(this.bannedChannels.contains((Object)channel) || this.bannedChannels.contains((Object)data) || channel.contains((CharSequence)"wdl") || channel.startsWith((String)"worlddownloader") || data.contains((CharSequence)"wdl"))) {
            if (!data.startsWith((String)"worlddownloader")) return;
        }
        event.setCancelled((boolean)true);
        player.disconnect((BaseComponent)new TextComponent((String)this.message));
    }
}

