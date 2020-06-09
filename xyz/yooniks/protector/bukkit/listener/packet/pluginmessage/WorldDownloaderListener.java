/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.reflect.StructureModifier
 *  com.comphenix.protocol.wrappers.MinecraftKey
 *  io.netty.buffer.ByteBuf
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.listener.packet.pluginmessage;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.MinecraftKey;
import io.netty.buffer.ByteBuf;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.basic.CustomPluginMessageListener;

public class WorldDownloaderListener
extends CustomPluginMessageListener {
    private final List<String> bannedChannels = Arrays.asList("wdl|control", "wdl|init", "wdl|request", "wdl:control", "wdl:init", "wdl:request", "legacy:wdl|request", "legacy:wdl|init", "legacy:wdl:request");
    private boolean exception = false;
    private final List<String> bypass;

    public WorldDownloaderListener(ProtectorSpigot a, List<String> a2) {
        super((ProtectorSpigot)a);
        WorldDownloaderListener a3;
        a3.bypass = a2;
    }

    public void onPacketReceiving(PacketEvent a) {
        WorldDownloaderListener a2;
        if (a.getPacketType() != PacketType.Play.Client.CUSTOM_PAYLOAD) return;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        if (a2.bypass.contains((Object)player.getName())) {
            return;
        }
        Object object = a.getPacket();
        String string = object.getStrings().size() <= 0 ? ((MinecraftKey)object.getMinecraftKeys().readSafely((int)0)).getFullKey() : (String)object.getStrings().readSafely((int)0);
        if (a2.bannedChannels.contains((Object)string.toLowerCase()) || string.startsWith((String)"WorldDownloader-") || string.contains((CharSequence)"wdl")) {
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$WDL);
            return;
        }
        if (object.getByteArrays().size() > 0) {
            string = new String((byte[])((byte[])object.getByteArrays().readSafely((int)0)), (Charset)StandardCharsets.UTF_8);
            if (!string.startsWith((String)"WorldDownloader-")) {
                if (!string.contains((CharSequence)"wdl")) return;
            }
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$WDL);
            return;
        }
        if (a2.exception) {
            return;
        }
        try {
            string = ((ByteBuf)object.getSpecificModifier(ByteBuf.class).readSafely((int)0)).copy();
            if (string == null) {
                return;
            }
            object = new byte[string.readableBytes()];
            string.readBytes((byte[])object);
            object = new String((byte[])object);
            if (!a2.bannedChannels.contains((Object)((String)object).toLowerCase())) {
                if (!((String)object).startsWith((String)"WorldDownloader-")) return;
            }
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$WDL);
            return;
        }
        catch (Exception exception) {
            a2.exception = true;
        }
    }
}

