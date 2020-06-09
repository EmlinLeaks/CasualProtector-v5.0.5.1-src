/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package xyz.yooniks.protector.bukkit.redstone;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.yooniks.protector.bukkit.api.inventory.ItemBuilder;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosClickableChangeableInventory;
import xyz.yooniks.protector.bukkit.api.inventory.PhasmatosClickableInventory;
import xyz.yooniks.protector.bukkit.redstone.DangerousChunk;
import xyz.yooniks.protector.bukkit.redstone.RedstoneHelper;
import xyz.yooniks.protector.bukkit.redstone.RedstoneManager;

public class RedstoneInventory
extends PhasmatosClickableChangeableInventory {
    private final RedstoneManager redstoneManager = RedstoneManager.getInstance();

    public RedstoneInventory() {
        super((String)((Object)ChatColor.YELLOW + "Dangerous chunks"), (int)45);
        RedstoneInventory a;
    }

    private /* synthetic */ String color(int a) {
        if (a > 55) {
            return "&4";
        }
        if (a > 35) {
            return "&c";
        }
        if (a <= 17) return "&a";
        return "&6";
    }

    private static /* synthetic */ void lambda$getItems$0(Location a, Player a2) {
        a2.teleport((Location)a);
    }

    private static /* synthetic */ void lambda$getItems$1(DangerousChunk a, Player a2) {
        if (!a.isAllowed()) {
            a.setAllowed((boolean)true);
            a2.sendMessage((String)((Object)ChatColor.YELLOW + "State of this dangerous chunk has been set to allowed."));
        } else {
            a.setAllowed((boolean)false);
            a2.sendMessage((String)((Object)ChatColor.YELLOW + "State of this dangerous chunk has been set to disallowed."));
        }
        a2.closeInventory();
    }

    @Override
    public Map<Integer, ItemStack> getItems() {
        RedstoneInventory a;
        a.itemActions.clear();
        a.rightClickActions.clear();
        HashMap<Integer, ItemStack> hashMap = new HashMap<Integer, ItemStack>();
        int n = 0;
        Iterator<DangerousChunk> iterator = a.redstoneManager.sortedDangerousChunks().iterator();
        while (iterator.hasNext()) {
            int n2;
            Object object = iterator.next();
            int n3 = ((DangerousChunk)object).getRedstones();
            Object object2 = RedstoneHelper.teleportToRedstone((Chunk)((DangerousChunk)object).getChunk());
            if ((n2 = n++) >= a.getSize()) continue;
            ItemStack itemStack = new ItemBuilder((Material)Material.REDSTONE).withName((String)new StringBuilder().insert((int)0, (String)a.color((int)n3)).append((String)"Dangerous chunk").toString()).withAmount((int)n3).withLore((String[])new String[]{"&cRedstones in this chunk: " + a.color((int)n3) + n3, "&cLeft click to &6teleport", "&cRight click to &6allow use redstones on this chunk when limit exceed", new StringBuilder().insert((int)0, (String)"&cAlready allowed: ").append((String)(((DangerousChunk)object).isAllowed() ? "&ayes" : "&cno")).toString()}).build();
            object2 = arg_0 -> RedstoneInventory.lambda$getItems$0((Location)object2, arg_0);
            object = arg_0 -> RedstoneInventory.lambda$getItems$1((DangerousChunk)object, arg_0);
            a.addItemAction((int)n2, (Consumer<Player>)object2);
            a.addRightClickAction((int)n2, (Consumer<Player>)object);
            hashMap.put((Integer)Integer.valueOf((int)n2), (ItemStack)itemStack);
        }
        return hashMap;
    }

    @Override
    public ItemStack updateItem(ItemStack a, int a2, Player a3) {
        return a;
    }

    @Override
    public void removeItemAction(int a) {
        RedstoneInventory a2;
        super.removeItemAction((int)a);
    }
}

