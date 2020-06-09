/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 *  org.bukkit.event.player.PlayerQuitEvent
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.listener.normal.AlreadyDisconnectedManager;
import xyz.yooniks.protector.bukkit.listener.normal.basic.CustomListener;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;

public class TwoStepVerificationListener
extends CustomListener {
    private final Set<UUID> verified = new HashSet<UUID>();
    private final AlreadyDisconnectedManager alreadyDisconnectedManager = AlreadyDisconnectedManager.getInstance();
    private final WhitelistManager whitelistManager;

    @EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        TwoStepVerificationListener a2;
        if (a2.whitelistManager.isWhitelist((String)a.getName())) {
            return;
        }
        UUID uUID = a.getUniqueId();
        if (a.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_OTHER) return;
        if (a2.alreadyDisconnectedManager.isDisconnected((UUID)uUID)) {
            return;
        }
        if (a2.verified.contains((Object)uUID)) return;
        a2.alreadyDisconnectedManager.addDisconnected((UUID)a.getUniqueId());
        a2.verified.add((UUID)uUID);
        a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)new StringBuilder().insert((int)0, (String)a2.PREFIX).append((String)MessageHelper.colored((String)MessagesConfig.ANTIBOT$DOUBLE_JOIN)).toString());
    }

    public TwoStepVerificationListener(WhitelistManager a) {
        TwoStepVerificationListener a2;
        a2.whitelistManager = a;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent a) {
        TwoStepVerificationListener a2;
        a2.alreadyDisconnectedManager.remove((UUID)a.getPlayer().getUniqueId());
    }
}

