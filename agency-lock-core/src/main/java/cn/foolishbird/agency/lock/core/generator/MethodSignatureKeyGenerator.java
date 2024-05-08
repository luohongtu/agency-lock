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

package cn.foolishbird.agency.lock.core.generator;

import java.lang.reflect.Method;
import java.util.Objects;

import cn.foolishbird.agency.lock.core.KeyGenerator;

/**
 * @author foolishbird
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
