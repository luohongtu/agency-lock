package cn.foolishbird.agency.lock.core.jdk;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;

import cn.foolishbird.agency.lock.core.generator.MethodSignatureKeyGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * MethodKeyGenerator Tester.
 *
 * @author foolish-bird
 * @version 1.0
 * @since 1月 8, 2023
 */
public class MethodsignatureKeyGeneratorTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: keyGenerator()
     */
    @Test
    public void testKeyGenerator() throws Exception {
        Method method = HashMap.class.getMethod("put", Object.class, Object.class);
        MethodSignatureKeyGenerator generator = new MethodSignatureKeyGenerator();
        String key = generator.keyGenerator(new HashMap<>(), method, 12323, 2323);
        Assert.assertTrue(Objects.nonNull(key));
        System.out.println("生成的MethodKey:" + key);
    }

}
