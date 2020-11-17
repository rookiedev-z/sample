package net.gittab.basic.jvm.oom;

/**
 * JavaVMStackSOF.
 *
 * @author rookiedev 2020/10/13 22:05
 **/
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        // -Xss144k
        JavaVMStackSOF stackSOF = new JavaVMStackSOF();

        try {
            stackSOF.stackLeak();
        }catch (Throwable e){
            System.out.println(stackSOF.stackLength);
            throw e;
        }
    }
}
