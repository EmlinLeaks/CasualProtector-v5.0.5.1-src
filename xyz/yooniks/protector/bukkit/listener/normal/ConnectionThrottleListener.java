/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.listener.normal.AlreadyDisconnectedManager;

public class ConnectionThrottleListener
implements Listener {
    private final long delay;
    private final AlreadyDisconnectedManager alreadyDisconnectedManager = AlreadyDisconnectedManager.getInstance();
    private final Map<String, Long> timestamp = new HashMap<String, Long>();

    public ConnectionThrottleListener(long a) {
        ConnectionThrottleListener a2;
        a2.delay = a;
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        ConnectionThrottleListener a2;
        if (a.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_OTHER) return;
        if (a2.alreadyDisconnectedManager.isDisconnected((UUID)a.getUniqueId())) {
            return;
        }
        String string = a.getAddress().getHostName();
        if (a2.timestamp.containsKey((Object)string) && a2.timestamp.get((Object)string).longValue() >= System.currentTimeMillis()) {
            if (a2.timestamp.get((Object)string).longValue() <= System.currentTimeMillis()) return;
            a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)StringUtils.replace((String)MessagesConfig.ANTIBOT$LIMITER$CONNECTION_THROTTLE$DISCONNECT_MESSAGE, (String)"{SECONDS}", (String)String.valueOf((int)((int)(a2.timestamp.get((Object)string).longValue() - System.currentTimeMillis()) / 1000)))));
            return;
        }
        a2.timestamp.put((String)string, (Long)Long.valueOf((long)(System.currentTimeMillis() + a2.delay)));
    }
}

