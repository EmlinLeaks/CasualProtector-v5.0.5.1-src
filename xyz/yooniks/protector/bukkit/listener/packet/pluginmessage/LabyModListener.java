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
import java.util.List;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.basic.CustomPluginMessageListener;

public class LabyModListener
extends CustomPluginMessageListener {
    private final List<String> bypass;

    public void onPacketReceiving(PacketEvent a) {
        LabyModListener a2;
        if (a.getPacketType() != PacketType.Play.Client.CUSTOM_PAYLOAD) return;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        if (a2.bypass.contains((Object)player.getName())) {
            return;
        }
        Object object = a.getPacket();
        object = object.getStrings().size() <= 0 ? ((MinecraftKey)object.getMinecraftKeys().readSafely((int)0)).getFullKey() : (String)object.getStrings().readSafely((int)0);
        if (!((String)object).equalsIgnoreCase((String)"LMC")) return;
        a.setCancelled((boolean)true);
        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$LMC);
    }

    public LabyModListener(ProtectorSpigot a, List<String> a2) {
        super((ProtectorSpigot)a);
        LabyModListener a3;
        a3.bypass = a2;
    }
}

