package net.gittab.basic.jvm.loader;

/**
 * LoaderTest.
 *
 * @author rookiedev 2020/10/19 23:22
 **/
public class LoaderTest {

    public static void main(String[] args) {
        // -XX:+TraceClassLoading
        // System.out.println(SubClass.a);
        // SubClass[] subClasses = new SubClass[5];
        System.out.println(ConstClass.A);
    }
}
