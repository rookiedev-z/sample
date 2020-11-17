package net.gittab.basic.jvm;

/**
 * ExceptionTable.
 *
 * @author rookiedev 2020/10/18 10:15
 **/
public class ExceptionTable {

    public static int inc(){
        int x;

        try {
            x = 1;
//            if(x == 1){
//                throw new IllegalArgumentException();
//            }
             int y = x;
             x = 3;
             return y;
            // return x;
        }catch (Exception e){
            x = 2;
            int y = x;
            x = 3;
            return y;
            // return x;
        }
    }

    public static IntegerObj inc1(){
        IntegerObj x;

        try {
            x = new IntegerObj(1);
//            if(x == 1){
//                throw new IllegalArgumentException();
//            }
            return x;
        }catch (Exception e){
            x = new IntegerObj(2);
            return x;
        }finally {
            x = new IntegerObj(3);
            // System.out.println(x);
        }
    }

    public static long test(){
        long a;
        try {
            a = 4L;
            return a;
        }catch (Exception e){
            a = 5L;
            return a;
        }finally {
            a = 6L;

        }

    }



    public static void main(String[] args) {
        int result = inc();
        System.out.println(result);

        IntegerObj result1 = inc1();
        System.out.println(result1 + "=" + result1.getA());
    }
}
