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
 *  com.comphenix.protocol.wrappers.MinecraftKey
 *  com.comphenix.protocol.wrappers.nbt.NbtCompound
 *  com.comphenix.protocol.wrappers.nbt.NbtFactory
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.MinecraftKey;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;

public class DebugModeListener
extends PacketAdapter {
    private boolean sendMinecraftKeys = true;

    public void onPacketReceiving(PacketEvent a4) {
        DebugModeListener a3;
        Player player = a4.getPlayer();
        if (player == null) {
            return;
        }
        PacketContainer packetContainer = a4.getPacket();
        if (packetContainer.getType() != PacketType.Play.Client.WINDOW_CLICK) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)("(" + player.getName() + "), packetType: " + (Object)packetContainer.getType() + " info( "));
        stringBuilder.append((String)new StringBuilder().insert((int)0, (String)"cancelled: ").append((boolean)a4.isCancelled()).append((String)" ").toString());
        try {
            packetContainer.getItemModifier().getValues().stream().filter(Objects::nonNull).forEach(a2 -> {
                stringBuilder.append((String)("item: type: " + a2.getType().name() + ", meta: " + (a2.hasItemMeta() ? new StringBuilder().insert((int)0, (String)"name: ").append((String)(a2.getItemMeta().hasDisplayName() ? a2.getItemMeta().getDisplayName() : "null")).append((String)" ").append((String)(a2.getItemMeta().hasLore() ? Arrays.toString((Object[])a2.getItemMeta().getLore().toArray()) : " null")).toString() : "null")));
                a2 = (NbtCompound)NbtFactory.fromItemTag((ItemStack)a2);
                if (a2 == null) return;
                stringBuilder.append((String)new StringBuilder().insert((int)0, (String)"item: nbt keys size: ").append((int)a2.getKeys().size()).append((String)", values size: ").append((int)a2.getValue().size()).toString());
                stringBuilder.append((String)new StringBuilder().insert((int)0, (String)"| ").append((String)a2.toString()).append((String)"\n").append((String)a2.getKeys().toString()).append((String)"\n").append((String)a2.getValue().values().toString()).toString());
            });
        }
        catch (IllegalArgumentException a4) {
            // empty catch block
        }
        packetContainer.getStrings().getValues().forEach(a2 -> stringBuilder.append((String)(" string: " + a2)));
        packetContainer.getIntegers().getValues().forEach(a2 -> stringBuilder.append((String)(" integer: " + a2)));
        if (a3.sendMinecraftKeys) {
            try {
                packetContainer.getMinecraftKeys().getValues().forEach(a2 -> stringBuilder.append((String)("minecraftkey full key: " + a2.getFullKey())));
            }
            catch (Exception a4) {
                a3.sendMinecraftKeys = false;
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"[Debugger] PacketContainer#getMinecraftKeys() getting error, preventing next exceptions, don't worry. ").append((String)a4.getMessage()).toString());
            }
        }
        packetContainer.getDoubles().getValues().forEach(a2 -> stringBuilder.append((String)(" double: " + a2)));
        packetContainer.getBytes().getValues().forEach(a2 -> stringBuilder.append((String)(" byte: " + a2)));
        packetContainer.getByteArrays().getValues().forEach(a2 -> stringBuilder.append((String)(" bytes array: " + Arrays.toString((byte[])a2))));
        packetContainer.getIntegerArrays().getValues().forEach(a2 -> stringBuilder.append((String)(" integers array: " + Arrays.toString((int[])a2))));
        packetContainer.getStringArrays().getValues().forEach(a2 -> stringBuilder.append((String)(" strings array: " + Arrays.toString((Object[])a2))));
        stringBuilder.append((String)" )");
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"[DEBUGGER] ").append((String)stringBuilder.toString()).toString());
    }

    public DebugModeListener(ProtectorSpigot a) {
        super((Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, (Iterable)PacketType.Play.Client.getInstance().values());
        DebugModeListener a2;
    }
}

