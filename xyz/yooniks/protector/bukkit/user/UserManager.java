/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import xyz.yooniks.protector.bukkit.user.User;

public class UserManager {
    private final Map<UUID, User> userMap = new HashMap<UUID, User>();
    private static UserManager instance;

    public User createUser(UUID a) {
        UserManager a2;
        User user = a2.userMap.get((Object)a);
        if (user != null) return user;
        user = new User();
        a2.userMap.put((UUID)a, (User)user);
        return user;
    }

    public User getOrNull(UUID a) {
        UserManager a2;
        return a2.userMap.get((Object)a);
    }

    public UserManager() {
        UserManager a;
        instance = a;
    }

    public static UserManager getInstance() {
        if (instance != null) return instance;
        return new UserManager();
    }
}

