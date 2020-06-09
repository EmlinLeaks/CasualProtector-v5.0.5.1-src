/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.vpn;

public final class StringReplacer {
    private /* synthetic */ StringReplacer() {
        StringReplacer a;
    }

    public static String replace(String a, String a2, String a3) {
        if (a == null) return a;
        if (a.isEmpty()) return a;
        if (a2.isEmpty()) {
            return a;
        }
        if (a3 == null) {
            a3 = "";
        }
        int n = 0;
        int n2 = -1;
        int n3 = a.indexOf((String)a2, (int)n);
        if (n3 == -1) {
            return a;
        }
        int n4 = a2.length();
        int n5 = a3.length() - n4;
        int n6 = n5 = n5 < 0 ? 0 : n5;
        StringBuilder stringBuilder = new StringBuilder((int)(a.length() + (n5 *= n2 < 0 ? 16 : (n2 > 64 ? 64 : n2))));
        while (n3 != -1) {
            stringBuilder.append((CharSequence)a, (int)n, (int)n3).append((String)a3);
            n = n3 + n4;
            if (--n2 == 0) break;
            n3 = a.indexOf((String)a2, (int)n);
        }
        stringBuilder.append((String)a.substring((int)n));
        return stringBuilder.toString();
    }
}

