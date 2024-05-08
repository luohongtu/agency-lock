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

/**
 * @author foolishbird
 */
public interface AgencyLockManger {

    /**
     * obtains the proxy lock based on the lock key
     *
     * @param key Lock key
     * @return AgencyLock  AgencyLock
     * @throws Exception Failure to obtain the lock reported an error
     */
    AgencyLock getLock(String key) throws Exception;

    /**
     * Remove a lock from the lock manager
     *
     * @param key Lock key
     * @throws Exception Failure to remove the lock reported an Exception
     */
    void removeLock(String key) throws Exception;

}
