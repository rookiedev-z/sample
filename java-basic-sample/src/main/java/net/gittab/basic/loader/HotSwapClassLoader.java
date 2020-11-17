package net.gittab.basic.loader;

/**
 * HotSwapClassLoader.
 *
 * @author rookiedev 2020/11/16 22:13
 **/
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader(){
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] bytes){
        return defineClass(null, bytes, 0, bytes.length);
    }

}
