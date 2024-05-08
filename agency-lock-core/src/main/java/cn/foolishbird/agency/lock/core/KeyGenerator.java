package cn.foolishbird.agency.lock.core;

import java.lang.reflect.Method;

/**
 * @author foolish bird
 * @date 2020-07-13
 */
@FunctionalInterface
public interface KeyGenerator<R> {

    /**
     * key生成策略
     * @param target
     * @param method
     * @param params
     * @return
     */
    R keyGenerator(String key, Object target, Method method, Object... params);

}
