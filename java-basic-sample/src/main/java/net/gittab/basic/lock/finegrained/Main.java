package net.gittab.basic.lock.finegrained;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import lombok.SneakyThrows;

/**
 * Main.
 *
 * @author rookiedev 2020/11/15 16:25
 **/
public class Main {

    // private static SegmentLock<KeyObject> segmentLock = new SegmentLock<>();

    private static SegmentLock<String> segmentLock = new SegmentLock<>();

    private static HashLock<String> hashLock = new HashLock<>();

    private static WeakHashLock<String> weakHashLock = new WeakHashLock<>();

    private static int accountBalance = 10;

    private static Map<String, Integer> accountMap = new HashMap<>(4);

    static {
        accountMap.put("1", 10);
        accountMap.put("2", 10);
    }

    @SneakyThrows
    public static void main(String[] args) {

        Thread thread0 = new Thread(() -> consume("1", 10));
        Thread thread1 = new Thread(() -> consume("1", 10));
        Thread thread2 = new Thread(() -> consume("2", 10));
        Thread thread3 = new Thread(() -> consume("2", 10));
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        TimeUnit.MILLISECONDS.sleep(5000);

    }

    @SneakyThrows
    private static void consume(String userId, int amount){
        System.out.println("verify that the account is normal...");
        TimeUnit.MILLISECONDS.sleep(500);
        // KeyObject keyObject = new KeyObject(userId);
        // segmentLock.lock(keyObject);
        ReentrantLock lock = segmentLock.get(userId);
        lock.lock();
        try {
            System.out.println("enter the deduction code block");
            Integer userAccountBalance = accountMap.get(userId);
            if(userAccountBalance >= amount){
                // deduction
                TimeUnit.MILLISECONDS.sleep(2000);
                userAccountBalance -= amount;
                accountMap.put(userId, userAccountBalance);
                System.out.println(Thread.currentThread().getName() + " deduction success");
            }else{
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " deduction failed, insufficient account balance.");
            }
        }finally {
            // segmentLock.unlock(keyObject);
            // segmentLock.unlock(userId);
            lock.unlock();
        }
    }

    @SneakyThrows
    private static void consume1(String userId, int amount){
        System.out.println("verify that the account is normal...");
        TimeUnit.MILLISECONDS.sleep(500);
        hashLock.lock(userId);
        try {
            System.out.println("enter the deduction code block");
            Integer userAccountBalance = accountMap.get(userId);
            if(userAccountBalance >= amount){
                // deduction
                TimeUnit.MILLISECONDS.sleep(2000);
                userAccountBalance -= amount;
                accountMap.put(userId, userAccountBalance);
                System.out.println(Thread.currentThread().getName() + " deduction success");
            }else{
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " deduction failed, insufficient account balance.");
            }
        }finally {
            hashLock.unlock(userId);
        }
    }

    @SneakyThrows
    private static void consume2(String userId, int amount){
        System.out.println("verify that the account is normal...");
        TimeUnit.MILLISECONDS.sleep(500);
        ReentrantLock lock = weakHashLock.get(userId);
        lock.lock();
        try {
            System.out.println("enter the deduction code block");
            Integer userAccountBalance = accountMap.get(userId);
            if(userAccountBalance >= amount){
                // deduction
                TimeUnit.MILLISECONDS.sleep(2000);
                userAccountBalance -= amount;
                accountMap.put(userId, userAccountBalance);
                System.out.println(Thread.currentThread().getName() + " deduction success");
            }else{
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " deduction failed, insufficient account balance.");
            }
        }finally {
            lock.unlock();
        }
    }


}
