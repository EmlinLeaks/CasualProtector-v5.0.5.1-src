/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package xyz.yooniks.protector.bukkit.multiplayer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayer;

public class OldMultiPlayer
implements MultiPlayer {
    private final Player player;

    @Override
    public ItemStack getItemInMainHand() {
        OldMultiPlayer a;
        return a.player.getItemInHand();
    }

    public OldMultiPlayer(Player a) {
        OldMultiPlayer a2;
        a2.player = a;
    }
}

