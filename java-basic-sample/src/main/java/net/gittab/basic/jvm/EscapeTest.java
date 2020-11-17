package net.gittab.basic.jvm;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * EscapeTest.
 *
 * @author rookiedev 2020/11/9 15:36
 **/
public class EscapeTest {
    // java -XX:+PrintGC -XX:+DoEscapeAnalysis EscapeTest
    public static void forEach(ArrayList<Object> list, Consumer<Object> f) {
        for (Object obj : list) {
            f.accept(obj);
        }
    }

    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        for (int i = 0; i < 400_000_000; i++) {
            forEach(list, obj -> {
            });
        }
    }
}