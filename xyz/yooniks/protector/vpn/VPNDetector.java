/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.vpn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public interface VPNDetector {
    default public String query(String a3, int a22) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = (HttpURLConnection)new URL((String)a3).openConnection();
        ((URLConnection)object).setConnectTimeout((int)a22);
        ((URLConnection)object).addRequestProperty((String)"User-Agent", (String)"Casual-AntiBot Minecraft Plugin");
        if (((HttpURLConnection)object).getResponseCode() == 429) {
            throw new IOException((String)"Too many requests");
        }
        BufferedReader a22 = new BufferedReader((Reader)new InputStreamReader((InputStream)((URLConnection)object).getInputStream()));
        object = null;
        try {
            while ((a3 = a22.readLine()) != null) {
                stringBuilder.append((String)a3);
            }
            return stringBuilder.toString();
        }
        catch (Throwable a3) {
            object = a3;
            throw object;
        }
        finally {
            if (object != null) {
                try {
                    a22.close();
                }
                catch (Throwable a3) {
                    ((Throwable)object).addSuppressed((Throwable)a3);
                }
            } else {
                a22.close();
            }
        }
    }

    public boolean isBad(String var1) throws IOException;

    public int count();

    public String getName();

    public int getLimit();

    public boolean isLimitable();

    public void clearCount();
}

