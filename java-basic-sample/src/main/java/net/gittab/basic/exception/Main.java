package net.gittab.basic.exception;

import java.io.*;

/**
 * Main.
 *
 * @author rookiedev
 * @date 2020/9/16 18:42
 **/
public class Main {

    public static void main(String[] args) {
        // Exception 和 Error 都继承自 Throwable
        // Error 一般表示程序在运行过程中出现重大问题，产生错误了，且程序是无法处理的，常见的有 NoClassDefFoundError, OutOfMemoryError, StackOverflowError
        // 而 Exception 则是在程序运行过程中出现了异常情况，但是程序可以自身进行处理，
        // 同时 Exception 还可分为 check exception 和 uncheck exception，其中 check exception 是需要在编译期间就进行处理，要么捕获，要么抛出，常见的有 IOException，FileNotFoundException
        // 而 uncheck exception 则是在运行期间可能出现的异常，不需要在编译期间处理，编码人员可根据情况进行程序恢复或者终止操作，常见的有 NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException

        // Throwable 类中常见的方法有：
        // public string getMessage(): 返回异常发生时的简要描述
        // public string toString(): 返回异常发生时的详细信息
        // public string getLocalizedMessage(): 返回异常对象的本地化信息。Throwable 的子类可覆盖这个方法，生成本地化信息。如果子类没有覆盖该方法，则该方法返回的信息与 getMessage（）返回的结果相同
        // public void printStackTrace(): 在 Console 打印 Throwable 对象封装的异常信息

        // 对于 Java 中的异常通常使用 try-catch... 捕获, 其中 catch 块可以有多个，也可以没有，当没有 catch 块时必须接一个 finally 块，
        // 对于 finally 块，无论是否捕获或处理异常，finally 块里的语句都会被执行。当在 try 块或 catch 块中遇到 return 语句时，finally 语句块将在方法返回之前被执行。
        // 当 finally 代码块中有 return 语句时，finally 语句块中的返回值将会覆盖 try 或 catch 块语句块中的返回值，
        // 注意：如果在 try 或 catch 块中提前执行了 System.exit(0); 退出程序的操作， 那么 finally 块的代码将不会执行，
        System.out.println("result :" + testFinally());

        // finally 代码块一般用来做资源关闭操作，在 try 代码块中使用到的资源，利用 finally 代码块总会得到的执行的特性，在里面进行资源关闭操作
        testFinallyClose();
        // 当然在 JDK7 后引入了 try-with-resources，让资源的关闭更加优雅，使用分号分隔，还可以在 try-with-resources 块中声明多个资源
        testWithResource();
    }

    private static void testFinallyClose(){
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(new File("input.txt")));
            bout = new BufferedOutputStream(new FileOutputStream(new File("output.txt")));
            int result;
            while ((result = bin.read()) != -1) {
                bout.write(result);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(bin != null){
                    bin.close();
                }
                if(bout != null){
                    bout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void testWithResource(){
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(new File("input.txt")));
             BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(new File("output.txt")))) {
            int result;
            while ((result = bin.read()) != -1) {
                bout.write(result);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int testFinally(){
        int a = 10;
        int b = 1;
        try {
            System.out.println("====== try executed");
            int result = a / b;
            /// System.exit(0);
            return result;
        }catch (Exception e){
            System.out.println("====== catch executed");
            e.printStackTrace();
            return b;
        }finally {
            System.out.println("====== finally executed");
            if(a < 0){
                throw new IllegalStateException();
            }
            // if throw exception on above code, unreachable statement below
            System.out.println("a / b, b == 0 is true, throw ArithmeticException");
            return a;
        }
    }
}
