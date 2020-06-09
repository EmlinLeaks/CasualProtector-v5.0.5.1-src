/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.reflect.StructureModifier
 *  com.comphenix.protocol.wrappers.MinecraftKey
 *  io.netty.buffer.ByteBuf
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerJoinEvent
 */
package xyz.yooniks.protector.bukkit.listener.packet.pluginmessage;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.MinecraftKey;
import io.netty.buffer.ByteBuf;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.basic.CustomPluginMessageListener;

public class ForgeListener
extends CustomPluginMessageListener
implements Listener {
    private final List<String> bypass;
    private boolean exception;

    @EventHandler
    public void onJoin(PlayerJoinEvent a2) {
        try {
            a2 = a2.getPlayer();
            PacketContainer packetContainer = new PacketContainer((PacketType)PacketType.Play.Server.CUSTOM_PAYLOAD);
            packetContainer.getStrings().write((int)0, (Object)"FML");
            packetContainer.getByteArrays().write((int)0, (Object)new byte[]{0, 0, 0, 0, 0, 2});
            ProtocolLibrary.getProtocolManager().sendServerPacket((Player)a2, (PacketContainer)packetContainer);
            return;
        }
        catch (Exception a2) {
            ProtectorSpigotLogger.warning((String)new StringBuilder().insert((int)0, (String)"Could not check if player is using forge client! ").append((String)a2.getMessage()).toString());
            return;
        }
    }

    public void onPacketReceiving(PacketEvent a) {
        ForgeListener a2;
        if (a.getPacketType() != PacketType.Play.Client.CUSTOM_PAYLOAD) return;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        if (a2.bypass.contains((Object)player.getName())) {
            return;
        }
        byte[] arrby = a.getPacket();
        String string = arrby.getStrings().size() <= 0 ? ((MinecraftKey)arrby.getMinecraftKeys().readSafely((int)0)).getFullKey() : (String)arrby.getStrings().readSafely((int)0);
        if (string.equalsIgnoreCase((String)"FML|HS") || string.equalsIgnoreCase((String)"FML|MP") || string.equalsIgnoreCase((String)"fml:hs") || string.equalsIgnoreCase((String)"fml:mp") || string.equalsIgnoreCase((String)"forgewurst") || string.equalsIgnoreCase((String)"legacy:fml:hs") || string.equalsIgnoreCase((String)"legacy:fml|hs")) {
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$FORGE);
        }
        if (arrby.getByteArrays().size() > 0) {
            string = new String((byte[])((byte[])arrby.getByteArrays().readSafely((int)0)), (Charset)StandardCharsets.UTF_8).toLowerCase();
            if (!string.contains((CharSequence)"fml")) return;
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$FORGE);
            return;
        }
        if (a2.exception) {
            return;
        }
        try {
            string = ((ByteBuf)arrby.getSpecificModifier(ByteBuf.class).readSafely((int)0)).copy();
            if (string == null) {
                return;
            }
            arrby = new byte[string.readableBytes()];
            string.readBytes((byte[])arrby);
            if (!new String((byte[])arrby).toLowerCase().contains((CharSequence)"fml")) return;
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$FORGE);
            return;
        }
        catch (Exception exception) {
            a2.exception = true;
        }
    }

    public ForgeListener(ProtectorSpigot a, List<String> a2) {
        super((ProtectorSpigot)a);
        ForgeListener a3;
        a3.bypass = a2;
    }
}

