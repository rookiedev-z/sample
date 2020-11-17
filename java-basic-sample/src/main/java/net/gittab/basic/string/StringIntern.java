package net.gittab.basic.string;

import net.gittab.basic.lock.XMutexFactory1;

/**
 * StringIntern.
 *
 * @author rookiedev 2020/10/23 16:44
 **/
public class StringIntern {

    public void save(String uniqueId)  {
        synchronized (uniqueId.intern()){
        }

    }

    public static void main(String[] args) {
        // run with -XX:StringTableSize=500
        // <java7 false false
        // >= java7 true, false,
        // 如果字符串常量池中存在则返回字符串常量池中的字符串引用地址，
        // 如果不存在会将堆中字符串引用地址拷贝放到字符串常量池中再返回该引用地址

        // false, 括号里面的字符串就是来自于字符串常量池
//        String s1 = new String("test_intern");
//        System.out.println( s1.intern() == s1);

        String str = "Java";
        // true
        String str1 = new StringBuilder().append("Ja").append("va").toString();
        System.out.println(str1.intern() == str1);
        System.out.println(str1.intern() == str);

        // false
        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

//        String s1 = "ab";
//        String s2 = new String(s1 + "d");
//        // s2.intern();
////        String s4 = "xxx";
//        String s3 = "abd";
//        System.out.println(s2);
//        System.out.println(s3);
//        System.out.println(s2 == s3);
//        System.out.println(s2.equals(s3));


//        StringIntern intern = new StringIntern();
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100_0000; i++) {
//            intern.testIntern("abcdefghigklmfakdfoajfojoejfje;aj;nbnggjnenbnsn13414u9nopqrstuvwxyz" + i + "abcdefghigklmdfaajaljfal;jf;ajflajdflajlfnopqrstuvwxyz");
//        }
//        System.out.println(System.currentTimeMillis() - start);

        // System.out.println(cost(500_0000));

        // System.out.println(testIntern(2_000_0000));


    }

    public static long cost(int num) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            String.valueOf(i).intern();
        }
        return System.currentTimeMillis() - start;
    }

    static XMutexFactory1 xMutexFactory1 = new XMutexFactory1();

    public static long testIntern(int num){
        long start = System.currentTimeMillis();
        long current = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
//            synchronized (xMutexFactory1.getMutex(i + "134ljfafhfoiahfohghgalajflajf")){
//                if (i % 1000_0000 == 0) {
//                    long temp = System.currentTimeMillis();
//                    System.out.println(temp - current);
//                    current = temp;
//                }
//            }
            synchronized ((i + "134ljfafhfoiahfohghgalajflajf").intern()){
                if (i % 100_0000 == 0) {
                    long temp = System.currentTimeMillis();
                    System.out.println(temp - current);
                    current = temp;
                }
            }
        }

        return System.currentTimeMillis() - start;
    }
}
