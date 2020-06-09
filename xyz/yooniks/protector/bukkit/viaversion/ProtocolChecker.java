/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  us.myles.ViaVersion.api.Via
 */
package xyz.yooniks.protector.bukkit.viaversion;

import java.util.UUID;
import org.bukkit.entity.Player;
import us.myles.ViaVersion.api.Via;

public class ProtocolChecker {
    public ProtocolChecker() {
        ProtocolChecker a;
    }

    public static int getProtocol(Player a) {
        return Via.getAPI().getPlayerVersion((UUID)a.getUniqueId());
    }
}

