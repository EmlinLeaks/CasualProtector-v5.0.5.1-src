/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.file.YamlConfiguration
 */
package xyz.yooniks.protector.bukkit.config.system;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.yooniks.protector.bukkit.config.system.Configuration;

public class Config
implements Configuration {
    private final Class<?> clazz;
    private final File file;

    @Override
    public synchronized void reload() {
        Config a;
        File file = a.checkFile((File)a.file);
        try {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
            a.parse(a.clazz, (YamlConfiguration)yamlConfiguration);
            yamlConfiguration.save((File)file);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    @Override
    public synchronized void save() {
        Config a;
        File file = a.checkFile((File)a.file);
        try {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
            a.parseSave(a.clazz, (YamlConfiguration)yamlConfiguration);
            yamlConfiguration.save((File)file);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public Config(File a, Class<?> a2) {
        Config a3;
        a3.file = a;
        a3.clazz = a2;
        a3.reload();
    }
}

