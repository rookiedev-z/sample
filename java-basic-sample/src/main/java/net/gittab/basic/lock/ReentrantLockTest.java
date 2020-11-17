package net.gittab.basic.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import lombok.SneakyThrows;

/**
 * ReentrantLockTest.
 *
 * @author rookiedev 2020/11/16 06:33
 **/
public class ReentrantLockTest {

    private static int accountBalance = 10;

    @SneakyThrows
    public static void main(String[] args) {

//        Thread thread0 = new Thread(() -> consume(10));
//        Thread thread1 = new Thread(() -> consume(10));
//        thread0.start();
//        thread1.start();
//        TimeUnit.MILLISECONDS.sleep(5000);
//        System.out.println("account balance: " + accountBalance);

        ReentrantLockTest test = new ReentrantLockTest();

//        Thread thread0 = new Thread(test::syncMethod4);
//        Thread thread1 = new Thread(test::syncMethod4);
//        thread0.start();
//        TimeUnit.MILLISECONDS.sleep(500);
//        thread1.start();
        Thread consumer = new Thread(() -> {
            try {
                test.consume();
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        Thread producer = new Thread(() -> {
            try {
                test.produce("string");
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        consumer.start();
        TimeUnit.MILLISECONDS.sleep(500);
        producer.start();



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

    private synchronized void syncMethod(){
        System.out.println("execute sync method");
    }

    private void syncMethod1(){
        System.out.println("enter sync method");
        synchronized (this){
            System.out.println("execute sync method");
        }
    }

    ReentrantLock lock = new ReentrantLock(true);

    private void syncMethod2(){
        lock.lock();
        try {
            System.out.println("execute sync method");
        }finally {
           lock.unlock();
        }
    }

    private void syncMethod3(){
         System.out.println("enter sync method");
        lock.lock();
        try {
//            if(true){
//                throw new IllegalStateException();
//            }
            System.out.println("execute sync method");
            // TimeUnit.SECONDS.sleep(4);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    private void syncMethod4() {
        if(lock.tryLock(3, TimeUnit.SECONDS)){
            try {
                System.out.println("execute sync method");
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }else{
            System.out.println("execute other logic");
        }
    }

    private List<String> list = new ArrayList<>();
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition notEmpty = reentrantLock.newCondition();

    private void produce(String str) throws InterruptedException {
        reentrantLock.lock();
        try {
            list.add(str);
            System.out.println("send a signal to add elements to the list");
            notEmpty.signal();
            TimeUnit.SECONDS.sleep(5);
        }finally {
            reentrantLock.unlock();
            System.out.println("producer unlock");
        }
    }

    private void consume() throws InterruptedException {
        reentrantLock.lock();
        try {
            if(list.size() == 0){
                System.out.println("waiting for elements to add the list");
                notEmpty.await();
                System.out.println("received a signal to add elements to the list");
            }
            list.remove(list.size() - 1);
            System.out.println("consume list element");
            TimeUnit.SECONDS.sleep(1);
        }finally {
            reentrantLock.unlock();
            System.out.println("consumer unlock");
        }
    }

    private void threadTest() throws InterruptedException {

        Thread thread0 = new Thread(() -> {

        });
        thread0.start();
        thread0.wait();
    }
}
