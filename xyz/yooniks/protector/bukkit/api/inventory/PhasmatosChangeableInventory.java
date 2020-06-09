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
import java.util.function.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory;

public abstract class PhasmatosChangeableInventory
implements PhasmatosInventory {
    private final int size;
    private final Map<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
    private final String title;

    public abstract ItemStack updateItem(ItemStack var1, int var2, Player var3);

    @Override
    public PhasmatosInventory addItem(int a, ItemStack a2) {
        PhasmatosChangeableInventory a3;
        a3.items.put((Integer)Integer.valueOf((int)a), (ItemStack)a2);
        return a3;
    }

    @Override
    public int getSize() {
        PhasmatosChangeableInventory a;
        return a.size;
    }

    @Override
    public String getTitle() {
        PhasmatosChangeableInventory a;
        return a.title;
    }

    @Override
    public void open(Player a) {
        PhasmatosChangeableInventory a2;
        Inventory inventory = Bukkit.createInventory(null, (int)a2.size, (String)a2.title);
        a2.getItems().forEach((a3, a4) -> {
            PhasmatosChangeableInventory a5;
            inventory.setItem((int)a3.intValue(), (ItemStack)a5.updateItem((ItemStack)a4, (int)a3.intValue(), (Player)a));
        });
        a.openInventory((Inventory)inventory);
        a.updateInventory();
    }

    public PhasmatosChangeableInventory(String a, int a2) {
        PhasmatosChangeableInventory a3;
        a3.title = a;
        a3.size = a2;
    }
}

