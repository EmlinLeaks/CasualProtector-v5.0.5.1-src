/*
 * Decompiled with CFR <Could not determine version>.
 */
package xyz.yooniks.protector.bukkit.tps;

import java.util.Iterator;
import java.util.LinkedList;

public class Ticking
implements Runnable {
    private final LinkedList<Double> history = new LinkedList<Double>();
    private static double result = 20.0;
    private transient long lastPoll = System.nanoTime();

    @Override
    public void run() {
        Ticking a;
        double d2;
        long l = System.nanoTime();
        long l2 = (l - a.lastPoll) / 1000L;
        if (l2 == 0L) {
            l2 = 1L;
        }
        if (a.history.size() > 10) {
            a.history.remove();
        }
        if ((d2 = 5.0E7 / (double)l2) <= 21.0) {
            a.history.add((Double)Double.valueOf((double)d2));
        }
        a.lastPoll = l;
        double d3 = 0.0;
        Iterator<E> iterator = a.history.iterator();
        do {
            if (!iterator.hasNext()) {
                result = d3 / (double)a.history.size();
                return;
            }
            Double d4 = (Double)iterator.next();
            if (d4 == null) continue;
            d3 += d4.doubleValue();
        } while (true);
    }

    public Ticking() {
        Ticking a;
        a.history.add(Double.valueOf((double)20.0));
    }

    public static double getTPS() {
        return result;
    }
}

