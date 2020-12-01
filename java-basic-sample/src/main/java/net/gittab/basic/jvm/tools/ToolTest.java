package net.gittab.basic.jvm.tools;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ToolTest.
 *
 * @author rookiedev 2020/11/20 10:02
 **/
public class ToolTest {

    private static final int BYTE_SIZE = 64 * 1024;

//    public static void main(String[] args) throws InterruptedException {
//        // -Xms100m -Xmx100m -XX:+HeapDumpOnOutOfMemoryError
//        Thread.sleep(15000);
//        fillHeap(1000);
//        Thread.sleep(500000);
//    }

    public static void fillHeap(int count) throws InterruptedException {
        var list = new ArrayList<>();
        for (var i = 0; i < count; i++){
            Thread.sleep(50);
            byte[] bytes = new byte[BYTE_SIZE];
            list.add(bytes);
        }
        System.out.println("fill heap end");
        Thread.sleep(5000);
        System.out.println("initial gc");
        System.gc();
    }

    public static void createBusyThread(){
        Thread thread = new Thread(() -> {
            while (true){

            }
        }, "testBusyThread");
        thread.start();
    }

    public static void createLockThread(final Object lock){
        Thread thread = new Thread(() -> {
            synchronized (lock){
                try{
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "testLockThread");
        thread.start();
    }

    public static void createDeadLockThread(){
        Thread thread0 = new Thread(() -> {
            synchronized (Integer.valueOf(1)){
                synchronized (Integer.valueOf(2)){
                    System.out.println(3);
                }
            }
        }, "thread0");


        Thread thread1 = new Thread(() -> {
            synchronized (Integer.valueOf(2)){
                synchronized (Integer.valueOf(1)){
                    System.out.println(3);
                }
            }
        }, "thread1");

        thread0.start();
        thread1.start();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread.sleep(15000);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        bufferedReader.readLine();
//        createBusyThread();
//        bufferedReader.readLine();
//        createLockThread(new Object());

        for (int i = 0; i < 200; i++){
            createDeadLockThread();
        }
    }


}
