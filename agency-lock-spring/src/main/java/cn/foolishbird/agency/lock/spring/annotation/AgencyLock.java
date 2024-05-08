package cn.foolishbird.agency.lock.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import org.springframework.core.annotation.AliasFor;

/**
 * @author foolish bird
 * @date 2023/1/8
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AgencyLock {

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
     * @return
     */
    long leaseTime() default -1;

    /**
     * 锁时长单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}
