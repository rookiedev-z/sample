package net.gittab.basic.jmh;

/**
 * LoopPerformanceTest.
 *
 * @author rookiedev 2020/11/10 17:23
 **/
public class LoopPerformanceTest {
    static long foo() {
//        long i = 0;
//        while (i < 1_000_000_000) {
//            i++;
//        }
//        return i;
        int i = 0;
        while (i < 1_000_000_000) {
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        // warm up
        for (int i = 0; i < 20_000; i++) {
            foo();
        }
        // measurement
        long current = System.nanoTime();
        for (int i = 1; i <= 10_000; i++) {
            foo();
            if (i % 1000 == 0) {
                long temp = System.nanoTime();
                System.out.println(temp - current);
                current = System.nanoTime();
            }
        }
    }
}
