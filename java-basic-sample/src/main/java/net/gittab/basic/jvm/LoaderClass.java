package net.gittab.basic.jvm;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * LoaderClass.
 *
 * @author rookiedev 2020/10/16 15:02
 **/
public class LoaderClass {

    static {
        System.out.println("execute static code block");
    }

    public static void main(String[] args) {
//        int[] arr = new int[]{1, 2, 3, 4, 5};
//
////        for (int i = 0; i < arr.length; i++){
////            System.out.println(arr[i]);
////        }
////        System.out.println("=============");
//        for (int item : arr) {
//            System.out.println(item);
//        }

        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        list.forEach(item -> System.out.println(item));

        Consumer consumer = item -> System.out.println(item);


//        int[] arr1 = arr; // store_2 -> aload_2
//        int length = arr.length; // istore_3 iload_3
//        int i = 0; // istore 4 iload 4
//        if(i < length){
//            int temp = arr[i]; // istore 5 iload 5
//            PrintStream stream = System.out; //
//            stream.println(temp);
//            i++;
//
//        }

    }
}
