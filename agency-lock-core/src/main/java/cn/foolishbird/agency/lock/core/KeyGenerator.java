package cn.foolishbird.agency.lock.core;

import java.lang.reflect.Method;

/**
 * @author foolishbird
 */
@FunctionalInterface
public interface KeyGenerator<R> {

    /**
     * Lock key generation policy
     *
     * @param key    key
     * @param target lock the object of the method
     * @param method lock method
     * @param params method args
     * @return lock key
     */
    R keyGenerator(String key, Object target, Method method, Object... params);

}
