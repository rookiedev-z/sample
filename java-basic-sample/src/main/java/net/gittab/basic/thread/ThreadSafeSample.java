package net.gittab.basic.thread;

/**
 * ThreadSafeSample.
 *
 * @author rookiedev 2020/11/2 11:13
 **/
public class ThreadSafeSample {
    public int sharedState;

    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void nonSafeAction() {
        while (sharedState < 100000) {
            synchronized (this){
                int former = sharedState++;
                int latter = sharedState;
                if (former != latter - 1) {
                    System.out.printf("Observed data race, former is " + former + ", " + "latter is " + latter);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadSafeSample sample = new ThreadSafeSample();
        Thread threadA = new Thread(sample::nonSafeAction);
        Thread threadB = new Thread(sample::nonSafeAction);
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
    }
}
