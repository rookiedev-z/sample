package net.gittab.basic.lock.finegrained;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SegmentLock.
 *
 * @author rookiedev 2020/11/15 16:10
 **/
public class SegmentLock<T> {

    public int DEFAULT_LOCK_COUNT = 20;

    private final ConcurrentHashMap<Integer, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public SegmentLock(){
        init(null, false);
    }

    public SegmentLock(Integer count, boolean isFair){
        init(count, isFair);
    }

    private void init(Integer count, boolean isFair){
        if(count != null && count != 0){
            this.DEFAULT_LOCK_COUNT = count;
        }
        for (int i = 0; i < this.DEFAULT_LOCK_COUNT; i++) {
            this.lockMap.put(i, new ReentrantLock(isFair));
        }
    }

    public ReentrantLock get(T key){
        return this.lockMap.get((key.hashCode() >>> 1) % DEFAULT_LOCK_COUNT);
    }

    public void lock(T key){
        ReentrantLock lock = this.lockMap.get((key.hashCode() >>> 1) % DEFAULT_LOCK_COUNT);
        lock.lock();
    }

    public void unlock(T key){
        ReentrantLock lock = this.lockMap.get((key.hashCode() >>> 1) % DEFAULT_LOCK_COUNT);
        lock.unlock();
    }
}
