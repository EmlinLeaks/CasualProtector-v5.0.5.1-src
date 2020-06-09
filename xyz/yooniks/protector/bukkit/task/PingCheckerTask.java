/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.task;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.helper.PingHelper;
import xyz.yooniks.protector.bukkit.tps.Tps;

public class PingCheckerTask
implements Runnable {
    private final boolean checkTps;
    private final int limit;

    @Override
    public void run() {
        PingCheckerTask a2;
        Bukkit.getOnlinePlayers().stream().filter(a -> {
            PingCheckerTask a2;
            if (a.hasPermission((String)MessagesConfig.ANTIBOT$TOO_BIG_PING_BYPASS_PERMISSION)) {
                return false;
            }
            if (PingHelper.getPing((Player)a) <= a2.limit) return false;
            if (!a2.checkTps) {
                return true;
            }
            if (!(Tps.getTPS() > 19.5)) return false;
            return true;
        }).forEach(a -> {
            PingCheckerTask a2;
            a.kickPlayer((String)MessageHelper.colored((String)StringUtils.replace((String)MessagesConfig.ANTIBOT$TOO_BIG_PING, (String)"{LIMIT}", (String)String.valueOf((int)a2.limit))));
        });
    }

    public PingCheckerTask(int a, boolean a2) {
        PingCheckerTask a3;
        a3.limit = a;
        a3.checkTps = a2;
    }
}

