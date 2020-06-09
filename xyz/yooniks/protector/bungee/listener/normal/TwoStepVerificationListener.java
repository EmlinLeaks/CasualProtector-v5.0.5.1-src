/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.PendingConnection
 *  net.md_5.bungee.api.event.LoginEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener.normal;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TwoStepVerificationListener
implements Listener {
    private final Set<UUID> verified = new HashSet<UUID>();
    private final String kickMessage;

    @EventHandler(priority=-64)
    public void onConnect(LoginEvent a) {
        TwoStepVerificationListener a2;
        UUID uUID = a.getConnection().getUniqueId();
        if (a2.verified.contains((Object)uUID)) return;
        a2.verified.add((UUID)uUID);
        a.setCancelled((boolean)true);
        a.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)a2.kickMessage)});
    }

    public TwoStepVerificationListener(String a) {
        TwoStepVerificationListener a2;
        a2.kickMessage = a;
    }
}

