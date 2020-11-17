package net.gittab.basic.jvm.oom;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * DirectMemoryOOM.
 *
 * @author rookiedev 2020/10/13 22:39
 **/
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1MB);
        }
    }
}
