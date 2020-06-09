/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.inventory.ItemStack
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayer;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayerManager;

public class AntiRunCommandsListener
implements Listener {
    private final List<String> whitelistedCommands;
    private final MultiPlayerManager multiPlayerManager;
    private final List<String> blockedCommands;

    public AntiRunCommandsListener(List<String> a, List<String> a2, MultiPlayerManager a3) {
        AntiRunCommandsListener a4;
        a4.blockedCommands = a;
        a4.whitelistedCommands = a2;
        a4.multiPlayerManager = a3;
    }

    @EventHandler
    public void onCommandWhenHoldingBook(PlayerCommandPreprocessEvent a) {
        AntiRunCommandsListener a2;
        Player player = a.getPlayer();
        Object object = a2.multiPlayerManager.createMultiPlayer((Player)player).getItemInMainHand();
        if (object == null) return;
        if (!object.getType().name().contains((CharSequence)"BOOK")) {
            return;
        }
        object = a.getMessage().split((String)" ")[0].toLowerCase();
        if (a2.whitelistedCommands.contains((Object)object)) {
            return;
        }
        if (!a2.blockedCommands.contains((Object)"*")) {
            if (!a2.blockedCommands.contains((Object)object)) return;
        }
        a.setCancelled((boolean)true);
        player.sendMessage((String)MessageHelper.colored((String)MessagesConfig.ANTI_RUN_COMMANDS$WHEN_HOLDING_BOOK));
    }
}

