package util;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements Metrics {
    private final Map<String, Long> map = new HashMap<>();
    public void inc(String key, long delta) { map.put(key, map.getOrDefault(key, 0L) + delta); }
    public long get(String key) { return map.getOrDefault(key, 0L); }
    public long time(Runnable r, String key) {
        long t0 = System.nanoTime(); r.run(); long dt = System.nanoTime() - t0; inc(key, dt); return dt;
    }
    public String toString() { return map.toString(); }
}
