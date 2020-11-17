package net.gittab.basic.lock.finegrained;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * WeakHashLock1.
 *
 * @author rookiedev 2020/11/15 18:44
 **/
public class WeakHashLock2<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final ConcurrentReferenceHashMap.ReferenceType DEFAULT_REFERENCE_TYPE =
            ConcurrentReferenceHashMap.ReferenceType.WEAK;

    private final ConcurrentReferenceHashMap<T, ReentrantLock> referenceHashMap;

    /**
     * Create mutex factory with default settings
     */
    public WeakHashLock2() {
        this.referenceHashMap = new ConcurrentReferenceHashMap<>(DEFAULT_INITIAL_CAPACITY,
                DEFAULT_LOAD_FACTOR,
                DEFAULT_CONCURRENCY_LEVEL,
                DEFAULT_REFERENCE_TYPE);
    }

    public WeakHashLock2(int concurrencyLevel,
                         ConcurrentReferenceHashMap.ReferenceType referenceType) {
        this.referenceHashMap = new ConcurrentReferenceHashMap<>(DEFAULT_INITIAL_CAPACITY,
                DEFAULT_LOAD_FACTOR,
                concurrencyLevel,
                referenceType);
    }

    public ReentrantLock getMutex(T key) {
        return this.referenceHashMap.computeIfAbsent(key, lock -> new ReentrantLock());
    }

}
