/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.Server
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit.redstone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosBukkitInventoryAPI;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosInventory;
import xyz.yooniks.protector.bukkit.redstone.DangerousChunk;
import xyz.yooniks.protector.bukkit.redstone.RedstoneBrowser;
import xyz.yooniks.protector.bukkit.redstone.RedstoneCommand;
import xyz.yooniks.protector.bukkit.redstone.RedstoneInventory;
import xyz.yooniks.protector.bukkit.redstone.RedstoneListener;

public class RedstoneManager {
    private static RedstoneManager instance;
    private final List<DangerousChunk> chunksToCheck = new CopyOnWriteArrayList<DangerousChunk>();
    private final Map<Chunk, DangerousChunk> dangerousChunks = new HashMap<Chunk, DangerousChunk>();

    public List<DangerousChunk> getChunksToCheck() {
        RedstoneManager a;
        return a.chunksToCheck;
    }

    public void init() {
        RedstoneManager a;
        ProtectorSpigot protectorSpigot = ProtectorSpigot.getInstance();
        PhasmatosBukkitInventoryAPI phasmatosBukkitInventoryAPI = new PhasmatosBukkitInventoryAPI();
        RedstoneInventory redstoneInventory = new RedstoneInventory();
        phasmatosBukkitInventoryAPI.addInventory((PhasmatosInventory)redstoneInventory);
        phasmatosBukkitInventoryAPI.register((Plugin)protectorSpigot);
        phasmatosBukkitInventoryAPI = protectorSpigot.getConfig().getConfigurationSection((String)"redstone");
        boolean bl = phasmatosBukkitInventoryAPI.getBoolean((String)"enabled", (boolean)false);
        protectorSpigot.getCommand((String)"redstone").setExecutor((CommandExecutor)new RedstoneCommand((RedstoneInventory)redstoneInventory, (boolean)bl));
        if (!bl) {
            return;
        }
        protectorSpigot.getServer().getPluginManager().registerEvents((Listener)new RedstoneListener((int)phasmatosBukkitInventoryAPI.getInt((String)"limit-per-chunk", (int)65), (int)phasmatosBukkitInventoryAPI.getInt((String)"limit-per-world", (int)400)), (Plugin)protectorSpigot);
        protectorSpigot.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)protectorSpigot, (Runnable)new RedstoneBrowser((RedstoneManager)a), (long)60L, (long)20L);
    }

    public static RedstoneManager getInstance() {
        if (instance != null) return instance;
        return new RedstoneManager();
    }

    public void undangerous(Chunk a) {
        RedstoneManager a2;
        a2.dangerousChunks.remove((Object)a);
    }

    public List<DangerousChunk> sortedDangerousChunks() {
        RedstoneManager a3;
        ArrayList<DangerousChunk> arrayList = new ArrayList<DangerousChunk>(a3.dangerousChunks.values());
        arrayList.sort((a, a2) -> Integer.compare((int)a2.getRedstones(), (int)a.getRedstones()));
        return arrayList;
    }

    public DangerousChunk dangerous(Chunk a) {
        RedstoneManager a2;
        DangerousChunk dangerousChunk = a2.dangerousChunks.get((Object)a);
        if (dangerousChunk != null) return dangerousChunk;
        dangerousChunk = new DangerousChunk((Chunk)a);
        a2.dangerousChunks.put((Chunk)a, (DangerousChunk)dangerousChunk);
        return dangerousChunk;
    }

    public void removeChunkToCheck(DangerousChunk a) {
        RedstoneManager a2;
        a2.chunksToCheck.remove((Object)a);
    }

    public void addChunkToCheck(DangerousChunk a) {
        RedstoneManager a2;
        if (a2.chunksToCheck.contains((Object)a)) {
            return;
        }
        a2.chunksToCheck.add((DangerousChunk)a);
    }

    public List<DangerousChunk> getDangerousChunks() {
        RedstoneManager a;
        return new ArrayList<DangerousChunk>(a.dangerousChunks.values());
    }

    public RedstoneManager() {
        RedstoneManager a;
        instance = a;
    }
}

