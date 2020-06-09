/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelFuture
 *  net.minecraft.server.v1_8_R3.EntityPlayer
 *  net.minecraft.server.v1_8_R3.NetworkManager
 *  net.minecraft.server.v1_8_R3.PlayerConnection
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package xyz.yooniks.protector.bukkit.closer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.helper.PingHelper;
import xyz.yooniks.protector.bukkit.tps.Ticking;
import xyz.yooniks.protector.bukkit.tps.Tps;

public class ConnectionCloser_1_8_8
implements ConnectionCloser,
Listener {
    private final Set<UUID> toDisconnect = new HashSet<UUID>();

    @Override
    public void closeConnection(Player a3, String a4) {
        ConnectionCloser_1_8_8 a5;
        if (a3 == null) return;
        if (!a3.isOnline()) return;
        if (a5.toDisconnect.contains((Object)a3.getUniqueId())) {
            return;
        }
        a5.toDisconnect.add((UUID)a3.getUniqueId());
        CraftPlayer craftPlayer = (CraftPlayer)a3;
        craftPlayer.getHandle().playerConnection.networkManager.channel.close();
        craftPlayer.disconnect((String)MessageHelper.colored((String)(MessagesConfig.PREFIX + " " + a4)));
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)a3.getName()).append((String)" got kicked! (").append((String)a4).append((String)") ping: ").append((int)PingHelper.getPing((Player)a3)).append((String)", tps: ").append((String)Tps.tpsToString((double)Ticking.getTPS())).append((String)" & ").append((String)Tps.tpsToString((double)Tps.getTPS())).toString());
        if (!MessagesConfig.CONNECTION_CLOSER$SEND_MESSAGE_TO_OPS) return;
        Bukkit.getOnlinePlayers().stream().filter(a -> {
            if (a.isOp()) return true;
            if (!a.hasPermission((String)MessagesConfig.CONNECTION_CLOSER$SEND_MESSAGE_TO_PLAYERS_WITH_PERM)) return false;
            return true;
        }).forEach(a2 -> a2.sendMessage((String)MessageHelper.colored((String)StringUtils.replace((String)MessagesConfig.CONNECTION_CLOSER$MESSAGE_TO_OPS, (String)"%player%", (String)a3.getName()))));
    }

    @Override
    public boolean isClosing(UUID a) {
        ConnectionCloser_1_8_8 a2;
        return a2.toDisconnect.contains((Object)a);
    }

    public ConnectionCloser_1_8_8() {
        ConnectionCloser_1_8_8 a;
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent a) {
        ConnectionCloser_1_8_8 a2;
        a2.toDisconnect.remove((Object)a.getPlayer().getUniqueId());
    }
}

