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
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryView
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet.limit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.listener.packet.limit.PacketChecker;
import xyz.yooniks.protector.bukkit.user.SpamType;

public class WindowClickPacketAdapter
extends PacketAdapter {
    private final PacketChecker checker;

    public WindowClickPacketAdapter(Plugin a, PacketChecker a2) {
        super((Plugin)a, (PacketType[])new PacketType[]{PacketType.Play.Client.WINDOW_CLICK});
        WindowClickPacketAdapter a3;
        a3.checker = a2;
    }

    public void onPacketReceiving(PacketEvent a) {
        WindowClickPacketAdapter a2;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        PacketContainer packetContainer = a.getPacket();
        StructureModifier structureModifier = packetContainer.getIntegers();
        if ((player = player.getOpenInventory()) == null) {
            a2.checker.checkThrough((SpamType)SpamType.WINDOW_CLICK_WRONG, (PacketEvent)a);
            return;
        }
        if (structureModifier.size() <= 1) {
            a2.checker.checkThrough((SpamType)SpamType.WINDOW_CLICK, (PacketEvent)a);
            return;
        }
        Inventory inventory = player.getTopInventory();
        Inventory inventory2 = player.getBottomInventory();
        int n = player.countSlots();
        int n2 = ((Integer)structureModifier.read((int)1)).intValue();
        if (inventory.getType() == InventoryType.CRAFTING && inventory2.getType() == InventoryType.PLAYER) {
            n += 4;
        }
        if (n2 >= n) {
            a2.checker.checkThrough((SpamType)SpamType.WINDOW_CLICK_WRONG, (PacketEvent)a);
            return;
        }
        if ((packetContainer = packetContainer.getItemModifier()).size() > 0 && n2 > 0) {
            player = player.getItem((int)n2);
            if ((packetContainer = (ItemStack)packetContainer.read((int)0)) == null) {
                a2.checker.checkThrough((SpamType)SpamType.WINDOW_CLICK, (PacketEvent)a);
                return;
            }
            Material material = packetContainer.getType();
            if (material != Material.AIR && material != Material.getMaterial((String)"LEGACY_AIR") && !packetContainer.isSimilar((ItemStack)player)) {
                a2.checker.checkThrough((SpamType)SpamType.WINDOW_CLICK_WRONG, (PacketEvent)a);
                return;
            }
            a2.checker.checkThrough((SpamType)SpamType.WINDOW_CLICK, (PacketEvent)a);
            return;
        }
        a2.checker.checkThrough((SpamType)SpamType.WINDOW_CLICK, (PacketEvent)a);
    }
}

