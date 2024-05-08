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
import java.util.concurrent.locks.ReentrantLock;

import cn.foolishbird.agency.lock.core.AgencyLock;
import cn.foolishbird.agency.lock.core.AgencyLockManger;

/**
 * @author foolishbird
 */
public class ReentrantAgencyLockManager implements AgencyLockManger {

    /**
     * 锁
     */
    private volatile WeakConcurrentMap<String, AgencyLock> lockCache = new WeakConcurrentMap<>();

    @Override
    public AgencyLock getLock(String key) throws Exception {
        if (Objects.isNull(key)) {
            throw new NullPointerException("generatorKey is null");
        }

        AgencyLock lock = lockCache.getIfPresent(key);

        // 初始化锁
        if (null == lock) {
            synchronized (lockCache) {
                lock = new JDKReentrantAgencyLock(new ReentrantLock());
                lockCache.putIfProbablyAbsent(key, lock);
            }
        }
        return lock;
    }

    @Override
    public void removeLock(String key) throws Exception {

    }
}
