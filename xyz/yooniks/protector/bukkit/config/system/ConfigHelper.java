/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.config.system;

import java.io.File;
import xyz.yooniks.protector.bukkit.config.system.Config;

public final class ConfigHelper {
    public static Config create(File a, Class a2) {
        return new Config((File)a, a2);
    }

    private /* synthetic */ ConfigHelper() {
        ConfigHelper a;
    }
}

