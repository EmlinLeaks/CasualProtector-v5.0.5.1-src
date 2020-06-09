/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.api.plugin.Plugin
 *  net.md_5.bungee.api.plugin.PluginManager
 *  net.md_5.bungee.api.scheduler.ScheduledTask
 *  net.md_5.bungee.api.scheduler.TaskScheduler
 *  net.md_5.bungee.config.Configuration
 *  net.md_5.bungee.config.ConfigurationProvider
 *  net.md_5.bungee.config.YamlConfiguration
 */
package xyz.yooniks.protector.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.yooniks.protector.bungee.helper.MessageHelper;
import xyz.yooniks.protector.bungee.listener.AddressBlocker;
import xyz.yooniks.protector.bungee.listener.VPNListener;
import xyz.yooniks.protector.bungee.listener.normal.MaxAccountsListener;
import xyz.yooniks.protector.bungee.listener.normal.NamesLengthCheckListener;
import xyz.yooniks.protector.bungee.listener.normal.TooManyConnectionListener;
import xyz.yooniks.protector.bungee.listener.normal.TwoStepVerificationListener;
import xyz.yooniks.protector.bungee.listener.pluginmessage.BufferOverflowListener;
import xyz.yooniks.protector.bungee.listener.pluginmessage.ForgeListener;
import xyz.yooniks.protector.bungee.listener.pluginmessage.ProxyFixListener;
import xyz.yooniks.protector.bungee.listener.pluginmessage.WorldDownloaderListener;
import xyz.yooniks.protector.vpn.VPNDetector;
import xyz.yooniks.protector.vpn.VPNDetectorClearLimitTask;
import xyz.yooniks.protector.vpn.VPNDetectorDefault;
import xyz.yooniks.protector.vpn.VPNDetectorIPApi;

