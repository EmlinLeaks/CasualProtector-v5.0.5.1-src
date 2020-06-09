/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.vpn;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import xyz.yooniks.protector.vpn.VPNDetector;

public class VPNDetectorClearLimitTask
implements Runnable {
    private final List<VPNDetector> detectors;

    public VPNDetectorClearLimitTask(List<VPNDetector> a) {
        VPNDetectorClearLimitTask a2;
        a2.detectors = a;
    }

    @Override
    public void run() {
        VPNDetectorClearLimitTask a;
        a.detectors.stream().filter(VPNDetector::isLimitable).forEach(VPNDetector::clearCount);
    }
}

