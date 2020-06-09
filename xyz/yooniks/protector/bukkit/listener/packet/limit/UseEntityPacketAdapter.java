/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet.limit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.listener.packet.limit.PacketChecker;
import xyz.yooniks.protector.bukkit.user.SpamType;

public class UseEntityPacketAdapter
extends PacketAdapter {
    private final PacketChecker checker;

    public void onPacketReceiving(PacketEvent a) {
        UseEntityPacketAdapter a2;
        a2.checker.checkThrough((SpamType)SpamType.USE_ENTITY, (PacketEvent)a);
    }

    public UseEntityPacketAdapter(Plugin a, PacketChecker a2) {
        super((Plugin)a, (PacketType[])new PacketType[]{PacketType.Play.Client.USE_ENTITY});
        UseEntityPacketAdapter a3;
        a3.checker = a2;
    }
}