public class ProtectorBungee
extends Plugin {
    private static ProtectorBungee instance;
    private Configuration configuration;
    private Configuration messagesConfiguration;
    private AddressBlocker addressBlocker;
    private TooManyConnectionListener tooManyConnectionListener;

    public static ProtectorBungee getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        this.registerLogger();
        this.saveDefaultConfig();
        this.saveMessagesConfig();
        PluginManager pluginManager = this.getProxy().getPluginManager();
        if (this.getConfig().getBoolean((String)"block.buffer-overflow")) {
            pluginManager.registerListener((Plugin)this, (Listener)new BufferOverflowListener((boolean)this.getConfig().getBoolean((String)"payload.fully-book"), (int)this.getConfig().getInt((String)"payload.register-limit"), (String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"pluginmessage.register"))));
        }
        if (this.getConfig().getBoolean((String)"block.wdl")) {
            pluginManager.registerListener((Plugin)this, (Listener)new WorldDownloaderListener((String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"pluginmessage.wdl"))));
        }
        if (this.getConfig().getBoolean((String)"block.forge")) {
            pluginManager.registerListener((Plugin)this, (Listener)new ForgeListener((String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"pluginmessage.forge"))));
        }
        if (this.getConfig().getBoolean((String)"block.proxy")) {
            pluginManager.registerListener((Plugin)this, (Listener)new ProxyFixListener((String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"pluginmessage.proxy"))));
        }
        if (this.getConfig().getBoolean((String)"antibot.two-step-verification")) {
            pluginManager.registerListener((Plugin)this, (Listener)new TwoStepVerificationListener((String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"antibot.double-join"))));
        }
        if (this.getConfig().getBoolean((String)"antibot.max-accounts-per-ip.enabled")) {
            pluginManager.registerListener((Plugin)this, (Listener)new MaxAccountsListener((int)this.getConfig().getInt((String)"antibot.max-accounts-per-ip.amount"), (String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"antibot.too-many-accounts"))));
        }
        if (this.getConfig().getBoolean((String)"antibot.last-name-length-checker.enabled", (boolean)true)) {
            pluginManager.registerListener((Plugin)this, (Listener)new NamesLengthCheckListener((String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"antibot.too-many-connections", (String)"Too many connections! Wait a moment please")), (int)this.getConfig().getInt((String)"antibot.last-name-length-checker.sensibility", (int)4)));
            this.getLogger().info((String)"Enabled names length checker, too many connections in one seconds with similar nicknames will be closed!");
        }
        if (this.messagesConfiguration.getBoolean((String)"antibot.address-blocker-enabled")) {
            this.addressBlocker = new AddressBlocker((File)new File((File)this.getDataFolder(), (String)"blocked-addresses.txt"), (String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"antibot.address-blocker-blocked")));
            this.getProxy().getScheduler().runAsync((Plugin)this, (Runnable)this.addressBlocker);
            this.getProxy().getPluginManager().registerListener((Plugin)this, (Listener)this.addressBlocker);
        }
        if (this.messagesConfiguration.getBoolean((String)"antibot.vpn-enabled")) {
            ArrayList<VPNDetector> vpnDetectors = new ArrayList<VPNDetector>();
            for (String checkerId : this.getConfig().getSection((String)"bad-ip-checkers").getKeys()) {
                Configuration section = this.getConfig().getSection((String)("bad-ip-checkers." + checkerId));
                if (!section.getBoolean((String)"enabled")) continue;
                VPNDetector vpnDetector = checkerId.equalsIgnoreCase((String)"ipapi") ? new VPNDetectorIPApi((List<String>)section.getStringList((String)"allowed-countries"), (int)section.getInt((String)"timeout", (int)5000), (int)section.getInt((String)"limit", (int)148), (boolean)section.getBoolean((String)"limitable", (boolean)true)) : new VPNDetectorDefault((String)section.getString((String)"query"), (String)section.getString((String)"result"), (int)section.getInt((String)"timeout", (int)5000), (boolean)section.getBoolean((String)"must-equal", (boolean)true), (int)section.getInt((String)"limit", (int)148), (boolean)section.getBoolean((String)"limitable", (boolean)false));
                vpnDetectors.add((VPNDetector)vpnDetector);
                this.getLogger().info((String)("Loaded vpnDetector: " + vpnDetector.getName()));
            }
            if (vpnDetectors.size() > 0) {
                this.getProxy().getScheduler().schedule((Plugin)this, (Runnable)new VPNDetectorClearLimitTask(vpnDetectors), (long)1L, (TimeUnit)TimeUnit.MINUTES);
            }
            this.getProxy().getPluginManager().registerListener((Plugin)this, (Listener)new VPNListener((String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"antibot.vpn")), vpnDetectors));
        }
        if (!this.messagesConfiguration.getBoolean((String)"antibot.too-many-connections-enabled")) return;
        this.tooManyConnectionListener = new TooManyConnectionListener((String)MessageHelper.colored((String)this.messagesConfiguration.getString((String)"antibot.too-many-connections-message")), (int)this.messagesConfiguration.getInt((String)"antibot.too-many-connections-limit"), (File)new File((File)this.getDataFolder(), (String)"bypass.txt"));
        this.getProxy().getScheduler().runAsync((Plugin)this, (Runnable)this.tooManyConnectionListener);
        this.getProxy().getPluginManager().registerListener((Plugin)this, (Listener)this.tooManyConnectionListener);
    }

    public void onDisable() {
        if (this.addressBlocker != null) {
            this.addressBlocker.save();
        }
        if (this.tooManyConnectionListener == null) return;
        this.tooManyConnectionListener.save();
    }

    public Configuration getConfig() {
        return this.configuration;
    }

    private void saveDefaultConfig() {
        this.getDataFolder().mkdirs();
        File file = new File((File)this.getDataFolder(), (String)"config.yml");
        if (!file.exists()) {
            try {
                InputStream in = this.getResourceAsStream((String)"config.yml");
                Throwable throwable = null;
                try {
                    Files.copy((InputStream)in, (Path)file.toPath(), (CopyOption[])new CopyOption[0]);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (in != null) {
                        ProtectorBungee.$closeResource((Throwable)throwable, (AutoCloseable)in);
                    }
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load((File)new File((File)this.getDataFolder(), (String)"config.yml"));
            return;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveMessagesConfig() {
        this.getDataFolder().mkdirs();
        File file = new File((File)this.getDataFolder(), (String)"messagesbungee.yml");
        if (!file.exists()) {
            try {
                InputStream in = this.getResourceAsStream((String)"messagesbungee.yml");
                Throwable throwable = null;
                try {
                    Files.copy((InputStream)in, (Path)file.toPath(), (CopyOption[])new CopyOption[0]);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (in != null) {
                        ProtectorBungee.$closeResource((Throwable)throwable, (AutoCloseable)in);
                    }
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        try {
            this.messagesConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load((File)new File((File)this.getDataFolder(), (String)"messagesbungee.yml"));
            return;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void registerLogger() {
        Logger logger = this.getLogger();
        try {
            this.getDataFolder().mkdirs();
            File logsFile = new File((File)this.getDataFolder(), (String)"logs.log");
            logsFile.createNewFile();
            FileHandler fh = new FileHandler((String)(this.getDataFolder().getAbsolutePath() + "/logs.log"));
            logger.addHandler((Handler)fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter((Formatter)formatter);
            return;
        }
        catch (IOException | SecurityException ex) {
            ex.printStackTrace();
        }
    }

    private static /* synthetic */ void $closeResource(Throwable x0, AutoCloseable x1) {
        if (x0 == null) {
            x1.close();
            return;
        }
        try {
            x1.close();
            return;
        }
        catch (Throwable throwable) {
            x0.addSuppressed((Throwable)throwable);
            return;
        }
    }
}

