/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.injector.server.TemporaryPlayer
 *  com.comphenix.protocol.reflect.StructureModifier
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.server.TemporaryPlayer;
import com.comphenix.protocol.reflect.StructureModifier;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;

public class ClientPositionPacket
extends PacketAdapter
implements Listener {
    private final Map<UUID, Double> lastZ = new ConcurrentHashMap<UUID, Double>();
    private final Map<UUID, Double> lastY = new ConcurrentHashMap<UUID, Double>();
    private boolean temporary = true;
    private final ConnectionCloser connectionCloser;

    @EventHandler
    public void onQuit(PlayerQuitEvent a) {
        ClientPositionPacket a2;
        a = a.getPlayer().getUniqueId();
        a2.lastY.remove((Object)a);
        a2.lastZ.remove((Object)a);
    }

    public void onPacketReceiving(PacketEvent a) {
        PacketContainer packetContainer;
        ClientPositionPacket a2;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        if (a2.temporary) {
            try {
                if (player instanceof TemporaryPlayer) {
                    return;
                }
            }
            catch (Exception exception) {
                a2.temporary = false;
            }
        }
        if ((packetContainer = a.getPacket()).getIntegers().size() >= 2) {
            int n = ((Integer)packetContainer.getIntegers().readSafely((int)0)).intValue();
            int n2 = ((Integer)packetContainer.getIntegers().readSafely((int)1)).intValue();
            int n3 = ((Integer)packetContainer.getIntegers().readSafely((int)2)).intValue();
            if (n >= Integer.MAX_VALUE || n2 >= Integer.MAX_VALUE || n3 >= Integer.MAX_VALUE) {
                a.setCancelled((boolean)true);
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" Invalid position | Too big position! (>= Integer.MAX_VALUE)").toString());
                return;
            }
        }
        if (packetContainer.getDoubles().size() >= 2) {
            double d2 = ((Double)packetContainer.getDoubles().readSafely((int)0)).doubleValue();
            double d3 = ((Double)packetContainer.getDoubles().readSafely((int)1)).doubleValue();
            double d4 = ((Double)packetContainer.getDoubles().readSafely((int)2)).doubleValue();
            if (d2 >= Double.MAX_VALUE || d3 >= Double.MAX_VALUE || d4 >= Double.MAX_VALUE) {
                a.setCancelled((boolean)true);
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" Invalid position | Too big position! (>= Double.MAX_VALUE)").toString());
                return;
            }
            boolean bl = false;
            if (!a2.lastY.containsKey((Object)player.getUniqueId())) {
                a2.lastY.put((UUID)player.getUniqueId(), (Double)Double.valueOf((double)d3));
            }
            if (!a2.lastZ.containsKey((Object)player.getUniqueId())) {
                a2.lastZ.put((UUID)player.getUniqueId(), (Double)Double.valueOf((double)d4));
            }
            if (d3 - a2.lastY.get((Object)player.getUniqueId()).doubleValue() == 9.0) {
                bl = true;
                a.setCancelled((boolean)bl);
                a2.lastY.remove((Object)player.getUniqueId());
            } else if (d4 - a2.lastZ.get((Object)player.getUniqueId()).doubleValue() == 9.0) {
                bl = true;
                a.setCancelled((boolean)bl);
                a2.lastZ.remove((Object)player.getUniqueId());
            }
            if (bl) {
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" invalid position packet!").toString());
            }
        }
        if (packetContainer.getFloat().size() < 1) return;
        float f = ((Float)packetContainer.getFloat().readSafely((int)0)).floatValue();
        float f2 = ((Float)packetContainer.getFloat().readSafely((int)1)).floatValue();
        if (f != Float.NEGATIVE_INFINITY && f2 != Float.NEGATIVE_INFINITY && !(f >= Float.MAX_VALUE)) {
            if (!(f2 >= Float.MAX_VALUE)) return;
        }
        a.setCancelled((boolean)true);
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" Invalid position | Too big position! (>= Float.MAX_VALUE or NEGATIVE_INFINITY)").toString());
    }

    public ClientPositionPacket(ProtectorSpigot a) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.NORMAL, (PacketType[])new PacketType[]{PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.LOOK, PacketType.Play.Client.FLYING});
        ClientPositionPacket a2;
        a2.connectionCloser = a.getConnectionCloser();
    }
}

