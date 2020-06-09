/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;

public class TooBigMoveDifferenceListener
implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent a) {
        if (!(a.getFrom().distance((Location)a.getTo()) > 20.0)) return;
        a.setCancelled((boolean)true);
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getPlayer().getName()).append((String)" too big distance difference between event.getto and event.getfrom! Cancelling.").toString());
    }

    public TooBigMoveDifferenceListener() {
        TooBigMoveDifferenceListener a;
    }
}

