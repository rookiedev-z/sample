package net.gittab.basic.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

/**
 * SynchronizedTest.
 *
 * @author rookiedev 2020/11/3 10:04
 **/
public class SynchronizedTest {

    private static int accountBalance = 10;

    private static Map<String, Integer> accountMap = new HashMap<>(4);

    static {
        accountMap.put("1", 10);
        accountMap.put("2", 10);
    }

    @SneakyThrows
    public static void main(String[] args) {

//        Thread thread0 = new Thread(() -> consume(10));
//        Thread thread1 = new Thread(() -> consume(10));
//        thread0.start();
//        thread1.start();
//        TimeUnit.MILLISECONDS.sleep(5000);
//        System.out.println("account balance: " + accountBalance);

        Thread thread0 = new Thread(() -> consume("1", 10));
        Thread thread1 = new Thread(() -> consume("1", 10));
        Thread thread2 = new Thread(() -> consume("2", 10));
        Thread thread3 = new Thread(() -> consume("2", 10));
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        TimeUnit.MILLISECONDS.sleep(5000);

        accountMap.forEach((key, val) -> System.out.println(key + " account balance: " + val));

    }

    @SneakyThrows
    private static void consume(int amount){
        System.out.println("verify that the account is normal...");
        TimeUnit.MILLISECONDS.sleep(500);
        synchronized (SynchronizedTest.class){
            if(accountBalance >= amount){
                // deduction
                TimeUnit.MILLISECONDS.sleep(1000);
                accountBalance -= amount;
                System.out.println(Thread.currentThread().getName() + " deduction success");
            }else{
                System.out.println(Thread.currentThread().getName() + " deduction failed, insufficient account balance.");
            }
        }

    }

    @SneakyThrows
    private static void consume(String userId, int amount){
        System.out.println("verify that the account is normal...");
        TimeUnit.MILLISECONDS.sleep(500);
        synchronized (userId.intern()){
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
        }
    }

}
