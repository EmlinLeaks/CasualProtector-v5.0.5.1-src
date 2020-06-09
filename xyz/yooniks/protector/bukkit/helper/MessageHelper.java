/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package xyz.yooniks.protector.bukkit.helper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.ChatColor;

public final class MessageHelper {
    public static String colored(String a) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)a);
    }

    private /* synthetic */ MessageHelper() {
        MessageHelper a;
    }

    public static List<String> colored(List<String> a) {
        return a.stream().map(MessageHelper::colored).collect(Collectors.<T>toList());
    }
}

