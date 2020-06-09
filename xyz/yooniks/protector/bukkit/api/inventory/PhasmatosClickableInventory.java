/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import java.util.function.Consumer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface PhasmatosClickableInventory {
    public PhasmatosClickableInventory addItemAction(int var1, Consumer<Player> var2);

    public void onClick(InventoryClickEvent var1);

    public void removeItemAction(int var1);
}

