/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.normal.AlreadyDisconnectedManager;
import xyz.yooniks.protector.bukkit.listener.normal.basic.CustomListener;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;

public class MaxAccountsListener
extends CustomListener {
    private final WhitelistManager whitelistManager;
    private final AlreadyDisconnectedManager alreadyDisconnectedManager = AlreadyDisconnectedManager.getInstance();
    private final int maxAmount;

    @EventHandler(ignoreCancelled=true)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        MaxAccountsListener a3;
        if (a3.whitelistManager.isWhitelist((String)a.getName())) return;
        if (a3.alreadyDisconnectedManager.isDisconnected((UUID)a.getUniqueId())) {
            return;
        }
        if (a.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_OTHER) {
            return;
        }
        String string = a.getAddress().getHostAddress();
        if (Bukkit.getOnlinePlayers().stream().filter(a2 -> a2.getAddress().getAddress().getHostAddress().equalsIgnoreCase((String)string)).count() <= (long)a3.maxAmount) return;
        a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)new StringBuilder().insert((int)0, (String)a3.PREFIX).append((String)StringUtils.replace((String)MessagesConfig.ANTIBOT$TOO_MANY_ACCOUNTS_PER_IP, (String)"%limit%", (String)String.valueOf((int)a3.maxAmount))).toString());
    }

    public MaxAccountsListener(int a, WhitelistManager a2) {
        MaxAccountsListener a3;
        a3.maxAmount = a;
        a3.whitelistManager = a2;
    }
}

