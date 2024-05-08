package cn.foolishbird.agency.lock.core.generator;

import cn.foolishbird.agency.lock.core.KeyGenerator;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author foolishbird
 */
public class MethodArgumentKeyGenerator implements KeyGenerator<String> {

    @Override
    public String keyGenerator(String key, Object target, Method method, Object... params) {
        if (Objects.isNull(method)) {
            throw new NullPointerException("method is null");
        }

        StringBuilder keyStr = new StringBuilder();
        keyStr.append(method.getDeclaringClass().getName())
                .append("#")
                .append(method.getName());

        if (null != params) {
            for (Object obj : params) {
                keyStr.append("_").append(obj.toString());
            }
        }
        return keyStr.toString();
    }
}
