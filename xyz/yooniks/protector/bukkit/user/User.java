/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.user;

import java.util.HashMap;
import java.util.Map;
import xyz.yooniks.protector.bukkit.user.SpamType;

public class User {
    private double lastViolation = 0.0;
    private final Map<SpamType, Integer> violations = new HashMap<SpamType, Integer>();

    public int getViolations(SpamType a) {
        User a2;
        return a2.violations.getOrDefault((Object)((Object)a), (Integer)Integer.valueOf((int)0)).intValue();
    }

    public void clearViolations() {
        User a;
        a.violations.clear();
    }

    public User() {
        User a;
    }

    public void addViolation(SpamType a) {
        User a2;
        double d2 = (double)System.currentTimeMillis();
        if (d2 - a2.lastViolation >= 1000.0) {
            a2.lastViolation = d2;
            a2.violations.clear();
        }
        a2.violations.put((SpamType)a, (Integer)Integer.valueOf((int)(a2.violations.getOrDefault((Object)((Object)a), (Integer)Integer.valueOf((int)0)).intValue() + 1)));
    }
}

