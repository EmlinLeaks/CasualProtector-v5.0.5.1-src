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

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ForgeListener
implements Listener {
    private final String message;

    public ForgeListener(String message) {
        this.message = message;
    }

    @EventHandler
    public void onPacket(PluginMessageEvent event) {
        String channel = event.getTag();
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer)event.getSender();
        if (!(channel.equalsIgnoreCase((String)"FML|HS") || channel.equalsIgnoreCase((String)"FML") || channel.equalsIgnoreCase((String)"fml:hs") || channel.equalsIgnoreCase((String)"fml") || channel.equalsIgnoreCase((String)"fml:handshake"))) {
            if (!channel.toLowerCase().contains((CharSequence)"fml")) return;
        }
        event.setCancelled((boolean)true);
        player.disconnect((BaseComponent)new TextComponent((String)this.message));
    }
}

