/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import java.util.List;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory;

public interface PhasmatosInventoryAPI {
    public List<PhasmatosInventory> getInventories();

    public void addInventory(PhasmatosInventory var1);

    public PhasmatosInventory findByTitleAndSize(String var1, int var2);

    public PhasmatosInventory findByTitle(String var1);
}

