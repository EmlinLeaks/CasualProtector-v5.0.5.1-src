/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.player.PlayerEditBookEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.BookMeta
 *  org.bukkit.inventory.meta.ItemMeta
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.NBTHelper;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayer;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayerManager;

public class BooksAndInteractListener
implements Listener {
    private final Pattern pattern = Pattern.compile((String)"[^\u00fc\u00e9\u00e1\u00ed\u00f3\u00fa\u00f1\u00c1\u00c9\u00cd\u00d3\u00da\u00dcA-Za-z.!@?#\"$%&\u00a7:;()\u00d1\u00bf\u00a1 *'+,/\\-=\\[\\]^_{|}~`<>\\x00-\\x7F]+");
    private final MultiPlayerManager multiPlayerManager;
    private final ConnectionCloser connectionCloser;

    public BooksAndInteractListener(ConnectionCloser a, MultiPlayerManager a2) {
        BooksAndInteractListener a3;
        a3.connectionCloser = a;
        a3.multiPlayerManager = a2;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent a) {
        BooksAndInteractListener a2;
        Player player = a.getPlayer();
        ItemStack itemStack = a2.multiPlayerManager.createMultiPlayer((Player)player).getItemInMainHand();
        if (itemStack == null) {
            return;
        }
        if (!NBTHelper.isInvalid((Player)player, (ItemStack)itemStack)) return;
        itemStack.setItemMeta((ItemMeta)new ItemStack((Material)itemStack.getType()).getItemMeta());
        a.setCancelled((boolean)true);
        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.INTERACT$BLOCKED_ITEM);
    }

    @EventHandler
    public void onBookEdit(PlayerInteractEvent a) {
        if (a.getItem() == null) {
            return;
        }
        ItemStack itemStack = a.getItem();
        if (itemStack.getType() != Material.WRITTEN_BOOK) {
            if (itemStack.getType() != Material.BOOK_AND_QUILL) return;
        }
        if (itemStack.getEnchantments().size() <= 0) return;
        if (!itemStack.containsEnchantment((Enchantment)Enchantment.DAMAGE_ALL) || itemStack.getEnchantmentLevel((Enchantment)Enchantment.DAMAGE_ALL) <= 5) {
            if (!itemStack.containsEnchantment((Enchantment)Enchantment.DIG_SPEED)) return;
            if (itemStack.getEnchantmentLevel((Enchantment)Enchantment.DIG_SPEED) <= 5) return;
        }
        a.setCancelled((boolean)true);
        a.getPlayer().getInventory().removeItem((ItemStack[])new ItemStack[]{itemStack});
        a.getPlayer().updateInventory();
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)a.getPlayer().getName()).append((String)" had enchants in book (>5 enchant level) at PlayerInteractEvent!").toString());
    }

    @EventHandler(ignoreCancelled=true)
    public void onPlace(BlockPlaceEvent a) {
        BooksAndInteractListener a2;
        ItemStack itemStack = a.getItemInHand();
        if (itemStack == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        Player player = a.getPlayer();
        if (!NBTHelper.isInvalid((Player)player, (ItemStack)itemStack)) {
            if (itemMeta == null) return;
            if (!itemStack.hasItemMeta()) return;
            if (itemStack.getType() == Material.SKULL_ITEM) return;
            if (itemStack.getType() == Material.SKULL) return;
            if (String.valueOf((Object)itemMeta).length() <= 17384) return;
        }
        a.setCancelled((boolean)true);
        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.HACKED_ITEMS_KICK_MESSAGE);
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerEditBook(PlayerEditBookEvent a) {
        String string;
        BooksAndInteractListener a2;
        Object object = a.getNewBookMeta();
        if (object == null) {
            a.setCancelled((boolean)true);
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)a.getPlayer().getName()).append((String)" bookMeta==null in PlayerEditBookEvent!").toString());
            return;
        }
        if (object.getEnchants().size() != 0) {
            a.setCancelled((boolean)true);
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)a.getPlayer().getName()).append((String)" had enchants in PlayerEditBookEvent!").toString());
            return;
        }
        if (object.getPageCount() > 50) {
            a.setCancelled((boolean)true);
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)a.getPlayer().getName()).append((String)" pageCount>50 in PlayerEditBookEvent!").toString());
            return;
        }
        object = object.getPages().iterator();
        do {
            if (!object.hasNext()) return;
            string = (String)object.next();
            if (!NBTHelper.BLOCK_INVALID_CHARS_IN_BOOK || !a2.pattern.matcher((CharSequence)string).find()) continue;
            Player player = a.getPlayer();
            a.setCancelled((boolean)true);
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)a.getPlayer().getName()).append((String)" page contains invalid chars! PlayerEditBookEvent!").toString());
            a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PAYLOAD$DESERIALIZE$INVALID_BOOK_DATA);
            return;
        } while (string.length() <= 400);
        Player player = a.getPlayer();
        a.setCancelled((boolean)true);
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Player ").append((String)a.getPlayer().getName()).append((String)" page.length()>400 in PlayerEditBookEvent!").toString());
        a2.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.PAYLOAD$DESERIALIZE$INVALID_BOOK_DATA);
    }
}

