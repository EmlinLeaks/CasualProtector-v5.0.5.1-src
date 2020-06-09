/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.filter.nbt;

import org.bukkit.entity.Player;

public interface Filter<T> {
    public boolean isInvalid(Player var1, T var2);
}

