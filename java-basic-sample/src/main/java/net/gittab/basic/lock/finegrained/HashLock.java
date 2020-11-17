package net.gittab.basic.lock.finegrained;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * HashLock.
 *
 * @author rookiedev 2020/11/15 17:06
 **/
public class HashLock<T> {
    private boolean fair = false;
    private final SegmentLock<T> segmentLock = new SegmentLock<>();
    private final ConcurrentHashMap<T, ReentrantLockCount> lockMap = new ConcurrentHashMap<>();

    public HashLock(){

    }

    public HashLock(boolean fair){
        this.fair = fair;
    }

    public void lock(T key){
        ReentrantLockCount lock;
        this.segmentLock.lock(key);
        try {
            lock = this.lockMap.get(key);
            if(lock == null){
                lock = new ReentrantLockCount(this.fair);
                this.lockMap.put(key, lock);
            }else{
                lock.incrementAndGet();
            }
        }finally {
            this.segmentLock.unlock(key);
        }
        lock.lock();
    }

    public void unlock(T key){
        ReentrantLockCount reentrantLockCount = this.lockMap.get(key);
        if(reentrantLockCount.getCount() == 1){
            this.segmentLock.lock(key);
            try {
                if(reentrantLockCount.getCount() == 1){
                    this.lockMap.remove(key);
                }
            }finally {
                this.segmentLock.unlock(key);
            }
        }
        reentrantLockCount.unlock();
    }

    static class ReentrantLockCount {
        private ReentrantLock reentrantLock;
        private AtomicInteger count = new AtomicInteger(1);

        public ReentrantLockCount(boolean fair){
            this.reentrantLock = new ReentrantLock(fair);
        }

        public void lock(){
            this.reentrantLock.lock();
        }

        public void unlock(){
            this.count.decrementAndGet();
            this.reentrantLock.unlock();
        }

        public int incrementAndGet(){
            return this.count.incrementAndGet();
        }

        public int getCount(){
            return this.count.get();
        }
    }
}
