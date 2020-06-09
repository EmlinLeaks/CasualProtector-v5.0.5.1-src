/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.config.system;

import xyz.yooniks.protector.bukkit.config.system.Configuration;

public abstract class ConfigExtender {
    private static Configuration configuration;

    public ConfigExtender(Configuration a) {
        ConfigExtender a2;
        configuration = a;
    }

    public static void reloadConfig() {
        configuration.reload();
    }

    public static void saveConfig() {
        configuration.save();
    }

    public static void setConfiguration(Configuration a) {
        configuration = a;
    }
}

