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

package cn.foolishbird.agency.lock.spring.generator;

import cn.foolishbird.agency.lock.core.KeyGenerator;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author foolishbird
 */
public class SpringELMethonNameKeyGenerator implements KeyGenerator<String> {

    @Override
    public String keyGenerator(String key, Object target, Method method, Object... params) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("spring el, key is not null");
        }

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);

        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setRootObject(method);

        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);

        for (int i = 0; i < params.length; i++) {
            context.setVariable(parameterNames[i], params[i]);
        }
        StringBuilder keyStr = new StringBuilder();
        keyStr.append(method.getDeclaringClass().getName())
                .append("#")
                .append(method.getName())
                .append(":")
                .append(expression.getValue(context, String.class));
        return keyStr.toString();
    }

}
