package net.gittab.basic.jvm;

import java.util.ArrayList;

/**
 * Parent.
 *
 * @author rookiedev 2020/10/11 10:57
 **/
public class Parent {

    public Number add(String a, String b){
        return Integer.parseInt(a) + Integer.parseInt(b);
    }

    public void test(String a){
        switch (a){
            case "a":
                System.out.println(a);
                break;
            case "b":
                System.out.println(a + "==");
        }
    }

    public void foo() {
        var value = 1;
        var list = new ArrayList<Integer>();
        list.add(value);
        // 这一句能够编译吗？
        // list.add("1");
    }
}
