/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.Chunk
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.redstone;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.redstone.DangerousChunk;
import xyz.yooniks.protector.bukkit.redstone.RedstoneHelper;
import xyz.yooniks.protector.bukkit.redstone.RedstoneManager;

public class RedstoneBrowser
implements Runnable {
    private final RedstoneManager redstoneManager;
    private long lastBroadcast = 0L;

    public RedstoneBrowser(RedstoneManager a) {
        RedstoneBrowser a2;
        a2.redstoneManager = a;
    }

    @Override
    public void run() {
        RedstoneBrowser a2;
        Iterator<DangerousChunk> iterator = a2.redstoneManager.getChunksToCheck().iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            int n = RedstoneHelper.countRedstone((Chunk)((DangerousChunk)object).getChunk());
            ((DangerousChunk)object).setRedstones((int)n);
            a2.redstoneManager.removeChunkToCheck((DangerousChunk)object);
            if (!((DangerousChunk)object).isLimit()) {
                return;
            }
            if (a2.lastBroadcast > System.currentTimeMillis()) {
                return;
            }
            Object object2 = RedstoneHelper.teleportToRedstone((Chunk)((DangerousChunk)object).getChunk());
            object2 = (int)object2.getX() + ":" + (int)object2.getY() + ":" + (int)object2.getZ();
            a2.lastBroadcast = System.currentTimeMillis() + 10000L;
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Cancelled redstone actions! Redstones per chunk: ").append((int)n).append((String)", chunk: ").append((String)((DangerousChunk)object).getChunk().toString()).append((String)", current block location: ").append((String)object2).toString());
            if (!MessagesConfig.REDSTONE$CANCELLED_ADMINS_MESSAGE_ENABLED) continue;
            object = MessageHelper.colored((String)MessagesConfig.REDSTONE$CANCELLED_ADMINS_MESSAGE);
            object = StringUtils.replace((String)object, (String)"{REDSTONES}", (String)String.valueOf((int)n));
            object = StringUtils.replace((String)object, (String)"{LOCATION}", (String)object2);
            Bukkit.getOnlinePlayers().stream().filter(a -> a.hasPermission((String)MessagesConfig.REDSTONE$CANCELLED_ADMINS_PERMISSION)).forEach(arg_0 -> RedstoneBrowser.lambda$run$1((String)object, arg_0));
        }
    }

    private static /* synthetic */ void lambda$run$1(String a, Player a2) {
        a2.sendMessage((String)a);
    }
}

