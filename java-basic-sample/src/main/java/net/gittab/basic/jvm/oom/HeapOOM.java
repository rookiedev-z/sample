package net.gittab.basic.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * HeapOOM.
 *
 * @author rookiedev 2020/10/13 21:46
 **/
public class HeapOOM {

    static class OOMObject{

    }

    public static void main(String[] args) {
        // -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
        List<OOMObject> oomList = new ArrayList<>();
        while(true){
            oomList.add(new OOMObject());
        }
    }
}
