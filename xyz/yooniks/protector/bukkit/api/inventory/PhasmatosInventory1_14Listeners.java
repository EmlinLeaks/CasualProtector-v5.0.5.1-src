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
 *  org.bukkit.inventory.InventoryView
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosClickableInventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventoryAPI;

public class PhasmatosInventory1_14Listeners
implements Listener {
    private final PhasmatosInventoryAPI inventoryAPI;

    public PhasmatosInventory1_14Listeners(PhasmatosInventoryAPI a) {
        PhasmatosInventory1_14Listeners a2;
        a2.inventoryAPI = a;
    }

    @EventHandler
    public void onClick(InventoryClickEvent a) {
        PhasmatosInventory1_14Listeners a2;
        if (!(a.getWhoClicked() instanceof Player)) {
            return;
        }
        Object object = a.getInventory();
        if (object == null) {
            return;
        }
        object = a2.inventoryAPI.findByTitleAndSize((String)a.getView().getTitle(), (int)object.getSize());
        if (!(object instanceof PhasmatosClickableInventory)) return;
        ((PhasmatosClickableInventory)object).onClick((InventoryClickEvent)a);
    }
}

