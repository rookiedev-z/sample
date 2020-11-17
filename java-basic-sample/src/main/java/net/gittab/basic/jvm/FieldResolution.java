package net.gittab.basic.jvm;

/**
 * FieldResolution.
 *
 * @author rookiedev 2020/10/20 22:04
 **/
public class FieldResolution {

    interface Interface0 {
        int A = 0;
    }

    interface Interface1 extends Interface0 {
        int A = 1;
    }

    interface Interface2 {
        int A = 2;
    }

    static class Parent implements Interface1, Interface2 {
        public static int A = 3;
    }

    static class Sub extends Parent {
        // public static int A = 4;
    }

    static class DeadLoopClass {
        static {
            if(true){
                System.out.println("DeadLoopClass init");
                while (true){

                }
            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "start");
                DeadLoopClass deadLoopClass = new DeadLoopClass();
                System.out.println(Thread.currentThread() + "run over");
            }
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);

        thread1.start();
        thread2.start();
    }
}
