package net.gittab.basic.generics;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * SugarTest.
 *
 * @author rookiedev 2020/11/24 09:57
 **/
public class SugarTest {

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        // true -128 -127 缓存
        System.out.println(c == d);
        // false
        System.out.println(e == f);
        // true,计算时会自动拆箱
        System.out.println(c == (a + b));
        // true,自动装箱
        System.out.println(c.equals(a + b));
        // true,计算时会自动拆箱
        System.out.println(g == (a + b));
        // false,自动装箱
        System.out.println(g.equals(a + b));

        List<Integer> list = Arrays.asList(1, 2, 3, 4);
    }

    @SupportedAnnotationTypes("*")
    @SupportedSourceVersion(SourceVersion.RELEASE_6)
    private class NameCheckerProcessor extends AbstractProcessor{

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


            return false;
        }
    }

//    public static void test(List<String> list){
//
//    }

//    public static void test(List<Integer> list){
//
//    }

//    public static int test(List<Integer> list){
//        return 0;
//    }
//
//    public static String test(List<String> list){
//        return "";
//    }



}
