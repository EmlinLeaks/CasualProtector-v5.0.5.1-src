/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.ProtocolManager
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketListener
 *  org.bukkit.Bukkit
 *  org.bukkit.Server
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.FileConfigurationOptions
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginLoader
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.plugin.messaging.Messenger
 *  org.bukkit.plugin.messaging.PluginMessageListener
 *  org.bukkit.plugin.messaging.PluginMessageListenerRegistration
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.scheduler.BukkitTask
 */
package xyz.yooniks.protector.bukkit;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import test.H;
import test.K;
import test.c;
import test.m;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser_1_8_8;
import xyz.yooniks.protector.bukkit.closer.ConnectionCloser_Undefined;
import xyz.yooniks.protector.bukkit.command.CasualProtectorCommand;
import xyz.yooniks.protector.bukkit.config.MessagesConfig;
import xyz.yooniks.protector.bukkit.config.system.ConfigHelper;
import xyz.yooniks.protector.bukkit.config.system.Configuration;
import xyz.yooniks.protector.bukkit.helper.NBTHelper;
import xyz.yooniks.protector.bukkit.listener.normal.AntiRunCommandsListener;
import xyz.yooniks.protector.bukkit.listener.normal.AntiWorldEditExploitListener;
import xyz.yooniks.protector.bukkit.listener.normal.BannedNicknamesListener;
import xyz.yooniks.protector.bukkit.listener.normal.BooksAndInteractListener;
import xyz.yooniks.protector.bukkit.listener.normal.ChannelVerificationListener;
import xyz.yooniks.protector.bukkit.listener.normal.ChunkCleanerListener;
import xyz.yooniks.protector.bukkit.listener.normal.ConnectionThrottleListener;
import xyz.yooniks.protector.bukkit.listener.normal.CrashTryListener;
import xyz.yooniks.protector.bukkit.listener.normal.HackedItemsListener;
import xyz.yooniks.protector.bukkit.listener.normal.InvalidMessageListener;
import xyz.yooniks.protector.bukkit.listener.normal.LoggedFromAnotherLocationListener;
import xyz.yooniks.protector.bukkit.listener.normal.MaxAccountsListener;
import xyz.yooniks.protector.bukkit.listener.normal.NameLengthCheckListener;
import xyz.yooniks.protector.bukkit.listener.normal.OverEnchantedItemsListener;
import xyz.yooniks.protector.bukkit.listener.normal.ShulkerBoxListener;
import xyz.yooniks.protector.bukkit.listener.normal.SignExploitListener;
import xyz.yooniks.protector.bukkit.listener.normal.SimilarNamesListener;
import xyz.yooniks.protector.bukkit.listener.normal.SpoofUUIDExploitListener;
import xyz.yooniks.protector.bukkit.listener.normal.TooManyItemsListener;
import xyz.yooniks.protector.bukkit.listener.normal.TwoStepVerificationListener;
import xyz.yooniks.protector.bukkit.listener.normal.VPNListener;
import xyz.yooniks.protector.bukkit.listener.packet.ClientArmAnimationListener;
import xyz.yooniks.protector.bukkit.listener.packet.ClientBlockPlaceListener;
import xyz.yooniks.protector.bukkit.listener.packet.ClientPositionPacket;
import xyz.yooniks.protector.bukkit.listener.packet.ClientWindowClickListener;
import xyz.yooniks.protector.bukkit.listener.packet.DebugModeListener;
import xyz.yooniks.protector.bukkit.listener.packet.limit.initializers.LimiterInitializer;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.BookEditListener;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.ForgeListener;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.LabyModListener;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.LiteLoaderListener;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.ProxyFixListener;
import xyz.yooniks.protector.bukkit.listener.packet.pluginmessage.WorldDownloaderListener;
import xyz.yooniks.protector.bukkit.multiplayer.MultiPlayerManager;
import xyz.yooniks.protector.bukkit.redstone.RedstoneManager;
import xyz.yooniks.protector.bukkit.task.MemoryInfoTask;
import xyz.yooniks.protector.bukkit.task.PingCheckerTask;
import xyz.yooniks.protector.bukkit.tps.Ticking;
import xyz.yooniks.protector.bukkit.tps.Tps;
import xyz.yooniks.protector.bukkit.whitelist.WhiteListLoader;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;
import xyz.yooniks.protector.bukkit.wrapper.WrapperPlayServerSpawnEntity;
import xyz.yooniks.protector.http.UpdateChecker;
import xyz.yooniks.protector.vpn.VPNDetector;
import xyz.yooniks.protector.vpn.VPNDetectorClearLimitTask;
import xyz.yooniks.protector.vpn.VPNDetectorDefault;
import xyz.yooniks.protector.vpn.VPNDetectorIPApi;

