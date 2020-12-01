package net.gittab.basic.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * StringLockTest.
 *
 * @author rookiedev 2020/10/23 15:36
 **/
public class StringLockTest {

    public static List<String> list = new ArrayList<>();

    Logger logger = Logger.getLogger(StringLockTest.class.getName());

    XMutexFactory1<String> xMutexFactory1 = new XMutexFactory1<>();

    public void save(String uniqueId) throws InterruptedException {
        // https://docs.oracle.com/javase/9/docs/api/java/lang/String.html#intern--
        // 调用 intern 方法时，如果池已经包含由 equals（Object）方法确定和此 String 对象的字符串相等，则返回池中的字符串
        // 否则，将此 String 对象添加到池中，并返回对此 String 对象的引用。
        // 对于任意两个字符串s和t，当且仅当s.equals（t）为true时，s.intern（）== t.intern（）才为true。
        // 方法返回与该字符串具有相同内容的字符串，但保证来自唯一字符串池
        // 1. 字符串常量池中的字符串很难被 GC 清理，并且 String.intern 字符串量比较大时会消耗很多的资源。
        // 2. 字符串常量池中内存占用过大
        // 3. 外部代码有可能在与当前应用程序使用相同的字符串实例上同步，这可能导致死锁。
        synchronized (uniqueId.intern()){
            if(!existByUniqueId(uniqueId)){
                logger.info("data does not exist, perform insert operation");
                insertData(uniqueId);
            }
        }


        synchronized (xMutexFactory1.getMutex(uniqueId)){
            if(!existByUniqueId(uniqueId)){
                logger.info("data does not exist, perform insert operation");
                insertData(uniqueId);
            }
        }

    }

    public boolean existByUniqueId(String uniqueId) throws InterruptedException {
        return list.contains(uniqueId);
    }

    public void insertData(String uniqueId) throws InterruptedException {
        list.add(uniqueId);
        TimeUnit.SECONDS.sleep(3);
        logger.info("insert data " + uniqueId);
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        StringLockTest stringLockTest = new StringLockTest();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    stringLockTest.save((finalI % 3) + "");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println("collection size:" + list.size());
    }





}
