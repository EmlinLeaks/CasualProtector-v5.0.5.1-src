/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.PendingConnection
 *  net.md_5.bungee.api.event.LoginEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener.normal;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.yooniks.protector.vpn.FileWriter;
import xyz.yooniks.protector.vpn.StringReplacer;

public class TooManyConnectionListener
implements Listener,
Runnable {
    private final String kickMessage;
    private final int limit;
    private final Set<String> bypass = new HashSet<String>();
    private final File bypassFile;
    private long lastJoin;
    private int count;

    public TooManyConnectionListener(String kickMessage, int limit, File bypassFile) {
        this.kickMessage = kickMessage;
        this.limit = limit;
        this.bypassFile = bypassFile;
    }

    @EventHandler(priority=-64)
    public void onConnect(LoginEvent event) {
        String address = event.getConnection().getAddress().getHostString();
        if (this.bypass.contains((Object)address)) {
            return;
        }
        if (this.lastJoin > System.currentTimeMillis()) {
            ++this.count;
        } else {
            this.bypass.add((String)address);
            this.count = 0;
        }
        this.lastJoin = System.currentTimeMillis() + 1000L;
        if (this.count <= this.limit) return;
        event.setCancelled((boolean)true);
        event.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)this.kickMessage)});
    }

    public void save() {
        Collection<String> bypass = this.read((File)this.bypassFile);
        Collection toWrite = (Collection)this.bypass.stream().filter(ip -> {
            if (bypass.contains((Object)ip)) return false;
            return true;
        }).collect(Collectors.<T>toList());
        try {
            FileWriter.writeLines((File)this.bypassFile, (Collection<String>)toWrite);
            return;
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.bypass.addAll(this.read((File)this.bypassFile));
    }

    private Collection<String> read(File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            HashSet<String> bypass = new HashSet<String>();
            Scanner scanner = new Scanner((File)file);
            while (scanner.hasNextLine()) {
                String line = StringReplacer.replace((String)scanner.nextLine(), (String)" ", (String)"");
                if (line.startsWith((String)"##") || line.isEmpty()) continue;
                bypass.add((String)line);
            }
            return bypass;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }
}

