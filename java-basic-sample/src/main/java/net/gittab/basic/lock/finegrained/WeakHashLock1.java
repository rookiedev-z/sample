package net.gittab.basic.lock.finegrained;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
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
        return this.weakHashMap.computeIfAbsent(key, lock -> new WeakReference<>(new ReentrantLock())).get();
    }

    public static void main(String[] args) throws InterruptedException {
        WeakHashLock1<String> lock1 = new WeakHashLock1<>();
        String weakKey = new String("weakKey");
        ReentrantLock reentrantLock = new ReentrantLock();
        lock1.weakHashMap.put(weakKey, new WeakReference<>(reentrantLock));
        reentrantLock = null;
        System.gc();
        System.out.println("==========weak reference gc2==========");
        // 在经过一次 gc 标记之后，弱引用所在的内存就已经被回收了，
        System.out.println(lock1.weakHashMap.isEmpty());
        System.out.println("====================");
        String test = new String("test");
        ReentrantLock lock = lock1.get(test);
        test = null;
        System.out.println(lock);
        Thread.sleep(1);
        // System.gc();
        Thread.sleep(1);
        test = new String("test");
        lock = lock1.get(test);
        System.out.println(lock);
    }

}
