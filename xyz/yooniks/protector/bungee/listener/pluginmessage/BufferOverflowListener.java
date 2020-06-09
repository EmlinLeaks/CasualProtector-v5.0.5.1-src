/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Charsets
 *  com.google.common.cache.Cache
 *  com.google.common.cache.CacheBuilder
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.Connection
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 *  net.md_5.bungee.api.event.PluginMessageEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener.pluginmessage;

import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BufferOverflowListener
implements Listener {
    private final int registerLimit;
    private final Cache<UUID, Integer> registerRequests = CacheBuilder.newBuilder().expireAfterWrite((long)60L, (TimeUnit)TimeUnit.SECONDS).build();
    private final String message;

    public BufferOverflowListener(boolean blockBook, int registerLimit, String message) {
        this.registerLimit = registerLimit;
        this.message = message;
    }

    @EventHandler
    public void onPacket(PluginMessageEvent event) {
        String channel = event.getTag();
        if (!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }
        byte[] data = event.getData();
        String dataString = new String((byte[])event.getData(), (Charset)Charsets.UTF_8);
        ProxiedPlayer player = (ProxiedPlayer)event.getSender();
        int dataSize = dataString.split((String)"[\u0001-\t]").length;
        if (channel == null || data == null) {
            event.setCancelled((boolean)true);
            return;
        }
        if (dataSize > 14336) {
            event.setCancelled((boolean)true);
            player.disconnect((BaseComponent)new TextComponent((String)this.message));
            return;
        }
        if (!channel.equalsIgnoreCase((String)"REGISTER")) return;
        Integer amount = (Integer)this.registerRequests.getIfPresent((Object)player.getUniqueId());
        if (amount == null) {
            this.registerRequests.put((Object)player.getUniqueId(), (Object)Integer.valueOf((int)1));
            return;
        }
        this.registerRequests.put((Object)player.getUniqueId(), (Object)Integer.valueOf((int)(amount.intValue() + 1)));
        if (amount.intValue() == this.registerLimit) {
            event.setCancelled((boolean)true);
            player.disconnect((BaseComponent)new TextComponent((String)this.message));
            return;
        }
        if (amount.intValue() <= this.registerLimit) return;
        event.setCancelled((boolean)true);
    }
}

