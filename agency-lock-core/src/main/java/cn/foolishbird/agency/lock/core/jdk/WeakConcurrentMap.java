package cn.foolishbird.agency.lock.core.jdk;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author foolish-bird
 * @date 2023/01/08
 */
public class WeakConcurrentMap<K, V> extends ReferenceQueue<K> {

    final ConcurrentMap<WeakKey<K>, V> target = new ConcurrentHashMap();

    public WeakConcurrentMap() {
    }

    public V getIfPresent(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        } else {
            this.expungeStaleEntries();
            return this.target.get(key);
        }
    }

    public V putIfProbablyAbsent(K key, V value) {
        if (key == null) {
            throw new NullPointerException("key == null");
        } else if (value == null) {
            throw new NullPointerException("value == null");
        } else {
            this.expungeStaleEntries();
            return (V)this.target.putIfAbsent(new WeakKey(key, this), value);
        }
    }

    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        } else {
            this.expungeStaleEntries();
            return this.target.remove(key);
        }
    }

    protected void expungeStaleEntries() {
        Reference reference;
        while ((reference = this.poll()) != null) {
            this.removeStaleEntry(reference);
        }

    }

    protected V removeStaleEntry(Reference<?> reference) {
        return this.target.remove(reference);
    }

    public String toString() {
        Class thisClass;
        for (thisClass = this.getClass(); thisClass.getSimpleName().isEmpty(); thisClass = thisClass.getSuperclass()) {
        }

        this.expungeStaleEntries();
        return thisClass.getSimpleName() + this.target.keySet();
    }

    static boolean equal(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    static final class WeakKey<T> extends WeakReference<T> {
        final int hashCode;

        WeakKey(T key, ReferenceQueue<? super T> queue) {
            super(key, queue);
            this.hashCode = key.hashCode();
        }

        public int hashCode() {
            return this.hashCode;
        }

        public String toString() {
            T value = this.get();
            return value != null ? value.toString() : "ClearedReference()";
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else {
                assert o instanceof WeakReference : "Bug: unexpected input to equals";

                return WeakConcurrentMap.equal(this.get(), ((WeakReference)o).get());
            }
        }
    }
}
