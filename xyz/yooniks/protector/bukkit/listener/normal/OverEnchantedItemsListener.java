/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package xyz.yooniks.protector.bukkit.listener.normal;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayer;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayerManager;

public class OverEnchantedItemsListener
implements Listener {
    private final MultiPlayerManager multiPlayerManager;
    private final List<String> worldsToBypass;

    public OverEnchantedItemsListener(MultiPlayerManager a, List<String> a2) {
        OverEnchantedItemsListener a3;
        a3.multiPlayerManager = a;
        a3.worldsToBypass = a2;
    }

    private /* synthetic */ ItemMeta fixMeta(ItemStack a) {
        ItemMeta itemMeta = null;
        if (!a.getItemMeta().hasEnchants()) return itemMeta;
        Iterator<Map.Entry<K, V>> iterator = a.getEnchantments().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            if (((Integer)entry.getValue()).intValue() <= ((Enchantment)entry.getKey()).getMaxLevel()) continue;
            if (itemMeta == null) {
                itemMeta = a.getItemMeta();
            }
            itemMeta.removeEnchant((Enchantment)((Enchantment)entry.getKey()));
            itemMeta.addEnchant((Enchantment)((Enchantment)entry.getKey()), (int)((Enchantment)entry.getKey()).getMaxLevel(), (boolean)true);
        }
        return itemMeta;
    }

    @EventHandler(ignoreCancelled=true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent a) {
        OverEnchantedItemsListener a2;
        if (!(a.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player)a.getDamager();
        String string = player.getWorld().getName().toLowerCase();
        if (a2.worldsToBypass.contains((Object)string)) {
            return;
        }
        string = a2.multiPlayerManager.createMultiPlayer((Player)player).getItemInMainHand();
        if (string == null) return;
        if (!string.hasItemMeta()) return;
        if (!string.getItemMeta().hasEnchants()) {
            return;
        }
        ItemMeta itemMeta = a2.fixMeta((ItemStack)string);
        if (itemMeta == null) return;
        string.setItemMeta((ItemMeta)itemMeta);
        a.setCancelled((boolean)true);
        player.sendMessage((String)MessageHelper.colored((String)MessagesConfig.HACKED_ITEMS$OVER_ENCHANTED_ITEM_MESSAGE));
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" had over-enchanted items (damage)!").toString());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent a) {
        OverEnchantedItemsListener a2;
        Player player = a.getPlayer();
        String string = player.getWorld().getName().toLowerCase();
        if (a2.worldsToBypass.contains((Object)string)) {
            return;
        }
        string = a2.multiPlayerManager.createMultiPlayer((Player)player).getItemInMainHand();
        if (string == null) return;
        if (!string.hasItemMeta()) return;
        if (!string.getItemMeta().hasEnchants()) {
            return;
        }
        ItemMeta itemMeta = a2.fixMeta((ItemStack)string);
        if (itemMeta == null) return;
        string.setItemMeta((ItemMeta)itemMeta);
        a.setCancelled((boolean)true);
        player.sendMessage((String)MessageHelper.colored((String)MessagesConfig.HACKED_ITEMS$OVER_ENCHANTED_ITEM_MESSAGE));
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)player.getName()).append((String)" had over-enchanted items (interact)!").toString());
    }
}

