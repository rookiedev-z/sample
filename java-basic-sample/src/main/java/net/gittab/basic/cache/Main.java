package net.gittab.basic.cache;

/**
 * Main.
 *
 * @author rookiedev
 * @date 2020/9/8 3:33 下午
 **/
public class Main {

    public static void main(String[] args) {
        // 八种基本数据类型，每种都有其对应的包装类型,
        // byte(1B) -> Byte, short(2B) -> Short(), int(4B) -> Integer, long(8B) -> Long
        // float(4B) -> Float, double(8B) -> Double, char(2B) -> Character, boolean(1bit) -> Boolean

        // (重要)自动装箱和自动拆箱，其实是通过调用对应包装类型里面的 O valueOf(o s) 方法和 o oValue()

        // 其中在 Byte，Short，Integer，Long，Boolean 这几个包装类型里面都有一个 public static O valueOf(o s) 方法，O 表示对应的的类型
        // 在该方法中可以看到对应类型的 Cache 类，在该类中有个对应类型的 cache 数组用来作为缓存

        byteTest();
        shortTest();
        integerTest();
        longTest();
        characterTest();
        booleanTest();
        floatDoubleTest();

    }

    private static void byteTest(){
        // Byte 只能表示 -128 - 127 这个范围，Byte 将这个范围的值全部缓存起来了
//        public static Byte valueOf(byte b) {
//          final int offset = 128;
//          return ByteCache.cache[(int)b + offset];
//        }
//        private static class ByteCache {
//        private ByteCache(){}
//
//        static final Byte cache[] = new Byte[-(-128) + 127 + 1];
//
//        static {
//            for(int i = 0; i < cache.length; i++)
//                cache[i] = new Byte((byte)(i - 128));
//        }
//    }
        Byte byte1 = -128;
        Byte byte2 = -128;
        Byte byte3 = 127;
        Byte byte4 = 127;
        Byte byte5 = new Byte("1");
        Byte byte6 = new Byte("1");
        // byte1 == byte2: true, byte3 == byte4: true, byte5 == byte6: false
        System.out.println(String.format("byte1 == byte2: %s, byte3 == byte4: %s, byte5 == byte6: %s", byte1 == byte2, byte3 == byte4, byte5 == byte6));
    }

    private static void shortTest(){
        // Short 将 -128 - 127 这个范围的数值进行缓存
//        public static Short valueOf(short s) {
//            final int offset = 128;
//            int sAsInt = s;
//            if (sAsInt >= -128 && sAsInt <= 127) { // must cache
//                return Short.ShortCache.cache[sAsInt + offset];
//            }
//            return new Short(s);
//        }
//        private static class ShortCache {
//            private ShortCache(){}
//
//            static final Short cache[] = new Short[-(-128) + 127 + 1];
//
//            static {
//                for(int i = 0; i < cache.length; i++)
//                    cache[i] = new Short((short)(i - 128));
//            }
//        }
        Short short1 = -128;
        Short short2 = -128;

        Short short3 = 127;
        Short short4 = 127;

        Short short5 = 128;
        Short short6 = 128;

        Short short7 = new Short("127");
        Short short8 = new Short("127");
        // short1 == short2: true, short3 == short4: true
        System.out.println(String.format("short1 == short2: %s, short3 == short4: %s", short1 == short2, short3 == short4));
        // short5 == short6: false, short7 == short8: false
        System.out.println(String.format("short5 == short6: %s, short7 == short8: %s", short5 == short6, short7 == short8));
    }

    private static void integerTest(){
        // Integer 默认将 -128 - 127 这个范围的数值进行缓存，其中缓存的最大值 127 是可配置的，也就是说如果有需要可以增加缓存的上限值，
        // 可通过参数 java.lang.Integer.IntegerCache.high 配置
//        public static Integer valueOf(int i) {
//            if (i >= Integer.IntegerCache.low && i <= Integer.IntegerCache.high)
//                return Integer.IntegerCache.cache[i + (-Integer.IntegerCache.low)];
//            return new Integer(i);
//        }
//        private static class IntegerCache {
//            static final int low = -128;
//            static final int high;
//            static final Integer cache[];
//
//            static {
//                // high value may be configured by property
//                int h = 127;
//                String integerCacheHighPropValue =
//                        sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
//                if (integerCacheHighPropValue != null) {
//                    try {
//                        int i = parseInt(integerCacheHighPropValue);
//                        i = Math.max(i, 127);
//                        // Maximum array size is Integer.MAX_VALUE
//                        h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
//                    } catch( NumberFormatException nfe) {
//                        // If the property cannot be parsed into an int, ignore it.
//                    }
//                }
//                high = h;
//
//                cache = new Integer[(high - low) + 1];
//                int j = low;
//                for(int k = 0; k < cache.length; k++)
//                    cache[k] = new Integer(j++);
//
//                // range [-128, 127] must be interned (JLS7 5.1.7)
//                assert Integer.IntegerCache.high >= 127;
//            }
//
//            private IntegerCache() {}
//        }
        Integer integer1 = -128;
        Integer integer2 = -128;

        Integer integer3 = 127;
        Integer integer4 = 127;

        Integer integer5 = 128;
        Integer integer6 = 128;

        Integer integer7 = new Integer("127");
        Integer integer8 = new Integer("127");
        // integer1 == integer2: true, integer3 == integer4: true
        System.out.println(String.format("integer1 == integer2: %s, integer3 == integer4: %s", integer1 == integer2, integer3 == integer4));
        // integer5 == integer6: false, integer7 == integer8: false
        System.out.println(String.format("integer5 == integer6: %s, integer7 == integer8: %s", integer5 == integer6, integer7 == integer8));
    }

