package net.gittab.basic.equals;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Main.
 *
 * @author rookiedev
 * @date 2020/9/8 2:38 下午
 **/
public class Main {

    public static void main(String[] args) {
        Object object1 = new Object();
        Object object2 = new Object();
        // object1 == object2: false, object1.equals(object2): false
        System.out.println(String.format("object1 == object2: %s, object1.equals(object2): %s", object1 == object2, object1.equals(object2)));

        String str1 = new String("123");
        String str2 = new String("123");
        // str1 == str2: false, str1.equals(str2): true
        System.out.println(String.format("str1 == str2: %s, str1.equals(str2): %s", str1 == str2, str1.equals(str2)));

        String string1 = "123";
        String string2 = "123";
        // string1 == string2: true, string1.equals(string2): true
        System.out.println(String.format("string1 == string2: %s, string1.equals(string2): %s", string1 == string2, string1.equals(string2)));

        StrRepresentation str3 = new StrRepresentation("123");
        StrRepresentation str4 = new StrRepresentation("123");
        // 重写 StrRepresentation 的 equals 方法前, str3 == str4: false, str3.equals(str4): false
        // 重写 StrRepresentation 的 equals 方法后, str3 == str4: false, str3.equals(str4): true
        System.out.println(String.format("str3 == str4: %s, str3.equals(str4): %s", str3 == str4, str3.equals(str4)));

        // 重写 StrRepresentation 的 hashCode 方法前, str3.hashCode() == str4.hashCode(): false
        // 重写 StrRepresentation 的 hashCode 方法后, str3.hashCode() == str4.hashCode(): true
        System.out.println(String.format("str3.hashCode() == str4.hashCode(): %s", str3.hashCode() == str4.hashCode()));

        var map = new HashMap<>(4);
        var strRep1 = new StrRepresentation("str1key");
        var strRep2 = new StrRepresentation("str2key");
        var strRep3 = new StrRepresentation("str3key");
        var strRep4 = new StrRepresentation("str1key");
        map.put(strRep1, "str1");
        map.put(strRep2, "str2");
        map.put(strRep3, "str3");
        map.put(strRep4, "str4");

        // 不重写 StrRepresentation 的 equals 方法和 hashCode 方法, map size: 4
        // 只重写 StrRepresentation 的 equals 方法不重写 hashCode 方法, map size: 4
        // 只重写 StrRepresentation 的 hashCode 方法不重写 equals 方法, map size: 4
        // 重写 StrRepresentation 的 equals 方法和 hashCode 方法, map size: 3, strRep4 覆盖了 strRep1 的值
        System.out.println(String.format("map size: %s", map.size()));
        map.forEach((key, val) -> {
            System.out.println(String.format("key: %s, val: %s", key, val));
        });

    }

    private static class StrRepresentation {

        private String string;

        public StrRepresentation() {
        }

        public StrRepresentation(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return "StrRepresentation{" +
                    "string='" + string + '\'' +
                    '}';
        }

//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            StrRepresentation that = (StrRepresentation) o;
//            return Objects.equals(getString(), that.getString());
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(string);
//        }
    }


}
