package net.gittab.basic.jvm.invokedynamic;
import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * GrandFather.
 *
 * @author rookiedev 2020/11/12 07:28
 **/
class DynamicTest {


    class GrandFather {
        void thinking(){
            System.out.println("I am grandfather.");
        }
    }

    class Father extends GrandFather{
        @Override
        void thinking(){
            System.out.println("I am father test.");
        }
    }

    class Son extends Father{
        @Override
        void thinking(){
//            GrandFather grandFather = new GrandFather();
//            grandFather.thinking();

            try {
                MethodType methodType = MethodType.methodType(void.class);
                Field lookupImpl = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                lookupImpl.setAccessible(true);
                // java
                // MethodHandle methodHandle = ((MethodHandles.Lookup) lookupImpl.get(null)).findSpecial(GrandFather.class,"thinking", methodType, GrandFather.class);

                MethodHandle methodHandle = lookup().findSpecial(GrandFather.class, "thinking", methodType, GrandFather.class);

                methodHandle.invoke(this);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Throwable {

        (new DynamicTest().new Son()).thinking();
        Object obj = System.currentTimeMillis() % 2 == 0? System.out : new ClassA();
        getPrintlnMH(obj).invokeExact("testprintln");


    }

    static class ClassA{
        public void println(String s){
            System.out.println("ClassA: " + s);
        }
    }

    private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return lookup().findVirtual(receiver.getClass(), "println", methodType).bindTo(receiver);
    }



}
