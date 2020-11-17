package net.gittab.basic.jvm;

import java.lang.reflect.Method;

/**
 * Test.
 *
 * @author rookiedev 2020/10/11 17:09
 **/
public class Test {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
//        System.out.println("target#" + i);
    }

    public static void main(String[] args) throws Exception {

        System.setProperty("key", "value");
        System.out.println(System.getProperty("key"));

        // System.out.println(args[0] + "-" + args[1]);
        Class klass = Class.forName("net.gittab.basic.jvm.Test");
        Method method = klass.getMethod("target", int.class);
        for (int i = 0; i < 20; i++) {
            Object object = method.invoke(null, i);
            // System.out.println(object);

        }
    }


}
