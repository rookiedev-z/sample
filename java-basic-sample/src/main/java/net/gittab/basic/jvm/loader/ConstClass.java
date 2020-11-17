package net.gittab.basic.jvm.loader;

/**
 * ConstClass.
 *
 * @author rookiedev 2020/10/19 23:28
 **/
public class ConstClass {

    static {
        System.out.println("ConstClass init");
    }

    public static final int A = 2;

}
