package cn.foolishbird.agency.lock.spring.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * This is a comment, annotated above the method, the function of the method is to idempotent restriction of the result
 * of the operation of the method, called once and multiple times in a certain time resulting in the same business result
 *
 * @author foolishbird
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
