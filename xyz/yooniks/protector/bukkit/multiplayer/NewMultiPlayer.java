/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package xyz.yooniks.protector.bukkit.multiplayer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayer;

public class NewMultiPlayer
implements MultiPlayer {
    private final Player player;

    public NewMultiPlayer(Player a) {
        NewMultiPlayer a2;
        a2.player = a;
    }

    @Override
    public ItemStack getItemInMainHand() {
        NewMultiPlayer a;
        return a.player.getInventory().getItemInMainHand();
    }
}

