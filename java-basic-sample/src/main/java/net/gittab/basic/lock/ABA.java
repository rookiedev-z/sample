package net.gittab.basic.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA.
 *
 * @author rookiedev 2020/11/1 20:59
 **/
public class ABA {

    private static AtomicInteger atomicInt = new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedRef =
            new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) throws InterruptedException {
        Thread intT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }
        });

        Thread intT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean c3 = atomicInt.compareAndSet(100, 101);
                // print true
                System.out.println(c3);
            }
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();

        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                atomicStampedRef.compareAndSet(100, 101,
                        atomicStampedRef.getStamp(), atomicStampedRef.getStamp()+1);
                atomicStampedRef.compareAndSet(101, 100,
                        atomicStampedRef.getStamp(), atomicStampedRef.getStamp()+1);
            }
        });

        Thread refT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedRef.getStamp();
                // print before sleep : stamp = 0
                System.out.println("before sleep : stamp = " + stamp);
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // print after sleep : stamp = 2
                System.out.println("after sleep : stamp = " + atomicStampedRef.getStamp());
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp+1);
                // print false
                System.out.println(c3);
            }
        });

        refT1.start();
        refT2.start();
    }


}
