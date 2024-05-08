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
