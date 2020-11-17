package net.gittab.basic.jvm;

/**
 * Children.
 *
 * @author rookiedev 2020/10/11 10:58
 **/
public class Children extends Parent{

    @Override
    public Double add(String a, String b) {
        return Double.parseDouble(a) + Double.parseDouble(b);
    }

    public static void main(String[] args) {
        Parent sub = new Children();
        Number result = sub.add("11" , "12");

        Children children = new Children();
        Double number = children.add("1", "2");

        System.out.println(result);
    }
}
