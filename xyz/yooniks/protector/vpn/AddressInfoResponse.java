/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.vpn;

public final class AddressInfoResponse {
    private String regionName;
    private String region;
    private String as;
    private String status;
    private boolean proxy;
    private String country;
    private String city;
    private String org;

    public String getOrg() {
        AddressInfoResponse a;
        return a.org;
    }

    public String getCountry() {
        AddressInfoResponse a;
        return a.country;
    }

    public String getStatus() {
        AddressInfoResponse a;
        return a.status;
    }

    public String getCity() {
        AddressInfoResponse a;
        return a.city;
    }

    public boolean isProxy() {
        AddressInfoResponse a;
        return a.proxy;
    }

    public String getAs() {
        AddressInfoResponse a;
        return a.as;
    }

    public String getRegion() {
        AddressInfoResponse a;
        return a.region;
    }

    public String toString() {
        AddressInfoResponse a;
        return new StringBuilder().insert((int)0, (String)"AddressInfoResponse{country='").append((String)a.country).append((char)'\'').append((String)", status='").append((String)a.status).append((char)'\'').append((String)", city='").append((String)a.city).append((char)'\'').append((String)", region='").append((String)a.region).append((char)'\'').append((String)", regionName='").append((String)a.regionName).append((char)'\'').append((String)", org='").append((String)a.org).append((char)'\'').append((String)", as='").append((String)a.as).append((char)'\'').append((String)", proxy=").append((boolean)a.proxy).append((char)'}').toString();
    }

    public AddressInfoResponse() {
        AddressInfoResponse a;
    }

    public String getRegionName() {
        AddressInfoResponse a;
        return a.regionName;
    }

    public AddressInfoResponse(boolean a) {
        AddressInfoResponse a2;
        a2.proxy = a;
    }
}

