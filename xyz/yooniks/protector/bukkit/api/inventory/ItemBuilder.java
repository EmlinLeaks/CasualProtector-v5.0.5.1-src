/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package xyz.yooniks.protector.bukkit.api.inventory;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.yooniks.protector.bukkit.helper.MessageHelper;

public class ItemBuilder {
    private ItemStack item;

    public ItemBuilder withLore(List<String> a) {
        ItemBuilder a2;
        ItemMeta itemMeta = a2.item.getItemMeta();
        itemMeta.setLore(MessageHelper.colored(a));
        a2.item.setItemMeta((ItemMeta)itemMeta);
        return a2;
    }

    public ItemBuilder withDurability(short a) {
        ItemBuilder a2;
        a2.item.setDurability((short)a);
        return a2;
    }

    public ItemBuilder withName(String a) {
        ItemBuilder a2;
        ItemMeta itemMeta = a2.item.getItemMeta();
        itemMeta.setDisplayName((String)MessageHelper.colored((String)a));
        a2.item.setItemMeta((ItemMeta)itemMeta);
        return a2;
    }

    public ItemBuilder withAmount(int a) {
        ItemBuilder a2;
        a2.item.setAmount((int)a);
        return a2;
    }

    public ItemBuilder(Material a) {
        ItemBuilder a2;
        if (a == Material.AIR) {
            throw new IllegalArgumentException((String)"Material cannot be AIR!");
        }
        a2.item = new ItemStack((Material)a);
    }

    public ItemBuilder(Material a, int a2, short a3) {
        ItemBuilder a4;
        if (a == Material.AIR) {
            throw new IllegalArgumentException((String)"Material cannot be AIR!");
        }
        a4.item = new ItemStack((Material)a, (int)a2, (short)a3);
    }

    public ItemBuilder withType(Material a) {
        ItemBuilder a2;
        if (a == Material.AIR) {
            throw new IllegalArgumentException((String)"Material cannot be AIR!");
        }
        if (a2.item == null) {
            a2.item = new ItemStack((Material)a);
            return a2;
        }
        a2.item.setType((Material)a);
        return a2;
    }

    public ItemStack build() {
        ItemBuilder a;
        return a.item;
    }

    public ItemBuilder addEnchantment(Enchantment a, int a2) {
        ItemBuilder a3;
        ItemMeta itemMeta = a3.item.getItemMeta();
        itemMeta.addEnchant((Enchantment)a, (int)a2, (boolean)true);
        a3.item.setItemMeta((ItemMeta)itemMeta);
        return a3;
    }

    public ItemBuilder(ItemStack a) {
        ItemBuilder a2;
        a2.item = a;
    }

    public ItemBuilder withLore(String ... a) {
        ItemBuilder a2;
        return a2.withLore(Arrays.asList(a));
    }

    public static ItemBuilder withSection(ConfigurationSection a) {
        Object object;
        if (a == null) {
            return new ItemBuilder((Material)Material.GRASS).withName((String)"error podczas buildowania z sekcji");
        }
        ItemBuilder itemBuilder = new ItemBuilder((Material)Material.GRASS);
        if (a.isString((String)"material") && (object = Material.matchMaterial((String)a.getString((String)"material"))) != null) {
            itemBuilder.withType((Material)object);
        }
        if (a.isList((String)"lore")) {
            itemBuilder.withLore((List<String>)a.getStringList((String)"lore"));
        }
        if (a.isString((String)"name")) {
            itemBuilder.withName((String)a.getString((String)"name"));
        }
        if (a.isInt((String)"amount")) {
            itemBuilder.withAmount((int)a.getInt((String)"amount"));
        }
        if (a.isInt((String)"data")) {
            itemBuilder.withDurability((short)((short)a.getInt((String)"data")));
        }
        if (!a.isList((String)"enchants")) return itemBuilder;
        object = a.getStringList((String)"enchants").iterator();
        while (object.hasNext()) {
            Enchantment enchantment;
            int n;
            String[] arrstring = ((String)object.next()).split((String)";");
            if (arrstring.length < 1 || (enchantment = Enchantment.getByName((String)arrstring[0])) == null) continue;
            try {
                n = Integer.parseInt((String)arrstring[1]);
            }
            catch (NumberFormatException numberFormatException) {
                continue;
            }
            itemBuilder.addEnchantment((Enchantment)enchantment, (int)n);
        }
        return itemBuilder;
    }
}

