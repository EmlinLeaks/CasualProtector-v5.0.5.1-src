/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.vpn;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import xyz.yooniks.protector.vpn.StringReplacer;
import xyz.yooniks.protector.vpn.VPNDetector;

public final class VPNDetectorDefault
implements VPNDetector {
    private final String url;
    private final String result;
    private final int timeout;
    private final boolean mustEqual;
    private final int limit;
    private final boolean limitable;
    private final AtomicInteger count = new AtomicInteger();
    private final String name;

    public VPNDetectorDefault(String url, String result, int timeout, boolean mustEqual, int limit, boolean limitable) {
        this.url = url;
        this.result = result;
        this.timeout = timeout;
        this.mustEqual = mustEqual;
        this.limit = limit;
        this.limitable = limitable;
        this.name = "default (" + this.url + ")";
    }

    @Override
    public boolean isBad(String address) throws IOException {
        boolean bl;
        String query = this.query((String)StringReplacer.replace((String)this.url, (String)"{ADDRESS}", (String)address), (int)this.timeout);
        if (this.mustEqual) {
            bl = query.equalsIgnoreCase((String)this.result);
            return bl;
        }
        bl = query.toLowerCase().contains((CharSequence)this.result.toLowerCase());
        return bl;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isLimitable() {
        return this.limitable;
    }

    @Override
    public int getLimit() {
        return this.limit;
    }

    @Override
    public int count() {
        return this.count.getAndIncrement();
    }

    @Override
    public void clearCount() {
        this.count.set((int)0);
    }
}

