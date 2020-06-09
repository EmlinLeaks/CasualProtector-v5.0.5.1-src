/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Block
 */
package xyz.yooniks.protector.bukkit.redstone;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public final class RedstoneHelper {
    private /* synthetic */ RedstoneHelper() {
        RedstoneHelper a;
    }

    public static Location teleportToRedstone(Chunk a) {
        if (!a.isLoaded()) {
            return ((World)Bukkit.getWorlds().get((int)0)).getSpawnLocation();
        }
        int n = 0;
        int n2 = 0;
        int n3 = n;
        block0 : while (n3 < n + 16) {
            int n4 = n2;
            do {
                if (n4 < n2 + 16) {
                } else {
                    ++n3;
                    continue block0;
                }
                for (int i = 0; i < 255; ++i) {
                    Block block = a.getBlock((int)n3, (int)i, (int)n4);
                    if (block == null) continue;
                    Material material = block.getType();
                    if (material == Material.REDSTONE) return block.getLocation();
                    if (material != Material.REDSTONE_WIRE) continue;
                    return block.getLocation();
                }
                ++n4;
            } while (true);
            break;
        }
        return a.getBlock((int)10, (int)80, (int)10).getLocation();
    }

    public static int countRedstone(Chunk a) {
        int n = 0;
        if (!a.isLoaded()) {
            return -1;
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = n2;
        block0 : while (n4 < n2 + 16) {
            int n5 = n3;
            do {
                if (n5 < n3 + 16) {
                } else {
                    ++n4;
                    continue block0;
                }
                for (int i = 0; i < 255; ++i) {
                    Block block = a.getBlock((int)n4, (int)i, (int)n5);
                    if (block == null || (block = block.getType()) != Material.REDSTONE && block != Material.REDSTONE_WIRE) continue;
                    ++n;
                }
                ++n5;
            } while (true);
            break;
        }
        return n;
    }
}

