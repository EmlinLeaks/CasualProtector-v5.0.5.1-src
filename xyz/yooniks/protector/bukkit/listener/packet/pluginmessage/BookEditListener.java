/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.events.ListenerOptions
 *  com.comphenix.protocol.events.NetworkMarker
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.reflect.StructureModifier
 *  com.comphenix.protocol.wrappers.MinecraftKey
 *  io.netty.buffer.ByteBuf
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.plugin.PluginManager
 */
package xyz.yooniks.protector.bukkit.listener.packet.pluginmessage;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.NetworkMarker;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.MinecraftKey;
import io.netty.buffer.ByteBuf;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.event.CrashTryEvent;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.basic.CustomPluginMessageListener;
import xyz.yooniks.protector.bukkit.user.SpamType;
import xyz.yooniks.protector.bukkit.user.User;
import xyz.yooniks.protector.bukkit.user.UserManager;

public class BookEditListener
extends CustomPluginMessageListener {
    private final Map<UUID, Integer> payloadLimits = new HashMap<UUID, Integer>();
    private boolean noClass = false;
    private final boolean blockFully;
    private final UserManager userManager = UserManager.getInstance();
    private final boolean deserialize;
    private final Map<UUID, Map.Entry<Integer, Long>> editTimestamp = new HashMap<UUID, Map.Entry<Integer, Long>>();

    /*
     * Exception decompiling
     */
    private /* synthetic */ boolean isInvalidNbt(PacketEvent a) throws Exception {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 11[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:901)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$7.run(MainViewerGUI.java:1287)
        throw new IllegalStateException("Decompilation failed");
    }

    public BookEditListener(ProtectorSpigot a, boolean a2, boolean a3) {
        super((ProtectorSpigot)a, (ListenerOptions)ListenerOptions.INTERCEPT_INPUT_BUFFER);
        BookEditListener a4;
        a4.blockFully = a2;
        a4.deserialize = a3;
    }

    public void onPacketReceiving(PacketEvent a) {
        BookEditListener a2;
        UUID uUID;
        Object object;
        if (a.getPacketType() != PacketType.Play.Client.CUSTOM_PAYLOAD) return;
        Player player = a.getPlayer();
        if (player == null) {
            return;
        }
        try {
            uUID = player.getUniqueId();
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            a.setCancelled((boolean)true);
            return;
        }
        if (a2.connectionCloser.isClosing((UUID)uUID)) {
            a.setCancelled((boolean)true);
            return;
        }
        Object object2 = a.getPacket();
        object2 = object2.getStrings().size() <= 0 ? ((MinecraftKey)object2.getMinecraftKeys().readSafely((int)0)).getFullKey() : (String)object2.getStrings().readSafely((int)0);
        try {
            int n;
            if (a.getNetworkMarker() != null && (object = a.getNetworkMarker().getInputBuffer((boolean)false)) != null && (n = ((Buffer)object).remaining()) > MessagesConfig.MAX_PLUGINMESSAGE_CAPACITY) {
                a.setCancelled((boolean)true);
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" remaining> ").append((int)MessagesConfig.MAX_PLUGINMESSAGE_CAPACITY).append((String)"! Channel: ").append((String)object2).append((String)", remaining: ").append((int)n).toString());
                a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PAYLOAD$DESERIALIZE$JIGSAW);
                Bukkit.getServer().getPluginManager().callEvent((Event)new CrashTryEvent((OfflinePlayer)player));
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (((String)object2).equalsIgnoreCase((String)"MC|BEdit") || ((String)object2).equalsIgnoreCase((String)"MC|BSign") || ((String)object2).equalsIgnoreCase((String)"minecraft:bedit") || ((String)object2).equalsIgnoreCase((String)"minecraft:bsign")) {
            int n;
            boolean bl = false;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack == null || !itemStack.getType().name().contains((CharSequence)"BOOK")) continue;
                bl = true;
                break;
            }
            if (!bl) {
                a.setCancelled((boolean)true);
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)"/").append((Object)player.getAddress()).append((String)", sent book payload without book item in inventory!").toString());
                a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PAYLOAD_WITHOUT_BOOK);
                return;
            }
            User user = a2.userManager.createUser((UUID)uUID);
            user.addViolation((SpamType)SpamType.CUSTOM_PAYLOAD);
            if (user.getViolations((SpamType)SpamType.CUSTOM_PAYLOAD) > 30) {
                a.setCancelled((boolean)true);
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)"/").append((Object)player.getAddress()).append((String)", too many CUSTOM_PAYLOAD packets > 30").toString());
                a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.TOO_MANY_PACKETS);
                return;
            }
            if (a2.blockFully) {
                a.setCancelled((boolean)true);
                a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.BLOCK$BEDIT);
                return;
            }
            if (!a2.noClass && a.getPacket().getModifier().getValues().size() > 1) {
                try {
                    E e2 = a.getPacket().getModifier().getValues().get((int)1);
                    if (e2 instanceof ByteBuf && ((n = ((ByteBuf)e2).capacity()) > 30000 || n == 8190 || n == 8200)) {
                        a.setCancelled((boolean)true);
                        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PAYLOAD$DESERIALIZE$JIGSAW);
                        Bukkit.getServer().getPluginManager().callEvent((Event)new CrashTryEvent((OfflinePlayer)player));
                        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)"/").append((Object)player.getAddress()).append((String)", capacity (bookEdit): ").append((int)n).toString());
                        return;
                    }
                }
                catch (NoClassDefFoundError noClassDefFoundError) {
                    a2.noClass = true;
                }
            }
            if (a2.deserialize) {
                if (a2.editTimestamp.containsKey((Object)player.getUniqueId())) {
                    Map.Entry<Integer, Long> entry = a2.editTimestamp.get((Object)uUID);
                    n = entry.getKey().intValue();
                    long l = entry.getValue().longValue();
                    if (l > System.currentTimeMillis()) {
                        ++n;
                        a.setCancelled((boolean)true);
                    }
                    if (n > 4) {
                        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PAYLOAD$DESERIALIZE$TOO_FAST_BEDIT);
                        return;
                    }
                    a2.editTimestamp.put((UUID)player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Integer.valueOf((int)n), Long.valueOf((long)(System.currentTimeMillis() + 300L))));
                } else {
                    a2.editTimestamp.put((UUID)player.getUniqueId(), new AbstractMap.SimpleEntry<Integer, Long>(Integer.valueOf((int)1), Long.valueOf((long)(System.currentTimeMillis() + 300L))));
                }
                try {
                    if (a2.isInvalidNbt((PacketEvent)a)) {
                        a.setCancelled((boolean)true);
                        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PAYLOAD$DESERIALIZE$INVALID_BOOK_DATA);
                        Bukkit.getServer().getPluginManager().callEvent((Event)new CrashTryEvent((OfflinePlayer)player));
                        return;
                    }
                }
                catch (Exception exception) {
                    a.setCancelled((boolean)true);
                    ProtectorSpigotLogger.warning((String)new StringBuilder().insert((int)0, (String)"Exception while deserializing book in bookeditlistener, ").append((String)exception.getMessage()).toString());
                    return;
                }
            }
        }
        object = a2.userManager.createUser((UUID)uUID);
        if ((((String)object2).equalsIgnoreCase((String)"BungeeCord") || ((String)object2).equalsIgnoreCase((String)"bungeecord:main")) && Bukkit.getOnlinePlayers().size() < 2) {
            return;
        }
        ((User)object).addViolation((SpamType)SpamType.CUSTOM_PAYLOAD_OTHER);
        if (((User)object).getViolations((SpamType)SpamType.CUSTOM_PAYLOAD_OTHER) <= 350) return;
        a.setCancelled((boolean)true);
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)"/").append((String)player.getAddress().getHostString()).append((String)", too many CUSTOM_PAYLOAD_OTHER packets > 200").toString());
        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.TOO_MANY_PACKETS);
    }
}

