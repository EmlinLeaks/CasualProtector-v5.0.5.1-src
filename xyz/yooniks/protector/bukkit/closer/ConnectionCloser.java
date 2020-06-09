/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.closer;

import java.util.UUID;
import org.bukkit.entity.Player;

public interface ConnectionCloser {
    public boolean isClosing(UUID var1);

    public void closeConnection(Player var1, String var2);
}

