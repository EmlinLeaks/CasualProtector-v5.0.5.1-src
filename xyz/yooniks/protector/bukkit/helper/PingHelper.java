/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

public final class PingHelper {
    private static Method handleMethod;
    private static Field pingField;

    private /* synthetic */ PingHelper() {
        PingHelper a;
    }

    public static int getPing(Player a) {
        try {
            int n;
            if (handleMethod == null) {
                handleMethod = a.getClass().getDeclaredMethod((String)"getHandle", new Class[0]);
                handleMethod.setAccessible((boolean)true);
            }
            Object object = handleMethod.invoke((Object)a, (Object[])new Object[0]);
            if (pingField == null) {
                pingField = object.getClass().getDeclaredField((String)"ping");
                pingField.setAccessible((boolean)true);
            }
            if ((n = pingField.getInt((Object)object)) <= 0) return 0;
            return n;
        }
        catch (Exception exception) {
            return 1;
        }
    }
}

