package net.gittab.basic.generics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main.
 *
 * @author rookiedev 2020/9/22 22:45
 **/
public class Main {

    public static void main(String[] args) {
        // Java 泛型（generics）是 JDK 5 中引入的一个新特性, 泛型提供了编译时类型安全检查，这一特性使得我们在编译时就能够知道非法的类型
        List<String> strList = new ArrayList<>();
        strList.add("str1");
        strList.add("str2");
        // compile error
        /// strList.add(1);
        // 在编译前就能够确定类型是否正确
        List<Object> list = new ArrayList();
        list.add("str1");
        list.add("str2");
        /// list.add(1);
        // 避免了类型强制转换，其实底层还是通过强制转换来实现的，编译之后的字节码中是没有泛型的信息的
        list.forEach(item -> System.out.println(((String)item).toUpperCase()));

        // 泛型通配符 ? T E R K V
        Map<String, String> map = new HashMap<>();
        // Class, List, Function, Map


        GenericObject<ParentTypeObject> parentGenericObject = new GenericObject<>();
        List<ParentTypeObject> parentList = new ArrayList<>();
        List<SubTypeObject> subList = new ArrayList<>();

        parentGenericObject.listSize(parentList);
        // compile error
        // parentGenericObject.listSize(subList);

        parentGenericObject.listSize1(parentList);
        parentGenericObject.listSize1(subList);

        List<? extends Object> listObject = new ArrayList<>();
//         listObject.add("test");

        Class<ObjectClass> clazz = ObjectClass.class;
        ObjectClass object = null;
        ObjectClass object1 = null;
        try {
            // object = clazz.newInstance();
            object1 = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            System.out.println("throw InstantiationException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("throw IllegalAccessException");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("throw InvocationTargetException");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("throw NoSuchMethodException");
            e.printStackTrace();
        }









    }

}
