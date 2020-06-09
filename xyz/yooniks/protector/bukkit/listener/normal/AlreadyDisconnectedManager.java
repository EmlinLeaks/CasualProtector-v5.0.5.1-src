/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AlreadyDisconnectedManager {
    private static AlreadyDisconnectedManager instance;
    private final Map<UUID, Long> disconnected = new HashMap<UUID, Long>();

    public void remove(UUID a) {
        AlreadyDisconnectedManager a2;
        a2.disconnected.remove((Object)a);
    }

    public AlreadyDisconnectedManager() {
        AlreadyDisconnectedManager a;
    }

    public static AlreadyDisconnectedManager getInstance() {
        if (instance != null) return instance;
        instance = new AlreadyDisconnectedManager();
        return instance;
    }

    public void addDisconnected(UUID a) {
        AlreadyDisconnectedManager a2;
        a2.disconnected.put((UUID)a, (Long)Long.valueOf((long)(System.currentTimeMillis() + 1000L)));
    }

    public boolean isDisconnected(UUID a) {
        AlreadyDisconnectedManager a2;
        if (!a2.disconnected.containsKey((Object)a)) return false;
        if (a2.disconnected.get((Object)a).longValue() <= System.currentTimeMillis()) return false;
        return true;
    }
}

