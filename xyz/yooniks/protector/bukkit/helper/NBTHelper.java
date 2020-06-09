/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.wrappers.nbt.NbtBase
 *  com.comphenix.protocol.wrappers.nbt.NbtCompound
 *  com.comphenix.protocol.wrappers.nbt.NbtFactory
 *  com.comphenix.protocol.wrappers.nbt.NbtList
 *  com.comphenix.protocol.wrappers.nbt.NbtWrapper
 *  com.google.common.io.BaseEncoding
 *  org.bukkit.Material
 *  org.bukkit.block.BlockState
 *  org.bukkit.block.ShulkerBox
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BlockStateMeta
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.json.simple.JSONObject
 *  org.json.simple.parser.JSONParser
 */
package xyz.yooniks.protector.bukkit.helper;

import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtList;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import com.google.common.io.BaseEncoding;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import xyz.yooniks.protector.bukkit.ProtectorSpigot;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.listener.normal.ChannelVerificationListener;

public final class NBTHelper {
    public static int PAGE_LENGTH;
    public static boolean BLOCK_SIGNS_WITH_NBT;
    public static boolean ENABLED;
    public static boolean CHECK_SHULKER_BOXES;
    public static boolean ADVANCED_DESERIALIZATION;
    private static final Pattern pattern;
    public static boolean BLOCK_INVALID_CHARS_IN_BOOK;
    public static boolean BLOCK_FIREWORKS;
    public static int MAX_SIMILAR_PAGES;
    private static final String JESSICA_1_12_2_BOOK_CONTENT = "longStr0[wveb54yn4y]";
    public static boolean BLOCK_ADDRESS_ON_INVALID_NBT;
    public static final List<String> ANTI_RUN_COMMANDS;

