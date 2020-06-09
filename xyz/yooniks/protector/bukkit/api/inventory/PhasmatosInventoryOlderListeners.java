/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosClickableInventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventoryAPI;

public class PhasmatosInventoryOlderListeners
implements Listener {
    private final PhasmatosInventoryAPI inventoryAPI;

    public PhasmatosInventoryOlderListeners(PhasmatosInventoryAPI a) {
        PhasmatosInventoryOlderListeners a2;
        a2.inventoryAPI = a;
    }

    @EventHandler
    public void onClick(InventoryClickEvent a) {
        PhasmatosInventoryOlderListeners a2;
        if (!(a.getWhoClicked() instanceof Player)) {
            return;
        }
        Object object = (Player)a.getWhoClicked();
        object = a.getInventory();
        if (object == null) {
            return;
        }
        if (!((object = a2.inventoryAPI.findByTitleAndSize((String)object.getTitle(), (int)object.getSize())) instanceof PhasmatosClickableInventory)) return;
        ((PhasmatosClickableInventory)object).onClick((InventoryClickEvent)a);
    }
}

