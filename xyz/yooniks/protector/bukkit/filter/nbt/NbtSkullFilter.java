/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.wrappers.nbt.NbtCompound
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.filter.nbt;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.filter.nbt.NbtFilter;

public class NbtSkullFilter
implements NbtFilter {
    @Override
    public boolean isInvalid(Player a, NbtCompound a2) {
        return false;
    }

    public NbtSkullFilter() {
        NbtSkullFilter a;
    }
}

