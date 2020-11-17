package net.gittab.basic.jmh;

import org.openjdk.jmh.annotations.Benchmark;

/**
 * MyBenchmark.
 *
 * @author rookiedev 2020/11/11 22:45
 **/
public class MyBenchmark {

    @Benchmark
    public void testMethod() {
        // This is a demo/sample template for building your JMH benchmarks. Edit as needed.
        // Put your benchmark code here.
        new Exception();

    }

    public static void main(String[] args) {
        int[][] arr = new int[0][-1];

        System.out.println(arr);
    }
}
