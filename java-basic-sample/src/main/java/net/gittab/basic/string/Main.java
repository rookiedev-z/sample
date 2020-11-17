package net.gittab.basic.string;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main.
 *
 * @author rookiedev
 * @date 2020/9/15 17:42
 **/
public class Main {

    public static void main(String[] args) {
        // final 修饰的类不能被继承，
        // 修饰的变量必须显式的进行初始化或构造方法或初始化代码块中初始化，赋值后不能被修改，引用类型变量内存地址不可变，但内存地址中的内容是可变的，比如对象中的属性
        // 而接口中定义的成员默认就是 final static 修饰
        // 修饰的方法不能被子类重写，但是可以重载
        // JDK 中提供的八个包装类和 String 类都是不可变类
        String str1 = "string";
        String str2 = "string";

        String str3 = new String("string");
        String str4 = new String("string");

        // str1 == str2 is true, str1 == str3 is false, str3 == str4 is false, str1 和 str2 会从字符串常量池中取，取不到再创建
        System.out.println(String.format("str1 == str2 is %s, str1 == str3 is %s, str3 == str4 is %s", str1 == str2, str1 == str3, str3 == str4));

        String strAppend = str1 + str2;

        System.out.println(strAppend);

        // StringBuffer 中的方法都被 Synchronized 修饰符所修饰，故线程安全
        StringBuffer stringBuffer = new StringBuffer(30);
        stringBuffer.append("buffer").append(" is").append(" thread").append(" safe");

        StringBuilder stringBuilder = new StringBuilder(35);
        stringBuilder.append("builder").append(" is not").append(" thread").append(" safe");
        String str5 = "buffer" + " is" + " thread" + " safe";
        String str6 = "buffer" + " is not" + " thread" + " safe";

        // buffer string is: buffer is thread safe, builder string is : builder is not thread safe
        System.out.println(String.format("buffer string is: %s, builder string is : %s", stringBuffer.toString(), stringBuilder.toString()));

        Object[] array;
        List<String> itemIds = Arrays.asList("1", "2");
        String ids = itemIds.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(",", "", ""));
        System.out.println(ids);

    }

    public String append(String str){
        return str.intern() + "abc";
    }

    public String append1(String str){
        StringBuilder builder = new StringBuilder();
        return builder.append(str).append("abc").toString();
    }

    public String append2(String str){
        // StringConcatFactory.makeConcatWithConstants();
        return null;
    }

}
