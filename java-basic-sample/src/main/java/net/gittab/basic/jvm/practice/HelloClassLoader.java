package net.gittab.basic.jvm.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * HelloClassLoader.
 *
 * @author rookiedev 2020/10/13 15:44
 **/
public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {
        try {
            Class<?> helloClass = new HelloClassLoader().findClass("Hello");
            Object object = helloClass.getDeclaredConstructor().newInstance();
            Method method = helloClass.getDeclaredMethod("hello");
            method.invoke(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = getClassBytes();
        if(bytes.length == 0){
            throw new IllegalArgumentException("the provided byte file is illegal");
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] getClassBytes(){
        try(FileInputStream fileInputStream = new FileInputStream(
                new File("java-basic-sample/src/main/resources/Hello.xlass"))){
            byte[] bytes = fileInputStream.readAllBytes();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte)(255 - bytes[i]);
            }
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
