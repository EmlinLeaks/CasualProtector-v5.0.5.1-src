/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.ListenerOptions
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.NetworkMarker
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.NetworkMarker;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;

public class ClientPacketsFilterListener
extends PacketAdapter {
    private final ConnectionCloser connectionCloser;

    public ClientPacketsFilterListener(Plugin a, ConnectionCloser a2) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, Arrays.asList(new PacketType[]{PacketType.Play.Client.CUSTOM_PAYLOAD, PacketType.Play.Client.POSITION, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.SET_CREATIVE_SLOT, PacketType.Play.Client.WINDOW_CLICK}), (ListenerOptions[])new ListenerOptions[]{ListenerOptions.INTERCEPT_INPUT_BUFFER});
        ClientPacketsFilterListener a3;
        a3.connectionCloser = a2;
    }

    public void onPacketReceiving(PacketEvent a) {
        ClientPacketsFilterListener a2;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        if (a2.connectionCloser.isClosing((UUID)player.getUniqueId())) {
            a.setCancelled((boolean)true);
            return;
        }
        if (a.getNetworkMarker() == null) return;
        if (a.getNetworkMarker().getInputBuffer() == null) {
            return;
        }
        int n = a.getNetworkMarker().getInputBuffer().remaining();
        if (n <= 8000) return;
        a.setCancelled((boolean)true);
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" remaining>8000! Packet name: ").append((String)a.getPacketType().name()).append((String)", remaining: ").append((int)n).toString());
        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PACKET_FILTER$INVALID_PACKET);
    }
}

