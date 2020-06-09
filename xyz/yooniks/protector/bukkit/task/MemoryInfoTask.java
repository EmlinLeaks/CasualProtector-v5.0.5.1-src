/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.task;

import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MemoryHelper;

public class MemoryInfoTask
implements Runnable {
    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }
        String string = ChatColor.translateAlternateColorCodes((char)'&', (String)MessagesConfig.MEMORY_INFO$MESSAGE);
        string = StringUtils.replace((String)string, (String)"{USED-RAM}", (String)String.valueOf((int)((int)MemoryHelper.usedMemory())));
        string = StringUtils.replace((String)string, (String)"{MAX-RAM}", (String)String.valueOf((double)MemoryHelper.maxMemory()));
        string = StringUtils.replace((String)string, (String)"{TPS}", (String)MemoryHelper.getTPS((int)0));
        Iterator<E> iterator = Bukkit.getOnlinePlayers().iterator();
        while (iterator.hasNext()) {
            Player player = (Player)iterator.next();
            if (!player.isOp()) continue;
            player.sendMessage((String)string);
        }
    }

    public MemoryInfoTask() {
        MemoryInfoTask a;
    }
}

