/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.wrappers.nbt.NbtCompound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package xyz.yooniks.protector.bukkit.filter.nbt;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.yooniks.protector.bukkit.filter.nbt.NbtFilter;

public class NbtFilters {
    private final boolean enabled = false;
    private final Set<NbtFilter> filters = new HashSet<NbtFilter>();

    private static /* synthetic */ boolean lambda$isInvalid$0(Player a, NbtCompound a2, NbtFilter a3) {
        return a3.isInvalid((Player)a, a2);
    }

    public NbtFilters() {
        NbtFilters a;
    }

    public boolean isInvalid(Player a, ItemStack a2) {
        NbtFilters a3;
        Objects.requireNonNull(a3);
        return false;
    }
}

