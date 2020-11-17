package net.gittab.basic.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * ReferenceTest.
 *
 * @author rookiedev 2020/10/25 22:11
 **/
public class ReferenceTest {

    public static void main(String[] args) throws InterruptedException {
        // vm args: -Xms3m -Xmx3m -Xlog:gc*
        // strong reference
        ReferenceObject referenceObject1 = new ReferenceObject();
        ReferenceObject referenceObject2 = referenceObject1;

        referenceObject1 = null;
        referenceObject2 = null;

        System.gc();
        System.out.println("=========strong reference gc===========");
        // 由于第一行创建的对象实例已经没有引用指向该实例了，在 gc 之后将会回收该实例所在的内存空间


        // weak reference
        String weak = new String("weak");
        WeakReference<String> weakReference = new WeakReference<>(weak);

        weak = null;
        System.out.println(weakReference.get());
        System.gc();
        // 在经过一次 gc 标记之后，弱引用所在的内存就已经被回收了，
        System.out.println("==========weak reference gc1==========");
        System.out.println(weakReference.get() == null);

        String weakKey = new String("weakKey");
        WeakHashMap<String, Boolean> weakHashMap = new WeakHashMap<>();
        weakHashMap.put(weakKey, true);
        weakKey = null;
        System.gc();
        System.out.println("==========weak reference gc2==========");
        // 在经过一次 gc 标记之后，弱引用所在的内存就已经被回收了，
        System.out.println(weakHashMap.isEmpty());

        // soft reference
        String soft = new String("soft");
        SoftReference<String> softReference = new SoftReference<>(soft);

        soft = null;
        // System.out.println(softReference.get());
        System.gc();
        System.out.println("==========soft reference gc==========");

        System.out.println(softReference.get() == null);

//        List<String> list = new ArrayList<>();
//        while (true){
//            String string = new String("123");
//            list.add(string);
//            // 只有当 JVM 发现堆中内存不够用了，快要发生 OOM 时才会去回收软引用
//            if(softReference.get() == null){
//                System.out.println(softReference.get() == null);
//                System.out.println("==========soft reference gc==========");
//                break;
//            }
//        }

        // phantom reference
        ReferenceObject referenceObject = new ReferenceObject();
        ReferenceQueue<ReferenceObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<ReferenceObject> phantomReference = new PhantomReference<>(referenceObject, referenceQueue);

        System.out.println(phantomReference.get());

        referenceObject = null;
        System.gc();
//        System.runFinalization();
//        System.gc();
        System.out.println("==========phantom reference gc==========");

        // TimeUnit.SECONDS.sleep(1);

//        while (true){
////            String string = new String("123");
////            list.add(string);
//            if(phantomReference.isEnqueued()){
//                System.out.println(phantomReference.isEnqueued());
//                // System.out.println(Objects.equals(referenceQueue.poll(), phantomReference));
//                System.out.println("==========phantom reference gc==========");
//                break;
//            }
//        }

//        Reference ref = referenceQueue.remove(10000L);
//        if (ref != null) {
//            System.out.println(phantomReference.isEnqueued());
//            System.out.println(Objects.equals(referenceQueue.poll(), phantomReference));
//        }

        System.out.println(phantomReference.isEnqueued());
        System.out.println(referenceQueue.poll() == phantomReference);



    }
}
