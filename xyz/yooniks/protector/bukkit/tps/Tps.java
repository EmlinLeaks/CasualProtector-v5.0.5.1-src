/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit.tps;

import java.text.DecimalFormat;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Tps
implements Runnable {
    public static long[] TICKS;
    public static long LAST_TICK;
    public static int TICK_COUNT;
    public static final DecimalFormat FORMAT;

    @Override
    public void run() {
        Tps.TICKS[Tps.TICK_COUNT % Tps.TICKS.length] = System.currentTimeMillis();
        ++TICK_COUNT;
    }

    public static double getTPS(int a) {
        if (TICK_COUNT < a) {
            return 20.0;
        }
        int n = (TICK_COUNT - 1 - a) % TICKS.length;
        long l = System.currentTimeMillis() - TICKS[n];
        return (double)a / ((double)l / 1000.0);
    }

    public static String tpsToString(double a) {
        return FORMAT.format((double)a);
    }

    public static double getTPS() {
        return Tps.getTPS((int)100);
    }

    public static {
        FORMAT = new DecimalFormat((String)"##.##");
        TICK_COUNT = 0;
        TICKS = new long[600];
        LAST_TICK = 0L;
    }

    public Tps() {
        Tps a;
    }

    public static void start(Plugin a) {
        a.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)a, (Runnable)new Tps(), (long)100L, (long)1L);
    }

    public static long getElapsed(int a) {
        int n = TICKS.length;
        long l = TICKS[a % TICKS.length];
        return System.currentTimeMillis() - l;
    }
}

