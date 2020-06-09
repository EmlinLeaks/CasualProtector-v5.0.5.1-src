/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosChangeableInventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosClickableInventory;

public abstract class PhasmatosClickableChangeableInventory
extends PhasmatosChangeableInventory
implements PhasmatosClickableInventory {
    public final Map<Integer, Consumer<Player>> itemActions = new HashMap<Integer, Consumer<Player>>();
    public final Map<Integer, Consumer<Player>> rightClickActions = new HashMap<Integer, Consumer<Player>>();

    public PhasmatosClickableInventory addRightClickAction(int a, Consumer<Player> a2) {
        PhasmatosClickableChangeableInventory a3;
        a3.rightClickActions.put((Integer)Integer.valueOf((int)a), a2);
        return a3;
    }

    public PhasmatosClickableChangeableInventory(String a, int a2) {
        super((String)a, (int)a2);
        PhasmatosClickableChangeableInventory a3;
    }

    @Override
    public void removeItemAction(int a) {
        PhasmatosClickableChangeableInventory a2;
        a2.itemActions.remove((Object)Integer.valueOf((int)a));
    }

    @Override
    public void onClick(InventoryClickEvent a) {
        PhasmatosClickableChangeableInventory a2;
        if (!(a.getWhoClicked() instanceof Player)) {
            return;
        }
        a.setCancelled((boolean)true);
        if (a.isLeftClick()) {
            if (!a2.itemActions.containsKey((Object)Integer.valueOf((int)a.getSlot()))) return;
            a2.itemActions.get((Object)Integer.valueOf((int)a.getSlot())).accept((Player)((Player)a.getWhoClicked()));
            return;
        }
        if (!a2.rightClickActions.containsKey((Object)Integer.valueOf((int)a.getSlot()))) return;
        a2.rightClickActions.get((Object)Integer.valueOf((int)a.getSlot())).accept((Player)((Player)a.getWhoClicked()));
    }

    @Override
    public PhasmatosClickableInventory addItemAction(int a, Consumer<Player> a2) {
        PhasmatosClickableChangeableInventory a3;
        a3.itemActions.put((Integer)Integer.valueOf((int)a), a2);
        return a3;
    }
}

