package cn.foolishbird.agency.lock.spring.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 禁止重入锁
 *
 * @author: foolish bird
 * @date: 2023/4/4
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * alias for key
     *
     * @return
     */
    String value() default "";

    /**
     * key
     *
     * @return
     */
    String key() default "";

    /**
     * key生成策略
     *
     * @return
     */
    String keyGenerator() default "";

    /**
     * 加锁时间，超过该时长自动解锁，默认单位为：秒
     *
     */
    long leaseTime();

    /**
     * 锁时长单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
