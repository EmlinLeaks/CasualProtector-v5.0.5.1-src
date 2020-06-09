/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.whitelist;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class WhitelistManager {
    private final Set<String> blocked = new HashSet<String>();
    private final Set<String> whitelist = new HashSet<String>();

    public Set<String> getWhitelist() {
        WhitelistManager a;
        return new HashSet<String>(a.whitelist);
    }

    public void addWhitelist(String a) {
        WhitelistManager a2;
        a2.whitelist.add((String)a);
    }

    public boolean isBlocked(String a) {
        WhitelistManager a2;
        return a2.blocked.contains((Object)a);
    }

    public void addBlocked(String a) {
        WhitelistManager a2;
        a2.blocked.add((String)a);
    }

    public boolean isWhitelist(String a) {
        WhitelistManager a2;
        return a2.whitelist.contains((Object)a);
    }

    public WhitelistManager() {
        WhitelistManager a;
    }

    public Set<String> getBlocked() {
        WhitelistManager a;
        return new HashSet<String>(a.blocked);
    }
}

