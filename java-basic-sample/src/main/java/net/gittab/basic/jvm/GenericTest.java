package net.gittab.basic.jvm;

import java.util.ArrayList;

/**
 * GenericTest.
 *
 * @author rookiedev 2020/10/20 16:31
 **/
public class GenericTest<T extends Number> {

    T foo(T t){
        return t;
    }

    public int foo() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        int result = list.get(0);
        return result;
    }


    class Merchant<T extends Number> {
        public double actionPrice(T customer) {
            return 0.0d;
        }
    }

    class VIPOnlyMerchant extends Merchant<Double> {
        @Override
        public double actionPrice(Double customer) {
            return 0.0d;
        }
    }
}
