/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public final class URLHelper {
    private /* synthetic */ URLHelper() {
        URLHelper a;
    }

    public static String readContent(URL a) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = a.openConnection();
        ((URLConnection)object).setConnectTimeout((int)7500);
        ((URLConnection)object).setReadTimeout((int)7500);
        object = new BufferedReader((Reader)new InputStreamReader((InputStream)((URLConnection)object).getInputStream()));
        do {
            String string;
            if ((string = ((BufferedReader)object).readLine()) == null) {
                ((BufferedReader)object).close();
                return stringBuilder.toString();
            }
            stringBuilder.append((String)string);
        } while (true);
    }
}

