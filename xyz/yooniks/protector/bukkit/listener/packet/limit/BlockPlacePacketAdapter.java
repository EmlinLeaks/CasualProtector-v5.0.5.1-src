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
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet.limit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.listener.packet.limit.PacketChecker;
import xyz.yooniks.protector.bukkit.user.SpamType;

public class BlockPlacePacketAdapter
extends PacketAdapter {
    private final boolean viaVersion;
    private final PacketChecker checker;

    public void onPacketReceiving(PacketEvent a) {
        BlockPlacePacketAdapter a2;
        Player player = a.getPlayer();
        if (player == null) return;
        StructureModifier structureModifier = a.getPacket().getItemModifier();
        if (structureModifier == null) return;
        if (structureModifier.size() <= 0) return;
        if ((structureModifier = (ItemStack)structureModifier.read((int)0)) == null) return;
        if (structureModifier.getType().name().contains((CharSequence)"BOOK") && structureModifier.getType() != Material.BOOK && structureModifier.getType() != Material.ENCHANTED_BOOK) {
            a2.checker.checkThrough((SpamType)SpamType.BLOCK_PLACE_BOOKS, (PacketEvent)a);
        } else {
            a2.checker.checkThrough((SpamType)SpamType.BLOCK_PLACE, (PacketEvent)a);
        }
        player = player.getItemInHand();
        if (player == null) return;
        if (player.getType() == Material.AIR) return;
        if (!player.hasItemMeta()) return;
        if (player.getType() == structureModifier.getType()) return;
        a2.checker.checkThrough((SpamType)SpamType.BLOCK_PLACE_WRONG, (PacketEvent)a);
    }

    public BlockPlacePacketAdapter(Plugin a, PacketChecker a2) {
        super((Plugin)a, (PacketType[])new PacketType[]{PacketType.Play.Client.BLOCK_PLACE});
        BlockPlacePacketAdapter a3;
        boolean bl = a3.viaVersion = Bukkit.getPluginManager().isPluginEnabled((String)"ViaVersion") || Bukkit.getPluginManager().getPlugin((String)"ViaVersion") != null;
        if (a3.viaVersion) {
            ProtectorSpigotLogger.warning((String)"We detected that you use ViaVersion! This plugin is changing some packets (for example: BlockPlacePacket) and some players can bypass anticrash if you will not remove the plugin, just informing. I'm going to create my own fork (version) of ViaVersion soon to fix these problems, it's the problem of ViaVersion, not CProtector, because of ViaVersion, Cprotector doesn't get real packets.");
        }
        a3.checker = a2;
    }
}

