package cn.foolishbird.agency.lock.core.jdk;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import cn.foolishbird.agency.lock.core.AgencyLock;

/**
 * @author foolish bird
 * @version 1.0
 * @date 2020-01-24
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
