/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package xyz.yooniks.protector.bukkit.helper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import org.bukkit.Bukkit;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;

public final class MemoryHelper {
    private static Field TPS_FIELD;
    private static String VERSION;
    private static Object SERVER_INSTANCE;
    private static final DecimalFormat FORMAT;

    public static String getTPS(int a) {
        try {
            double[] arrd = (double[])TPS_FIELD.get((Object)SERVER_INSTANCE);
            return FORMAT.format((double)arrd[a]);
        }
        catch (IllegalAccessException illegalAccessException) {
            return "-1";
        }
    }

    private static /* synthetic */ double maxMemory(Runtime a) {
        return (double)a.maxMemory() / 1048576.0;
    }

    private /* synthetic */ MemoryHelper() {
        MemoryHelper a;
    }

    private static /* synthetic */ Class<?> getNMSClass(String a) {
        try {
            return Class.forName((String)new StringBuilder().insert((int)0, (String)"net.minecraft.server.").append((String)VERSION).append((String)".").append((String)a).toString());
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new RuntimeException((Throwable)classNotFoundException);
        }
    }

    public static double usedMemory() {
        return MemoryHelper.usedMemory((Runtime)Runtime.getRuntime());
    }

    private static /* synthetic */ double usedMemory(Runtime a) {
        long l = a.totalMemory();
        long l2 = a.freeMemory();
        return (double)(l - l2) / 1048576.0;
    }

    public static double maxMemory() {
        return MemoryHelper.maxMemory((Runtime)Runtime.getRuntime());
    }

    public static {
        VERSION = MemoryHelper.version();
        FORMAT = new DecimalFormat((String)"##.##");
        if (VERSION.equals((Object)"none")) {
            ProtectorSpigotLogger.warning((String)"Couldn't get the nms version! Are you using custom spigot? Getting tps won't work.");
            return;
        }
        try {
            SERVER_INSTANCE = MemoryHelper.getNMSClass((String)"MinecraftServer").getMethod((String)"getServer", new Class[0]).invoke(null, (Object[])new Object[0]);
            TPS_FIELD = SERVER_INSTANCE.getClass().getField((String)"recentTps");
            return;
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException exception) {
            ProtectorSpigotLogger.warning((String)new StringBuilder().insert((int)0, (String)"Could not initialize serverInstance and tpsField in commandExecutor: ").append((String)exception.getMessage()).toString());
            return;
        }
    }

    private static /* synthetic */ String version() {
        try {
            String string = Bukkit.getServer().getClass().getPackage().getName();
            return string.substring((int)(string.lastIndexOf((int)46) + 1));
        }
        catch (Exception exception) {
            return "none";
        }
    }
}

