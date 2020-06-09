/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;

public final class ProtectorSpigotLogger {
    private static AtomicInteger lastLimitPrints;
    private static boolean printEverything;
    private static final Logger LOGGER;

    private /* synthetic */ ProtectorSpigotLogger() {
        ProtectorSpigotLogger a;
    }

    public static void warning(String a) {
        if (!printEverything) {
            return;
        }
        LOGGER.warning((String)a);
    }

    public static {
        LOGGER = ProtectorSpigot.getInstance().getLogger();
        printEverything = false;
        lastLimitPrints = new AtomicInteger();
    }

    public static void info(String a) {
        if (!printEverything) {
            return;
        }
        if (a.contains((CharSequence)"limit") && lastLimitPrints.getAndIncrement() > 5) {
            return;
        }
        LOGGER.info((String)a);
    }

    public static void setPrintEverything(boolean a) {
        printEverything = a;
    }

    public static void setup(Plugin a) {
        try {
            a.getDataFolder().mkdirs();
            File file = new File((File)a.getDataFolder(), (String)"logs.log");
            file.createNewFile();
            FileHandler fileHandler = new FileHandler((String)new StringBuilder().insert((int)0, (String)a.getDataFolder().getAbsolutePath()).append((String)"/logs.log").toString());
            LOGGER.addHandler((Handler)fileHandler);
            SimpleFormatter simpleFormatter = new SimpleFormatter();
            fileHandler.setFormatter((Formatter)simpleFormatter);
        }
        catch (IOException | SecurityException exception) {
            exception.printStackTrace();
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)a, () -> lastLimitPrints.set((int)0), (long)20L, (long)200L);
    }
}

