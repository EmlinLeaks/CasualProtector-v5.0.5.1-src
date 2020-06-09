/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.reflect.StructureModifier
 *  io.netty.buffer.ByteBuf
 *  org.bukkit.Bukkit
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 */
package xyz.yooniks.protector.bukkit.listener.packet.limit;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import io.netty.buffer.ByteBuf;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.user.SpamType;
import xyz.yooniks.protector.bukkit.user.User;
import xyz.yooniks.protector.bukkit.user.UserManager;

public class PacketChecker {
    private final Map<SpamType, Integer> limits = new HashMap<SpamType, Integer>();
    private final ConnectionCloser closer = ProtectorSpigot.getInstance().getConnectionCloser();
    private final UserManager userManager = UserManager.getInstance();
    private final boolean isOneDotSeven = Bukkit.getServer().getVersion().contains((CharSequence)"1.7");

    public void load(FileConfiguration a) {
        PacketChecker a2;
        for (String object : a.getConfigurationSection((String)"packet-limitter").getKeys((boolean)false)) {
            SpamType spamType = SpamType.byName((String)object);
            if (spamType == null) continue;
            a2.limits.put((SpamType)spamType, (Integer)Integer.valueOf((int)a.getInt((String)("packet-limitter." + object))));
        }
        if (a2.limits.size() != 0) return;
        a2.limits.put((SpamType)SpamType.ABILITIES, (Integer)Integer.valueOf((int)250));
        a2.limits.put((SpamType)SpamType.ARM_ANIMATION, (Integer)Integer.valueOf((int)80));
        a2.limits.put((SpamType)SpamType.BLOCK_DIG, (Integer)Integer.valueOf((int)120));
        a2.limits.put((SpamType)SpamType.BLOCK_PLACE, (Integer)Integer.valueOf((int)80));
        a2.limits.put((SpamType)SpamType.BLOCK_PLACE_BOOKS, (Integer)Integer.valueOf((int)5));
        a2.limits.put((SpamType)SpamType.ENTITY_ACTION, (Integer)Integer.valueOf((int)60));
        a2.limits.put((SpamType)SpamType.KEEP_ALIVE, (Integer)Integer.valueOf((int)8));
        a2.limits.put((SpamType)SpamType.POSITION, (Integer)Integer.valueOf((int)250));
        a2.limits.put((SpamType)SpamType.SET_CREATIVE_SLOT, (Integer)Integer.valueOf((int)210));
        a2.limits.put((SpamType)SpamType.SET_CREATIVE_SLOT_WRONG, (Integer)Integer.valueOf((int)1));
        a2.limits.put((SpamType)SpamType.TAB_COMPLETE, (Integer)Integer.valueOf((int)50));
        a2.limits.put((SpamType)SpamType.UPDATE_SIGN, (Integer)Integer.valueOf((int)8));
        a2.limits.put((SpamType)SpamType.USE_ENTITY, (Integer)Integer.valueOf((int)80));
        a2.limits.put((SpamType)SpamType.USE_ITEM, (Integer)Integer.valueOf((int)80));
        a2.limits.put((SpamType)SpamType.WINDOW_CLICK, (Integer)Integer.valueOf((int)80));
        a2.limits.put((SpamType)SpamType.WINDOW_CLICK_WRONG, (Integer)Integer.valueOf((int)15));
        a2.limits.put((SpamType)SpamType.TRANSACTION, (Integer)Integer.valueOf((int)45));
        a2.limits.put((SpamType)SpamType.BLOCK_PLACE_WRONG, (Integer)Integer.valueOf((int)9));
        a2.limits.put((SpamType)SpamType.SETTINGS, (Integer)Integer.valueOf((int)110));
        a2.limits.put((SpamType)SpamType.RESOURCE_PACK_STATUS, (Integer)Integer.valueOf((int)10));
        a2.limits.put((SpamType)SpamType.CLOSE_WINDOW, (Integer)Integer.valueOf((int)30));
        a2.limits.put((SpamType)SpamType.RECIPE_DISPLAYED, (Integer)Integer.valueOf((int)120));
        a2.limits.put((SpamType)SpamType.AUTO_RECIPE, (Integer)Integer.valueOf((int)40));
        a2.limits.put((SpamType)SpamType.PAYLOAD_ITEM_ADV, (Integer)Integer.valueOf((int)30));
        a2.limits.put((SpamType)SpamType.PAYLOAD_ITEM_ADV_WRONG, (Integer)Integer.valueOf((int)1));
        for (Map.Entry entry : a2.limits.entrySet()) {
            a.set((String)new StringBuilder().insert((int)0, (String)"packet-limitter.").append((String)((SpamType)((Object)entry.getKey())).name()).toString(), entry.getValue());
        }
        try {
            a.save((File)new File((File)ProtectorSpigot.getInstance().getDataFolder(), (String)"config.yml"));
            return;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public PacketChecker() {
        PacketChecker a;
    }

    public void checkThrough(SpamType a, PacketEvent a2) {
        Object object;
        Object object2;
        PacketChecker a3;
        Player player = a2.getPlayer();
        if (player == null) {
            return;
        }
        try {
            object = player.getUniqueId();
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return;
        }
        if (a3.closer.isClosing((UUID)object)) {
            a2.setCancelled((boolean)true);
            return;
        }
        if (a2.isCancelled()) return;
        int n = 1;
        object = a2.getPacket().getModifier().getValues();
        if (object.size() > 1) {
            object2 = object.get((int)1);
            if (!a3.isOneDotSeven && object2 instanceof ByteBuf) {
                n = ((ByteBuf)object2).capacity();
            }
        }
        object2 = a3.userManager.createUser((UUID)player.getUniqueId());
        int n2 = a3.getLimit((SpamType)a);
        if (n > MessagesConfig.MAX_PACKET_BUFFER_SIZE) {
            a3.closer.closeConnection((Player)player, (String)MessagesConfig.TOO_MANY_PACKETS);
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" too big packet size! PacketChecker, ByteBuf#capacity > ").append((int)MessagesConfig.MAX_PACKET_BUFFER_SIZE).toString());
            a2.setCancelled((boolean)true);
            return;
        }
        if (n2 <= 0) return;
        ((User)object2).addViolation((SpamType)a);
        if (((User)object2).getViolations((SpamType)a) <= n2) return;
        a3.closer.closeConnection((Player)player, (String)MessagesConfig.TOO_MANY_PACKETS);
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" too many ").append((String)a.name()).append((String)" packets! >").append((int)n2).append((String)", if it has false detects please change a limit of ").append((String)a.name()).append((String)" to bigger value!").toString());
        a2.setCancelled((boolean)true);
    }

    private /* synthetic */ int getLimit(SpamType a) {
        PacketChecker a2;
        return a2.limits.getOrDefault((Object)((Object)a), (Integer)Integer.valueOf((int)0)).intValue();
    }
}

