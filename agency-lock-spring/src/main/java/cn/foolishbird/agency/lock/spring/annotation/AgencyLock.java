package cn.foolishbird.agency.lock.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 *This is a comment that is annotated above the method and is used to execute the method once at the same time
 *
 * @author foolishbird
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AgencyLock {

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
    long leaseTime() default -1;

    /**
     * time unit
     *
     * @return time unit
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
