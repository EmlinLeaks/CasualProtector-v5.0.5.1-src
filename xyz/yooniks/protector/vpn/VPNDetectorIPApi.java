/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.vpn;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import xyz.yooniks.protector.vpn.AddressInfoResponse;
import xyz.yooniks.protector.vpn.VPNDetector;

public final class VPNDetectorIPApi
implements VPNDetector,
AddressInfoResponse.VPNResponsable {
    private static final Gson GSON = new Gson();
    private final boolean limitable;
    private final int timeout;
    private final int limit;
    private final String name = "ip-api (http://ip-api.com/json/{address})";
    private final AtomicInteger count = new AtomicInteger();
    private final List<String> allowedCountries;

    @Override
    public int count() {
        VPNDetectorIPApi a;
        return a.count.getAndIncrement();
    }

    @Override
    public boolean isBad(String a) throws IOException {
        VPNDetectorIPApi a2;
        if (((AddressInfoResponse)(a = a2.info((String)a))).isProxy()) return true;
        if (a2.allowedCountries.contains((Object)((AddressInfoResponse)a).getCountry())) return false;
        return true;
    }

    @Override
    public int getLimit() {
        VPNDetectorIPApi a;
        return a.limit;
    }

    @Override
    public boolean isLimitable() {
        VPNDetectorIPApi a;
        return a.limitable;
    }

    @Override
    public void clearCount() {
        VPNDetectorIPApi a;
        a.count.set((int)0);
    }

    @Override
    public String getName() {
        VPNDetectorIPApi a;
        Objects.requireNonNull(a);
        return "ip-api (http://ip-api.com/json/{address})";
    }

    @Override
    public AddressInfoResponse info(String a) throws IOException {
        VPNDetectorIPApi a2;
        return GSON.fromJson((String)a2.query((String)("http://ip-api.com/json/" + a), (int)a2.timeout), AddressInfoResponse.class);
    }

    public VPNDetectorIPApi(List<String> a, int a2, int a3, boolean a4) {
        VPNDetectorIPApi a5;
        a5.allowedCountries = a;
        a5.timeout = a2;
        a5.limit = a3;
        a5.limitable = a4;
    }
}

