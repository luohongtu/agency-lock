package cn.foolishbird.agency.lock.spring.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 禁止重入锁
 *
 * @author: foolishbird
 * @date: 2023/4/4
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * alias for key
     *
     * @return key
     */
    String value() default "";

    /**
     * lock key
     *
     * @return lock key
     */
    String key() default "";

    /**
     * Gets the policy name of the key generation policy from the context object
     *
     * @return the policy name of the key generation policy
     */
    String keyGenerator() default "";

    /**
     * maximum time when the lock is automatically released
     *
     * @return maximum time
     */
    long leaseTime();

    /**
     * time unit
     *
     * @return time unit
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
