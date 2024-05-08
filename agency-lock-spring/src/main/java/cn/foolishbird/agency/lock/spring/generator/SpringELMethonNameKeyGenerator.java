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
 * spring el表达式
 *
 * @author foolish bird
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
