/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 */
package xyz.yooniks.protector.bukkit.redstone;

import org.bukkit.Chunk;

public class DangerousChunk {
    private boolean allowed;
    private final Chunk chunk;
    private boolean limit;
    private int redstones;

    public int getRedstones() {
        DangerousChunk a;
        return a.redstones;
    }

    public void setLimit(boolean a) {
        a.limit = a;
    }

    public DangerousChunk(Chunk a) {
        DangerousChunk a2;
        a2.chunk = a;
    }

    public boolean isAllowed() {
        DangerousChunk a;
        return a.allowed;
    }

    public Chunk getChunk() {
        DangerousChunk a;
        return a.chunk;
    }

    public void setAllowed(boolean a) {
        a.allowed = a;
    }

    public void setRedstones(int a) {
        a.redstones = a;
    }

    public boolean isLimit() {
        DangerousChunk a;
        return a.limit;
    }
}

