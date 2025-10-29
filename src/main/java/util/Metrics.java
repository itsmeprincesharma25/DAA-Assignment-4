package util;

/** micro metrics interface: counters + simple timer */
public interface Metrics {
    void inc(String key, long delta);
    long get(String key);
    long time(Runnable r, String key);
}
