/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.PendingConnection
 *  net.md_5.bungee.api.event.PreLoginEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener.normal;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class NamesLengthCheckListener
implements Listener {
    private int lastNameLength;
    private final int sens;
    private final String kickMessage;
    private int count;
    private long lastSimilarNameLoginTime;

    public NamesLengthCheckListener(String a, int a2) {
        NamesLengthCheckListener a3;
        a3.kickMessage = a;
        a3.sens = a2;
    }

    @EventHandler
    public void onConnect(PreLoginEvent a) {
        NamesLengthCheckListener a2;
        int n = a.getConnection().getName().length();
        if (a2.lastNameLength != n) {
            a2.count = 0;
            a2.lastNameLength = n;
            return;
        }
        if (a2.lastSimilarNameLoginTime > System.currentTimeMillis()) {
            a.setCancelled((boolean)true);
            a.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)a2.kickMessage)});
            return;
        }
        ++a2.count;
        if (a2.count < a2.sens) return;
        a2.lastSimilarNameLoginTime = System.currentTimeMillis() + 10000L;
        a2.count = 0;
        a.setCancelled((boolean)true);
        a.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)a2.kickMessage)});
    }
}

