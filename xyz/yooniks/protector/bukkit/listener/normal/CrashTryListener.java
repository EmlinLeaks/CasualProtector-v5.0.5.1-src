/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.List;
import java.util.function.Consumer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.event.CrashTryEvent;

public class CrashTryListener
implements Listener {
    private final Plugin plugin = ProtectorSpigot.getInstance();

    private static /* synthetic */ void lambda$onCrash$1(String a) {
        try {
            MessagesConfig.EXPLOIT$COMMANDS_ON_TRY.forEach(a2 -> Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)StringUtils.replace((String)a2, (String)"%name%", (String)a)));
            return;
        }
        catch (Exception exception) {
            ProtectorSpigotLogger.warning((String)new StringBuilder().insert((int)0, (String)"CrashTryListener | ").append((String)exception.getMessage()).toString());
            return;
        }
    }

    @EventHandler
    public void onCrash(CrashTryEvent a) {
        CrashTryListener a2;
        a = a.getPlayer().getName();
        Bukkit.getScheduler().runTask((Plugin)a2.plugin, () -> CrashTryListener.lambda$onCrash$1((String)a));
    }

    public CrashTryListener() {
        CrashTryListener a;
    }
}

