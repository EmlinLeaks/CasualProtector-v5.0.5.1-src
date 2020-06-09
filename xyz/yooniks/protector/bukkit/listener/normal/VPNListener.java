/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;
import xyz.yooniks.protector.vpn.VPNDetector;

public final class VPNListener
implements Listener {
    private final List<String> bypass = new ArrayList<String>();
    private final List<VPNDetector> vpnDetectors;
    private final WhitelistManager whitelistManager;

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        VPNListener a2;
        if (a.getAddress() == null) {
            return;
        }
        String string = a.getAddress().getHostAddress();
        Object object = a.getUniqueId();
        String string2 = a.getName();
        if (string == null) return;
        if (object == null) return;
        if (string2 == null) {
            return;
        }
        if (string.contains((CharSequence)"127.0.0.1")) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"VPNListener -> ").append((String)string2).append((String)"'s address contains 127.0.0.1 ip! VpnListener doesn't check address info on localhost servers anyway. If that's not localhost server please make sure you have switched ip-forward & bungeecord to true in your spigot configuration.").toString());
            return;
        }
        if (a2.whitelistManager.isWhitelist((String)string2)) return;
        if (a2.bypass.contains((Object)string)) {
            return;
        }
        if (a2.whitelistManager.isBlocked((String)string)) {
            a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)MessagesConfig.ANTIBOT$CANNOT_JOIN_FROM_VPN));
            return;
        }
        object = a2.vpnDetectors.iterator();
        do {
            if (!object.hasNext()) {
                a2.bypass.add((String)string);
                return;
            }
            VPNDetector vPNDetector = (VPNDetector)object.next();
            if (vPNDetector == null) continue;
            if (vPNDetector.isLimitable() && vPNDetector.count() > vPNDetector.getLimit()) {
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Trying to check info about ").append((String)string2).append((String)"'s address but vpndetector has reached requests per second! Allowing to join.").toString());
                return;
            }
            try {
                if (!vPNDetector.isBad((String)string)) continue;
                a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)MessagesConfig.ANTIBOT$CANNOT_JOIN_FROM_VPN));
                a2.whitelistManager.addBlocked((String)string);
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Blocked ").append((String)string2).append((String)"'s address! (bad ip(vpn/proxy or banned country) checker: ").append((String)vPNDetector.getName()).append((String)")").toString());
                return;
            }
            catch (IOException iOException) {
                a2.vpnDetectors.remove((Object)vPNDetector);
                ProtectorSpigotLogger.warning((String)new StringBuilder().insert((int)0, (String)"Couldn't get info about ").append((String)string2).append((String)"'s address! Removing vpnDetector to prevent next exceptions.. ").append((String)iOException.getMessage()).toString());
                continue;
            }
            break;
        } while (true);
    }

    public VPNListener(List<VPNDetector> a, WhitelistManager a2) {
        VPNListener a3;
        a3.vpnDetectors = new CopyOnWriteArrayList<VPNDetector>(a);
        a3.whitelistManager = a2;
    }
}

