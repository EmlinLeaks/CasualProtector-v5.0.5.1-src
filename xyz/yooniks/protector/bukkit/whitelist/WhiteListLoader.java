/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 */
package xyz.yooniks.protector.bukkit.whitelist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import xyz.yooniks.protector.bukkit.ProtectorSpigotLogger;
import xyz.yooniks.protector.bukkit.whitelist.WhitelistManager;

public final class WhiteListLoader
implements Runnable {
    private final File blockedAddressesFile;
    private final WhitelistManager whitelistManager;
    private final File whitelistFile;

    private /* synthetic */ void scan(File a) throws FileNotFoundException {
        a = new Scanner((File)a);
        while (((Scanner)a).hasNextLine()) {
            WhiteListLoader a2;
            String string = StringUtils.replace((String)((Scanner)a).nextLine(), (String)" ", (String)"");
            if (string.startsWith((String)"##") || string.isEmpty()) continue;
            a2.whitelistManager.addWhitelist((String)string);
        }
    }

    public WhiteListLoader(WhitelistManager a, File a2, File a3) {
        WhiteListLoader a4;
        a4.whitelistFile = a2;
        a4.whitelistManager = a;
        a4.blockedAddressesFile = a3;
    }

    @Override
    public void run() {
        try {
            WhiteListLoader a;
            if (!a.whitelistFile.exists()) {
                a.whitelistFile.createNewFile();
            }
            if (!a.blockedAddressesFile.exists()) {
                a.blockedAddressesFile.createNewFile();
            }
            a.scan((File)a.whitelistFile);
            a.scan((File)a.blockedAddressesFile);
            ProtectorSpigotLogger.info((String)new StringBuilder().insert((int)0, (String)"Loaded ").append((int)a.whitelistManager.getWhitelist().size()).append((String)" whitelisted players, ").append((int)a.whitelistManager.getBlocked().size()).append((String)" blocked addresses!").toString());
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }
}

