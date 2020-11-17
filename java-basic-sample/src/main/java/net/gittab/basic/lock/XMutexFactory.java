package net.gittab.basic.lock;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * XMutexFactory.
 *
 * @author rookiedev 2020/10/25 09:46
 **/
public class XMutexFactory<KeyT> {

    public final Map<XMutex<KeyT>, WeakReference<XMutex<KeyT>>> weakHashMap =
            Collections.synchronizedMap(new WeakHashMap<>());

    public XMutex<KeyT> getMutex(KeyT key) {
        // validateKey(key);
        return getExist(key)
                .orElseGet(() -> saveNewReference(key));
    }

    private Optional<XMutex<KeyT>> getExist(KeyT key) {
        return Optional.ofNullable(weakHashMap.get(XMutex.of(key)))
                .map(WeakReference::get);
    }

    private XMutex<KeyT> saveNewReference(KeyT key) {

        XMutex<KeyT> mutex = XMutex.of(key);

        WeakReference<XMutex<KeyT>> res = weakHashMap.put(mutex, new WeakReference<>(mutex));
        if (res != null && res.get() != null) {
            return res.get();
        }
        return mutex;
    }
}
