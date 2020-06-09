/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.ItemDespawnEvent
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemDespawnEvent;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.normal.basic.CustomListener;

public class TooManyItemsListener
extends CustomListener {
    private final int limit;

    @EventHandler(ignoreCancelled=true)
    public void onItemDrop(ItemDespawnEvent a) {
        TooManyItemsListener a2;
        if (a2.getItems((World)(a = a.getLocation().getWorld())).size() < a2.limit) return;
        a2.getItems((World)a).forEach(Entity::remove);
        ProtectorSpigotLogger.info((String)"There were too many items on the grounds, we removed them!");
        Bukkit.broadcastMessage((String)new StringBuilder().insert((int)0, (String)a2.PREFIX).append((String)MessagesConfig.TOO_MANY_ITEMS$CLEARED).toString());
    }

    public TooManyItemsListener(int a) {
        TooManyItemsListener a2;
        a2.limit = a;
    }

    private /* synthetic */ List<Entity> getItems(World a2) {
        return a2.getEntities().stream().filter(a -> a instanceof Item).collect(Collectors.<T>toList());
    }
}