    public static boolean checkSkull(ItemStack a2, NbtCompound a3) {
        Iterator iterator;
        String string;
        Object object;
        if (a2 != null && a2.hasItemMeta() && ((object = a2.getItemMeta()).hasDisplayName() || object.hasLore()) && (object.hasDisplayName() && object.getDisplayName().length() > 110 || object.hasLore() && object.getLore().size() > 110)) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Too big itemmeta (displayname or lore size), itemType: ").append((String)a2.getType().name()).toString());
            return true;
        }
        if (a3.containsKey((String)"Name") && ((String)(object = a3.getStringOrDefault((String)"Name"))).length() > 40) {
            ProtectorSpigotLogger.info((String)"Too big name tag! skull method");
            return true;
        }
        if (a3.containsKey((String)"Lore")) {
            object = a3.getListOrDefault((String)"Lore");
            iterator = object.iterator();
            while (iterator.hasNext()) {
                string = (String)iterator.next();
                if (string.length() <= 80) continue;
                ProtectorSpigotLogger.info((String)"Too big lore length! skull method");
                return true;
            }
            if (object.size() > 30) {
                ProtectorSpigotLogger.info((String)"Too big lore list size! skull method");
                return true;
            }
        }
        if (a3.containsKey((String)"END") && (object = a3.getListOrDefault((String)"END")) != null && object.size() > 200) {
            ProtectorSpigotLogger.info((String)"Too big end list! skull method");
            return true;
        }
        if (a3.getKeys().size() > 300) {
            ProtectorSpigotLogger.info((String)"Too many root keys!");
            return true;
        }
        if (a3.containsKey((String)"SkullOwner") && (object = a3.getCompound((String)"SkullOwner")).containsKey((String)"Properties") && (iterator = object.getCompound((String)"Properties")).containsKey((String)"textures")) {
            string = iterator.getList((String)"textures");
            for (NbtBase nbtBase : string.asCollection()) {
                if (!(nbtBase instanceof NbtCompound)) continue;
                if (!((NbtCompound)nbtBase).containsKey((String)"Value")) {
                    a3.remove((String)"SkullOwner");
                    return true;
                }
                if (((NbtCompound)nbtBase).getString((String)"Value").trim().length() <= 0) {
                    a3.remove((String)"SkullOwner");
                    return true;
                }
                try {
                    String string2 = new String((byte[])BaseEncoding.base64().decode((CharSequence)((NbtCompound)nbtBase).getString((String)"Value")));
                    JSONObject jSONObject = (JSONObject)new JSONParser().parse((String)string2);
                    if (jSONObject.containsKey((Object)"textures")) {
                        jSONObject = (JSONObject)jSONObject.get((Object)"textures");
                    }
                    if (jSONObject.containsKey((Object)"SKIN")) {
                        jSONObject = (JSONObject)jSONObject.get((Object)"SKIN");
                    }
                    if (!jSONObject.containsKey((Object)"url")) {
                        a3.remove((String)"SkullOwner");
                        return true;
                    }
                    if (((String)jSONObject.get((Object)"url")).trim().length() != 0) return false;
                    a3.remove((String)"SkullOwner");
                    return true;
                }
                catch (Exception exception) {
                    a3.remove((String)"SkullOwner");
                    return true;
                }
            }
        }
        if (a3.containsKey((String)"Owner") && (object = a3.getCompound((String)"Owner")).containsKey((String)"Properties") && (iterator = object.getCompound((String)"Properties")).containsKey((String)"textures")) {
            string = iterator.getList((String)"textures");
            for (NbtBase nbtBase : string.asCollection()) {
                if (!(nbtBase instanceof NbtCompound)) continue;
                if (!((NbtCompound)nbtBase).containsKey((String)"Value")) {
                    a3.remove((String)"Owner");
                    return true;
                }
                if (((NbtCompound)nbtBase).getString((String)"Value").trim().length() <= 0) {
                    a3.remove((String)"Owner");
                    return true;
                }
                try {
                    String string3 = new String((byte[])BaseEncoding.base64().decode((CharSequence)((NbtCompound)nbtBase).getString((String)"Value")));
                    JSONObject jSONObject = (JSONObject)new JSONParser().parse((String)string3);
                    if (jSONObject.containsKey((Object)"textures")) {
                        jSONObject = (JSONObject)jSONObject.get((Object)"textures");
                    }
                    if (jSONObject.containsKey((Object)"SKIN")) {
                        jSONObject = (JSONObject)jSONObject.get((Object)"SKIN");
                    }
                    if (!jSONObject.containsKey((Object)"url")) {
                        a3.remove((String)"Owner");
                        return true;
                    }
                    if (((String)jSONObject.get((Object)"url")).trim().length() != 0) return false;
                    a3.remove((String)"Owner");
                    return true;
                }
                catch (Exception exception) {
                    a3.remove((String)"Owner");
                    return true;
                }
            }
        }
        Objects.requireNonNull(a3);
        return a3.getKeys().stream().map(((NbtCompound)a3)::getValue).anyMatch(a -> {
            if (a.getName().equalsIgnoreCase((String)"www.wurstclient.net")) return true;
            if (a.getName().length() > 80) return true;
            if (a.getValue() instanceof String) {
                if (((String)a.getValue()).length() > 110) return true;
            }
            if (!(a instanceof NbtList)) return false;
            if (((NbtList)a).size() <= 80) return false;
            return true;
        });
    }

    private static /* synthetic */ boolean lambda$isInvalid$0(NbtBase a) {
        if (a.getName().equalsIgnoreCase((String)"www.wurstclient.net")) return true;
        if (a.getName().length() > 80) return true;
        if (a.getValue() instanceof String) {
            if (((String)a.getValue()).length() > 150) return true;
        }
        if (!(a instanceof NbtList)) return false;
        if (((NbtList)a).size() <= 80) return false;
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static boolean isInvalid(Player a, ItemStack a) {
        if (!NBTHelper.ENABLED) {
            return false;
        }
        if (a == null) return false;
        if (a.getType() == Material.AIR) return false;
        if (!a.hasItemMeta()) {
            return false;
        }
        var2_3 = NbtFactory.fromItemTag((ItemStack)a);
        if (var2_3 == null) return false;
        if (!(var2_3 instanceof NbtCompound)) {
            return false;
        }
        if ((var2_3 = (NbtCompound)var2_3) == null) {
            return false;
        }
        if (NBTHelper.BLOCK_SIGNS_WITH_NBT && var2_3.containsKey((String)"BlockEntityTag") && (var3_4 = var2_3.getCompound((String)"BlockEntityTag")) != null) {
            for (var4_5 = 1; var4_5 < 5; ++var4_5) {
                var5_14 = new StringBuilder().insert((int)0, (String)"Text").append((int)var4_5).toString();
                if (!var3_4.containsKey((String)var5_14)) continue;
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" had sign with nbt tags!").toString());
                return true;
            }
        }
        if (NBTHelper.BLOCK_FIREWORKS && a.getType().name().contains((CharSequence)"FIREWORK")) {
            var3_4 = new HashSet<E>(var2_3.getKeys());
            try {
                var4_6 = var3_4.iterator();
                while (var4_6.hasNext()) {
                    var5_14 = (String)var4_6.next();
                    var2_3.remove((String)var5_14);
                }
            }
            catch (Exception var4_7) {
                // empty catch block
            }
            ProtectorSpigotLogger.info((String)"Detected firework item! Firework is banned in the config.yml, to allow using firework please set remove-firework-data to false.");
            return true;
        }
        if (var2_3.containsKey((String)"Fireworks")) {
            var3_4 = var2_3.getCompound((String)"Fireworks");
            if (var3_4 != null && var3_4.getValue((String)"Flight") != null && (var4_8 = var3_4.getValue((String)"Flight").getValue()) != null && (var4_8 instanceof Integer && ((Integer)var4_8).intValue() > 3 || var4_8 instanceof Byte && ((Byte)var4_8).byteValue() > 3)) {
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" had too big Flight size! (>3)").toString());
                return true;
            }
            if (var3_4 != null) {
                var4_9 = var3_4.getListOrDefault((String)"Explosions");
                for (E var6_17 : var4_9) {
                    if (!(var6_17 instanceof NbtCompound)) continue;
                    var7_28 = (NbtCompound)var6_17;
                    ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Firework! Explosion: ").append((String)var6_17.toString()).toString());
                    if (var7_28.getIntegerArray((String)"Colors") == null || var7_28.getIntegerArray((String)"Colors").length <= 25) continue;
                    ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)"Too big colors array in firework! ").append((String)((var8_31 = var6_17.toString()).length() > 30 ? var8_31.substring((int)0, (int)29) : var8_31)).toString());
                    return true;
                }
            }
        }
        if (var2_3.containsKey((String)"www.wurstclient.net") || var2_3.getKeys().contains((Object)"www.wurstclient.net")) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" has just tried to use wurst chest exploit!").toString());
            return true;
        }
        if (a.getType().name().contains((CharSequence)"MAP")) {
            if (var2_3.containsKey((String)"Decorations") && (var3_4 = var2_3.getList((String)"Decorations")) != null) {
                var4_10 = var3_4.iterator();
                while (var4_10.hasNext()) {
                    var5_14 = var4_10.next();
                    if (!(var5_14 instanceof NbtCompound) || !(var6_19 = (NbtCompound)var5_14).containsKey((String)"type") || var6_19.getByte((String)"type") >= 0 && var6_19.getByte((String)"type") <= 15) continue;
                    if (a == null) return true;
                    ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)"Invalid map decoration type! ").append((int)var6_19.getByte((String)"type")).toString());
                    return true;
                }
            }
            if (var2_3.getName() != null && var2_3.getName().equalsIgnoreCase((String)"Decorations") && (var3_4 = var2_3.getList((String)"Decorations")) != null) {
                var4_11 = var3_4.iterator();
                while (var4_11.hasNext()) {
                    var5_14 = var4_11.next();
                    if (!(var5_14 instanceof NbtCompound) || !(var6_21 = (NbtCompound)var5_14).containsKey((String)"type") || var6_21.getByte((String)"type") >= 0 && var6_21.getByte((String)"type") <= 15) continue;
                    if (a == null) return true;
                    ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)"Invalid map decoration type! ").append((int)var6_21.getByte((String)"type")).toString());
                    return true;
                }
            }
        }
        if (a.getType().name().contains((CharSequence)"SPAWNER") && var2_3.containsKey((String)"BlockEntityTag") && (var3_4 = var2_3.getCompound((String)"BlockEntityTag")) != null && var3_4.containsKey((String)"EntityId") && var3_4.getStringOrDefault((String)"EntityId").equalsIgnoreCase((String)"ItemFrame")) {
            if (a == null) return true;
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" banned EntityId tag!").toString());
            return true;
        }
        if (var2_3.getName() != null && var2_3.getName().equalsIgnoreCase((String)"EntityTag") || var2_3.containsKey((String)"EntityTag")) {
            if (var2_3.containsKey((String)"Pos")) {
                return true;
            }
            if (var2_3.containsKey((String)"Size") && var2_3.getValue((String)"Size") != null && var2_3.getValue((String)"Size").getValue() != null && var2_3.getValue((String)"Size").getValue() instanceof Integer && var2_3.getInteger((String)"Size") > 500) {
                ((ProtectorSpigot)ProtectorSpigot.getPlugin(ProtectorSpigot.class)).getLogger().info((String)new StringBuilder().insert((int)0, (String)"(").append((String)a.getName()).append((String)") Too big integer \"Size\" in EntityTag!").toString());
                return true;
            }
        }
        if (var2_3.containsKey((String)"CustomName") && (var3_4 = var2_3.getListOrDefault((String)"CustomName")).size() > 40) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big customname listtag size!").toString());
            if (NBTHelper.BLOCK_ADDRESS_ON_INVALID_NBT == false) return true;
            ChannelVerificationListener.addBlockedAddress((Player)a);
            return true;
        }
        if (var2_3.getKeys().size() > 400) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too many compound keys!").toString());
            if (NBTHelper.BLOCK_ADDRESS_ON_INVALID_NBT == false) return true;
            ChannelVerificationListener.addBlockedAddress((Player)a);
            return true;
        }
        if (a.getType().name().contains((CharSequence)"SHULKER_BOX") && NBTHelper.CHECK_SHULKER_BOXES && a.getItemMeta() instanceof BlockStateMeta && (var3_4 = (BlockStateMeta)a.getItemMeta()).getBlockState() instanceof ShulkerBox) {
            var4_12 = (ShulkerBox)var3_4.getBlockState();
            var5_15 = 0;
            for (ItemStack var9_34 : var4_12.getInventory().getContents()) {
                if (var9_34 == null) continue;
                if (var9_34.getType().name().contains((CharSequence)"BOOK") && ++var5_15 > 15) {
                    ProtectorSpigotLogger.info((String)((a == null ? "none" : a.getName()) + " too many books in shulkerbox!"));
                    return true;
                }
                if (!NBTHelper.isInvalid((Player)a, (ItemStack)var9_34)) continue;
                ProtectorSpigotLogger.info((String)((a == null ? "none" : a.getName()) + " item in shulker_box is invalid!"));
                return true;
            }
        }
        if (!a.getType().name().contains((CharSequence)"BOOK") || a.getType() == Material.ENCHANTED_BOOK) ** GOTO lbl-1000
        if (var2_3.containsKey((String)"author") && var2_3.getString((String)"author").length() > 30) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big author length!").toString());
            return true;
        }
        if (var2_3.containsKey((String)"title") && var2_3.getString((String)"title").length() > 30) {
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big title length!").toString());
            return true;
        }
        if (!var2_3.containsKey((String)"pages")) ** GOTO lbl-1000
        var3_4 = var2_3.getListOrDefault((String)"pages");
        var4_5 = var3_4.size();
        var5_16 = 0;
        var6_24 = "";
        if (var4_5 > 50) {
            if (NBTHelper.BLOCK_ADDRESS_ON_INVALID_NBT && a != null) {
                ChannelVerificationListener.addBlockedAddress((Player)a);
            }
            ProtectorSpigotLogger.info((String)((a == null ? "none" : a.getName()) + " size of pages in book >50"));
            return true;
        }
        var7_30 = var3_4.iterator();
        do {
            if (var7_30.hasNext()) {
                var8_33 = (String)var7_30.next();
                if (NBTHelper.BLOCK_INVALID_CHARS_IN_BOOK && NBTHelper.pattern.matcher((CharSequence)var8_33).find()) {
                    ProtectorSpigotLogger.info((String)((a == null ? "none" : a.getName()) + " pattern match! - invalid chars in book"));
                    return true;
                }
                var9_35 = 0;
            } else lbl-1000: // 3 sources:
            {
                if (var2_3.containsKey((String)"Name") && (var3_4 = var2_3.getStringOrDefault((String)"Name")).length() > 30) {
                    ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big name tag!!").toString());
                    return true;
                }
                if (var2_3.containsKey((String)"Lore")) {
                    var3_4 = var2_3.getListOrDefault((String)"Lore");
                    var4_13 = var3_4.iterator();
                    while (var4_13.hasNext()) {
                        var5_14 = (String)var4_13.next();
                        if (var5_14.length() <= 75) continue;
                        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big lore strng!").toString());
                        return true;
                    }
                    if (var3_4.size() > 35) {
                        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big lore length list!").toString());
                        return true;
                    }
                }
                if (var2_3.containsKey((String)"END") && (var3_4 = var2_3.getListOrDefault((String)"END")) != null && var3_4.size() > 200) {
                    ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big END tag list!").toString());
                    return true;
                }
                Objects.requireNonNull(var2_3);
                return var2_3.getKeys().stream().map((Function<String, NbtBase>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getValue(java.lang.String ), (Ljava/lang/String;)Lcom/comphenix/protocol/wrappers/nbt/NbtBase;)((NbtCompound)var2_3)).anyMatch((Predicate<NbtBase>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Z, lambda$isInvalid$0(com.comphenix.protocol.wrappers.nbt.NbtBase ), (Lcom/comphenix/protocol/wrappers/nbt/NbtBase;)Z)());
            }
            for (a = 0; a < var8_33.length(); ++a) {
                var10_36 = var8_33.charAt((int)a);
                if (var10_36 == 167) continue;
                var10_36 = String.valueOf((char)var10_36).getBytes().length;
                if (NBTHelper.BLOCK_INVALID_CHARS_IN_BOOK && var10_36 > 1) {
                    if (++var9_35 <= 3) continue;
                    ProtectorSpigotLogger.info((String)((a == null ? "none" : a.getName()) + " countOfTooBigBytes (invalidCharsInBook) >3"));
                    continue;
                }
                if (var10_36 < 5 || ++var9_35 <= 20) continue;
                ProtectorSpigotLogger.info((String)((a == null ? "none" : a.getName()) + " countOfTooBigBytes >20"));
                return true;
            }
            if (var6_25.equalsIgnoreCase((String)var8_33)) {
                ++var5_16;
            }
            var6_26 = var8_33;
            if (var5_16 > NBTHelper.MAX_SIMILAR_PAGES) {
                if (var6_26.length() > 22) {
                    ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"(").append((String)a.getName()).append((String)") Too many similar pages! (").append((int)var5_16).append((String)", content (re-sized to 22 chars): ").append((String)var6_26.substring((int)0, (int)22)).append((String)") Prevent small book exploits.. (mainly jessica 1.12.2)").toString());
                    return true;
                }
                ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"(").append((String)a.getName()).append((String)") Too many similar pages! (").append((int)var5_16).append((String)", content ").append((String)var6_26).append((String)") Prevent small book exploits.. (mainly jessica 1.12.2)").toString());
                return true;
            }
            if (!var8_33.contains((CharSequence)"longStr0[wveb54yn4y]")) continue;
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"(").append((String)a.getName()).append((String)") Advanced deserialization! Used method against the \"Jessica 1.12.2\" (v2) exploit method.").toString());
            return true;
        } while (var8_33.length() <= NBTHelper.PAGE_LENGTH);
        ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)a.getName()).append((String)" too big page length! Length: ").append((int)var8_33.length()).toString());
        if (NBTHelper.BLOCK_ADDRESS_ON_INVALID_NBT == false) return true;
        ChannelVerificationListener.addBlockedAddress((Player)a);
        return true;
    }

    public static {
        ENABLED = true;
        BLOCK_INVALID_CHARS_IN_BOOK = false;
        ANTI_RUN_COMMANDS = new ArrayList<String>();
        CHECK_SHULKER_BOXES = true;
        PAGE_LENGTH = 500;
        BLOCK_FIREWORKS = true;
        pattern = Pattern.compile((String)"[^\u00fc\u00e9\u00e1\u00ed\u00f3\u00fa\u00f1\u00c1\u00c9\u00cd\u00d3\u00da\u00dcA-Za-z.!@?#\"$%&\u00a7:;()\u00d1\u00bf\u00a1 *'+,/\\-=\\[\\]^_{|}~`<>\\x00-\\x7F]+");
    }

    private /* synthetic */ NBTHelper() {
        NBTHelper a;
    }
}

