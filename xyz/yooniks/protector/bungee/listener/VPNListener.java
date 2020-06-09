/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.PendingConnection
 *  net.md_5.bungee.api.event.PreLoginEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.yooniks.protector.bungee.ProtectorBungee;
import xyz.yooniks.protector.vpn.VPNDetector;

public final class VPNListener
implements Listener {
    private final Set<String> blocked = new HashSet<String>();
    private final List<VPNDetector> vpnDetectors;
    private final Set<String> bypass = new HashSet<String>();
    private final String kickMessage;

    public VPNListener(String a, List<VPNDetector> a2) {
        VPNListener a3;
        a3.kickMessage = a;
        a3.vpnDetectors = new CopyOnWriteArrayList<VPNDetector>(a2);
    }

    @EventHandler
    public void onConnect(PreLoginEvent a) {
        VPNListener a2;
        if (a.getConnection().getAddress() == null) return;
        if (a.getConnection().getUniqueId() == null) {
            return;
        }
        String string = a.getConnection().getAddress().getHostName();
        if (a2.bypass.contains((Object)string)) {
            return;
        }
        if (a2.blocked.contains((Object)string)) {
            a.setCancelled((boolean)true);
            a.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)a2.kickMessage)});
            return;
        }
        Iterator<VPNDetector> iterator = a2.vpnDetectors.iterator();
        do {
            if (!iterator.hasNext()) {
                a2.bypass.add((String)string);
                return;
            }
            VPNDetector vPNDetector = iterator.next();
            try {
                if (vPNDetector.isLimitable() && vPNDetector.count() > vPNDetector.getLimit()) {
                    ProtectorBungee.getInstance().getLogger().info((String)new StringBuilder().insert((int)0, (String)"Trying to check info about ").append((String)a.getConnection().getName()).append((String)"'s address but vpndetector has reached requests per second! Allowing to join.").toString());
                    return;
                }
                if (!vPNDetector.isBad((String)string)) continue;
                a.setCancelled((boolean)true);
                a.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)a2.kickMessage)});
                a2.blocked.add((String)string);
                ProtectorBungee.getInstance().getLogger().info((String)new StringBuilder().insert((int)0, (String)"Blocked ").append((String)a.getConnection().getName()).append((String)"'s address! (bad ip(vpn/proxy or banned country) checker: ").append((String)vPNDetector.getName()).append((String)")").toString());
                return;
            }
            catch (IOException iOException) {
                a2.vpnDetectors.remove((Object)vPNDetector);
                ProtectorBungee.getInstance().getLogger().warning((String)new StringBuilder().insert((int)0, (String)"Couldn't get info about ").append((String)a.getConnection().getName()).append((String)"'s address! Removing vpnDetector to prevent next exceptions.. ").append((String)iOException.getMessage()).toString());
                continue;
            }
            break;
        } while (true);
    }
}

