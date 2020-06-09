/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.http;

import java.net.URL;
import xyz.yooniks.protector.http.URLHelper;

public final class UpdateChecker {
    private String version = "undefined";

    public boolean isValid(String a) {
        try {
            UpdateChecker a2;
            String string = "https://api.spigotmc.org/legacy/update.php?resource=59866";
            a2.version = URLHelper.readContent((URL)new URL((String)"https://api.spigotmc.org/legacy/update.php?resource=59866"));
            return a.equalsIgnoreCase((String)a2.version);
        }
        catch (Exception exception) {
            return true;
        }
    }

    public UpdateChecker() {
        UpdateChecker a;
    }

    public String getVersion() {
        UpdateChecker a;
        return a.version;
    }
}

