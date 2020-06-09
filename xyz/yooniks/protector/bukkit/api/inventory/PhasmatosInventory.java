/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface PhasmatosInventory {
    public PhasmatosInventory addItem(int var1, ItemStack var2);

    public String getTitle();

    public Map<Integer, ItemStack> getItems();

    public void open(Player var1);

    public int getSize();
}

