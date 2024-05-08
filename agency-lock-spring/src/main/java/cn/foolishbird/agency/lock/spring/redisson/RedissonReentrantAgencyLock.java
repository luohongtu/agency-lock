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

package cn.foolishbird.agency.lock.spring.redisson;

import cn.foolishbird.agency.lock.core.AgencyLock;
import org.redisson.api.RLock;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author foolishbird
 */
public class RedissonReentrantAgencyLock implements AgencyLock {

    private final RLock lock;

    public RedissonReentrantAgencyLock(RLock lock) {
        if (Objects.isNull(lock)) {
            throw new NullPointerException("RLock is null");
        }
        this.lock = lock;
    }

    @Override
    public boolean tryLock() {
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        return lock.tryLock(waitTime, leaseTime, timeUnit);
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void lock(long leaseTime, TimeUnit timeUnit) {
        lock.lock(leaseTime, timeUnit);
    }

    @Override
    public void unlock() {
        lock.unlock();
    }
}
