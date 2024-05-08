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

package cn.foolishbird.agency.lock.core.jdk;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import cn.foolishbird.agency.lock.core.AgencyLock;

/**
 * @author foolishbird
 */
public class JDKReentrantAgencyLock implements AgencyLock {

    protected Lock lock;

    public JDKReentrantAgencyLock(Lock lock) {
        if (Objects.isNull(lock)) {
            throw new NullPointerException("lock is null");
        }
        this.lock = lock;
    }

    @Override
    public boolean tryLock() {
        return this.lock.tryLock();
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        return this.lock.tryLock(waitTime, timeUnit);
    }

    @Override
    public void lock() {
        this.lock.lock();
    }

    @Override
    public void lock(long leaseTime, TimeUnit timeUnit) {
        this.lock.lock();
    }

    @Override
    public void unlock() {
        this.lock.unlock();
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
