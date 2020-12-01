package net.gittab.basic.lock;

import org.springframework.util.ConcurrentReferenceHashMap;

/**
 * MutexFactory.
 *
 * @author rookiedev 2020/10/25 09:57
 **/
public class MutexFactory<T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final ConcurrentReferenceHashMap.ReferenceType DEFAULT_REFERENCE_TYPE =
            ConcurrentReferenceHashMap.ReferenceType.WEAK;

    private final ConcurrentReferenceHashMap<T, Mutex<T>> referenceHashMap;

    public MutexFactory() {
        this.referenceHashMap = new ConcurrentReferenceHashMap<>(DEFAULT_INITIAL_CAPACITY,
                DEFAULT_LOAD_FACTOR,
                DEFAULT_CONCURRENCY_LEVEL,
                DEFAULT_REFERENCE_TYPE);
    }

    public MutexFactory(int concurrencyLevel,
                        ConcurrentReferenceHashMap.ReferenceType referenceType) {
        this.referenceHashMap = new ConcurrentReferenceHashMap<>(DEFAULT_INITIAL_CAPACITY,
                DEFAULT_LOAD_FACTOR,
                concurrencyLevel,
                referenceType);
    }

    public Mutex<T> getMutex(T key) {
        return this.referenceHashMap.computeIfAbsent(key, Mutex::new);
    }

    public long size() {
        return this.referenceHashMap.size();
    }

    public void purgeUnreferenced() {
        this.referenceHashMap.purgeUnreferencedEntries();
    }

}
