package net.gittab.basic.lock;

import org.springframework.util.ConcurrentReferenceHashMap;
/**
 * XMutexFactory1.
 *
 * @author rookiedev 2020/10/25 09:57
 **/
public class XMutexFactory1<KeyT> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    private static final ConcurrentReferenceHashMap.ReferenceType DEFAULT_REFERENCE_TYPE =
            ConcurrentReferenceHashMap.ReferenceType.WEAK;

    private final ConcurrentReferenceHashMap<KeyT, XMutex<KeyT>> referenceHashMap;

    /**
     * Create mutex factory with default settings
     */
    public XMutexFactory1() {
        this.referenceHashMap = new ConcurrentReferenceHashMap<>(DEFAULT_INITIAL_CAPACITY,
                DEFAULT_LOAD_FACTOR,
                DEFAULT_CONCURRENCY_LEVEL,
                DEFAULT_REFERENCE_TYPE);
    }

    public XMutexFactory1(int concurrencyLevel,
                             ConcurrentReferenceHashMap.ReferenceType referenceType) {
        this.referenceHashMap = new ConcurrentReferenceHashMap<>(DEFAULT_INITIAL_CAPACITY,
                DEFAULT_LOAD_FACTOR,
                concurrencyLevel,
                referenceType);
    }

    /**
     * Creates and returns a mutex by the key.
     * If the mutex for this key already exists in the weak-map,
     * then returns the same reference of the mutex.
     */
    public XMutex<KeyT> getMutex(KeyT key) {
        return this.referenceHashMap.computeIfAbsent(key, XMutex::new);
    }

    /**
     * @return count of mutexes in this factory.
     */
    public long size() {
        return this.referenceHashMap.size();
    }

    /**
     * Remove any entries that have been garbage collected and are no longer referenced.
     * Under normal circumstances garbage collected entries are automatically purged
     * when new items are created by a factory. This method can be used to force a purge.
     */
    public void purgeUnreferenced() {
        ((ConcurrentReferenceHashMap) this.referenceHashMap).purgeUnreferencedEntries();
    }

}
