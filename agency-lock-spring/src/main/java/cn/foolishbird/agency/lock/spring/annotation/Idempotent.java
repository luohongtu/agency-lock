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
