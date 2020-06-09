/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.ProtocolManager
 *  com.comphenix.protocol.events.PacketListener
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.plugin.Plugin
 */
package xyz.yooniks.protector.bukkit.listener.packet.limit.initializers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import xyz.yooniks.protector.bukkit.listener.packet.limit.AbilitiesPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.ArmAnimationPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.BlockDigPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.BlockPlacePacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.ClientAutoRecipePacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.ClientCloseWindowPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.ClientRecipeDisplayedPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.ClientResourcePackPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.ClientSettingsPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.ClientTransactionPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.CustomPayloadOtherPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.EntityActionPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.KeepAlivePacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.PacketChecker;
import xyz.yooniks.protector.bukkit.listener.packet.limit.PositionPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.SetCreativeSlotPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.TabCompletePacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.UpdateSignPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.UseEntityPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.UseItemPacketAdapter;
import xyz.yooniks.protector.bukkit.listener.packet.limit.WindowClickPacketAdapter;

public class LimiterInitializer {
    public LimiterInitializer(Plugin a) {
        LimiterInitializer a2;
        ProtocolManager protocolManager;
        PacketChecker packetChecker = new PacketChecker();
        packetChecker.load((FileConfiguration)a.getConfig());
        ProtocolManager protocolManager2 = protocolManager = ProtocolLibrary.getProtocolManager();
        ProtocolManager protocolManager3 = protocolManager;
        protocolManager3.removePacketListeners((Plugin)a);
        protocolManager2.addPacketListener((PacketListener)new AbilitiesPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager3.addPacketListener((PacketListener)new ArmAnimationPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager2.addPacketListener((PacketListener)new BlockDigPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager3.addPacketListener((PacketListener)new BlockPlacePacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager2.addPacketListener((PacketListener)new EntityActionPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager3.addPacketListener((PacketListener)new KeepAlivePacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager2.addPacketListener((PacketListener)new PositionPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager3.addPacketListener((PacketListener)new SetCreativeSlotPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager2.addPacketListener((PacketListener)new TabCompletePacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager.addPacketListener((PacketListener)new UpdateSignPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager.addPacketListener((PacketListener)new UseEntityPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        try {
            protocolManager.addPacketListener((PacketListener)new UseItemPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        }
        catch (Exception exception) {
            // empty catch block
        }
        protocolManager.addPacketListener((PacketListener)new ClientTransactionPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager.addPacketListener((PacketListener)new ClientSettingsPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager.addPacketListener((PacketListener)new ClientCloseWindowPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager.addPacketListener((PacketListener)new ClientResourcePackPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager.addPacketListener((PacketListener)new WindowClickPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        protocolManager.addPacketListener((PacketListener)new CustomPayloadOtherPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
        try {
            protocolManager.addPacketListener((PacketListener)new ClientAutoRecipePacketAdapter((Plugin)a, (PacketChecker)packetChecker));
            protocolManager.addPacketListener((PacketListener)new ClientRecipeDisplayedPacketAdapter((Plugin)a, (PacketChecker)packetChecker));
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

