/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.listener.normal.basic.CustomListener;

public class InvalidMessageListener
extends CustomListener {
    private final List<Character> charsToSkip;
    private final int cancelWhen;

    @EventHandler(ignoreCancelled=true)
    public void onChat(AsyncPlayerChatEvent a) {
        InvalidMessageListener a2;
        if (!a2.check((String)a.getMessage())) return;
        a.setCancelled((boolean)true);
        a.getPlayer().sendMessage((String)((Object)ChatColor.RED + MessagesConfig.BLOCK$INVALID_CHARS));
    }

    @EventHandler(ignoreCancelled=true)
    public void onChat(PlayerCommandPreprocessEvent a) {
        InvalidMessageListener a2;
        if (!a2.check((String)a.getMessage())) return;
        a.setCancelled((boolean)true);
        a.getPlayer().sendMessage((String)((Object)ChatColor.RED + MessagesConfig.BLOCK$INVALID_CHARS));
    }

    private /* synthetic */ boolean check(String a) {
        int n = 0;
        int n2 = 0;
        while (n2 < a.length()) {
            InvalidMessageListener a2;
            char c2 = a.charAt((int)n2);
            if (!a2.charsToSkip.contains((Object)Character.valueOf((char)c2)) && String.valueOf((char)c2).getBytes().length > 1 && ++n > a2.cancelWhen) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public InvalidMessageListener(List<Character> a, int a2) {
        InvalidMessageListener a3;
        a3.charsToSkip = a;
        a3.charsToSkip.add((Character)Character.valueOf((char)'\u00a7'));
        a3.cancelWhen = a2;
    }
}

