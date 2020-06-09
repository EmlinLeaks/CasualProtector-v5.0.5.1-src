/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.md_5.bungee.api.connection.PendingConnection
 *  net.md_5.bungee.api.event.PreLoginEvent
 *  net.md_5.bungee.api.plugin.Listener
 *  net.md_5.bungee.event.EventHandler
 */
package xyz.yooniks.protector.bungee.listener;

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
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.yooniks.protector.vpn.FileWriter;
import xyz.yooniks.protector.vpn.StringReplacer;

public class AddressBlocker
implements Runnable,
Listener {
    private final Set<String> blockedAddresses = new HashSet<String>();
    private final File addressesFile;
    private final String messsage;

    public void save() {
        AddressBlocker a;
        Collection collection = a.read((File)a.addressesFile);
        collection = (Collection)a.blockedAddresses.stream().filter(a2 -> {
            if (collection.contains((Object)a2)) return false;
            return true;
        }).collect(Collectors.<T>toList());
        try {
            FileWriter.writeLines((File)a.addressesFile, (Collection<String>)collection);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    private /* synthetic */ boolean isBlocked(String a) {
        AddressBlocker a2;
        return a2.blockedAddresses.contains((Object)a);
    }

    @Override
    public void run() {
        try {
            AddressBlocker a;
            if (!a.addressesFile.exists()) {
                a.addressesFile.createNewFile();
            }
            a.scan((File)a.addressesFile);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    private /* synthetic */ Collection<String> read(File a) {
        try {
            if (!((File)a).exists()) {
                ((File)a).createNewFile();
            }
            HashSet<String> hashSet = new HashSet<String>();
            a = new Scanner((File)a);
            while (((Scanner)a).hasNextLine()) {
                String string = StringReplacer.replace((String)((Scanner)a).nextLine(), (String)" ", (String)"");
                if (string.startsWith((String)"##") || string.isEmpty()) continue;
                hashSet.add((String)string);
            }
            return hashSet;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    private /* synthetic */ void scan(File a) {
        AddressBlocker a2;
        a2.blockedAddresses.addAll(a2.read((File)a));
    }

    @EventHandler
    public void onJoin(PreLoginEvent a) {
        AddressBlocker a2;
        if (!a2.isBlocked((String)a.getConnection().getAddress().getHostName())) return;
        a.setCancelled((boolean)true);
        a.setCancelReason((BaseComponent[])new BaseComponent[]{new TextComponent((String)ChatColor.translateAlternateColorCodes((char)'&', (String)a2.messsage))});
    }

    public AddressBlocker(File a, String a2) {
        AddressBlocker a3;
        a3.addressesFile = a;
        a3.messsage = a2;
    }
}

