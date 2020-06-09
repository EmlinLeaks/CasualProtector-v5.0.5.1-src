/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.Iterator;
import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;

public class BannedNicknamesListener
implements Listener {
    private final List<String> banned;

    @EventHandler(ignoreCancelled=true)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        BannedNicknamesListener a2;
        String string;
        String string2 = a.getName().toLowerCase();
        Iterator<String> iterator = a2.banned.iterator();
        do {
            if (!iterator.hasNext()) return;
        } while (!string2.startsWith((String)(string = iterator.next())));
        a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)MessagesConfig.ANTIBOT$BANNED_NICKNAME));
    }

    public BannedNicknamesListener(List<String> a) {
        BannedNicknamesListener a2;
        a2.banned = a;
    }
}

