/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory1_14Listeners;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventoryAPI;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventoryOlderListeners;

public class PhasmatosBukkitInventoryAPI
implements PhasmatosInventoryAPI {
    private final List<PhasmatosInventory> inventories = new ArrayList<PhasmatosInventory>();

    public PhasmatosBukkitInventoryAPI() {
        PhasmatosBukkitInventoryAPI a;
    }

    @Override
    public PhasmatosInventory findByTitle(String a) {
        PhasmatosBukkitInventoryAPI a3;
        return (PhasmatosInventory)a3.inventories.stream().filter(a2 -> a2.getTitle().equalsIgnoreCase((String)a)).findFirst().orElse(null);
    }

    @Override
    public PhasmatosInventory findByTitleAndSize(String a, int a2) {
        PhasmatosBukkitInventoryAPI a4;
        return (PhasmatosInventory)a4.inventories.stream().filter(a3 -> {
            if (!a3.getTitle().equalsIgnoreCase((String)a)) return false;
            if (a3.getSize() != a2) return false;
            return true;
        }).findFirst().orElse(null);
    }

    public void register(Plugin a) {
        PhasmatosBukkitInventoryAPI a2;
        PluginManager pluginManager = a.getServer().getPluginManager();
        if (!a.getServer().getVersion().contains((CharSequence)"1.14") && !a.getServer().getVersion().contains((CharSequence)"1.15")) {
            pluginManager.registerEvents((Listener)new PhasmatosInventoryOlderListeners((PhasmatosInventoryAPI)a2), (Plugin)a);
            return;
        }
        pluginManager.registerEvents((Listener)new PhasmatosInventory1_14Listeners((PhasmatosInventoryAPI)a2), (Plugin)a);
    }

    @Override
    public void addInventory(PhasmatosInventory a) {
        PhasmatosBukkitInventoryAPI a2;
        a2.inventories.add((PhasmatosInventory)a);
    }

    @Override
    public List<PhasmatosInventory> getInventories() {
        PhasmatosBukkitInventoryAPI a;
        return new ArrayList<PhasmatosInventory>(a.inventories);
    }
}

