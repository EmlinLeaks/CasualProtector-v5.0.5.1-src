/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.reflect.StructureModifier
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit.listener.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;
import test.M;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.event.CrashTryEvent;
import xyz.yooniks.protector.bukkit.helper.NBTHelper;

public class ClientWindowClickListener
extends PacketAdapter
implements Listener {
    private final int sameWCLimit;
    private boolean checkLastPacket;
    private final Map<UUID, Map.Entry<Long, M>> lastPacket = new HashMap<UUID, Map.Entry<Long, M>>();
    private final Map<UUID, Map.Entry<Integer, Long>> editTimestamp = new HashMap<UUID, Map.Entry<Integer, Long>>();
    private final ConnectionCloser connectionCloser;

    public ClientWindowClickListener(ProtectorSpigot a, boolean a2, int a3) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, Arrays.asList(new PacketType[]{PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.SET_CREATIVE_SLOT}));
        ClientWindowClickListener a4;
        a4.connectionCloser = a.getConnectionCloser();
        a4.checkLastPacket = a2;
        a4.sameWCLimit = a3;
    }

    public void onPacketReceiving(PacketEvent a) {
        ClientWindowClickListener a2;
        UUID uUID;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        try {
            uUID = player.getUniqueId();
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return;
        }
        if (a2.connectionCloser.isClosing((UUID)uUID)) {
            a.setCancelled((boolean)true);
            return;
        }
        PacketContainer packetContainer = a.getPacket();
        ItemStack itemStack = (ItemStack)packetContainer.getItemModifier().readSafely((int)0);
        if (itemStack == null) return;
        if (itemStack.getType() == Material.AIR) {
            return;
        }
        if (packetContainer.getType() == PacketType.Play.Client.SET_CREATIVE_SLOT && player.getGameMode() != GameMode.CREATIVE) {
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)"Tried to click in creativeInventory without creative mode!");
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)player.getName()).append((String)" is trying to click in creative window (clientwindowcreativeaction) but he is not in creative gamemode! Cancelling=)").toString());
            Bukkit.getScheduler().runTask((Plugin)a2.plugin, () -> Bukkit.getServer().getPluginManager().callEvent((Event)new CrashTryEvent((OfflinePlayer)player)));
            return;
        }
        if (NBTHelper.isInvalid((Player)player, (ItemStack)itemStack)) {
            a.setCancelled((boolean)true);
            a2.connectionCloser.closeConnection((Player)player, (String)"Your item has invalid nbt data!");
            Bukkit.getScheduler().runTask((Plugin)a2.plugin, () -> Bukkit.getServer().getPluginManager().callEvent((Event)new CrashTryEvent((OfflinePlayer)player)));
            return;
        }
        if (!a2.checkLastPacket) return;
        try {
            if (packetContainer.getIntegers() == null) return;
            if (packetContainer.getIntegers().size() <= 0) return;
            if (!a2.lastPacket.containsKey((Object)uUID)) {
                if (packetContainer.getIntegers() == null) return;
                if (packetContainer.getIntegers().size() <= 0) return;
                a2.lastPacket.put((UUID)uUID, new AbstractMap.SimpleEntry<Long, M>(Long.valueOf((long)(System.currentTimeMillis() + 500L)), new M((ClientWindowClickListener)a2, (List)packetContainer.getIntegers().getValues(), (ItemStack)itemStack, (int)1)));
                return;
            }
            Map.Entry<Long, M> entry = a2.lastPacket.get((Object)uUID);
            M m2 = entry.getValue();
            int n = m2.getAttempt();
            if (entry.getKey().longValue() <= System.currentTimeMillis()) {
                a2.lastPacket.put((UUID)uUID, new AbstractMap.SimpleEntry<Long, M>(Long.valueOf((long)(System.currentTimeMillis() + 500L)), new M((ClientWindowClickListener)a2, (List)packetContainer.getIntegers().getValues(), (ItemStack)itemStack, (int)1)));
                return;
            }
            if (n > a2.sameWCLimit) {
                a.setCancelled((boolean)true);
                a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.TOO_MANY_SIMILAR_WC_PACKETS);
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" sent the same 50 packets (and the delay between these packets was <0.5 second)! The same integers and item. Cancelling event and disconnecting player.").toString());
                Bukkit.getScheduler().runTask((Plugin)a2.plugin, () -> Bukkit.getServer().getPluginManager().callEvent((Event)new CrashTryEvent((OfflinePlayer)player)));
                return;
            }
            if (packetContainer.getIntegers() == null) return;
            if (packetContainer.getIntegers().size() <= 0) return;
            a = new M((ClientWindowClickListener)a2, (List)packetContainer.getIntegers().getValues(), (ItemStack)itemStack, (int)(n + 1));
            if (!m2.equals((Object)a)) return;
            a2.lastPacket.put((UUID)uUID, new AbstractMap.SimpleEntry<Long, PacketEvent>(Long.valueOf((long)(System.currentTimeMillis() + 500L)), a));
            return;
        }
        catch (Exception exception) {
            a2.checkLastPacket = false;
            ProtectorSpigotLogger.warning((String)new StringBuilder().insert((int)0, (String)"Exception while putting lastPacket (packet.getIntegers()), preventing next exceptions, don't worry. ").append((String)exception.getMessage()).toString());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent a) {
        ClientWindowClickListener a2;
        a2.editTimestamp.remove((Object)a.getPlayer().getUniqueId());
        a2.lastPacket.remove((Object)a.getPlayer().getUniqueId());
    }
}

