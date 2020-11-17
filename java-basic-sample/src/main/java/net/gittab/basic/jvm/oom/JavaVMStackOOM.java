package net.gittab.basic.jvm.oom;

/**
 * JavaVMStackOOM.
 *
 * @author rookiedev 2020/10/13 22:15
 **/
public class JavaVMStackOOM {

    private void dontStop(){
        while (true){

        }
    }

    public void stackLeakByThread(){
        while (true){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        // OOM -Xss2M
//        JavaVMStackOOM javaVMStackOOM = new JavaVMStackOOM();
//        javaVMStackOOM.stackLeakByThread();
    }
}
