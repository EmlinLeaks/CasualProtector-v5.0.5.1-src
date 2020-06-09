/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.messaging.PluginMessageListener
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitTask;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.normal.ChannelVerificationListener;
import xyz.yooniks.protector.bukkit.tps.Tps;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;

public class ChannelVerificationListener
implements Listener,
PluginMessageListener {
    private final Map<String, Long> blockedAdresses = new ConcurrentHashMap<String, Long>();
    private final WhitelistManager whitelistManager;
    private static ChannelVerificationListener instance;
    private final Map<UUID, Long> timestamps = new ConcurrentHashMap<UUID, Long>();
    private final int blockOnMinutes;
    private final boolean block;

    public static /* synthetic */ int access$200(ChannelVerificationListener a) {
        return a.blockOnMinutes;
    }

    public ChannelVerificationListener(boolean a, WhitelistManager a2, int a3) {
        ChannelVerificationListener a4;
        a4.block = a;
        a4.whitelistManager = a2;
        a4.blockOnMinutes = a3;
        instance = a4;
    }

    public void runTask(Plugin a) {
        ChannelVerificationListener a2;
        Bukkit.getScheduler().runTaskTimer((Plugin)a, (Runnable)new VerifyTask((ChannelVerificationListener)a2), (long)20L, (long)20L);
    }

    public static /* synthetic */ Map access$000(ChannelVerificationListener a) {
        return a.timestamps;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent a) {
        ChannelVerificationListener a2;
        a = a.getPlayer();
        a2.timestamps.remove((Object)a.getUniqueId());
    }

    public static /* synthetic */ Map access$300(ChannelVerificationListener a) {
        return a.blockedAdresses;
    }

    public static /* synthetic */ boolean access$100(ChannelVerificationListener a) {
        return a.block;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent a) {
        ChannelVerificationListener a2;
        if (a2.whitelistManager.isWhitelist((String)(a = a.getPlayer()).getName())) return;
        if (Tps.getTPS() < MessagesConfig.BYPASS_WHEN_TPS_UNDER) {
            return;
        }
        a2.timestamps.put((UUID)a.getUniqueId(), (Long)Long.valueOf((long)System.currentTimeMillis()));
    }

    public static void addBlockedAddress(Player a) {
        if (instance == null) {
            return;
        }
        if (ChannelVerificationListener.instance.block) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)"'s address has been just blocked for 25 minutes for trying to crash the server! (channel-verification has to be enabled)").toString());
        }
        ChannelVerificationListener.instance.blockedAdresses.put((String)a.getAddress().getHostString(), (Long)Long.valueOf((long)(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis((long)25L))));
    }

    public void onPluginMessageReceived(String a, Player a2, byte[] a3) {
        ChannelVerificationListener a4;
        if (!a.equalsIgnoreCase((String)"MC|Brand") && !a.equalsIgnoreCase((String)"minecraft:brand")) {
            return;
        }
        a4.timestamps.remove((Object)a2.getUniqueId());
    }

    @EventHandler(ignoreCancelled=true)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        ChannelVerificationListener a2;
        String string = a.getAddress().getHostAddress();
        if (!a2.blockedAdresses.containsKey((Object)string)) return;
        if (a2.blockedAdresses.get((Object)string).longValue() > System.currentTimeMillis()) {
            a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)ChatColor.translateAlternateColorCodes((char)'&', (String)MessagesConfig.ANTIBOT$CHANNEL_VERIFICATION$KICKMESSAGE));
            return;
        }
        a2.blockedAdresses.remove((Object)string);
    }
}

