/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;

public class LoggedFromAnotherLocationListener
implements Listener {
    @EventHandler(ignoreCancelled=true)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        if (a.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_OTHER) return;
        if (Bukkit.getPlayerExact((String)a.getName()) == null) {
            if (Bukkit.getPlayer((UUID)a.getUniqueId()) == null) return;
        }
        a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)MessagesConfig.ANTI_LOGGED_FROM_ANOTHER_LOCATION));
    }

    public LoggedFromAnotherLocationListener() {
        LoggedFromAnotherLocationListener a;
    }
}