    private static void longTest(){
        // Integer 将 -128 - 127 这个范围的数值进行缓存
//        public static Long valueOf(long l) {
//            final int offset = 128;
//            if (l >= -128 && l <= 127) { // will cache
//                return Long.LongCache.cache[(int)l + offset];
//            }
//            return new Long(l);
//        }
//        private static class LongCache {
//            private LongCache(){}
//
//            static final Long cache[] = new Long[-(-128) + 127 + 1];
//
//            static {
//                for(int i = 0; i < cache.length; i++)
//                    cache[i] = new Long(i - 128);
//            }
//        }
        Long long1 = -128L;
        Long long2 = -128L;

        Long long3 = 127L;
        Long long4 = 127L;

        Long long5 = 128L;
        Long long6 = 128L;

        Long long7 = new Long("127");
        Long long8 = new Long("127");
        // long1 == long2: true, long3 == long4: true
        System.out.println(String.format("long1 == long2: %s, long3 == long4: %s", long1 == long2, long3 == long4));
        // long5 == long6: false, long7 == long8: false
        System.out.println(String.format("long5 == long6: %s, long7 == long8: %s", long5 == long6, long7 == long8));
    }

    private static void characterTest(){
        // Character 将 0 - 127 这个范围的数值进行缓存
//        public static Character valueOf(char c) {
//            if (c <= 127) { // must cache
//                return Character.CharacterCache.cache[(int)c];
//            }
//            return new Character(c);
//        }
//        private static class CharacterCache {
//            private CharacterCache(){}
//
//            static final Character cache[] = new Character[127 + 1];
//
//            static {
//                for (int i = 0; i < cache.length; i++)
//                    cache[i] = new Character((char)i);
//            }
//        }
        Character character1 = 0;
        Character character2 = 0;
        Character character3 = 127;
        Character character4 = 127;

        Character character5 = 128;
        Character character6 = 128;
        // character1 == character2: true, character3 == character4: true, character5 == character6: false
        System.out.println(String.format("character1 == character2: %s, character3 == character4: %s, character5 == character6: %s", character1 == character2, character3 == character4, character5 == character6));
    }

    private static void booleanTest(){
        // Boolean 就只有两个值, true 和 false
//        public static Boolean valueOf(boolean b) {
//            return (b ? TRUE : FALSE);
//        }
//        public static final Boolean TRUE = new Boolean(true);
//        public static final Boolean FALSE = new Boolean(false);

        Boolean boolean1 = true;
        Boolean boolean2 = true;

        Boolean boolean3 = false;
        Boolean boolean4 = false;

        Boolean boolean5 = new Boolean(true);
        Boolean boolean6 = new Boolean(true);
        // boolean1 == boolean2: true, boolean3 == boolean4: true, boolean5 == boolean6: false
        System.out.println(String.format("boolean1 == boolean2: %s, boolean3 == boolean4: %s, boolean5 == boolean6: %s", boolean1 == boolean2, boolean3 == boolean4, boolean5 == boolean6));

    }

    private static void floatDoubleTest(){
        // 而 Float 和 Double 则由于有小数位，在某个范围的数值是非常多的，不太好进行缓存，所以这两个包装类没有做缓存，可以看到他们的 valueOf() 方法里面是直接通过 new 来创建对象的
//        public static Float valueOf(float f) {
//            return new Float(f);
//        }
//        public static Double valueOf(double d) {
//            return new Double(d);
//        }

        Float float1 = 1f;
        Float float2 = 1f;
        Double double1 = 1d;
        Double double2 = 1d;
        // float1 == float2: false, double1 == double2: false
        System.out.println(String.format("float1 == float2: %s, double1 == double2: %s", float1 == float2, double1 == double2));
    }
}
