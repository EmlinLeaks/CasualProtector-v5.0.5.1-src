/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.reflect.StructureModifier
 *  com.comphenix.protocol.wrappers.MinecraftKey
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet.limit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.MinecraftKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.listener.packet.limit.PacketChecker;
import xyz.yooniks.protector.bukkit.user.SpamType;

public class CustomPayloadOtherPacketAdapter
extends PacketAdapter {
    private final PacketChecker checker;

    public void onPacketReceiving(PacketEvent a) {
        CustomPayloadOtherPacketAdapter a2;
        PacketContainer packetContainer = a.getPacket();
        String string = packetContainer.getStrings().size() <= 0 ? ((MinecraftKey)packetContainer.getMinecraftKeys().readSafely((int)0)).getFullKey() : (String)packetContainer.getStrings().readSafely((int)0);
        if (!string.equalsIgnoreCase((String)"MC|ItemName")) {
            if (!string.equalsIgnoreCase((String)"MC|AdvCdm")) return;
        }
        a2.checker.checkThrough((SpamType)SpamType.PAYLOAD_ITEM_ADV, (PacketEvent)a);
        if (!string.equalsIgnoreCase((String)"MC|ItemName")) return;
        packetContainer = a.getPlayer();
        if (packetContainer == null) {
            a.setCancelled((boolean)true);
            return;
        }
        if (packetContainer.getOpenInventory() == null) return;
        if (packetContainer.getOpenInventory().getType() == InventoryType.ANVIL) return;
        a.setCancelled((boolean)true);
        a2.checker.checkThrough((SpamType)SpamType.PAYLOAD_ITEM_ADV_WRONG, (PacketEvent)a);
    }

    public CustomPayloadOtherPacketAdapter(Plugin a, PacketChecker a2) {
        super((Plugin)a, (PacketType[])new PacketType[]{PacketType.Play.Client.CUSTOM_PAYLOAD});
        CustomPayloadOtherPacketAdapter a3;
        a3.checker = a2;
    }
}

