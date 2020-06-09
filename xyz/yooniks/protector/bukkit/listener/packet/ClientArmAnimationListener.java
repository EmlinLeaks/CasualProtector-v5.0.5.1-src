/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
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
import com.comphenix.protocol.events.PacketEvent;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.user.User;
import xyz.yooniks.protector.bukkit.user.UserManager;

public class ClientArmAnimationListener
extends PacketAdapter
implements Listener {
    private final UserManager userManager = UserManager.getInstance();
    private final Map<UUID, Long> timestamp = new WeakHashMap<UUID, Long>();
    private final long timestampTime;

    public ClientArmAnimationListener(Plugin a, long a2) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.NORMAL, (PacketType[])new PacketType[]{PacketType.Play.Client.ARM_ANIMATION});
        ClientArmAnimationListener a3;
        a3.timestampTime = a2;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent a) {
        ClientArmAnimationListener a2;
        a = a.getPlayer().getUniqueId();
        a2.timestamp.remove((Object)a);
        a = a2.userManager.getOrNull((UUID)a);
        if (a == null) return;
        ((User)a).clearViolations();
    }

    public void onPacketReceiving(PacketEvent a) {
        ClientArmAnimationListener a2;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        if (!a2.timestamp.containsKey((Object)player.getUniqueId())) {
            a2.timestamp.put((UUID)player.getUniqueId(), (Long)Long.valueOf((long)(System.currentTimeMillis() + a2.timestampTime)));
            return;
        }
        if (a2.timestamp.get((Object)player.getUniqueId()).longValue() <= System.currentTimeMillis()) return;
        a.setCancelled((boolean)true);
    }
}

