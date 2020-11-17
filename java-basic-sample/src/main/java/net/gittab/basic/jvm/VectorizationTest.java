package net.gittab.basic.jvm;

/**
 * VerctorizationTest.
 *
 * @author rookiedev 2020/11/9 16:20
 **/
public class VectorizationTest {
//    static void foo(int[] a, int[] b, int[] c) {
//        for (int i = 0; i < a.length; i++) {
//            c[i] = a[i] + b[i];
//        }
//    }

    static void foo(int[] a) { for (int i = 4; i < a.length; i++) { a[i] = a[i - 4]; } }

    // java -XX:CompileCommand='dontinline VectorizationTest.foo' -XX:CompileCommand='print VectorizationTest.foo' -XX:-TieredCompilation VectorizationTest
    public static void main(String[] args) throws InterruptedException {
//        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8};
//        int[] c = new int[16];


        int[] a = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8 };
        int[] c = new int[32];
        for (int i = 0; i < 20_000; i++) {
            foo(a);
        }
        Thread.sleep(2000);
    }
}
