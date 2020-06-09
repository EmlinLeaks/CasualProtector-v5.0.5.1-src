/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;

public class SimilarNamesListener
implements Listener {
    private final int limit;
    private final int start;
    private int end;

    public SimilarNamesListener(int a, int a2, int a3) {
        SimilarNamesListener a4;
        a4.limit = a;
        a4.start = a2;
        a4.end = a3;
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.LOWEST)
    public void onConnect(AsyncPlayerPreLoginEvent a) {
        SimilarNamesListener a3;
        if (a.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_OTHER) {
            return;
        }
        try {
            String string = a.getName().substring((int)a3.start, (int)a3.end);
            long l = Bukkit.getOnlinePlayers().stream().filter(a2 -> {
                SimilarNamesListener a3;
                if (a2.getName().length() <= a3.end) return false;
                if (!string.equalsIgnoreCase((String)a2.getName().substring((int)a3.start, (int)a3.end))) return false;
                return true;
            }).count();
            if (l <= (long)a3.limit) return;
            a.disallow((AsyncPlayerPreLoginEvent.Result)AsyncPlayerPreLoginEvent.Result.KICK_OTHER, (String)MessageHelper.colored((String)MessagesConfig.ANTIBOT$LIMITER$SIMILAR$NAMES$DISCONNECT_MESSAGE));
            return;
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            int n = a.getName().length();
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Looks like someone on your server has nickname with less than ").append((int)a3.end).append((String)" letters! We changed name-similarity-checker.end to ").append((int)n).append((String)" to prevent further issues.").toString());
            a3.end = n;
        }
    }
}

