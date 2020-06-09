/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockRedstoneEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.redstone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.redstone.DangerousChunk;
import xyz.yooniks.protector.bukkit.redstone.RedstoneManager;

public class RedstoneListener
implements Listener {
    private final Plugin plugin = ProtectorSpigot.getInstance();
    private final RedstoneManager redstoneManager = RedstoneManager.getInstance();
    private final Map<UUID, Long> leverTimestamp = new HashMap<UUID, Long>();
    private final int maxPerWorld;
    private final int maxPerChunk;

    @EventHandler(ignoreCancelled=true)
    public void onBreak(BlockBreakEvent a) {
        RedstoneListener a2;
        Material material = a.getBlock().getType();
        if (material != Material.REDSTONE) {
            if (material != Material.REDSTONE_WIRE) return;
        }
        a = a2.redstoneManager.dangerous((Chunk)a.getBlock().getChunk());
        a2.redstoneManager.addChunkToCheck((DangerousChunk)a);
    }

    public RedstoneListener(int a, int a2) {
        RedstoneListener a3;
        a3.maxPerChunk = a;
        a3.maxPerWorld = a2;
    }

    @EventHandler(ignoreCancelled=true)
    public void onLeverUse(PlayerInteractEvent a) {
        RedstoneListener a2;
        if (a.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Object object = a.getClickedBlock();
        if (object == null) return;
        if (object.getType() != Material.LEVER) {
            return;
        }
        object = a.getPlayer().getUniqueId();
        if (a2.leverTimestamp.containsKey((Object)object) && a2.leverTimestamp.get((Object)object).longValue() > System.currentTimeMillis()) {
            a.setCancelled((boolean)true);
            return;
        }
        a2.leverTimestamp.put((UUID)object, (Long)Long.valueOf((long)(System.currentTimeMillis() + 1500L)));
    }

    @EventHandler
    public void onRedstone(BlockRedstoneEvent a) {
        RedstoneListener a2;
        int n = a.getNewCurrent();
        Object object = a.getBlock();
        if (a.getOldCurrent() != 0) {
            if (n != 0) return;
            object = a2.redstoneManager.dangerous((Chunk)a.getBlock().getChunk());
            a2.redstoneManager.addChunkToCheck((DangerousChunk)object);
            return;
        }
        object = a2.redstoneManager.dangerous((Chunk)a.getBlock().getChunk());
        boolean bl = false;
        if (!(((DangerousChunk)object).getRedstones() <= a2.maxPerChunk && a2.redstoneManager.getDangerousChunks().stream().mapToInt(DangerousChunk::getRedstones).sum() <= a2.maxPerWorld || ((DangerousChunk)object).isAllowed())) {
            a.setNewCurrent((int)0);
            bl = true;
        }
        ((DangerousChunk)object).setLimit((boolean)bl);
        a2.redstoneManager.addChunkToCheck((DangerousChunk)object);
    }
}

