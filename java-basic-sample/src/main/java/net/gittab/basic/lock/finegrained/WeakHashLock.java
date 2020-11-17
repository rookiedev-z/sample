package net.gittab.basic.lock.finegrained;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WeakHashLock.
 *
 * @author rookiedev 2020/11/15 17:47
 **/
public class WeakHashLock<T> {

    private ReferenceQueue<ReentrantLock> queue = new ReferenceQueue<>();
    private ConcurrentHashMap<T, WeakRefLock<T, ReentrantLock>> lockMap = new ConcurrentHashMap<>();

    public ReentrantLock get(T key){
        if(this.lockMap.size() > 1000){
            clearEmptyRef();
        }

        WeakRefLock<T, ReentrantLock> weakRefLock = this.lockMap.get(key);
        ReentrantLock lock = weakRefLock == null? null: weakRefLock.get();
        while (lock == null){
            lockMap.putIfAbsent(key, new WeakRefLock<>(new ReentrantLock(), this.queue, key));
            weakRefLock = lockMap.get(key);
            lock = weakRefLock == null? null: weakRefLock.get();
            if(lock != null){
                return lock;
            }
            clearEmptyRef();
        }
        return lock;
    }

    @SuppressWarnings("unchecked")
    public void clearEmptyRef(){
        Reference<? extends ReentrantLock> ref;
        while ((ref = this.queue.poll()) != null){
            WeakRefLock<T, ? extends ReentrantLock> weakRefLock = (WeakRefLock<T, ? extends ReentrantLock>) ref;
            this.lockMap.remove(weakRefLock.key);
        }
    }


    static final class WeakRefLock<T, K> extends WeakReference<K>{

        private final T key;

        public WeakRefLock(K referent, ReferenceQueue<? super K> queue, T key) {
            super(referent, queue);
            this.key = key;
        }
    }
}
