package cn.foolishbird.agency.lock.spring.redisson;

import cn.foolishbird.agency.lock.core.AgencyLock;
import org.redisson.api.RLock;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author: foolish bird
 * @date: 2023/1/11
 */
public class RedissonReentrantAgencyLock implements AgencyLock {

    private RLock lock;

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
