/*
 * Copyright 2012-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.foolishbird.agency.lock.core.jdk;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author foolishbird
 */
public class WeakConcurrentMap<K, V> extends ReferenceQueue<K> {

    /**
     * cache
     */
    final ConcurrentMap<WeakKey<K>, V> cacheMap = new ConcurrentHashMap<>();

    public WeakConcurrentMap() {
    }

    /**
     * @param key key
     * @return V
     */
    public V getIfPresent(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        } else {
            this.expungeStaleEntries();
            return this.cacheMap.get(key);
        }
    }

    /**
     * @param key   key
     * @param value value
     * @return value
     */
    public V putIfProbablyAbsent(K key, V value) {
        if (key == null) {
            throw new NullPointerException("key == null");
        } else if (value == null) {
            throw new NullPointerException("value == null");
        } else {
            this.expungeStaleEntries();
            return (V) this.cacheMap.putIfAbsent(new WeakKey(key, this), value);
        }
    }

    /**
     * @param key key
     * @return V  The value that was removed.
     */
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        } else {
            this.expungeStaleEntries();
            return this.cacheMap.remove(key);
        }
    }

    protected void expungeStaleEntries() {
        Reference reference;
        while ((reference = this.poll()) != null) {
            this.removeStaleEntry(reference);
        }

    }

    /**
     * @param reference
     * @return
     */
    protected V removeStaleEntry(Reference<?> reference) {
        return this.cacheMap.remove(reference);
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static boolean equal(Object a, Object b) {
        return Objects.equals(a, b);
    }

    @Override
    public String toString() {
        Class<?> thisClass;
        for (thisClass = this.getClass(); thisClass.getSimpleName().isEmpty(); thisClass = thisClass.getSuperclass()) {
        }

        this.expungeStaleEntries();
        return thisClass.getSimpleName() + this.cacheMap.keySet();
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

                return WeakConcurrentMap.equal(this.get(), ((WeakReference) o).get());
            }
        }
    }
}
