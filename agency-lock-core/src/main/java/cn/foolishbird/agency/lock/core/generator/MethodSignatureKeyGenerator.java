package cn.foolishbird.agency.lock.core.generator;

import java.lang.reflect.Method;
import java.util.Objects;

import cn.foolishbird.agency.lock.core.KeyGenerator;

/**
 * @author foolish bird
 * @date 2023/1/8
 */
public class MethodSignatureKeyGenerator implements KeyGenerator<String> {

    @Override
    public String keyGenerator(String key, Object target, Method method, Object... params) {
        if (Objects.isNull(method)) {
            throw new NullPointerException("method is null");
        }

        StringBuilder keyStr = new StringBuilder();
        keyStr.append(method.getDeclaringClass().getName())
                .append("#")
                .append(method.getName());

        for (Class<?> clz : method.getParameterTypes()) {
            keyStr.append("_").append(clz.getName());
        }
        return keyStr.toString();
    }
}
