/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.PendingConnection
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 *  net.md_5.bungee.api.event.LoginEvent
 *  net.md_5.bungee.api.event.PlayerDisconnectEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener.normal;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.WeakHashMap;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.yooniks.protector.vpn.StringReplacer;

public class MaxAccountsListener
implements Listener {
    private final int maxAmount;
    private final Map<String, Integer> accountsPerIp = new WeakHashMap<String, Integer>();
    private final String kickMessage;

    @EventHandler(priority=-64)
    public void onConnect(LoginEvent a) {
        MaxAccountsListener a2;
        if (a.isCancelled()) {
            return;
        }
        String string = a.getConnection().getAddress().getHostString();
        int n = a2.accountsPerIp.getOrDefault((Object)string, (Integer)Integer.valueOf((int)0)).intValue();
        if (n > a2.maxAmount) {
            a.setCancelled((boolean)true);
            a.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)StringReplacer.replace((String)a2.kickMessage, (String)"%limit%", (String)String.valueOf((int)a2.maxAmount)))});
            return;
        }
        a2.accountsPerIp.put((String)string, (Integer)Integer.valueOf((int)(n + 1)));
    }

    @EventHandler(priority=64)
    public void onDisconnect(PlayerDisconnectEvent a) {
        MaxAccountsListener a2;
        if (!a2.accountsPerIp.containsKey((Object)(a = a.getPlayer().getAddress().getHostString()))) return;
        a2.accountsPerIp.put((String)a, (Integer)Integer.valueOf((int)(a2.accountsPerIp.get((Object)a).intValue() - 1)));
        if (a2.accountsPerIp.get((Object)a).intValue() > 0) return;
        a2.accountsPerIp.remove((Object)a);
    }

    public MaxAccountsListener(int a, String a2) {
        MaxAccountsListener a3;
        a3.maxAmount = a;
        a3.kickMessage = a2;
    }
}

