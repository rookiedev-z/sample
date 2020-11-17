package net.gittab.basic.generics;

import java.util.List;

/**
 * GenericObject.
 *
 * @author rookiedev 2020/9/22 22:53
 **/
public class GenericObject<T> {

    private T t;

    public GenericObject() {
    }

    public GenericObject(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public int listSize(List<T> list){
        return list.size();
    }

    public static int listSize1(List<? extends ParentTypeObject> list){
        return list.size();
    }

}
