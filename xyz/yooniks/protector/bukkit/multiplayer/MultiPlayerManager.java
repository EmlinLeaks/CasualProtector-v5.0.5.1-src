/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.multiplayer;

import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayer;
import xyz.yooniks.protector.bukkit.multiplayer.NewMultiPlayer;
import xyz.yooniks.protector.bukkit.multiplayer.OldMultiPlayer;

public class MultiPlayerManager {
    private final boolean version_1_9_or_newer;

    public MultiPlayer createMultiPlayer(Player a) {
        MultiPlayerManager a2;
        if (!a2.version_1_9_or_newer) return new OldMultiPlayer((Player)a);
        return new NewMultiPlayer((Player)a);
    }

    public MultiPlayerManager(boolean a) {
        MultiPlayerManager a2;
        a2.version_1_9_or_newer = a;
    }
}

