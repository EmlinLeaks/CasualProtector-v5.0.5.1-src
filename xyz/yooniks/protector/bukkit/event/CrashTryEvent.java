/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.event.Event
 *  org.bukkit.event.HandlerList
 */
package xyz.yooniks.protector.bukkit.event;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CrashTryEvent
extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final OfflinePlayer player;

    public CrashTryEvent(OfflinePlayer a) {
        CrashTryEvent a2;
        a2.player = a;
    }

    public OfflinePlayer getPlayer() {
        CrashTryEvent a;
        return a.player;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}

