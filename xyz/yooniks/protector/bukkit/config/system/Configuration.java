/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package xyz.yooniks.protector.bukkit.config.system;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.YamlConfiguration;

public interface Configuration {
    default public void parseSave(Class<?> a3, YamlConfiguration a2) {
        try {
            a3 = a3.getFields();
            int n = a3.length;
            int n2 = 0;
            while (n2 < n) {
                Object object = a3[n2];
                if (Modifier.isStatic((int)((Field)object).getModifiers()) && Modifier.isPublic((int)((Field)object).getModifiers())) {
                    Field field = object;
                    object = ((Field)object).getName().toLowerCase();
                    object = StringUtils.replace((String)object, (String)"$", (String)".");
                    object = StringUtils.replace((String)object, (String)"_", (String)"-");
                    Object object2 = field.get(null);
                    a2.set((String)object, (Object)object2);
                }
                ++n2;
            }
            return;
        }
        catch (Exception a3) {
            a3.printStackTrace();
        }
    }

    default public void parse(Class<?> a3, YamlConfiguration a2) {
        try {
            a3 = a3.getFields();
            int n = a3.length;
            int n2 = 0;
            while (n2 < n) {
                Object object = a3[n2];
                if (Modifier.isStatic((int)((Field)object).getModifiers()) && Modifier.isPublic((int)((Field)object).getModifiers())) {
                    Field field = object;
                    Field field2 = object;
                    object = ((Field)object).getName().toLowerCase();
                    object = StringUtils.replace((String)object, (String)"$", (String)".");
                    object = StringUtils.replace((String)object, (String)"_", (String)"-");
                    Object object2 = field2.get(null);
                    object2 = a2.get((String)object, (Object)object2);
                    a2.set((String)object, (Object)object2);
                    field.set(null, (Object)object2);
                }
                ++n2;
            }
            return;
        }
        catch (Exception a3) {
            a3.printStackTrace();
        }
    }

    default public File checkFile(File a) {
        if (a.exists()) return a;
        a.getParentFile().mkdirs();
        try {
            a.createNewFile();
            return a;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return a;
    }

    public void reload();

    public void save();
}

