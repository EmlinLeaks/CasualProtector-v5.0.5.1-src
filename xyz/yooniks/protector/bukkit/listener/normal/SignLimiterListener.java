/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.SignChangeEvent
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignLimiterListener
implements Listener {
    private final Map<UUID, Long> timestamp = new HashMap<UUID, Long>();
    private final String message;
    private final int secondsLimit;

    public SignLimiterListener(int a, String a2) {
        SignLimiterListener a3;
        a3.secondsLimit = a;
        a3.message = a2;
    }

    private /* synthetic */ boolean canUse(UUID a) {
        SignLimiterListener a2;
        if (!a2.timestamp.containsKey((Object)a)) {
            a2.timestamp.put((UUID)a, (Long)Long.valueOf((long)(System.currentTimeMillis() + 1000L * (long)a2.secondsLimit)));
            return true;
        }
        if (a2.timestamp.get((Object)a).longValue() <= System.currentTimeMillis()) return false;
        return true;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onSignChange(SignChangeEvent a) {
        SignLimiterListener a2;
        if (a2.canUse((UUID)a.getPlayer().getUniqueId())) return;
        a.setCancelled((boolean)true);
        a.getPlayer().sendMessage((String)a2.message);
    }
}

