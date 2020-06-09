/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.config;

import java.util.Collections;
import java.util.List;
import xyz.yooniks.protector.bukkit.config.system.ConfigExtender;
import xyz.yooniks.protector.bukkit.config.system.Configuration;

public class MessagesConfig
extends ConfigExtender {
    public static String ANTIBOT$TOO_MANY_ACCOUNTS_PER_IP = "You have more than %limit% accounts at this server with the same ip!";
    public static String ANTIBOT$BANNED_NICKNAME;
    public static String PREFIX;
    public static String BLOCK$BEDIT;
    public static String MEMORY_INFO$MESSAGE;
    public static String INTERACT$BLOCKED_ITEM;
    public static String UUID_SPOOF$KICK_MESSAGE;
    public static String BLOCK$LITELOADER;
    public static String BLOCK$BADLION;
    public static String PAYLOAD$DESERIALIZE$JIGSAW;
    public static String ANTIBOT$LIMITER$SIMILAR$NAMES$DISCONNECT_MESSAGE;
    public static String BLOCK$INVALID_CHARS;
    public static String TOO_MANY_SIMILAR_WC_PACKETS;
    public static String ANTIBOT$LIMITER$LAST_NAME_LENGTH$DISCONNECT_MESSAGE;
    public static String REDSTONE$CANCELLED_ADMINS_MESSAGE;
    public static int MAX_ENTITIES_ON_CHUNK;
    public static String COMMAND$NO_PERMISSION;
    public static String ANTIBOT$CHANNEL_VERIFICATION$KICKMESSAGE;
    public static String REDSTONE$CANCELLED_ADMINS_PERMISSION;
    public static String ANTIBOT$LIMITER$CONNECTION_THROTTLE$DISCONNECT_MESSAGE;
    public static int MAX_PACKET_BUFFER_SIZE;
    public static String ANTIBOT$TOO_BIG_PING;
    public static double BYPASS_WHEN_TPS_UNDER;
    public static String BLOCK$LMC;
    public static String PAYLOAD_WITHOUT_BOOK;
    public static String BLOCK$WDL;
    public static String CONNECTION_CLOSER$SEND_MESSAGE_TO_PLAYERS_WITH_PERM;
    public static int MAX_PACKET_CAPACITY;
    public static String PAYLOAD$DESERIALIZE$INVALID_BOOK_DATA;
    public static boolean CONNECTION_CLOSER$SEND_MESSAGE_TO_OPS;
    public static String BLOCK$FORGE;
    public static String ANTIBOT$CANNOT_JOIN_FROM_VPN;
    public static int HACKED_ITEMS$MAX_ITEMMETA_SIZE;
    public static boolean REDSTONE$CANCELLED_ADMINS_MESSAGE_ENABLED;
    public static String TOO_MANY_PACKETS;
    public static String CONNECTION_CLOSER$MESSAGE_TO_OPS;
    public static String ANTIBOT$CHANNEL_VERIFICATION$BROADCAST;
    public static String ANTI_RUN_COMMANDS$WHEN_HOLDING_BOOK;
    public static String ANTIBOT$DOUBLE_JOIN;
    public static String ANTI_WOLRD_EDIT_EXPLOIT_MESSAGE;
    public static List<String> EXPLOIT$COMMANDS_ON_TRY;
    public static int HACKED_ITEMS$MAX_ENCHANTMENT_LEVEL;
    public static String TOO_MANY_ITEMS$CLEARED;
    public static int MAX_PLUGINMESSAGE_CAPACITY;
    public static List<String> HACKED_ITEMS$BYPASS_WHEN_HAS_LORE;
    public static String BLOCK$PROXY;
    public static String SIGN_EXPLOIT_MESSAGE;
    public static String ANTI_LOGGED_FROM_ANOTHER_LOCATION;
    public static String PACKET_FILTER$INVALID_PACKET;
    public static String HACKED_ITEMS$OVER_ENCHANTED_ITEM_MESSAGE;
    public static String PAYLOAD$DESERIALIZE$TOO_FAST_BEDIT;
    public static String ANTIBOT$TOO_BIG_PING_BYPASS_PERMISSION;
    public static String HACKED_ITEMS_KICK_MESSAGE;

    public static {
        ANTIBOT$CANNOT_JOIN_FROM_VPN = "&6CProtector: &cVPN/Proxy (banned country/127.0.0.1 ip) detected!";
        ANTIBOT$TOO_BIG_PING = "&bToo big ping! (Max: {LIMIT})";
        ANTIBOT$TOO_BIG_PING_BYPASS_PERMISSION = "casualprotector.pingbypass";
        PAYLOAD_WITHOUT_BOOK = "&cYou don't have any book in inventory!";
        MAX_PACKET_CAPACITY = 80000;
        MAX_PLUGINMESSAGE_CAPACITY = 7000;
        MAX_PACKET_BUFFER_SIZE = 16384;
        MEMORY_INFO$MESSAGE = "&8&l&m-=-=-=-=&r&6CProtector&8&l&m-=-=-=-=\n&7Ram: &a{USED-RAM}&8/&c{MAX-RAM}\n&7Tps: &a{TPS}\n&8&l&m-=-=-=-=&r&6CProtector&8&l&m-=-=-=-=";
        ANTIBOT$LIMITER$LAST_NAME_LENGTH$DISCONNECT_MESSAGE = "&6CProtector: &cToo many players with similar nick are logging in.. Please wait 10 seconds and try again.";
        ANTIBOT$LIMITER$CONNECTION_THROTTLE$DISCONNECT_MESSAGE = "&6CProtector: &cPlease wait &e{SECONDS}s &cbefore rejoining!";
        ANTIBOT$LIMITER$SIMILAR$NAMES$DISCONNECT_MESSAGE = "&6CProtector: &cOn the server are already too many players with similar nickname! Please change your nickname..";
        PREFIX = "&8[&6CProtector&8] ";
        ANTIBOT$DOUBLE_JOIN = "You are verified now, join to server again!";
        COMMAND$NO_PERMISSION = "You do not have permission! (casualprotector.command)";
        TOO_MANY_PACKETS = "Sending too many packets! Lag or exploit?";
        TOO_MANY_SIMILAR_WC_PACKETS = "&cYou have sent too many similar windowclick packets! In 0.5 second you clicked in the same slot and item >50times!";
        REDSTONE$CANCELLED_ADMINS_MESSAGE_ENABLED = true;
        REDSTONE$CANCELLED_ADMINS_PERMISSION = "casualprotector.redstone.admin";
        REDSTONE$CANCELLED_ADMINS_MESSAGE = "&8[&6CProtector&8] &cCancelled redstone actions! Redstones on chunk: &6{REDSTONES}&c, location: &6{LOCATION}";
        ANTIBOT$BANNED_NICKNAME = "&cLooks like you are not able to join on this server.. =(";
        ANTI_LOGGED_FROM_ANOTHER_LOCATION = "&6[CasualProtector] &cPlayer with that name is already playing on this server!";
        HACKED_ITEMS_KICK_MESSAGE = "&cHacked items are not allowed on this server! Too big enchantments or amount.";
        ANTI_WOLRD_EDIT_EXPLOIT_MESSAGE = "&6[CasualProtector] &cYour message contains blocked words!";
        SIGN_EXPLOIT_MESSAGE = "&cYour sign line has too many or illegal characters!";
        UUID_SPOOF$KICK_MESSAGE = "&cYour UUID is invalid! Please disable anti-uuid-spoof or re-login with premium account.";
        BLOCK$FORGE = "You cannot join from forge!";
        BLOCK$WDL = "You cannot use worlddownloader mod!";
        BLOCK$LMC = "You cannot use labymod!";
        BLOCK$BADLION = "You cannot use badlion mod!";
        BLOCK$LITELOADER = "You cannot use liteloader mod!";
        BLOCK$PROXY = "Are you proxy user?!";
        BLOCK$BEDIT = "You cannot edit book!";
        PAYLOAD$DESERIALIZE$JIGSAW = "Jigsaw client detected!";
        PAYLOAD$DESERIALIZE$TOO_FAST_BEDIT = "You are editing book too fast!";
        PAYLOAD$DESERIALIZE$INVALID_BOOK_DATA = "Invalid book data!";
        BLOCK$INVALID_CHARS = "You have just written invalid message! Please use only english letters..";
        TOO_MANY_ITEMS$CLEARED = "On the ground were too many items! I have just removed all to optimize server :D";
        ANTIBOT$CHANNEL_VERIFICATION$KICKMESSAGE = "&6CasualProtector: &cYou have been recognized as a bot! Your account has been blocked for &610 minutes\n&cIf this is mistake, sorry";
        ANTIBOT$CHANNEL_VERIFICATION$BROADCAST = "&6CasualProtector: &8%player% &chas been recognized as a bot and his account is blocked now!";
        CONNECTION_CLOSER$MESSAGE_TO_OPS = "&cPlayer %player% has just tried to crash the server!";
        CONNECTION_CLOSER$SEND_MESSAGE_TO_OPS = false;
        CONNECTION_CLOSER$SEND_MESSAGE_TO_PLAYERS_WITH_PERM = "casualprotector.admin";
        BYPASS_WHEN_TPS_UNDER = 18.7;
        MAX_ENTITIES_ON_CHUNK = 1000;
        INTERACT$BLOCKED_ITEM = "This item has invalid nbt data (such as too many nbt tags or banned nbt tags)! You cannot interact with it.";
        ANTI_RUN_COMMANDS$WHEN_HOLDING_BOOK = "&6CProtector: &cYou cannot execute commands when you are holding a book in your hand! We sorry if it's annoying but it's only to protect you! It prevents executing unwanted commands such like paying someone money, adding his to your plot, or anything!";
        HACKED_ITEMS$OVER_ENCHANTED_ITEM_MESSAGE = "&6CProtector: &cYou had over-enchanted item (too big enchantment level) in your inventory! We have fixed it with normal enchants, have a nice day! &6=)";
        HACKED_ITEMS$MAX_ENCHANTMENT_LEVEL = 10;
        HACKED_ITEMS$MAX_ITEMMETA_SIZE = 17384;
        HACKED_ITEMS$BYPASS_WHEN_HAS_LORE = Collections.singletonList("bypassLoreLOLOLOLO, when lore contains this text HackedItems module will skip the item. The line is checking as lower case so use \"test\" instead of \"Test\"");
        PACKET_FILTER$INVALID_PACKET = "Looks like you are sending invalid packets.. If its an error please contact with the administrator or on discord with yooniks#2411.";
        EXPLOIT$COMMANDS_ON_TRY = Collections.singletonList("msg yooniks That is an example of command! - Player %name% tried to crash a server! =(");
    }

    public MessagesConfig(Configuration a) {
        super((Configuration)a);
        MessagesConfig a2;
    }
}

