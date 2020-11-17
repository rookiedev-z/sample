package net.gittab.basic.jvm;

import java.lang.reflect.Method;

/**
 * ReflectTest.
 *
 * @author rookiedev 2020/10/14 17:10
 **/
public class ReflectTest {

    public static void main(String[] args) throws Exception {
        Class klass = Class.forName("net.gittab.basic.jvm.ReflectTest");
        Method method = klass.getMethod("test", int.class);
        long current = System.currentTimeMillis();
//        Object[] arg = new Object[1]; // 在循环外构造参数数组
//        arg[0] = 128;
        method.setAccessible(true);
        polluteProfile();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            method.invoke(null, 128);
        }
    }

    public static void test(int a) {

    }

    public static void polluteProfile() throws Exception {
        Method method1 = ReflectTest.class.getMethod("test", int.class);
        Method method2 = ReflectTest.class.getMethod("test", int.class);
        System.out.println(method1 == method2);
        System.out.println(method1.equals(method2));
        for (int i = 0; i < 2000; i++) {
            method1.invoke(null, 0);
            method2.invoke(null, 0);
        }
    }

    public static void target1(int i) {
    }

    public static void target2(int i) {
    }
}
