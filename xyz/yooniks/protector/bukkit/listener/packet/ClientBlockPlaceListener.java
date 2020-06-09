/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.comphenix.protocol.reflect.StructureModifier
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit.listener.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.reflect.StructureModifier;
import java.util.Collections;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.event.CrashTryEvent;
import xyz.yooniks.protector.bukkit.helper.NBTHelper;

public class ClientBlockPlaceListener
extends PacketAdapter {
    private final ConnectionCloser connectionCloser;
    private final boolean blockHopper;

    public void onPacketReceiving(PacketEvent a) {
        ClientBlockPlaceListener a2;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        if (a2.connectionCloser.isClosing((UUID)player.getUniqueId())) {
            a.setCancelled((boolean)true);
            return;
        }
        PacketContainer packetContainer = a.getPacket();
        if (packetContainer.getItemModifier().size() <= 0) {
            ProtectorSpigotLogger.warning((String)"Please change block-place-listener to false, items in blockplacelistener doesnt exist because you are running the latest spigot version! Automatically removed listener.");
            ProtocolLibrary.getProtocolManager().removePacketListener((PacketListener)a2);
            return;
        }
        if ((packetContainer = (ItemStack)packetContainer.getItemModifier().readSafely((int)0)) == null) return;
        if (packetContainer.getType() == Material.AIR) {
            return;
        }
        if (a2.blockHopper && (packetContainer.getType() == Material.HOPPER || packetContainer.getType() == Material.HOPPER_MINECART)) {
            a.setCancelled((boolean)true);
            player.getInventory().remove((ItemStack)packetContainer);
            a2.connectionCloser.closeConnection((Player)player, (String)"You cannot use hopper! This item is banned!");
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)"|| To disable kicking for using hopper please change \"block.hooper: true\" to false").toString());
            return;
        }
        if (!NBTHelper.isInvalid((Player)player, (ItemStack)packetContainer)) return;
        a.setCancelled((boolean)true);
        a2.connectionCloser.closeConnection((Player)player, (String)"Your item has invalid nbt data!");
        Bukkit.getScheduler().runTask((Plugin)a2.plugin, () -> Bukkit.getServer().getPluginManager().callEvent((Event)new CrashTryEvent((OfflinePlayer)player)));
    }

    public ClientBlockPlaceListener(ProtectorSpigot a, boolean a2) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, Collections.singletonList(PacketType.Play.Client.BLOCK_PLACE));
        ClientBlockPlaceListener a3;
        a3.connectionCloser = a.getConnectionCloser();
        a3.blockHopper = a2;
    }
}

