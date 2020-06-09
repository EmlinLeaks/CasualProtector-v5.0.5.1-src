/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.entity.HumanEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryCreativeEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;

public class HackedItemsListener
implements Listener {
    private final List<String> worldsToBypass;
    private final ConnectionCloser connectionCloser;

    public HackedItemsListener(ConnectionCloser a, List<String> a2) {
        HackedItemsListener a3;
        a3.connectionCloser = a;
        a3.worldsToBypass = a2;
    }

    @EventHandler
    public void onInventoryCreative(InventoryCreativeEvent a2) {
        HackedItemsListener a3;
        ItemStack itemStack = a2.getCursor();
        if (!(a2.getWhoClicked() instanceof Player)) return;
        if (itemStack == null) return;
        if (!itemStack.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        Player player = (Player)a2.getWhoClicked();
        String string = player.getWorld().getName().toLowerCase();
        if (a3.worldsToBypass.contains((Object)string)) {
            return;
        }
        if (itemMeta != null) {
            if (itemMeta.hasLore() && itemMeta.getLore().stream().anyMatch(a -> {
                if (a.contains((CharSequence)"Chance")) return true;
                if (a.contains((CharSequence)"Power")) return true;
                if (a.contains((CharSequence)"Grants")) return true;
                if (a.contains((CharSequence)"Stats")) return true;
                if (!MessagesConfig.HACKED_ITEMS$BYPASS_WHEN_HAS_LORE.contains((Object)a.toLowerCase())) return false;
                return true;
            })) {
                return;
            }
            if (String.valueOf((Object)itemMeta).length() > MessagesConfig.HACKED_ITEMS$MAX_ITEMMETA_SIZE) {
                a2.setCancelled((boolean)true);
                a3.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.HACKED_ITEMS_KICK_MESSAGE);
                return;
            }
        }
        if (itemStack.getAmount() >= 1 && itemStack.getAmount() <= 10000) {
            if (itemStack.getEnchantments().isEmpty()) return;
            if (!itemStack.getEnchantments().values().stream().anyMatch(a -> {
                if (a.intValue() <= MessagesConfig.HACKED_ITEMS$MAX_ENCHANTMENT_LEVEL) return false;
                return true;
            })) return;
            a2.setCancelled((boolean)true);
            a3.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.HACKED_ITEMS_KICK_MESSAGE);
            return;
        }
        a2.setCancelled((boolean)true);
        a3.connectionCloser.closeConnection((Player)player, (String)MessagesConfig.HACKED_ITEMS_KICK_MESSAGE);
    }
}

