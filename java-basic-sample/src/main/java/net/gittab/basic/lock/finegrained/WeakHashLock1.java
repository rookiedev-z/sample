package net.gittab.basic.lock.finegrained;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WeakHashLock1.
 *
 * @author rookiedev 2020/11/15 18:49
 **/
public class WeakHashLock1<T> {

    public final Map<T, WeakReference<ReentrantLock>> weakHashMap =
            Collections.synchronizedMap(new WeakHashMap<>());


    public ReentrantLock get(T key){
        // return this.weakHashMap.computeIfAbsent(key, lock -> new WeakReference<>(new ReentrantLock())).get();
        return Optional.ofNullable(weakHashMap.get(key)).map(WeakReference::get).orElseGet(() -> addNewReference(key));
    }

    public ReentrantLock addNewReference(T key){
        ReentrantLock reentrantLock = new ReentrantLock();
        WeakReference<ReentrantLock> weakReference = this.weakHashMap.putIfAbsent(key, new WeakReference<>(reentrantLock));
        if(weakReference != null && weakReference.get() != null){
            return weakReference.get();
        }
        return reentrantLock;
    }

}
