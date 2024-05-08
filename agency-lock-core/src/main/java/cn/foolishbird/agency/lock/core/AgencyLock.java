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

package cn.foolishbird.agency.lock.core;

import java.util.concurrent.TimeUnit;

/**
 * 业务锁抽象接口
 *
 * @author foolishbird
 */
public interface AgencyLock {

    /**
     * Lock attempt once
     *
     * @return if true, the lock was successfully acquired
     */
    boolean tryLock();

    /**
     * Lock attempt once and set the lock release time automatically
     *
     * @param waitTime  obtaining the lock wait time
     * @param leaseTime maximum time when the lock is automatically released
     * @param timeUnit  unit of time
     * @return if true, the lock was successfully acquired
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException;

    /**
     * lock
     */
    void lock();

    /**
     * lock
     *
     * @param leaseTime maximum time when the lock is automatically released
     * @param timeUnit  unit of time
     */
    void lock(long leaseTime, TimeUnit timeUnit);

    /**
     * release local
     */
    void unlock();
}
