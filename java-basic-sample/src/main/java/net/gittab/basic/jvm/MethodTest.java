package net.gittab.basic.jvm;

/**
 * MethodTest.
 *
 * @author rookiedev 2020/10/11 10:19
 **/
public class MethodTest {

    public void add(Object obj, Object... objs){
        System.out.println("method1--" + obj + "--" + objs);
    }

    public void add(String str, Object obj, Object... objs){
        System.out.println("method2--" + str + "--" + obj + objs);
    }

    public static void add(){
        System.out.println("static method add");
    }

    public static void main(String[] args) {
        MethodTest methodTest = new MethodTest();
        methodTest.add(null, 1);
        methodTest.add(null, 1, 2);
        methodTest.add("1", 2, 3);
        methodTest.add(null, new Object[]{1});
    }
}
