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

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;

public class NameLengthCheckListener
implements Listener {
    private final int sensibility;
    private String lastName = "";
    private long lastSimilarNameLoginTime;
    private final WhitelistManager whitelistManager;
    private int lastNameLength;
    private int count;

    public NameLengthCheckListener(int a, WhitelistManager a2) {
        NameLengthCheckListener a3;
        a3.sensibility = a;
        a3.whitelistManager = a2;
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        NameLengthCheckListener a2;
        String string = a.getName();
        if (a2.whitelistManager.isWhitelist((String)string)) {
            return;
        }
        if (a.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_OTHER) {
            return;
        }
        int n = string.length();
        if (a2.lastNameLength != n) {
            a2.count = 0;
            a2.lastNameLength = n;
            a2.lastName = string;
            return;
        }
        if (a2.lastSimilarNameLoginTime > System.currentTimeMillis()) {
            a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)MessagesConfig.ANTIBOT$LIMITER$LAST_NAME_LENGTH$DISCONNECT_MESSAGE));
            return;
        }
        if (a2.lastName.equals((Object)string)) {
            return;
        }
        ++a2.count;
        if (a2.count < a2.sensibility) return;
        a2.lastSimilarNameLoginTime = System.currentTimeMillis() + 10000L;
        a2.count = 0;
        a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)MessagesConfig.ANTIBOT$LIMITER$LAST_NAME_LENGTH$DISCONNECT_MESSAGE));
    }
}

