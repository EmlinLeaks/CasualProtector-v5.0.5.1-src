/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory;

public class PhasmatosStableInventory
implements PhasmatosInventory {
    private final String name;
    private final Inventory inventory;

    @Override
    public PhasmatosInventory addItem(int a, ItemStack a2) {
        PhasmatosStableInventory a3;
        a3.inventory.setItem((int)a, (ItemStack)a2);
        return a3;
    }

    @Override
    public int getSize() {
        PhasmatosStableInventory a;
        return a.inventory.getSize();
    }

    @Override
    public Map<Integer, ItemStack> getItems() {
        PhasmatosStableInventory a;
        HashMap<Integer, ItemStack> hashMap = new HashMap<Integer, ItemStack>();
        int n = 0;
        while (n < a.inventory.getSize()) {
            ItemStack itemStack = a.inventory.getItem((int)n);
            if (itemStack != null) {
                hashMap.put((Integer)Integer.valueOf((int)n), (ItemStack)itemStack);
            }
            ++n;
        }
        return hashMap;
    }

    @Override
    public void open(Player a) {
        PhasmatosStableInventory a2;
        a.openInventory((Inventory)a2.inventory);
    }

    @Override
    public String getTitle() {
        PhasmatosStableInventory a;
        return a.name;
    }

    public PhasmatosStableInventory(String a, int a2) {
        PhasmatosStableInventory a3;
        a3.name = a;
        a3.inventory = Bukkit.createInventory(null, (int)a2, (String)a);
    }
}

