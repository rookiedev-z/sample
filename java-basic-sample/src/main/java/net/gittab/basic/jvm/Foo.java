package net.gittab.basic.jvm;

/**
 * Foo.
 *
 * @author rookiedev 2020/11/9 15:56
 **/
public class Foo {
    long placeHolder0;
    long placeHolder1;
    long placeHolder2;
    long placeHolder3;
    long placeHolder4;
    long placeHolder5;
    long placeHolder6;
    long placeHolder7;
    long placeHolder8;
    long placeHolder9;
    long placeHoldera;
    long placeHolderb;
    long placeHolderc;
    long placeHolderd;
    long placeHoldere;
    long placeHolderf;

    public static void bar(boolean condition) {
        Foo foo = new Foo();
        if (condition) {
            foo.hashCode();
        }
    }

    // -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler -Xlog:gc Foo
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            bar(i % 100 == 0);
        }
    }
}
