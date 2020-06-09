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
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.listener.packet.pluginmessage;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.MinecraftKey;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.basic.CustomPluginMessageListener;

public class ProxyFixListener
extends CustomPluginMessageListener {
    public ProxyFixListener(ProtectorSpigot a) {
        super((ProtectorSpigot)a);
        ProxyFixListener a2;
    }

    public void onPacketReceiving(PacketEvent a) {
        ProxyFixListener a2;
        if (a.getPacketType() != PacketType.Play.Client.CUSTOM_PAYLOAD) return;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        PacketContainer packetContainer = a.getPacket();
        String string = packetContainer.getStrings().size() <= 0 ? ((MinecraftKey)packetContainer.getMinecraftKeys().readSafely((int)0)).getFullKey() : (String)packetContainer.getStrings().readSafely((int)0);
        if (!string.toLowerCase().contains((CharSequence)"proxy")) return;
        a.setCancelled((boolean)true);
        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$PROXY);
    }
}

