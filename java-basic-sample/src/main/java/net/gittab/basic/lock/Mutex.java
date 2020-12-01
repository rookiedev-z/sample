package net.gittab.basic.lock;

import java.util.Objects;

/**
 * Mutex.
 *
 * @author rookiedev 2020/10/25 09:50
 **/
public class Mutex<T> {

    private final T key;

    public Mutex(T key) {
        this.key = key;
    }

    public static <T> Mutex<T> of(T key) {
        return new Mutex<>(key);
    }

    public T getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mutex<?> xMutex = (Mutex<?>) o;
        return Objects.equals(key, xMutex.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

}
