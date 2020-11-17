package net.gittab.basic.lock;

import java.util.concurrent.TimeUnit;

import lombok.SneakyThrows;

/**
 * StringInternDeadLock.
 *
 * @author rookiedev 2020/10/25 18:37
 **/
public class StringInternDeadLock {

    public void method1() throws InterruptedException {
        synchronized ("method1".intern()){
            System.out.println("get method1 lock");
            TimeUnit.SECONDS.sleep(1);
            synchronized ("method2".intern()){
                System.out.println("get method2 lock");
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    public void method2() throws InterruptedException {
        synchronized ("method2".intern()){
            System.out.println("get method2 lock");
            TimeUnit.SECONDS.sleep(1);
            synchronized ("method1".intern()) {
                System.out.println("get method1 lock");
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    public static void main(String[] args) {
        StringInternDeadLock deadLock = new StringInternDeadLock();
        Runnable target;
        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                deadLock.method1();

                deadLock.method2();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                deadLock.method2();

                deadLock.method1();
            }
        });

        thread1.start();
        thread2.start();
    }
}
