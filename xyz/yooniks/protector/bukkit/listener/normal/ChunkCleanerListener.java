/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.wrappers.nbt.NbtCompound
 *  com.comphenix.protocol.wrappers.nbt.NbtFactory
 *  org.bukkit.Chunk
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockState
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Item
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.ChunkLoadEvent
 *  org.bukkit.event.world.WorldLoadEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.material.MaterialData
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.NBTHelper;

public class ChunkCleanerListener
implements Listener {
    private MaterialData data = new MaterialData((Material)Material.AIR);
    private final Logger logger;

    private /* synthetic */ void cleanChunk(Chunk a3) {
        if (a3.getEntities().length > MessagesConfig.MAX_ENTITIES_ON_CHUNK) {
            Arrays.stream(a3.getEntities()).forEach(Entity::remove);
        }
        Object object = new ArrayList<E>();
        Arrays.stream(a3.getTileEntities()).filter(a -> {
            if (a == null) return false;
            if (a.getType() == Material.AIR) return false;
            if (a.getBlock() == null) return false;
            return true;
        }).forEach(a2 -> object.add(a2.getBlock()));
        object = object.iterator();
        do {
            ChunkCleanerListener a4;
            if (!object.hasNext()) {
                Arrays.stream(a3.getEntities()).filter(a -> {
                    if (!(a instanceof Item)) return false;
                    if (((Item)a).getItemStack() == null) return false;
                    if (((Item)a).getItemStack().getType().name().contains((CharSequence)"SIGN")) return false;
                    return true;
                }).map(a -> (Item)a).filter(a -> NBTHelper.isInvalid(null, (ItemStack)a.getItemStack())).forEach(Entity::remove);
                return;
            }
            Block block = (Block)object.next();
            try {
                NbtCompound nbtCompound = NbtFactory.readBlockState((Block)block);
                if (nbtCompound == null || !NBTHelper.checkSkull(null, (NbtCompound)nbtCompound)) continue;
                block.setType((Material)Material.AIR);
                if (block.getState() == null || a4.data == null) continue;
                block.getState().setData((MaterialData)a4.data);
            }
            catch (Exception exception) {
                a4.logger.warning((String)new StringBuilder().insert((int)0, (String)"Failed to read chunk data, please try to update ProtocolLib or contact with plugin author. ").append((String)exception.getMessage()).toString());
                continue;
            }
            break;
        } while (true);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent a) {
        ChunkCleanerListener a2;
        a2.cleanChunk((Chunk)a.getChunk());
    }

    public ChunkCleanerListener(Logger a) {
        ChunkCleanerListener a2;
        a2.logger = a;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent a) {
        a = a.getWorld().getLoadedChunks();
        int n = a.length;
        int n2 = 0;
        while (n2 < n) {
            ChunkCleanerListener a2;
            Chunk chunk = a[n2];
            a2.cleanChunk((Chunk)chunk);
            ++n2;
        }
    }
}