public final class ProtectorSpigot
extends JavaPlugin {
    private ConnectionCloser connectionCloser;
    private WhitelistManager whitelistManager;
    private ProtocolManager protocolManager;

    public WhitelistManager getWhitelistManager() {
        ProtectorSpigot a;
        return a.whitelistManager;
    }

    private /* synthetic */ ConnectionCloser setupConnectionCloser() {
        ProtectorSpigot a;
        String string;
        try {
            string = Bukkit.getServer().getClass().getPackage().getName().replace((CharSequence)".", (CharSequence)",").split((String)",")[3];
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            a.getLogger().info((String)"Couldn't get info about NMS version! =(");
            return new ConnectionCloser_Undefined((Plugin)a);
        }
        a.getLogger().info((String)new StringBuilder().insert((int)0, (String)"Server is running on: ").append((String)string).append((String)" NMS version").toString());
        if (!string.equals((Object)"v1_8_R3")) return new ConnectionCloser_Undefined((Plugin)a);
        a.getLogger().info((String)"Your server is running on 1.8 version, please download paperspigot-1.8.8 with some patches from readme.txt(you can find it in the downloaded .zip file)!");
        return new ConnectionCloser_1_8_8();
    }

    public ProtectorSpigot() {
        ProtectorSpigot a;
    }

    public void onEnable() {
        Object object;
        Object object2;
        Object object3;
        ProtectorSpigot a;
        String string;
        if (!a.getServer().getPluginManager().isPluginEnabled((String)"ProtocolLib")) {
            a.getLogger().warning((String)"Plugin ProtocolLib is not enabled! Download it, and enable plugin again!");
            a.getPluginLoader().disablePlugin((Plugin)a);
            return;
        }
        a.getConfig().options().copyDefaults((boolean)true);
        a.saveDefaultConfig();
        a.getLogger().info((String)"Plugins that we RECOMMEND to use with casualprotector (to have 100%+ protection): https://www.spigotmc.org/resources/dupe-fixes-illegal-stack-remover.44411/ - it's not required but you can just use it. Remember that if there are massive bots attacks (2k bots/sec) you have to block it using iptales, it's impossible to block it by minecraft plugin");
        ProtectorSpigotLogger.setPrintEverything((boolean)a.getConfig().getBoolean((String)"print-everything", (boolean)false));
        ProtectorSpigotLogger.setup((Plugin)a);
        a.getLogger().info((String)"Loading...");
        long l = System.currentTimeMillis();
        a.getServer().getScheduler().runTaskAsynchronously((Plugin)a, () -> {
            ProtectorSpigot a;
            if (!a.getConfig().getBoolean((String)"check-for-updates", (boolean)true)) return;
            UpdateChecker updateChecker = new UpdateChecker();
            a.getLogger().info((String)"Checking for updates..");
            if (updateChecker.isValid((String)a.getDescription().getVersion())) {
                a.getLogger().info((String)new StringBuilder().insert((int)0, (String)"Your version is newest! =) ").append((String)a.getDescription().getVersion()).toString());
            } else {
                a.getLogger().info((String)new StringBuilder().insert((int)0, (String)"Your version is old and not supported, your version: ").append((String)a.getDescription().getVersion()).append((String)", the latest version: ").append((String)updateChecker.getVersion()).append((String)" | please download the latest update: https://www.spigotmc.org/resources/casualprotector-best-server-protector-antibot-anticrash.59866/").toString());
            }
            a.getLogger().info((String)"Checked update info!");
        });
        if (a.getConfig().getBoolean((String)"replace-to-old", (boolean)false) && !a.getConfig().isBoolean((String)"window-click.check-last-packet")) {
            a.getLogger().info((String)"Detected old config version, renaming current config to old_config.yml and creating the new version.. Please check the new config and re-configure it.");
            object3 = new File((File)a.getDataFolder(), (String)"config.yml");
            ((File)object3).renameTo((File)new File((File)a.getDataFolder(), (String)"old_config.yml"));
            a.saveDefaultConfig();
            a.reloadConfig();
        }
        try {
            string = Bukkit.getServer().getClass().getPackage().getName().replace((CharSequence)".", (CharSequence)",").split((String)",")[3];
            object3 = new MultiPlayerManager((boolean)(string.contains((CharSequence)"1_9") || string.contains((CharSequence)"1_10") || string.contains((CharSequence)"1_11") || string.contains((CharSequence)"1_12") || string.contains((CharSequence)"1_13") || string.contains((CharSequence)"1_14")));
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            a.getLogger().info((String)"Could get info about server version! =( Using 1.8 multiplayermanager.");
            object3 = new MultiPlayerManager((boolean)false);
        }
        string = a.getServer().getPluginManager();
        PluginDescriptionFile pluginDescriptionFile = a.getDescription();
        if (!pluginDescriptionFile.getName().equalsIgnoreCase((String)"CasualProtector")) {
            a.getLogger().warning((String)"Looks like someone edited our plugin.yml! CasualProtector plugin name isn't CasualProtector.. Please use the latest version from spigotmc and don't change plugin.yml.");
            string.disablePlugin((Plugin)a);
            return;
        }
        if (!pluginDescriptionFile.getAuthors().contains((Object)"yooniks")) {
            a.getLogger().warning((String)"Looks like someone edited our plugin.yml! Authors doesn't contain yooniks. Please use the latest version from spigotmc and don't change plugin.yml.");
            string.disablePlugin((Plugin)a);
            return;
        }
        a.protocolManager = ProtocolLibrary.getProtocolManager();
        MessagesConfig.setConfiguration((Configuration)ConfigHelper.create((File)new File((File)a.getDataFolder(), (String)"messages.yml"), MessagesConfig.class));
        a.connectionCloser = a.setupConnectionCloser();
        if (a.connectionCloser instanceof ConnectionCloser_1_8_8) {
            string.registerEvents((Listener)((ConnectionCloser_1_8_8)a.connectionCloser), (Plugin)a);
        } else {
            if (!(a.connectionCloser instanceof ConnectionCloser_Undefined)) {
                a.getLogger().warning((String)"Error while registering connectioncloser listener");
                a.getServer().getPluginManager().disablePlugin((Plugin)a);
                return;
            }
            string.registerEvents((Listener)((ConnectionCloser_Undefined)a.connectionCloser), (Plugin)a);
        }
        if (string.getPlugin((String)"MyItems") != null) {
            a.getLogger().info((String)"MyItems plugin detected, WE RECOMMEND TO DISABLE hacked-items option in our configuration.");
        }
        if (string.getPlugin((String)"AdvancedEnchantments") != null) {
            a.getLogger().info((String)"AdvancedEnchantments plugin detected, WE RECOMMEND TO DISABLE hacked-items option in our configuration.");
        }
        NBTHelper.BLOCK_FIREWORKS = a.getConfig().getBoolean((String)"nbthelper.remove-firework-data", (boolean)true);
        boolean bl = a.getServer().getVersion().contains((CharSequence)"1.14");
        if (!a.getServer().getVersion().contains((CharSequence)"1.7")) {
            try {
                String string2 = ProtocolLibrary.getPlugin().getDescription().getVersion();
                if (bl && !string2.contains((CharSequence)"4.5.0")) {
                    a.getLogger().warning((String)"Looks like you are running 1.14 version but you don't use 4.5.0 protocollib! Please download the latest dev build here: http://ci.dmulloy2.net/job/ProtocolLib%20Gradle/lastStableBuild/");
                } else {
                    a.getLogger().info((String)new StringBuilder().insert((int)0, (String)"You are using ").append((String)string2).append((String)" protocolLib version.\n Just quickly reminder: if you are running 1.14 - use the latest dev build (http://ci.dmulloy2.net/job/ProtocolLib%20Gradle/lastStableBuild/) if 1.8-1.13 - use the latest release (https://www.spigotmc.org/resources/protocollib.1997/download?version=241216) if 1.7 and below - use the 3.7.0 release (https://github.com/dmulloy2/ProtocolLib/releases/tag/3.7.0)").toString());
                }
            }
            catch (Throwable throwable) {
                a.getLogger().warning((String)"Couldn't get protocollib version! Probably 1.7 spigot.");
            }
        }
        boolean bl2 = a.getConfig().getBoolean((String)"antibot.disable-all-features", (boolean)false);
        new LimiterInitializer((Plugin)a);
        NBTHelper.ADVANCED_DESERIALIZATION = a.getConfig().getBoolean((String)"window-click.advanced-deserialization", (boolean)false);
        NBTHelper.MAX_SIMILAR_PAGES = a.getConfig().getInt((String)"window-click.max-similar-pages", (int)10);
        if (a.getConfig().getBoolean((String)"debug-mode")) {
            a.protocolManager.addPacketListener((PacketListener)new DebugModeListener((ProtectorSpigot)a));
            a.getLogger().info((String)"Debug-mode enabled! Now all packets from players will be displayed in logs!");
        }
        if (!bl2 && a.getConfig().getBoolean((String)"antibot.check-nicknames.enabled", (boolean)true)) {
            string.registerEvents((Listener)new BannedNicknamesListener((List<String>)a.getConfig().getStringList((String)"antibot.check-nicknames.blocked")), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"block.too-big-slot", (boolean)true)) {
            a.protocolManager.addPacketListener((PacketListener)new m((ProtectorSpigot)a, (Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, (PacketType[])new PacketType[]{PacketType.Play.Client.WINDOW_CLICK}));
        }
        NBTHelper.BLOCK_SIGNS_WITH_NBT = a.getConfig().getBoolean((String)"sign-exploit.block-already-existing-nbt", (boolean)true);
        NBTHelper.BLOCK_INVALID_CHARS_IN_BOOK = a.getConfig().getBoolean((String)"invalid-chars.filter-books", (boolean)false);
        NBTHelper.CHECK_SHULKER_BOXES = a.getConfig().getBoolean((String)"nbthelper.check-shulker-boxes", (boolean)true);
        if (NBTHelper.CHECK_SHULKER_BOXES) {
            string.registerEvents((Listener)new ShulkerBoxListener(), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"too-many-items.enabled")) {
            string.registerEvents((Listener)new TooManyItemsListener((int)a.getConfig().getInt((String)"too-many-items.limit")), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"invalid-chars.enabled")) {
            string.registerEvents((Listener)new InvalidMessageListener((List<Character>)a.getConfig().getCharacterList((String)"invalid-chars.skipped"), (int)a.getConfig().getInt((String)"invalid-chars.cancel-only-when-count-bigger-than", (int)15)), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"anti-run-commands.enabled", (boolean)true)) {
            NBTHelper.ANTI_RUN_COMMANDS.addAll((Collection<String>)a.getConfig().getStringList((String)"anti-run-commands.blocked-commands"));
            string.registerEvents((Listener)new AntiRunCommandsListener((List<String>)a.getConfig().getStringList((String)"anti-run-commands.blocked-commands"), (List<String>)a.getConfig().getStringList((String)"anti-run-commands.whitelisted-commands"), (MultiPlayerManager)object3), (Plugin)a);
        }
        RedstoneManager.getInstance().init();
        string.registerEvents((Listener)new c((ProtectorSpigot)a), (Plugin)a);
        a.whitelistManager = new WhitelistManager();
        if (!bl2 && a.getConfig().getBoolean((String)"antibot.channel-verification.enabled")) {
            object2 = new ChannelVerificationListener((boolean)a.getConfig().getBoolean((String)"antibot.channel-verification.block", (boolean)false), (WhitelistManager)a.whitelistManager, (int)a.getConfig().getInt((String)"antibot.channel-verification.block-minutes", (int)5));
            object = a.getServer().getVersion();
            if (((String)object).contains((CharSequence)"1.13") || ((String)object).contains((CharSequence)"1.14")) {
                a.getLogger().info((String)"Using minecraft:brand instead of MC|Brand as a channel in antibot (channel-verification). (1.13.x, 1.14.x)");
            }
            a.getServer().getMessenger().registerIncomingPluginChannel((Plugin)a, (String)(((String)object).contains((CharSequence)"1.13") || ((String)object).contains((CharSequence)"1.14") || ((String)object).contains((CharSequence)"1.15") ? "minecraft:brand" : "MC|Brand"), (PluginMessageListener)object2);
            string.registerEvents((Listener)object2, (Plugin)a);
            ((ChannelVerificationListener)object2).runTask((Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"ping.enabled")) {
            boolean bl3 = a.getConfig().getBoolean((String)"ping.check-tps-before");
            a.getServer().getScheduler().runTaskTimer((Plugin)a, (Runnable)new PingCheckerTask((int)a.getConfig().getInt((String)"ping.limit"), (boolean)bl3), (long)0L, (long)200L);
        }
        if (a.getConfig().getBoolean((String)"memory-info.enabled", (boolean)false)) {
            a.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)a, (Runnable)new MemoryInfoTask(), (long)200L, (long)(20L * (long)a.getConfig().getInt((String)"memory-info.time", (int)60)));
        }
        a.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)a, (Runnable)new Ticking(), (long)30L, (long)30L);
        if (!bl2) {
            object2 = new ArrayList<E>();
            for (Object object4 : a.getConfig().getConfigurationSection((String)"bad-ip-checkers").getKeys((boolean)false)) {
                ConfigurationSection configurationSection = a.getConfig().getConfigurationSection((String)new StringBuilder().insert((int)0, (String)"bad-ip-checkers.").append((String)object4).toString());
                if (!configurationSection.isBoolean((String)"enabled") || !configurationSection.getBoolean((String)"enabled")) continue;
                object4 = ((String)object4).equalsIgnoreCase((String)"ipapi") ? new VPNDetectorIPApi((List<String>)configurationSection.getStringList((String)"allowed-countries"), (int)configurationSection.getInt((String)"timeout", (int)5000), (int)configurationSection.getInt((String)"limit", (int)148), (boolean)configurationSection.getBoolean((String)"limitable", (boolean)true)) : new VPNDetectorDefault((String)configurationSection.getString((String)"query"), (String)configurationSection.getString((String)"result"), (int)configurationSection.getInt((String)"timeout", (int)5000), (boolean)configurationSection.getBoolean((String)"must-equal", (boolean)true), (int)configurationSection.getInt((String)"limit", (int)148), (boolean)configurationSection.getBoolean((String)"limitable", (boolean)false));
                object2.add(object4);
                a.getLogger().info((String)new StringBuilder().insert((int)0, (String)"Loaded vpnDetector: ").append((String)object4.getName()).toString());
            }
            if (object2.size() > 0) {
                a.getServer().getScheduler().runTaskTimerAsynchronously((Plugin)a, (Runnable)new VPNDetectorClearLimitTask((List<VPNDetector>)object2), (long)200L, (long)1200L);
            }
            a.getServer().getPluginManager().registerEvents((Listener)new VPNListener((List<VPNDetector>)object2, (WhitelistManager)a.whitelistManager), (Plugin)a);
            a.getServer().getScheduler().runTaskAsynchronously((Plugin)a, (Runnable)new WhiteListLoader((WhitelistManager)a.whitelistManager, (File)new File((File)a.getDataFolder(), (String)"bypass.txt"), (File)new File((File)a.getDataFolder(), (String)"blocked-addresses.txt")));
        }
        object2 = new ClientWindowClickListener((ProtectorSpigot)a, (boolean)a.getConfig().getBoolean((String)"window-click.check-last-packet", (boolean)true), (int)a.getConfig().getInt((String)"window-click.check-last-packet-limit", (int)40));
        a.protocolManager.addPacketListener((PacketListener)object2);
        a.getServer().getPluginManager().registerEvents((Listener)object2, (Plugin)a);
        if (a.getConfig().getBoolean((String)"logged-from-another-location.block", (boolean)false)) {
            string.registerEvents((Listener)new LoggedFromAnotherLocationListener(), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"sign-exploit.block", (boolean)true)) {
            string.registerEvents((Listener)new SignExploitListener((ConnectionCloser)a.connectionCloser), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"hacked-items.block", (boolean)true)) {
            string.registerEvents((Listener)new HackedItemsListener((ConnectionCloser)a.connectionCloser, (List<String>)a.getConfig().getStringList((String)"hacked-items.bypass-worlds")), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"world-edit-exploit.block", (boolean)true)) {
            string.registerEvents((Listener)new AntiWorldEditExploitListener(), (Plugin)a);
        }
        string.registerEvents((Listener)new BooksAndInteractListener((ConnectionCloser)a.connectionCloser, (MultiPlayerManager)object3), (Plugin)a);
        if (a.getConfig().getBoolean((String)"block-place-listener")) {
            a.protocolManager.addPacketListener((PacketListener)new ClientBlockPlaceListener((ProtectorSpigot)a, (boolean)a.getConfig().getBoolean((String)"block.hopper-creative-crasher")));
        }
        if (a.getConfig().getBoolean((String)"anti-uuid-spoof.enabled", (boolean)false)) {
            string.registerEvents((Listener)new SpoofUUIDExploitListener((WhitelistManager)a.whitelistManager, (Plugin)a, (boolean)a.getConfig().getBoolean((String)"anti-uuid-spoof.run-task", (boolean)false)), (Plugin)a);
        }
        NBTHelper.BLOCK_ADDRESS_ON_INVALID_NBT = a.getConfig().getBoolean((String)"nbthelper.block-address-if-invalid");
        NBTHelper.ENABLED = a.getConfig().getBoolean((String)"nbthelper.enabled", (boolean)true);
        if (!(a.getServer().getVersion().contains((CharSequence)"1.12") || a.getServer().getVersion().contains((CharSequence)"1.10") || a.getServer().getVersion().contains((CharSequence)"1.11") || a.getServer().getVersion().contains((CharSequence)"1.13") || bl || !a.getConfig().getBoolean((String)"arm-animation.enabled"))) {
            object = new ClientArmAnimationListener((Plugin)a, (long)a.getConfig().getLong((String)"arm-animation.timestamp"));
            a.protocolManager.addPacketListener((PacketListener)object);
            string.registerEvents((Listener)object, (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"block.big-position", (boolean)true)) {
            object = new ClientPositionPacket((ProtectorSpigot)a);
            a.protocolManager.addPacketListener((PacketListener)object);
            string.registerEvents((Listener)object, (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"chunk-cleaner")) {
            string.registerEvents((Listener)new ChunkCleanerListener((Logger)a.getLogger()), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"too-many-items.enabled")) {
            string.registerEvents((Listener)new TooManyItemsListener((int)a.getConfig().getInt((String)"too-many-items.limit")), (Plugin)a);
        }
        if (!bl2 && a.getConfig().getBoolean((String)"antibot.name-similarity-checker.enabled", (boolean)true)) {
            a.getServer().getPluginManager().registerEvents((Listener)new SimilarNamesListener((int)a.getConfig().getInt((String)"antibot.name-similarity-checker.max-similar-names", (int)3), (int)a.getConfig().getInt((String)"antibot.name-similarity-checker.start", (int)0), (int)a.getConfig().getInt((String)"antibot.name-similarity-checker.end", (int)4)), (Plugin)a);
        }
        if (!bl2 && a.getConfig().getBoolean((String)"antibot.last-name-length-checker.enabled", (boolean)true)) {
            a.getServer().getPluginManager().registerEvents((Listener)new NameLengthCheckListener((int)a.getConfig().getInt((String)"antibot.last-name-length-checker.sensibility", (int)3), (WhitelistManager)a.whitelistManager), (Plugin)a);
        }
        if (!bl2 && a.getConfig().getBoolean((String)"antibot.connection-throttle.enabled", (boolean)true)) {
            a.getServer().getPluginManager().registerEvents((Listener)new ConnectionThrottleListener((long)a.getConfig().getLong((String)"antibot.connection-throttle.delay", (long)30L)), (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"block-payload-book-edit.enabled")) {
            a.protocolManager.addPacketListener((PacketListener)new BookEditListener((ProtectorSpigot)a, (boolean)a.getConfig().getBoolean((String)"block-payload-book-edit.block-fully"), (boolean)a.getConfig().getBoolean((String)"block-payload-book-edit.deserialize")));
        }
        if (a.getConfig().getBoolean((String)"block.wdl")) {
            a.protocolManager.addPacketListener((PacketListener)new WorldDownloaderListener((ProtectorSpigot)a, (List<String>)a.getConfig().getStringList((String)"block.wdl-bypass")));
        }
        if (a.getConfig().getBoolean((String)"block.forge")) {
            object = new ForgeListener((ProtectorSpigot)a, (List<String>)a.getConfig().getStringList((String)"block.forge-bypass"));
            a.protocolManager.addPacketListener((PacketListener)object);
            string.registerEvents((Listener)object, (Plugin)a);
        }
        if (a.getConfig().getBoolean((String)"block.lmc", (boolean)false)) {
            a.protocolManager.addPacketListener((PacketListener)new LabyModListener((ProtectorSpigot)a, (List<String>)a.getConfig().getStringList((String)"block.lmc-bypass")));
        }
        if (a.getConfig().getBoolean((String)"block.liteloader", (boolean)false)) {
            a.protocolManager.addPacketListener((PacketListener)new LiteLoaderListener((ProtectorSpigot)a, (List<String>)a.getConfig().getStringList((String)"block.liteloader-bypass")));
        }
        if (a.getConfig().getBoolean((String)"block.proxy")) {
            a.protocolManager.addPacketListener((PacketListener)new ProxyFixListener((ProtectorSpigot)a));
        }
        if (MessagesConfig.EXPLOIT$COMMANDS_ON_TRY.size() > 0 && !MessagesConfig.EXPLOIT$COMMANDS_ON_TRY.get((int)0).contains((CharSequence)"msg yooniks")) {
            string.registerEvents((Listener)new CrashTryListener(), (Plugin)a);
        }
        if (!bl2 && a.getConfig().getBoolean((String)"antibot.two-step-verification")) {
            string.registerEvents((Listener)new TwoStepVerificationListener((WhitelistManager)a.whitelistManager), (Plugin)a);
        }
        if (!bl2 && a.getConfig().getBoolean((String)"antibot.max-accounts-per-ip.enabled")) {
            string.registerEvents((Listener)new MaxAccountsListener((int)a.getConfig().getInt((String)"antibot.max-accounts-per-ip.amount"), (WhitelistManager)a.whitelistManager), (Plugin)a);
        }
        NBTHelper.PAGE_LENGTH = a.getConfig().getInt((String)"nbthelper.page-length-limit", (int)500);
        if (a.getConfig().getBoolean((String)"hacked-items.block-over-enchanted-items", (boolean)false)) {
            string.registerEvents((Listener)new OverEnchantedItemsListener((MultiPlayerManager)object3, (List<String>)a.getConfig().getStringList((String)"hacked-items.bypass-worlds")), (Plugin)a);
        }
        if (!bl && a.getConfig().getBoolean((String)"optimize-fps.enabled", (boolean)true)) {
            boolean bl4 = a.getConfig().getBoolean((String)"optimize-fps.firework", (boolean)false);
            if (bl4) {
                string.registerEvents((Listener)new K((ProtectorSpigot)a), (Plugin)a);
            }
            a.protocolManager.addPacketListener((PacketListener)new H((ProtectorSpigot)a, (Plugin)a, (ListenerPriority)ListenerPriority.HIGHEST, (PacketType[])new PacketType[]{WrapperPlayServerSpawnEntity.TYPE}, (boolean)bl4));
        }
        Tps.start((Plugin)a);
        a.getCommand((String)"casualprotector").setExecutor((CommandExecutor)new CasualProtectorCommand((Plugin)a, (WhitelistManager)a.whitelistManager));
        a.getLogger().info((String)new StringBuilder().insert((int)0, (String)"Loaded in ").append((long)(System.currentTimeMillis() - l)).append((String)"ms! \n").toString());
        a.getLogger().info((String)"Please read readme.txt from the downloaded .zip file and the config.yml before using plugin, maybe you dont want to use some of features.");
        a.getLogger().info((String)"Plugin created by yooniks, plugin for protecting servers against bots and hackers! For any bugs please send me private message on spigotmc or discord: yooniks#2411.");
    }

    public static ProtectorSpigot getInstance() {
        return (ProtectorSpigot)ProtectorSpigot.getPlugin(ProtectorSpigot.class);
    }

    public ConnectionCloser getConnectionCloser() {
        ProtectorSpigot a;
        return a.connectionCloser;
    }
}

