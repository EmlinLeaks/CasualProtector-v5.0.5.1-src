/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.whitelist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;
import xyz.yooniks.protector.vpn.StringReplacer;

public final class WhitelistUpdater
implements Runnable {
    private final WhitelistManager whitelistManager;
    private final File whitelistFile;

    public void save() {
        WhitelistUpdater a;
        Object object = a.read((File)a.whitelistFile);
        object = (Collection)a.whitelistManager.getWhitelist().stream().filter(a2 -> {
            if (object.contains((Object)a2)) return false;
            return true;
        }).collect(Collectors.<T>toList());
        try {
            BufferedWriter bufferedWriter = new BufferedWriter((Writer)new FileWriter((File)a.whitelistFile));
            object = object.iterator();
            while (object.hasNext()) {
                String string = (String)object.next();
                bufferedWriter.write((String)(string + "\n"));
            }
            ((Writer)bufferedWriter).flush();
            ((Writer)bufferedWriter).close();
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
            ((Scanner)a).close();
            return hashSet;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }

    public WhitelistUpdater(WhitelistManager a, File a2) {
        WhitelistUpdater a3;
        a3.whitelistFile = a2;
        a3.whitelistManager = a;
    }

    @Override
    public void run() {
        WhitelistUpdater a;
        a.save();
    }
}

